package com.example.muas.cataloguemovie.ui.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.muas.cataloguemovie.Database.DatabaseContract;
import com.example.muas.cataloguemovie.R;
import com.flaviofaria.kenburnsview.KenBurnsView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.muas.cataloguemovie.Database.DatabaseContract.CONTENT_URI;

public class DetailMovieActivity extends AppCompatActivity {

    @BindView(R.id.iv_detailGambarFilm)
    KenBurnsView ivDetailGambarFilm;
    @BindView(R.id.tv_detailJudulFilm)
    TextView tvDetailJudulFilm;
    @BindView(R.id.tv_detailDeskFilm)
    TextView tvDetailDeskFilm;
    @BindView(R.id.tv_detailRilisFilm)
    TextView tvDetailRilisFilm;
    @BindView(R.id.fab_detail)
    FloatingActionButton fabDetail;

    private long id;

    String img, judul, desc, tgl;
    String imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);


        ivDetailGambarFilm = (KenBurnsView) findViewById(R.id.iv_detailGambarFilm);
        tvDetailJudulFilm = (TextView) findViewById(R.id.tv_detailJudulFilm);
        tvDetailDeskFilm = (TextView) findViewById(R.id.tv_detailDeskFilm);
        tvDetailRilisFilm = (TextView) findViewById(R.id.tv_detailRilisFilm);
        fabDetail = (FloatingActionButton) findViewById(R.id.fab_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        judul = getIntent().getStringExtra("title");
        toolbar.setTitle(judul);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        /*memanggil metode getIntentData()*/
        getIntentData();
        setFavorite();

        ButterKnife.bind(this);

    }

    /*metode ini digunakan untuk menerima data yang dikirim dari MainActivity
     * berdasarkan RecyclerView yang diklik*/
    private void getIntentData() {

        img = getIntent().getStringExtra("backdrop");
        judul = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("overview");
        tgl = getIntent().getStringExtra("release");

        imgPath = "http://image.tmdb.org/t/p/w185" + img;
        Glide.with(getApplicationContext())
                .load(imgPath)
                .into(ivDetailGambarFilm);
        tvDetailJudulFilm.setText(judul);
        tvDetailRilisFilm.setText(tgl);
        tvDetailDeskFilm.setText(desc);
//        iniID = getIntent().getStringExtra("_ID");
        fabDetail.setImageResource(R.drawable.ic_star_favorite_unchecked_24dp);
    }

    public boolean setFavorite() {
        Uri uri = Uri.parse(CONTENT_URI + "");
        boolean favorite = false;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        String getTitle;
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getLong(0);
                getTitle = cursor.getString(1);
                if (getTitle.equals(getIntent().getStringExtra("title"))) {
                    fabDetail.setImageResource(R.drawable.ic_star_favorite_24dp);
                    favorite = true;
                }
            } while (cursor.moveToNext());

        }

        return favorite;

    }

    public void favorite(View view) {
        if (setFavorite()) {
            Uri uri = Uri.parse(CONTENT_URI + "/" + id);
            getContentResolver().delete(uri, null, null);
            fabDetail.setImageResource(R.drawable.ic_star_favorite_unchecked_24dp);
        } else {
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.FilmColumns.JUDUL, judul);
            values.put(DatabaseContract.FilmColumns.URL_POSTER, img);
            values.put(DatabaseContract.FilmColumns.RELEASE, tgl);
            values.put(DatabaseContract.FilmColumns.DESKRIPSI, desc);

            getContentResolver().insert(CONTENT_URI, values);
            setResult(101);

            fabDetail.setImageResource(R.drawable.ic_star_favorite_24dp);
        }
    }

}
