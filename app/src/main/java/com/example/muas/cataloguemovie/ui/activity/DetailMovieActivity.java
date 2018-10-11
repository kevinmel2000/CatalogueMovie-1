package com.example.muas.cataloguemovie.ui.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.muas.cataloguemovie.Database.DatabaseContract;
import com.example.muas.cataloguemovie.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.muas.cataloguemovie.Database.DatabaseContract.CONTENT_URI;

public class DetailMovieActivity extends AppCompatActivity {

    @BindView(R.id.iv_detailGambarFilm)
    ImageView ivDetailGambarFilm;
    @BindView(R.id.tv_detailJudulFilm)
    TextView tvDetailJudulFilm;
    @BindView(R.id.tv_detailDeskFilm)
    TextView tvDetailDeskFilm;
    @BindView(R.id.tv_detailRilisFilm)
    TextView tvDetailRilisFilm;
    @BindView(R.id.iv_favorit)
    ImageView imageViewFavorit;
    @BindView(R.id.button2)
    Button buttonFavorite;
    private Boolean isFavorite = false;

    private long id;

    String img, judul, desc, tgl;

    String iniID;
    String judulnya;
    String desknya;
    String rilisnya;
    String imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);


        ivDetailGambarFilm = (ImageView) findViewById(R.id.iv_detailGambarFilm);
        tvDetailJudulFilm = (TextView) findViewById(R.id.tv_detailJudulFilm);
        tvDetailDeskFilm = (TextView) findViewById(R.id.tv_detailDeskFilm);
        tvDetailRilisFilm = (TextView) findViewById(R.id.tv_detailRilisFilm);
        imageViewFavorit = (ImageView) findViewById(R.id.iv_favorit);

        /*memanggil metode getIntentData()*/
        getIntentData();
        setFavorite();

        /*imageViewFavorit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favorite(view);
            }
        });*/


        ButterKnife.bind(this);

    }

    /*metode ini digunakan untuk menerima data yang dikirim dari MainActivity
    * berdasarkan RecyclerView yang diklik*/
    private void getIntentData(){

        img = getIntent().getStringExtra("backdrop");
        judul = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("overview");
        tgl = getIntent().getStringExtra("release");

        imgPath = "http://image.tmdb.org/t/p/w185"+img;
        Glide.with(getApplicationContext())
                .load(imgPath)
                .into(ivDetailGambarFilm);
        tvDetailJudulFilm.setText(judul);
        tvDetailRilisFilm.setText(tgl);
        tvDetailDeskFilm.setText(desc);
//        iniID = getIntent().getStringExtra("_ID");
        imageViewFavorit.setImageResource(R.drawable.ic_star_favorite_unchecked_24dp);
    }

    public boolean setFavorite(){
        Uri uri = Uri.parse(CONTENT_URI+"");
        boolean favorite = false;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        String getTitle;
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getLong(0);
                getTitle = cursor.getString(1);
                if (getTitle.equals(getIntent().getStringExtra("title"))){
                    imageViewFavorit.setImageResource(R.drawable.ic_star_favorite_24dp);
                    favorite = true;
                }
            } while (cursor.moveToNext());

        }

        return favorite;

    }

    public void favorite (View view) {
        if(setFavorite()){
            Uri uri = Uri.parse(CONTENT_URI+"/"+id);
            getContentResolver().delete(uri, null, null);
            imageViewFavorit.setImageResource(R.drawable.ic_star_favorite_unchecked_24dp);
        }
        else{
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.FilmColumns.JUDUL, judul);
            values.put(DatabaseContract.FilmColumns.URL_POSTER, img);
            values.put(DatabaseContract.FilmColumns.RELEASE, tgl);
            values.put(DatabaseContract.FilmColumns.DESKRIPSI, desc);

            getContentResolver().insert(CONTENT_URI, values);
            setResult(101);

            imageViewFavorit.setImageResource(R.drawable.ic_star_favorite_24dp);
        }
    }

    /*private void favoriteSet() {
        if (isFavorite)
            imageViewFavorit.setImageResource(R.drawable.ic_favorite);
        else
            imageViewFavorit.setImageResource(R.drawable.ic_favorite_border);
    }

    private void FavoriteSave() {
        //Log.d("TAG", "FavoriteSave: " + item.getId());
        iniID = iniID;
        judulnya = tvDetailJudulFilm.getText().toString();
        desknya =  tvDetailDeskFilm.getText().toString();
        rilisnya = tvDetailRilisFilm.getText().toString();
        imgPath = ivDetailGambarFilm.toString();

        *//*Log.d("cobacoba", "id: " + iniID);
        Log.d("cobacoba", "judulnya: " + judulnya);
        Log.d("cobacoba", "desknya: " + desknya);
        Log.d("cobacoba", "rilisnya: " + rilisnya);
        Log.d("cobacoba", "gambar: " + imgPath);*//*
        ContentValues cv = new ContentValues();
        cv.put(DatabaseContract.FilmColumns._ID, iniID);
        cv.put(DatabaseContract.FilmColumns.JUDUL, judulnya);
        cv.put(DatabaseContract.FilmColumns.DESKRIPSI, desknya);
        cv.put(DatabaseContract.FilmColumns.RELEASE, rilisnya);
        cv.put(DatabaseContract.FilmColumns.URL_POSTER, imgPath);
        getContentResolver().insert(CONTENT_URI, cv);
        Toast.makeText(this, "Berhasil disimpan", Toast.LENGTH_SHORT).show();
    }

    private void FavoriteRemove() {
        getContentResolver().delete(
                Uri.parse(CONTENT_URI + "/" + iniID),
                null,
                null
        );
        Toast.makeText(this, "dihapus", Toast.LENGTH_SHORT).show();
    }*/

}
