package com.mad.iti.onthetable.ui.search.view;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.StringTokenizer;

public class CheckSearchBy implements Parcelable {
    public final static String category = "CATEGORY";
    public final static String country = "COUNTRY";
    public final static String ingredient = "INGREDIENT";
    private String name;
    private String type;

    public CheckSearchBy(){
        this.name = "";
        this.type = "";
    }

    public CheckSearchBy(String type , String name){
        this.type = type;
        this.name = name;
    }

    protected CheckSearchBy(Parcel in) {
        name = in.readString();
        type = in.readString();
    }

    public static final Creator<CheckSearchBy> CREATOR = new Creator<CheckSearchBy>() {
        @Override
        public CheckSearchBy createFromParcel(Parcel in) {
            return new CheckSearchBy(in);
        }

        @Override
        public CheckSearchBy[] newArray(int size) {
            return new CheckSearchBy[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(type);
    }
}
