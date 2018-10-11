package com.example.muas.cataloguemovie.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.muas.cataloguemovie.Model.MovieItems;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.muas.cataloguemovie.Database.DatabaseContract.FilmColumns.DESKRIPSI;
import static com.example.muas.cataloguemovie.Database.DatabaseContract.FilmColumns.FAVORITE_FILM;
import static com.example.muas.cataloguemovie.Database.DatabaseContract.FilmColumns.JUDUL;
import static com.example.muas.cataloguemovie.Database.DatabaseContract.FilmColumns.RELEASE;
import static com.example.muas.cataloguemovie.Database.DatabaseContract.FilmColumns.URL_POSTER;

/**
 * Created by user on 3/5/2018.
 */

public class FavoriteFilmHelper {

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

    public void close() {
        dataBaseHelper.close();
    }

    public ArrayList<MovieItems> getAllDataEng() {
        ArrayList<MovieItems> arrayList = new ArrayList<MovieItems>();
        Cursor cursor = database.query(FAVORITE_FILM,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();

        MovieItems filmModel;
        if (cursor.getCount() > 0) {
            do {
                filmModel = new MovieItems();
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

    public long insert(MovieItems items) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(JUDUL, items.getOriginal_title());
        contentValues.put(DESKRIPSI, items.getOverview());
        contentValues.put(RELEASE, items.getRelease_date());
        contentValues.put(URL_POSTER, items.getPoster_path());
        return database.insert(FAVORITE_FILM, null, contentValues);
    }

    public int update(MovieItems items) {
        ContentValues values = new ContentValues();
        values.put(JUDUL, items.getOriginal_title());
        values.put(DESKRIPSI, items.getOverview());
        values.put(RELEASE, items.getRelease_date());
        values.put(URL_POSTER, items.getPoster_path());
        return database.update(FAVORITE_FILM, values, _ID + "= '" + items.getId()
                + "'", null);
    }

    public int delete(int id) {
        return database.delete(FAVORITE_FILM, _ID + "= '" + id + "'", null);
    }

    public Cursor queyByIdProvider(String id) {
        return database.query(FAVORITE_FILM, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(FAVORITE_FILM
                , null
                , null
                , null
                , null
                , null
                , _ID + " DESC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(FAVORITE_FILM, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(FAVORITE_FILM, values, _ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(FAVORITE_FILM, _ID + " = ?", new String[]{id});
    }


    /*public void beginTransaction(){
        database.beginTransaction();
    }

    public void setTransactionSuccess(){
        database.setTransactionSuccessful();
    }

    public void endTransaction(){
        database.endTransaction();
    }

    public void insertTransactionEng(MovieItems film){
        String sql = "INSERT INTO "+FAVORITE_FILM+" ("+JUDUL+", "+DESKRIPSI
                +", "+RELEASE+", "+URL_POSTER+") VALUES (?,?,?,?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, film.getOriginal_title());
        stmt.bindString(2, film.getOverview());
        stmt.bindString(3, film.getRelease_date());
        stmt.bindString(4, film.getRelease_date());
        stmt.execute();
        stmt.clearBindings();
    }

    public boolean checkData(String title){
        Cursor cursor = database.query(FAVORITE_FILM,null,JUDUL+" LIKE ?",new String[]{title},null,null,_ID + " ASC",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0){
            return true;
        } else {
            return false;
        }
    }

    public int updateEng(MovieItems film){
        ContentValues args = new ContentValues();
        args.put(JUDUL, film.getOriginal_title());
        args.put(DESKRIPSI, film.getOverview());
        args.put(RELEASE, film.getRelease_date());
        args.put(URL_POSTER, film.getPoster_path());
        return database.update(FAVORITE_FILM, args, _ID+"= '"+film.getId()+"'", null);
    }

    public int deleteEng(int id){
        return database.delete(FAVORITE_FILM, _ID+" = '"+id+"'", null);
    }

    public Cursor queryByIdProvider(String id){
        return database.query(FAVORITE_FILM, null,
                _ID+" = ?",
                new String[]{id},
                null,
                null,
                null,
                null);
    }



    public Cursor queryProvider(){
        return database.query(FAVORITE_FILM,
                null,
                null,
                null,
                null,
                null,
                _ID+" DESC");
    }

    public long insertProvider(ContentValues values){
        return database.insert(FAVORITE_FILM,null, values);
    }

    public int updateProvider(String id, ContentValues values){
        return database.update(FAVORITE_FILM, values, _ID+" =?", new String[]{id});
    }

    public int deleteProvider(String id){
        return database.delete(FAVORITE_FILM, _ID+" = ?", new String[]{id});
    }*/
}
