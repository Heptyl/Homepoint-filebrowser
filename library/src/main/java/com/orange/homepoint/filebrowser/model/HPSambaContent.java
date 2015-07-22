package com.orange.homepoint.filebrowser.model;

import com.google.gson.annotations.SerializedName;

/**
 * Exemple de json :
 "path": "/mount_point/directory/Kalimba.mp3",
 "objectId": "64$0$D",
 "title": "Kalimba",
 "class": "object.item.audioItem.musicTrack",
 "description": "Ninja Tune Records",
 "creator": "Mr. Scruff",
 "date": "2008-01-01",
 "artist": "Mr. Scruff",
 "album": "Ninja Tuna",
 "genre": "Electronic",
 "originalTrackNumber":1,
 "albumArtUri":"http://192.168.254.1:8200/AlbumArt/1 - 53.jpg",
 "size": 8414449,
 "duration": "0:05:48.060",
 "bitrate": 192000,
 "sampleFrequency": 44100,
 "nrAudioChannels": 2,
 "protocolInfo": "http - get:*:audio/mpeg",
 "streamUri": "http://192.168.254.1:8200/MediaItems/53.mp3"
*/
public class HPSambaContent {

    private String path;
    private String objectId;
    private String title;
    @SerializedName("class")
    private String classUpnp;
    private String description;
    private String creator;
    private String date;
    private String artist;
    private String album;
    private String genre;
    private int originalTrackNumber;
    private String albumArtUri;
    private String size;
    private String duration;
    private int bitrate;
    private int sampleFrequency;
    private int nrAudioChannels;
    private String resolution;
    private String protocolInfo;
    private String streamUri;
    private String thumbnailUri;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
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

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getThumbnailUri() {
        return thumbnailUri;
    }

    public void setThumbnailUri(String thumbnailUri) {
        this.thumbnailUri = thumbnailUri;
    }

    @Override
    public String toString() {
        return "SambaDevice{" +
                "path='" + path + '\'' +
                ", objectId='" + objectId + '\'' +
                ", title='" + title + '\'' +
                ", class='" + classUpnp + '\'' +
                ", description='" + description + '\'' +
                ", creator='" + creator + '\'' +
                ", date='" + date + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", genre='" + genre + '\'' +
                ", originalTrackNumber='" + originalTrackNumber + '\'' +
                ", albumArtUri='" + albumArtUri + '\'' +
                ", size='" + size + '\'' +
                ", resolution='" + resolution + '\'' +
                ", duration='" + duration + '\'' +
                ", bitrate='" + bitrate + '\'' +
                ", sampleFrequency='" + sampleFrequency + '\'' +
                ", nrAudioChannels='" + nrAudioChannels + '\'' +
                ", protocolInfo='" + protocolInfo + '\'' +
                ", streamUri='" + streamUri + '\'' +
                ", thumbnailUri='" + thumbnailUri + '\'' +
                '}';
    }

}
