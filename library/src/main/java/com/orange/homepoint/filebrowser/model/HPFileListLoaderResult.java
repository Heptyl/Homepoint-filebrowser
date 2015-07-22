package com.orange.homepoint.filebrowser.model;

import java.util.List;

/**
 * Result wrapper for loader result, able to send exception through loader results
 * Created by kqxc4803 on 13/08/2014.
 */
public class HPFileListLoaderResult<T extends HPFile> {

    private  List<T> result;
    private  Exception error;

    public HPFileListLoaderResult(List<T> result, Exception error) {
        this.result = result;
        this.error = error;
    }

    public List<T> getResult() {
        return result;
    }

    public Exception getError() {
        return error;
    }

    /**
     * @return true if this result wrap an exception
     */
    public boolean isSucceed() {
        return (error == null);
    }
}
