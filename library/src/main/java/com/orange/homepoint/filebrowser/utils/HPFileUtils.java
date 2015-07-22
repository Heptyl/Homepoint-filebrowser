package com.orange.homepoint.filebrowser.utils;

import android.webkit.MimeTypeMap;

import com.orange.homepoint.filebrowser.core.Logif;
import com.orange.homepoint.filebrowser.model.HPFile;

import java.util.ArrayList;

/**
 * Created by fwms7220 on 11/07/14.
 */
public class HPFileUtils {

    public static final String URL_SEPARATOR = "/";
    public static final String SMB_SEPARATOR = "/";
    private static final String TAG = "HPFileUtils";
    public static final int UNITS_SIZE = 5;


    public static String getMimeType(String filename) {

        MimeTypeMap myMime = MimeTypeMap.getSingleton();
        String fileExtension = HPFileUtils.getFileExtension(filename);

        if(fileExtension == null) {
            Logif.v(TAG, "No extension found for this file (%s)", filename);
            return null;
        }

        String mimeType = myMime.getMimeTypeFromExtension(fileExtension);
        if(mimeType == null) {
            //Webkit TypeMime library can't find mime type
            // we try with our own @see HPFileMimeTypeUtils class
            mimeType = HPFileMimeTypeUtils.guessMimeTypeFromExtension(fileExtension);

            //really bad
            if(mimeType == null) {
                Logif.v(TAG, "No mime type found for this file (%s)", filename);
                return null;
            }
        }

        return mimeType;
    }

    public static String getFileExtension(String name) {
        if (name.indexOf("?")>-1) {
            name = name.substring(0, name.indexOf("?"));
        }
        if (name.lastIndexOf(".") == -1) {
            return null;
        } else {
            String ext = name.substring(name.lastIndexOf(".") + 1 );
            if (ext.indexOf("%")>-1) {
                ext = ext.substring(0,ext.indexOf("%"));
            }
            if (ext.indexOf("/")>-1) {
                ext = ext.substring(0,ext.indexOf("/"));
            }
            return ext.toLowerCase();
        }
    }

    public static String getFirstMimeType(String mimetype) {
        if(mimetype != null) {
            return mimetype.split(SMB_SEPARATOR)[0];
        } else {
            return null;
        }
    }

    public static String getSecondMimeType(String mimetype) {
        if(mimetype != null) {
            return mimetype.split(SMB_SEPARATOR)[1];
        } else {
            return null;
        }
    }

    public static String getFilenameFromPath(String path) {
        String[] pieceOfPath = path.split(SMB_SEPARATOR);
        return pieceOfPath[pieceOfPath.length-1];
    }

    public static int lookforPosition(String path, ArrayList<HPFile> files) {

        int position = 0;
        boolean found = false;
        int i = 0;
        while(!found && i<files.size()) {
            if(path.toLowerCase().equals(files.get(i).getPath().toLowerCase())) {
                position = i;
                found = true;
            }
            i++;
        }

        return position;
    }

    public static PreFormattedSize getFormattedSize(int size, int precision){
        // Format the size
        int pow = (int) Math.floor((size > 0 ? Math.log(size) : 0) / Math.log(1024));
        pow = Math.min(pow, UNITS_SIZE - 1);
        double value = (double) size / Math.pow(1024, pow);
        StringBuilder formatter = new StringBuilder();
        formatter.append("%.");
        formatter.append((pow == 0) ? 0 : precision);
        formatter.append("f");
        String decimal = String.format(formatter.toString(), value);
        return new PreFormattedSize(String.format("%s %%s", decimal), pow);
    }

    public static class PreFormattedSize{
        public String preFormattedString;
        public int pow;

        public PreFormattedSize(String preFormattedString, int pow) {
            this.preFormattedString = preFormattedString;
            this.pow = pow;
        }
    }

    public static String getNameFromUrl(String url) {
        String[] pieces = url.split(URL_SEPARATOR);
        return pieces[pieces.length-1];
    }

}
