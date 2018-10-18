package com.example.muas.cataloguemovie.service;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.muas.cataloguemovie.BuildConfig;
import com.example.muas.cataloguemovie.Database.DatabaseContract;
import com.example.muas.cataloguemovie.Model.MovieItems;
import com.example.muas.cataloguemovie.R;
import com.example.muas.cataloguemovie.ui.activity.DetailMovieActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import cz.msebera.android.httpclient.Header;

public class ReminderReleaseReceiver extends BroadcastReceiver {

    private final int NOTIF_ID_RELEASE = 21;
    public List<MovieItems> listMovie = new ArrayList<>();

    public ReminderReleaseReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        getUpCommingMovie(context);
    }

    public void setReminder(Context context, String type, String time, String message) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReleaseReceiver.class);
        intent.putExtra(DatabaseContract.EXTRA_MESSAGE_RECIEVE, message);
        intent.putExtra(DatabaseContract.EXTRA_TYPE_RECIEVE, type);
        String timeArray[] = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        int requestCode = NOTIF_ID_RELEASE;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        Toast.makeText(context, R.string.reminderSave, Toast.LENGTH_SHORT).show();
    }

    public void cancelReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReleaseReceiver.class);
        int requestCode = NOTIF_ID_RELEASE;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(context, R.string.reminderCancel, Toast.LENGTH_SHORT).show();
    }

    private void getUpCommingMovie(final Context context) {

        /*Metode ini akan menjalankan proses pengambilan data secara synchronous*/
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<MovieItems> movieItems = new ArrayList<>();
        String url = BuildConfig.OPEN_UPCOMING_MOVIE_API_KEY;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        MovieItems movieItems1 = new MovieItems(movie);
                        movieItems1.getPoster_path();
                        movieItems1.getOriginal_title();
                        movieItems1.getOverview();
                        movieItems1.getRelease_date();
                        movieItems.add(movieItems1);
                    }

                    int index = new Random().nextInt(movieItems.size());

                    MovieItems item = movieItems.get(index);

                    int notifId = 200;

                    String title = movieItems.get(index).getOriginal_title();
                    String message = movieItems.get(index).getOverview();
                    showNotification(context, title, message, notifId, item);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //Jika response gagal maka , do nothing
            }
        });

    }

    private void showNotification(Context context, String title, String message, int notifId, MovieItems item) {
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent i = new Intent(context, DetailMovieActivity.class);
        i.putExtra("title", item.getOriginal_title());
        i.putExtra("poster_path", item.getPoster_path());
        i.putExtra("overview", item.getOverview());
        i.putExtra("release_date", item.getRelease_date());
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, i, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, message)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        notificationManagerCompat.notify(notifId, builder.build());
    }

}