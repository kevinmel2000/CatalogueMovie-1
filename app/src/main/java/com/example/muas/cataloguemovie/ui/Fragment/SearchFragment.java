package com.example.muas.cataloguemovie.ui.Fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.muas.cataloguemovie.Adapter.MovieAdapter;
import com.example.muas.cataloguemovie.Model.MovieItems;
import com.example.muas.cataloguemovie.R;
import com.example.muas.cataloguemovie.Utils.MyAsyncTaskLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.muas.cataloguemovie.Database.DatabaseContract.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {

    static final String EXTRAS_MOVIE = "EXTRAS_MOVIE";
    MovieAdapter movieAdapter;
    @BindView(R.id.rv_search)
    RecyclerView rvSearch;
    Unbinder unbinder;

    private ArrayList<MovieItems> movieList;

    SharedPreferences sharedPreferences;
    public static final String PREFERENCE = "dataQuery";


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        unbinder = ButterKnife.bind(this, view);

        movieList = new ArrayList<>();

        rvSearch = (RecyclerView) view.findViewById(R.id.rv_search);

        rvSearch.setHasFixedSize(true);
        rvSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        movieAdapter = new MovieAdapter(getActivity());
        movieAdapter.setmData(movieList);
        rvSearch.setAdapter(movieAdapter);

        sharedPreferences = getContext().getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        if (sharedPreferences.contains("SPqueryCariMovie")) {
            String nilaiBmr = sharedPreferences.getString("SPqueryCariMovie", "");
            Log.d("ini nilainya", "onCreateView: " + nilaiBmr);
            Bundle bundle = new Bundle();
            bundle.putString(EXTRAS_MOVIE, nilaiBmr);

            /*menjalankan metode onCreateLoader()*/
            getLoaderManager().initLoader(0, bundle, this);
        }
        return view;
    }

    @NonNull
    @Override
    public Loader<ArrayList<MovieItems>> onCreateLoader(int id, @Nullable Bundle args) {
        String kumpulanMovie = "";
        if (args != null) {
            kumpulanMovie = args.getString(EXTRAS_MOVIE);
        }

        return new MyAsyncTaskLoader(getContext(), kumpulanMovie);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> data) {
        movieAdapter.setmData(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<MovieItems>> loader) {
        movieAdapter.setmData(null);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
