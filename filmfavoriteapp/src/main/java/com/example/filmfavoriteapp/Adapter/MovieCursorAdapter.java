package com.example.filmfavoriteapp.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.filmfavoriteapp.R;

import static com.example.filmfavoriteapp.db.DatabaseContract.FilmColumns.DESKRIPSI;
import static com.example.filmfavoriteapp.db.DatabaseContract.FilmColumns.JUDUL;
import static com.example.filmfavoriteapp.db.DatabaseContract.FilmColumns.URL_POSTER;
import static com.example.filmfavoriteapp.db.DatabaseContract.getColumnString;

public class MovieCursorAdapter extends CursorAdapter {

    public MovieCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.movie_items, viewGroup, false);
    }


    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor != null){
            ImageView imageFilm = (ImageView) view.findViewById(R.id.iv_gambarFilm);
            TextView judulFilm = (TextView) view.findViewById(R.id.tv_judulFilm);
            TextView overview = (TextView) view.findViewById(R.id.tv_deskFilm);
            TextView release_date = (TextView) view.findViewById(R.id.tv_rilisFilm);


            judulFilm.setText(getColumnString(cursor,JUDUL));
            overview.setText(getColumnString(cursor,DESKRIPSI));
            release_date.setText(getColumnString(cursor,URL_POSTER));
        }
    }


}