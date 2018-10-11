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

public class MovieItems implements Parcelable {

    private int id;
    private String poster_path;
    private String original_title;
    private String overview;
    private String release_date;


    public MovieItems(JSONObject object) {

        try {
            SimpleDateFormat simpleDateFormat;
            /*ubah format DATE waktu rilis film dari API*/
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = simpleDateFormat.parse(object.getString("release_date"));

            simpleDateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
            String releasedate = simpleDateFormat.format(date1); //

            int id = object.getInt("id");
            String poster_path = object.getString("poster_path");
            String original_title = object.getString("original_title");
            String overview = object.getString("overview");
            String release_date = releasedate;


            this.id = id;
            this.poster_path = poster_path;
            this.original_title = original_title;
            this.overview = overview;
            this.release_date = release_date;

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    protected MovieItems(Parcel in) {
        id = in.readInt();
        poster_path = in.readString();
        original_title = in.readString();
        overview = in.readString();
        release_date = in.readString();
    }

    public static final Creator<MovieItems> CREATOR = new Creator<MovieItems>() {
        @Override
        public MovieItems createFromParcel(Parcel in) {
            return new MovieItems(in);
        }

        @Override
        public MovieItems[] newArray(int size) {
            return new MovieItems[size];
        }
    };

    public MovieItems() {
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


    public MovieItems(Cursor cursor){
        this.id = getColumnInt(cursor, _ID);
        this.original_title = getColumnString(cursor, DatabaseContract.FilmColumns.JUDUL);
        this.overview = getColumnString(cursor, DatabaseContract.FilmColumns.DESKRIPSI);
        this.release_date = getColumnString(cursor, DatabaseContract.FilmColumns.RELEASE);
        this.poster_path = getColumnString(cursor, DatabaseContract.FilmColumns.URL_POSTER);
    }
}
