package com.orange.homepoint.filebrowser.model;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fwms7220 on 30/09/2014.
 */
public class HPShare implements Parcelable {

    private String ip;
    private String entryPoint;
    private String username;
    private String password;

    public HPShare(String ip, String entryPoint, String username, String password) {
        this.ip = ip;
        this.entryPoint = entryPoint;
        this.username = username;
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getEntryPoint() {
        return entryPoint;
    }

    public void setEntryPoint(String entryPoint) {
        this.entryPoint = entryPoint;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 4;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ip);
        dest.writeString(entryPoint);
        dest.writeString(username);
        dest.writeString(password);
    }

    public HPShare(Parcel in) {
        ip = in.readString();
        entryPoint = in.readString();
        username = in.readString();
        password = in.readString();
    }

    public static final Parcelable.Creator<HPShare> CREATOR = new Parcelable.Creator<HPShare>() {

        @Override
        public HPShare createFromParcel(Parcel source) {
            return new HPShare(source);
        }

        @Override
        public HPShare[] newArray(int size) {
            return new HPShare[size];
        }
    };
}
