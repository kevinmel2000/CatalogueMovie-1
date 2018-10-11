package com.example.muas.cataloguemovie.Utils;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import com.example.muas.cataloguemovie.BuildConfig;
import com.example.muas.cataloguemovie.Model.MovieItems;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MyAsyncTaskLoader extends AsyncTaskLoader<ArrayList<MovieItems>> {

    private ArrayList<MovieItems> mData;
    private boolean mHasResult = false;

    private String mKumpulanMovie;

    public MyAsyncTaskLoader(final Context context, String mKumpulanMovie) {
        super(context);
        onContentChanged();
        this.mKumpulanMovie = mKumpulanMovie;
    }

    /*API KEY dari
        www.themoviedb.org*/
    private static final String API_KEY = BuildConfig.OPEN_API_KEY;

    @Override
    public ArrayList<MovieItems> loadInBackground() {

        /*Metode ini akan menjalankan proses pengambilan data secara synchronous*/
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<MovieItems> movieItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" +
                API_KEY + "&language=en-US&query=" + mKumpulanMovie;

        client.get(url, new AsyncHttpResponseHandler() {
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
                        movieItems1.getPoster_path();
                        movieItems1.getOriginal_title();
                        movieItems1.getOverview();
                        movieItems1.getRelease_date();
                        movieItems.add(movieItems1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //Jika response gagal maka , do nothing
            }
        });

        return movieItems;
    }


    /*OnStartLoading() dipanggil setelah proses load berjalan*/
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
    public void deliverResult(final ArrayList<MovieItems> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
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
