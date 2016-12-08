package com.android.waldoapp.fragments;

import android.content.Context;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.waldoapp.R;
import com.android.waldoapp.interfaces.OnMainFragmentListener;
import com.android.waldoapp.util.WaldoConstants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by kaplanf on 07/12/2016.
 */

@EFragment(R.layout.webview_fragment)
public class WebViewFragment extends BaseFragment {

    OnMainFragmentListener listener;

    @ViewById(R.id.webView)
    WebView webView;

    private android.webkit.CookieManager webkitCookieManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (OnMainFragmentListener) context;
    }

    @AfterViews
    protected void afterViews() {
        webkitCookieManager = android.webkit.CookieManager.getInstance();

        webView.setWebViewClient(new CustomWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(NetworkConstants.AUTH_URL);
    }

    private class CustomWebViewClient extends WebViewClient {

        boolean isloaded = false;

        @Override
        public void onPageFinished(WebView view, String url) {

            if (!isloaded) {
                final String javascriptCommand = "javascript:" +
                        "document.getElementById('password').value = '1234';" +
                        "document.getElementById('username').value = 'andy';" +
                        "document.getElementsByTagName('input')[3].click();";

                view.loadUrl(javascriptCommand);

                String cookies = CookieManager.getInstance().getCookie(url);

                if (cookies != null) {
                    isloaded = true;
                    Map<String, List<String>> res = new java.util.HashMap<String, List<String>>();

                    String cookie = webkitCookieManager.getCookie(url);
                    getPreferences().edit().putString(WaldoConstants.AUTH_COOKIE, cookie).commit();

                    if (cookie != null) res.put("Cookie", Arrays.asList(cookie));
                    Log.d("MainActivity", "All the cookies in a string:" + cookies);
                    hideProgressDialog();
                    listener.toImageList();
                }
            }
        }
    }
}
