package com.orange.homepoint.filebrowser.core;

import android.util.Log;

/**
 * Created by kqxc4803 on 09/07/2014.
 */
public class Logif {
    protected final static String TAG_PREFIX = "homepoint:";

    protected static void log(int priority, String originalTag, String message, Object... args) {
        String customTag = TAG_PREFIX.concat(originalTag);
        if (args.length > 0) {
            message = String.format(message, args);
        }
        Log.println(priority, customTag, message);
    }

    public static void v(String tag, String message, Object... args) {
        log(Log.VERBOSE, tag, message, args);
    }

    public static void d(String tag, String message, Object... args) {
        log(Log.DEBUG, tag, message, args);
    }

    public static void i(String tag, String message, Object... args) {
        log(Log.INFO, tag, message, args);
    }

    public static void w(String tag, String message, Object... args) {
        log(Log.WARN, tag, message, args);
    }

    public static void e(String tag, String message, Object... args) {
        log(Log.ERROR, tag, message, args);
    }

}
