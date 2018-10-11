package com.example.muas.cataloguemovie.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.muas.cataloguemovie.Model.FavoriteModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.muas.cataloguemovie.Database.DatabaseContract.FilmColumns.DESKRIPSI;
import static com.example.muas.cataloguemovie.Database.DatabaseContract.FilmColumns.JUDUL;
import static com.example.muas.cataloguemovie.Database.DatabaseContract.FilmColumns.RELEASE;
import static com.example.muas.cataloguemovie.Database.DatabaseContract.FilmColumns.TABLE_FAVORITE_FILM;
import static com.example.muas.cataloguemovie.Database.DatabaseContract.FilmColumns.URL_POSTER;

/**
 * Created by user on 3/5/2018.
 */

public class FavoriteFilmHelper {

    private static String DATABASE_TABLE = TABLE_FAVORITE_FILM;
    private Context context;
    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;

    public FavoriteFilmHelper(Context context) {
        this.context = context;
    }

    public FavoriteFilmHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public ArrayList<FavoriteModel> query() {
        ArrayList<FavoriteModel> arrayList = new ArrayList<FavoriteModel>();
        Cursor cursor = database.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();

        FavoriteModel filmModel;
        if (cursor.getCount() > 0) {
            do {
                filmModel = new FavoriteModel();
                filmModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                filmModel.setOriginal_title(cursor.getString(cursor.getColumnIndexOrThrow(JUDUL)));
                filmModel.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DESKRIPSI)));
                filmModel.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE)));
                filmModel.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow(URL_POSTER)));

                arrayList.add(filmModel);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public Cursor queyByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " DESC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}
