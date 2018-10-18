package com.example.filmfavoriteapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.filmfavoriteapp.R;
import com.example.filmfavoriteapp.ui.activity.DetailMovieActivity;

import static com.example.filmfavoriteapp.db.DatabaseContract.FilmColumns.DESKRIPSI;
import static com.example.filmfavoriteapp.db.DatabaseContract.FilmColumns.JUDUL;
import static com.example.filmfavoriteapp.db.DatabaseContract.FilmColumns.RELEASE;
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
    public void bindView(final View view, final Context context, final Cursor cursor) {
        if (cursor != null) {
            TextView textViewTitle, textViewOverview, textViewRelease;
            ImageView imgPoster;
            CardView cardView;
            Button btnDetail, btnShare;

            final String loadPoster = "http://image.tmdb.org/t/p/w185" + getColumnString(cursor, URL_POSTER);

            textViewTitle = view.findViewById(R.id.tv_judulFilm);
            textViewOverview = view.findViewById(R.id.tv_deskFilm);
            textViewRelease = view.findViewById(R.id.tv_rilisFilm);
            imgPoster = view.findViewById(R.id.iv_gambarFilm);
            btnDetail = view.findViewById(R.id.btn_detail);
            btnShare = view.findViewById(R.id.btn_share);
            cardView = view.findViewById(R.id.card_view_detail);
            btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(view.getContext(), DetailMovieActivity.class);
                    i.putExtra("title", getColumnString(cursor, JUDUL));
                    i.putExtra("poster_path", loadPoster);
                    i.putExtra("overview", getColumnString(cursor, DESKRIPSI));
                    i.putExtra("release_date", getColumnString(cursor, RELEASE));
                    context.startActivity(i);
                }
            });

            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Tombol share clicked!", Toast.LENGTH_SHORT).show();
                }
            });

            textViewTitle.setText(getColumnString(cursor, JUDUL));
            textViewOverview.setText(getColumnString(cursor, DESKRIPSI));
            textViewRelease.setText(getColumnString(cursor, RELEASE));
            Glide.with(context).load(loadPoster)
                    .placeholder(R.drawable.ic_person_black_24dp)
                    .into(imgPoster);
        }


    }

}