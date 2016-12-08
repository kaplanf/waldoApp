package com.android.waldoapp.rest.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kaplanf on 08/12/2016.
 */

public class PhotoResponse implements Serializable {

    @SerializedName("data")
    public Data data;

    public class Data implements Serializable {

        @SerializedName("album")
        public Album album;
    }

    public class Album implements Serializable {

        @SerializedName("id")
        public String albumId;

        @SerializedName("name")
        public String albumName;

        @SerializedName("photos")
        public Photos photos;
    }

    public class Photos implements Serializable {

        @SerializedName("records")
        public ArrayList<Records> recordList;
    }

    public class Records implements Serializable {

        @SerializedName("urls")
        public ArrayList<PhotoObject> urls;

        @SerializedName("id")
        public String recordsId;
    }
}
