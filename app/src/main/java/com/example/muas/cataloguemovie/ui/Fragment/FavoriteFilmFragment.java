package com.example.muas.cataloguemovie.ui.Fragment;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.muas.cataloguemovie.Adapter.FavoriteAdapter;
import com.example.muas.cataloguemovie.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.muas.cataloguemovie.Database.DatabaseContract.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFilmFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.rv_filmFavorit)
    RecyclerView rv_favorite;

    private Cursor listFavorite;
    private FavoriteAdapter adapter;

    public FavoriteFilmFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_film, container, false);
        unbinder = ButterKnife.bind(this, view);

        rv_favorite = (RecyclerView) view.findViewById(R.id.rv_filmFavorit);
        rv_favorite.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_favorite.setHasFixedSize(true);

        adapter = new FavoriteAdapter(getContext());
        adapter.setListFavorite(listFavorite);
        rv_favorite.setAdapter(adapter);

        new loadDataAsync().execute();


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
//        new loadDataAsync().execute();
    }

    private class loadDataAsync extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContext().getContentResolver().query(
                    CONTENT_URI,
                    null,
                    null,
                    null,
                    null
            );
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

            listFavorite = cursor;
            adapter.setListFavorite(listFavorite);
            adapter.notifyDataSetChanged();

            if (listFavorite.getCount() == 0) {
                showSnackbarMessage("Tidak ada data saat ini");
            }
        }
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(rv_favorite, message, Snackbar.LENGTH_SHORT).show();
    }

}
