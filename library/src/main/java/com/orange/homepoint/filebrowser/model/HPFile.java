package com.orange.homepoint.filebrowser.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.orange.homepoint.filebrowser.utils.HPFileTypeUtils;
import com.orange.homepoint.filebrowser.utils.HPFileUtils;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

/**
 * Created by fwms7220 on 30/09/2014.
 */
public class HPFile implements Parcelable {

    public  final static int THUMB_DOWNLOAD_TIMEOUT = 10000;

    final String spattern =  "[^/](/[^/].*)$";
    final Pattern pattern = Pattern.compile(spattern);

    private final static String sdurationPattern = "(0:)?(.*)\\..*";
    private final static Pattern durationPattern = Pattern.compile(sdurationPattern);

    public final static int HAS_DLNA_INFOS = 1;
    public final static int HAS_NOT_DLNA_INFOS = 0;
    public final static int HAS_REQUESTED_DLNA_INFOS = 2;

    // data from Samba
    private transient SmbFile smbFile = null;
    private HPShare hpShare = null;
    private String id;
    private String extension;
    private String mimeType;
    private String name;
    private String fullName;
    private String path;
    private String fullPath;
    private Boolean isDirectory;
    private int size;
    private HPFileUtils.PreFormattedSize formattedSize;
    private Date date;
    private String formattedDate;

    // data from DLNA/RestAPI
    private String objectId;
    private String title;
    private String classUpnp;
    private String description;
    private String creator;
    private String artist;
    private String album;
    private String genre;
    private int originalTrackNumber;
    private String albumArtUri;
    private String formattedDuration;
    private long duration;
    private String year;
    private int bitrate;
    private int sampleFrequency;
    private int nrAudioChannels;
    private String resolution;
    private String protocolInfo;
    private String streamUri;
    private String thumbnailUri;

    // common data
    private HPFileType type;
    private int hasDlnaInfos;


    public HPFile(SmbFile smbFile, HPShare hpShare) {
        this.hpShare = hpShare;
        this.smbFile = smbFile;

        this.fullName = this.smbFile.getName();

        try {
            this.isDirectory = this.smbFile.isDirectory();
        } catch(SmbException e) {
            e.printStackTrace();
            this.isDirectory = false;
        }

        //generate friendly name (without extension)
        String extension = isDirectory() ?  "/" : ".";
        int extensionPos = this.fullName.lastIndexOf(extension);
        if (extensionPos != -1) {
            this.name = this.fullName.substring(0, extensionPos);
        } else {
            this.name = this.fullName;
        }

        this.extension = HPFileUtils.getFileExtension(this.fullName);
        this.mimeType = HPFileUtils.getMimeType(this.fullName);
        this.size = this.smbFile.getContentLength();
        this.formattedSize = HPFileUtils.getFormattedSize(this.size, 0);

        this.date = new Date(this.smbFile.getDate());
        this.formattedDate = DateFormat.getDateInstance().format(this.date);

        this.fullPath = this.smbFile.getPath();
        Matcher m = pattern.matcher(this.fullPath);
        if(m.find()){
            this.path = m.group(1);
        } else {
            this.path = "";
        }

        this.id = this.fullPath;
        //this.type = HPFileType.NONE;
        this.type = HPFileTypeUtils.retrieveFileTypeFromSamba(HPFileUtils.getFirstMimeType(this.mimeType));
    }

    public void addHPSambaContent(HPSambaContent hpSambaContent) {
        this.hasDlnaInfos = HAS_DLNA_INFOS;

        this.artist = hpSambaContent.getArtist();
        this.albumArtUri = hpSambaContent.getAlbumArtUri();
        this.thumbnailUri = hpSambaContent.getThumbnailUri();

        // valorise le thumbnailUri s'il n'existe pas et qu'on dispose d'un AlbumArtUri
        if(this.albumArtUri != null && this.thumbnailUri == null) {
            this.thumbnailUri = this.albumArtUri;
        }

        this.album = hpSambaContent.getAlbum();
        this.nrAudioChannels = hpSambaContent.getNrAudioChannels();
        this.bitrate = hpSambaContent.getBitrate();
        this.creator = hpSambaContent.getCreator();
        this.formattedDuration = formatDuration(hpSambaContent.getDuration());
        this.duration = getDurationFromString(hpSambaContent.getDuration());
        this.year = getYear(hpSambaContent.getDate());
        this.description = hpSambaContent.getDescription();
        this.sampleFrequency = hpSambaContent.getSampleFrequency();
        this.genre = hpSambaContent.getGenre();
        this.objectId = hpSambaContent.getObjectId();
        this.protocolInfo = hpSambaContent.getProtocolInfo();
        this.originalTrackNumber = hpSambaContent.getOriginalTrackNumber();
        this.resolution = hpSambaContent.getResolution();
        this.streamUri = hpSambaContent.getStreamUri();
        this.title = hpSambaContent.getTitle();
        this.classUpnp = hpSambaContent.getClassUpnp();
        this.type = HPFileTypeUtils.retrieveFileTypeFromDlna(this.classUpnp);

        if(this.date == null && hpSambaContent.getDate() != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            this.formattedDate = hpSambaContent.getDate();
            try {
                this.date = formatter.parse(hpSambaContent.getDate());
                this.formattedDate = DateFormat.getDateInstance().format(this.date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if(this.size == 0 && hpSambaContent.getSize() != null) {
            this.size = Integer.parseInt(hpSambaContent.getSize());
            this.formattedSize = HPFileUtils.getFormattedSize(this.size, 0);
        }
    }

    protected String formatDuration(String duration){
        String newDuration = duration;
        if(duration !=null){
            Matcher matcher = durationPattern.matcher(duration);
            if(matcher.find() && matcher.groupCount()==2){
                newDuration = matcher.group(2);
            }
        }
        return newDuration;
    }

    protected long getDurationFromString(String duration) {
        long songDuration = 0;
        if (duration != null){
            String durations[] = duration.split(":");
            songDuration = TimeUnit.HOURS.toMillis(Long.parseLong(durations[0]))
                    + TimeUnit.MINUTES.toMillis(Long.parseLong(durations[1]));

            String durationMilli[] = durations[2].split("\\.");
            songDuration += (TimeUnit.SECONDS.toMillis(Long.parseLong(durationMilli[0]))
                    + Long.parseLong(durationMilli[1]));
        }
        return songDuration;
    }

    protected String getYear(String date){
        String year = date;
        if(date!=null){
            year = date.substring(0,4);
        }
        return year;
    }

    public int getForceOrder(){
        int val = HPFileTypeUtils.DIRECTORY_FORCE;
        if(!isDirectory()){
            val = getType().getForce();
        }
        return val;
    }

    // ex: ORANGE/
    public String getSambaDirectoryPath(){
        String directoryPath = getPath();
        if(!isDirectory){
            int lastSlash = directoryPath.lastIndexOf("/");
            // format is /ORANGE/
            directoryPath = directoryPath.substring(1, lastSlash+1);
        }
        return directoryPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public Boolean isDirectory() {
        return isDirectory;
    }

    public void isDirectory(Boolean isDirectory) {
        this.isDirectory = isDirectory;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClassUpnp() {
        return classUpnp;
    }

    public void setClassUpnp(String classUpnp) {
        this.classUpnp = classUpnp;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getOriginalTrackNumber() {
        return originalTrackNumber;
    }

    public void setOriginalTrackNumber(int originalTrackNumber) {
        this.originalTrackNumber = originalTrackNumber;
    }

    public String getAlbumArtUri() {
        return albumArtUri;
    }

    public void setAlbumArtUri(String albumArtUri) {
        this.albumArtUri = albumArtUri;
    }

    public String getFormattedDuration() {
        return formattedDuration;
    }

    public void setFormattedDuration(String formattedDuration) {
        this.formattedDuration = formattedDuration;
    }

    public int getBitrate() {
        return bitrate;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    public int getSampleFrequency() {
        return sampleFrequency;
    }

    public void setSampleFrequency(int sampleFrequency) {
        this.sampleFrequency = sampleFrequency;
    }

    public int getNrAudioChannels() {
        return nrAudioChannels;
    }

    public void setNrAudioChannels(int nrAudioChannels) {
        this.nrAudioChannels = nrAudioChannels;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getProtocolInfo() {
        return protocolInfo;
    }

    public void setProtocolInfo(String protocolInfo) {
        this.protocolInfo = protocolInfo;
    }

    public String getStreamUri() {
        return streamUri;
    }

    public void setStreamUri(String streamUri) {
        this.streamUri = streamUri;
    }

    public String getThumbnailUri() {
        return thumbnailUri;
    }

    public void setThumbnailUri(String thumbnailUri) {
        this.thumbnailUri = thumbnailUri;
    }

    public HPFileType getType() {
        return type;
    }

    public void setType(HPFileType type) {
        this.type = type;
    }

    public int hasDlnaInfos() {
        return hasDlnaInfos;
    }

    public void hasDlnaInfos(int hasDlnaInfos) {
        this.hasDlnaInfos = hasDlnaInfos;
    }

    public SmbFile getSmbFile() {
        return smbFile;
    }

    public void setSmbFile(SmbFile smbFile) {
        this.smbFile = smbFile;
    }

    public HPShare getHpShare() {
        return hpShare;
    }

    public void setHpShare(HPShare hpShare) {
        this.hpShare = hpShare;
    }

    public long getSize() {
        return this.size;
    }

    public Date getDate() {
        return date;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public HPFileUtils.PreFormattedSize getFormattedSize() {
        return formattedSize;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public int describeContents() {
        return 31;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeParcelable(hpShare, flags);
        dest.writeString(id);
        dest.writeString(extension);
        dest.writeString(mimeType);
        dest.writeString(name);
        dest.writeString(fullName);
        dest.writeString(path);
        dest.writeString(fullPath);
        dest.writeByte((byte) (isDirectory ? 1 : 0));
        dest.writeInt(size);
        dest.writeLong(date.getTime());
        dest.writeString(objectId);
        dest.writeString(title);
        dest.writeString(classUpnp);
        dest.writeString(description);
        dest.writeString(creator);
        dest.writeString(artist);
        dest.writeString(album);
        dest.writeString(genre);
        dest.writeInt(originalTrackNumber);
        dest.writeString(albumArtUri);
        dest.writeString(formattedDuration);
        dest.writeLong(duration);
        dest.writeString(year);
        dest.writeInt(bitrate);
        dest.writeInt(sampleFrequency);
        dest.writeInt(nrAudioChannels);
        dest.writeString(resolution);
        dest.writeString(protocolInfo);
        dest.writeString(streamUri);
        dest.writeString(thumbnailUri);
        dest.writeString(type.name());
        dest.writeInt(hasDlnaInfos);
    }

    public HPFile(Parcel in) {

        hpShare = in.readParcelable(HPShare.class.getClassLoader());
        id = in.readString();
        extension = in.readString();
        mimeType = in.readString();
        name = in.readString();
        fullName = in.readString();
        path = in.readString();
        fullPath = in.readString();
        isDirectory = in.readByte() != 0;
        size = in.readInt();
        date =  new Date(in.readLong());
        objectId = in.readString();
        title = in.readString();
        classUpnp = in.readString();
        description = in.readString();
        creator = in.readString();
        artist = in.readString();
        album = in.readString();
        genre = in.readString();
        originalTrackNumber = in.readInt();
        albumArtUri = in.readString();
        formattedDuration = in.readString();
        duration = in.readLong();
        year = in.readString();
        bitrate = in.readInt();
        sampleFrequency = in.readInt();
        nrAudioChannels = in.readInt();
        resolution = in.readString();
        protocolInfo = in.readString();
        streamUri = in.readString();
        thumbnailUri = in.readString();
        type = HPFileType.valueOf(in.readString());
        hasDlnaInfos = in.readInt();

        this.formattedDate = DateFormat.getDateInstance().format(this.date);
        this.formattedSize = HPFileUtils.getFormattedSize(this.size, 0);
    }

    public static final Parcelable.Creator<HPFile> CREATOR = new Parcelable.Creator<HPFile>() {

        @Override
        public HPFile createFromParcel(Parcel source) {
            return new HPFile(source);
        }

        @Override
        public HPFile[] newArray(int size) {
            return new HPFile[size];
        }
    };
}
