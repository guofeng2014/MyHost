package com.example.myhost;

import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import dalvik.system.DexClassLoader;

/**
 * create by guofeng
 * date on 2019-09-24
 */
public class PlugPackage implements Parcelable {

    public String packageName;

    public DexClassLoader classLoader;

    public String defaultActivity;

    public AssetManager assetManager;

    public Resources resources;

    public PackageInfo packageInfo;

    public PlugPackage() {
    }

    protected PlugPackage(Parcel in) {
        packageName = in.readString();
        defaultActivity = in.readString();
        packageInfo = in.readParcelable(PackageInfo.class.getClassLoader());
    }

    public static final Creator<PlugPackage> CREATOR = new Creator<PlugPackage>() {
        @Override
        public PlugPackage createFromParcel(Parcel in) {
            return new PlugPackage(in);
        }

        @Override
        public PlugPackage[] newArray(int size) {
            return new PlugPackage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(packageName);
        parcel.writeString(defaultActivity);
        parcel.writeParcelable(packageInfo, i);
    }
}
