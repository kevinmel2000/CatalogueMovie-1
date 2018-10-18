package com.example.filmfavoriteapp.ui.Fragment;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.filmfavoriteapp.Adapter.MovieCursorAdapter;
import com.example.filmfavoriteapp.Model.MovieCursorItems;
import com.example.filmfavoriteapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.filmfavoriteapp.db.DatabaseContract.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFilmFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    Unbinder unbinder;

    @BindView(R.id.lv_movies)
    ListView lvMovies;

    MovieCursorAdapter adapterFavorite;
    ArrayList<MovieCursorItems> movieFavoritesArrayList;

    private final int MOVIE_ID = 110;

    public FavoriteFilmFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_film, container, false);
        unbinder = ButterKnife.bind(this, view);

        lvMovies = view.findViewById(R.id.lv_movies);
        adapterFavorite = new MovieCursorAdapter(getContext(), null, true);
        lvMovies.setAdapter(adapterFavorite);

        getActivity().getSupportLoaderManager().initLoader(MOVIE_ID, null, this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().getSupportLoaderManager().destroyLoader(MOVIE_ID);
        /*unbinder.unbind();*/
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getSupportLoaderManager().restartLoader(MOVIE_ID, null, this);
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle bundle) {
        return new CursorLoader(getContext(), CONTENT_URI,
                null, null, null, null);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapterFavorite.swapCursor(data);
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapterFavorite.swapCursor(null);
    }


}
