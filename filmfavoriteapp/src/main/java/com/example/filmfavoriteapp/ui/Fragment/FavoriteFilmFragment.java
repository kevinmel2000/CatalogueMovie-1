package com.example.filmfavoriteapp.ui.Fragment;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

    @BindView(R.id.recycler_favorite)
    RecyclerView recyclerView;

    MovieCursorAdapter adapter;
    ArrayList<MovieCursorItems> movieFavorites;

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

        adapter = new MovieCursorAdapter(getContext(), null, true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        getActivity().getSupportLoaderManager().initLoader(MOVIE_ID, null, this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getSupportLoaderManager().restartLoader(0, null, this);
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        movieFavorites = new ArrayList<>();
        return new CursorLoader(getContext(), CONTENT_URI,
                null, null, null, null);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        movieFavorites = getItem(data);
        for (MovieCursorItems m : movieFavorites) {
            getFavoriteMovies(m.getId());
            adapter.notifyDataSetChanged();
            Log.v("Matt1", "List"+movieFavorites.size());
        }
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        movieCursorAdapter.swapCursor(null);
    }


}
