package com.orange.homepoint.filebrowser.model;

import com.orange.homepoint.filebrowser.utils.HPFileTypeUtils;

/**
 * Created by kqxc4803 on 23/07/2014.
 */


public enum HPFileType {
    MEDIA_IMAGE(HPFileTypeUtils.PHOTO_FORCE), MEDIA_VIDEO(HPFileTypeUtils.VIDEO_FORCE), MEDIA_AUDIO(HPFileTypeUtils.AUDIO_FORCE), OTHER(HPFileTypeUtils.OTHER_FORCE);

    protected int force;

    private HPFileType(int force){
        this.force = force;
    }

    public int getForce() {
        return force;
    }
}









