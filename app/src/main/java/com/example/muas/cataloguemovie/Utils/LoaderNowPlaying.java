package com.example.muas.cataloguemovie.Utils;


import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.example.muas.cataloguemovie.BuildConfig;
import com.example.muas.cataloguemovie.Model.MovieItems;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


import cz.msebera.android.httpclient.Header;

public class LoaderNowPlaying extends AsyncTaskLoader<ArrayList<MovieItems>> {

    private ArrayList<MovieItems> mData;
    private boolean mHasResult = false;

    public LoaderNowPlaying(Context context) {
        super(context);
        onContentChanged();
    }

    @Override
    public ArrayList<MovieItems> loadInBackground() {
        SyncHttpClient syncHttpClient = new SyncHttpClient();

        final ArrayList<MovieItems> movieItemsArrayList = new ArrayList<>();
        String url = BuildConfig.OPEN_POPULAR_MOVIE_API_KEY;
        syncHttpClient.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        MovieItems movieItems1 = new MovieItems(movie);
                        movieItems1.getId();
                        Log.d("idfilm", "onSuccess: id" + movieItems1.getId());
                        movieItems1.getPoster_path();
                        movieItems1.getOriginal_title();
                        movieItems1.getOverview();
                        movieItems1.getRelease_date();
                        movieItemsArrayList.add(movieItems1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return movieItemsArrayList;
    }

    @Override
    public void deliverResult(ArrayList<MovieItems> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            /*Metode forceLoad() akan dijalankan ketika data belum tersedia*/
            forceLoad();
        else if (mHasResult)
            /*Metode deliverResult() digunakan untuk menampilkan result data yang sudah ada.
                Metode ini akan dijalankan juga ketika terjadi reset pada loader.*/
            deliverResult(mData);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            onReleaseResources(mData);
            mData = null;
            mHasResult = false;
        }
    }

    protected void onReleaseResources(ArrayList<MovieItems> data) {
        //nothing to do.
    }
}
