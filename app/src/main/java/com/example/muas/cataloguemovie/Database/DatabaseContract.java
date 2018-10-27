package com.example.muas.cataloguemovie.Database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.example.muas.cataloguemovie.Database.DatabaseContract.FilmColumns.TABLE_FAVORITE_FILM;


public class DatabaseContract {

    public static final String AUTHORITY = "com.example.muas.cataloguemovie";
    public static final String SCHEME = "content";

    public DatabaseContract() {
    }

    public static final class FilmColumns implements BaseColumns {

        public static String TABLE_FAVORITE_FILM = "favorite_film";

        public static String JUDUL = "judul";
        public static String DESKRIPSI = "deskripsi";
        public static String RELEASE = "rilis";
        public static String URL_POSTER = "poster";
    }

    public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_FAVORITE_FILM)
            .build();

    public static final String KEY_HEADER_UPCOMING_REMINDER = "upcomingReminder";
    public static final String KEY_HEADER_DAILY_REMINDER = "dailyReminder";
    public static final String KEY_FIELD_UPCOMING_REMINDER = "checkedUpcoming";
    public static final String KEY_FIELD_DAILY_REMINDER = "checkedDaily";
    public final static String PREF_NAME = "reminderPreferences";
    public final static String KEY_REMINDER_Daily = "DailyTime";
    public final static String KEY_REMINDER_MESSAGE_Release = "reminderMessageRelease";
    public final static String KEY_REMINDER_MESSAGE_Daily = "reminderMessageDaily";
    public static final String EXTRA_MESSAGE_PREF = "message";
    public static final String EXTRA_TYPE_PREF = "type";
    public static final String TYPE_REMINDER_PREF = "reminderAlarm";
    public static final String EXTRA_MESSAGE_RECIEVE = "messageRelease";
    public static final String EXTRA_TYPE_RECIEVE = "typeRelease";
    public static final String TYPE_REMINDER_RECIEVE = "reminderAlarmRelease";
    public static final String URL_IMAGE = "http://image.tmdb.org/t/p/w185/";

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
