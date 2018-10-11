package com.example.muas.cataloguemovie.Provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.muas.cataloguemovie.Database.DatabaseContract;
import com.example.muas.cataloguemovie.Database.FavoriteFilmHelper;

import static com.example.muas.cataloguemovie.Database.DatabaseContract.AUTHORITY;
import static com.example.muas.cataloguemovie.Database.DatabaseContract.CONTENT_URI;
import static com.example.muas.cataloguemovie.Database.DatabaseContract.FilmColumns.FAVORITE_FILM;

/**
 * Created by user on 3/6/2018.
 */

public class FilmProvider extends ContentProvider {

    private static final int FILM = 1;
    private static final int FILM_ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static{
        sUriMatcher.addURI(AUTHORITY, FAVORITE_FILM, FILM);
        sUriMatcher.addURI(AUTHORITY, FAVORITE_FILM+ "/#", FILM_ID);
    }

    private FavoriteFilmHelper favoriteFilmHelper;

    @Override
    public boolean onCreate() {
        favoriteFilmHelper = new FavoriteFilmHelper(getContext());
        favoriteFilmHelper.open();
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)){
            case FILM:
                cursor = favoriteFilmHelper.queryProvider();
                break;
            case FILM_ID:
                cursor = favoriteFilmHelper.queyByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor!=null){
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        long added;

        switch (sUriMatcher.match(uri)){
            case FILM:
                added = favoriteFilmHelper.insertProvider(values);
                break;
            default:
                added = 0;
                break;
        }

        if (added>0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int deleted;
        switch (sUriMatcher.match(uri)){
            case FILM_ID:
                deleted = favoriteFilmHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        if (deleted>0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String s, String[] strings) {
        int updated;
        switch (sUriMatcher.match(uri)){
            case FILM_ID:
                updated = favoriteFilmHelper.updateProvider(uri.getLastPathSegment(), values);
                break;
            default:
                updated = 0;
                break;
        }

        if (updated>0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }
}