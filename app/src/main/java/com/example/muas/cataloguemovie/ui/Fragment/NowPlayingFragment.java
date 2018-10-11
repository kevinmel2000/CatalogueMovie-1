package com.example.muas.cataloguemovie.ui.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.muas.cataloguemovie.Adapter.MovieAdapter;
import com.example.muas.cataloguemovie.Model.MovieItems;
import com.example.muas.cataloguemovie.R;
import com.example.muas.cataloguemovie.Utils.LoaderNowPlaying;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {


    @BindView(R.id.rv_now_playing)
    RecyclerView rvNowPlaying;

    MovieAdapter movieAdapter;
    private ArrayList<MovieItems> movieList;

    private String BASE_URL_NOW_PLAYING =
            "https://api.themoviedb.org/3/movie/now_playing?api_key=a91db70d304c21ebc5320b123953a915&language=en-US";

    Unbinder unbinder;

    public NowPlayingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        unbinder = ButterKnife.bind(this, view);

        movieList = new ArrayList<>();

        getLoaderManager().initLoader(0, null, this);
        rvNowPlaying = (RecyclerView) view.findViewById(R.id.rv_now_playing);

        rvNowPlaying.setHasFixedSize(true);
        rvNowPlaying.setLayoutManager(new LinearLayoutManager(getContext()));
        movieAdapter = new MovieAdapter(getActivity());
        movieAdapter.setmData(movieList);
        rvNowPlaying.setAdapter(movieAdapter);


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @NonNull
    @Override
    public Loader<ArrayList<MovieItems>> onCreateLoader(int id, @Nullable Bundle args) {
        return new LoaderNowPlaying(getContext());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> data) {
        movieAdapter.setmData(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<MovieItems>> loader) {
        movieAdapter.setmData(null);
    }
}
