package com.android.waldoapp.activity;

import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.waldoapp.R;
import com.android.waldoapp.fragments.ImageListFragment;
import com.android.waldoapp.fragments.ImageListFragment_;
import com.android.waldoapp.fragments.WebViewFragment;
import com.android.waldoapp.fragments.WebViewFragment_;
import com.android.waldoapp.interfaces.OnMainFragmentListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity implements OnMainFragmentListener {

    @AfterViews
    protected void afterViews() {
        toWebView();
    }

    @Override
    public void onCloseFragment(String tag) {
        System.out.println("Fragment Ended: " + tag);
    }

    @Override
    public void onStartFragment(String tag) {
        System.out.println("Fragment Started: " + tag);
    }

    @Override
    public void toImageList() {
        ImageListFragment imageListFragment = new ImageListFragment_();
        replaceFragment(R.id.content_frame, imageListFragment, false);
    }

    void toWebView() {
        WebViewFragment webViewFragment = new WebViewFragment_();
        replaceFragment(R.id.content_frame, webViewFragment, false);
    }

}
