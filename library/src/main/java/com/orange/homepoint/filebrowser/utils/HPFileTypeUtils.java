package com.orange.homepoint.filebrowser.utils;

import com.orange.homepoint.filebrowser.model.HPFileType;

import java.util.Arrays;
import java.util.List;

/**
 * Created by kqxc4803 on 23/07/2014.
 */


public class HPFileTypeUtils {

    public static final int DIRECTORY_FORCE = 4;
    public static final int AUDIO_FORCE = 1;
    public static final int PHOTO_FORCE = 3;
    public static final int VIDEO_FORCE = 2;
    public static final int OTHER_FORCE = 0;

    //for upnp content type, see http://www.upnp.org/specs/av/UPnP-av-ContentDirectory-v1-Service.pdf (page 79)
    final static String DLNA_AUDIO = "item.audioItem.musicTrack";
    final static String DLNA_PHOTO = "item.imageItem.photo";
    final static String DLNA_VIDEO = "item.videoItem.movie";

    //for samba content type
    final static String SAMBA_AUDIO = "audio";
    final static String SAMBA_PHOTO = "image";
    final static String SAMBA_VIDEO = "video";

    public final static List<String> EXTENSION_OF_FILE_TO_DOWNLOAD = Arrays.asList("pdf",
                                                                                   //openoffice
                                                                                   "odb", "odf", "odg", "otg", "odi", "ods", "ots", "odt", "odm", "ott", "oth",
                                                                                   //microsoft office
                                                                                   "doc", "dot", "docx", "dotx", "xls", "xlt", "xlsx", "xltx", "ppt", "pot", "pps", "pptx", "potx", "ppsx",
                                                                                   //text files
                                                                                   "txt", "rtf", "text");

    public final static List<String> EXTENSION_OF_FILE_TO_SERVE = Arrays.asList("video", "audio");

    public static HPFileType retrieveFileTypeFromDlna(String classUpnp) {

        if(classUpnp == null) {
            return HPFileType.OTHER;
        }

        if(DLNA_AUDIO.contains(classUpnp)) {
            return HPFileType.MEDIA_AUDIO;
        } else if(DLNA_VIDEO.contains(classUpnp)) {
            return HPFileType.MEDIA_VIDEO;
        } else if(DLNA_PHOTO.contains(classUpnp)) {
            return HPFileType.MEDIA_IMAGE;
        } else {
            return HPFileType.OTHER;
        }
    }

    public static HPFileType retrieveFileTypeFromSamba(String firstMimeType) {

        if(firstMimeType == null) {
            return HPFileType.OTHER;
        }
        if(SAMBA_AUDIO.equals(firstMimeType)) {
            return HPFileType.MEDIA_AUDIO;
        } else if(SAMBA_VIDEO.contains(firstMimeType)) {
            return HPFileType.MEDIA_VIDEO;
        } else if(SAMBA_PHOTO.contains(firstMimeType)) {
            return HPFileType.MEDIA_IMAGE;
        } else {
            return HPFileType.OTHER;
        }
    }
}








