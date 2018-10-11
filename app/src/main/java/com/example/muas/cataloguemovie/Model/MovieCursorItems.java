package com.example.muas.cataloguemovie.Model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.muas.cataloguemovie.Database.DatabaseContract;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.provider.BaseColumns._ID;
import static com.example.muas.cataloguemovie.Database.DatabaseContract.getColumnInt;
import static com.example.muas.cataloguemovie.Database.DatabaseContract.getColumnString;

public class MovieCursorItems implements Parcelable {

    private int id;
    private String poster_path;
    private String original_title;
    private String overview;
    private String release_date;



    protected MovieCursorItems(Parcel in) {
        id = in.readInt();
        poster_path = in.readString();
        original_title = in.readString();
        overview = in.readString();
        release_date = in.readString();
    }

    public static final Creator<MovieCursorItems> CREATOR = new Creator<MovieCursorItems>() {
        @Override
        public MovieCursorItems createFromParcel(Parcel in) {
            return new MovieCursorItems(in);
        }

        @Override
        public MovieCursorItems[] newArray(int size) {
            return new MovieCursorItems[size];
        }
    };

    public MovieCursorItems() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(poster_path);
        dest.writeString(original_title);
        dest.writeString(overview);
        dest.writeString(release_date);
    }


    public MovieCursorItems(Cursor cursor){
        this.id = getColumnInt(cursor, _ID);
        this.original_title = getColumnString(cursor, DatabaseContract.FilmColumns.JUDUL);
        this.overview = getColumnString(cursor, DatabaseContract.FilmColumns.DESKRIPSI);
        this.release_date = getColumnString(cursor, DatabaseContract.FilmColumns.RELEASE);
        this.poster_path = getColumnString(cursor, DatabaseContract.FilmColumns.URL_POSTER);
    }
}
