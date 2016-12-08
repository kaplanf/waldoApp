package com.android.waldoapp.fragments;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.waldoapp.R;
import com.android.waldoapp.adapter.ImageListAdapter;
import com.android.waldoapp.interfaces.OnMainFragmentListener;
import com.android.waldoapp.rest.client.RestClient;
import com.android.waldoapp.rest.model.PhotoObject;
import com.android.waldoapp.rest.model.PhotoResponse;
import com.android.waldoapp.util.WaldoConstants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import static com.android.waldoapp.fragments.ImageListFragment.ListType.GRID;
import static com.android.waldoapp.fragments.ImageListFragment.ListType.LIST;

/**
 * Created by kaplanf on 07/12/2016.
 */

@EFragment(R.layout.imagelist_fragment)
public class ImageListFragment extends BaseFragment {

    public enum ListType {LIST, GRID}

    ListType listType;

    OnMainFragmentListener listener;

    @ViewById(R.id.galleryListViewSelect)
    RelativeLayout galleryListViewSelect;

    @ViewById(R.id.galleryGridViewSelect)
    RelativeLayout galleryGridViewSelect;

    @RestService
    RestClient restClient;

    @ViewById(R.id.recyclerView)
    RecyclerView recyclerView;

    ArrayList<PhotoObject> photoObjects;
    ArrayList<PhotoResponse.Records> recordsArraylist;

    private RecyclerView.LayoutManager layoutManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (OnMainFragmentListener) context;
    }

    @AfterViews
    protected void afterViews() {
        String queryString = getResources().getString(R.string.query_string);
        listType = LIST;
        getImages(queryString);
    }

    @Background
    void getImages(String queryString) {
        restClient.setHeader(HttpHeaders.COOKIE, getPreferences().getString(WaldoConstants.AUTH_COOKIE, ""));
        PhotoResponse photoResponse = null;
        try {
            photoResponse = restClient.getImages(queryString);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        } catch (HttpServerErrorException e) {
            e.printStackTrace();
        }

        recordsArraylist = new ArrayList<PhotoResponse.Records>();
        recordsArraylist = photoResponse.data.album.photos.recordList;

        initializeList(recordsArraylist, listType);

    }

    @Click({R.id.galleryListViewSelect, R.id.galleryGridViewSelect})
    void clickSelectView(View v) {
        switch (v.getId()) {
            case R.id.galleryListViewSelect:
                listType = LIST;
                initializeList(recordsArraylist, LIST);
                break;

            case R.id.galleryGridViewSelect:
                listType = GRID;
                initializeList(recordsArraylist, GRID);
                break;
        }
    }

    ArrayList<PhotoObject> prepareList(ListType listType, ArrayList<PhotoResponse.Records> records) {
        int size = records.size();
        ArrayList<PhotoObject> photos = new ArrayList<PhotoObject>();
        for (int a = 0; a < size; a++) {
            switch (listType) {
                case LIST:
                    photos.add(records.get(a).urls.get(5));
                    break;
                case GRID:
                    photos.add(records.get(a).urls.get(2));
                    break;
            }
        }
        return photos;
    }

    @UiThread
    protected void initializeList(ArrayList<PhotoResponse.Records> records, ListType listType) {
        switch (listType) {
            case LIST:
                layoutManager = new LinearLayoutManager(getActivity());
                break;
            case GRID:
                layoutManager = new GridLayoutManager(getActivity(), 2);
                break;
        }
        recyclerView.setLayoutManager(layoutManager);

        photoObjects = new ArrayList<PhotoObject>();
        photoObjects = prepareList(listType, records);

        ImageListAdapter adapter = new ImageListAdapter(photoObjects, getActivity(), listType);

        recyclerView.setAdapter(adapter);
    }
}
