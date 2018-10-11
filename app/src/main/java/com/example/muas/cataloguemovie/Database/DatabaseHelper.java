package com.example.muas.cataloguemovie.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.example.muas.cataloguemovie.Database.DatabaseContract.FilmColumns.DESKRIPSI;
import static com.example.muas.cataloguemovie.Database.DatabaseContract.FilmColumns.JUDUL;
import static com.example.muas.cataloguemovie.Database.DatabaseContract.FilmColumns.RELEASE;
import static com.example.muas.cataloguemovie.Database.DatabaseContract.FilmColumns.TABLE_FAVORITE_FILM;
import static com.example.muas.cataloguemovie.Database.DatabaseContract.FilmColumns.URL_POSTER;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "filmfavorit";

    private static final int DATABASE_VERSION = 1;

    public static String CREATE_TABLE_FAV_ENG = "create table "+TABLE_FAVORITE_FILM+
            " ("+_ID+" integer primary key autoincrement, " +
            JUDUL+" text not null, " +
            DESKRIPSI+" text not null, " +
            RELEASE+" text not null, " +
            URL_POSTER+" text not null);";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FAV_ENG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_FAVORITE_FILM);
        onCreate(db);
    }
}
