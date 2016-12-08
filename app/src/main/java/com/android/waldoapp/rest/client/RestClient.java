package com.android.waldoapp.rest.client;

import com.android.waldoapp.rest.model.PhotoResponse;

import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.RequiresHeader;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.RestClientHeaders;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

/**
 * Created by kaplanf on 07/12/2016.
 */


@Rest(rootUrl = NetworkConstants.BASE_URL, converters = {GsonHttpMessageConverter.class, StringHttpMessageConverter.class})
public interface RestClient extends RestClientHeaders {

    @RequiresHeader("Cookie")
    @Post(NetworkConstants.PHOTO_URL)
    PhotoResponse getImages(@Path String query);

    @Override
    void setHeader(String name, String value);
}
