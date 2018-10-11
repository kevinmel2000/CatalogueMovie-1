package com.example.muas.cataloguemovie.Database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.example.muas.cataloguemovie.Database.DatabaseContract.FilmColumns.FAVORITE_FILM;

/**
 * Created by user on 3/5/2018.
 */

public class DatabaseContract {


    public static final class FilmColumns implements BaseColumns {

        public static String FAVORITE_FILM = "favorite_film";

        public static String JUDUL = "judul";
        public static String DESKRIPSI = "deskripsi";
        public static String RELEASE = "rilis";
        public static String URL_POSTER = "poster";
    }

    public static final String AUTHORITY = "com.example.muas.cataloguemovie";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(FAVORITE_FILM)
            .build();

    public static String getColumnString(Cursor cursor, String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName){
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName){
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
