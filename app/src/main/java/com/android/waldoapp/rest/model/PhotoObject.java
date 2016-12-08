package com.android.waldoapp.rest.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by kaplanf on 08/12/2016.
 */

public class PhotoObject implements Serializable {

    @SerializedName("size_code")
    public String sizeCode;

    @SerializedName("url")
    public String url;

    @SerializedName("width")
    public int width;

    @SerializedName("height")
    public int height;

    @SerializedName("quality")
    public float quality;
}
