package com.example.muas.cataloguemovie.ui.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.muas.cataloguemovie.Database.DatabaseContract;
import com.example.muas.cataloguemovie.Model.MovieCursorItems;
import com.example.muas.cataloguemovie.Model.MovieItems;
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
    private Boolean isFavorite = false;

    MovieCursorItems items;

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

        imageViewFavorit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFavorite)
                    FavoriteRemove();
                else
                    FavoriteSave();

                isFavorite = !isFavorite;
                favoriteSet();
            }
        });


        ButterKnife.bind(this);

    }

    /*metode ini digunakan untuk menerima data yang dikirim dari MainActivity
    * berdasarkan RecyclerView yang diklik*/
    private void getIntentData(){
        imgPath = "http://image.tmdb.org/t/p/w185"+getIntent().getStringExtra("backdrop");
        Glide.with(this)
                .load(imgPath)
                .into(ivDetailGambarFilm);
        tvDetailJudulFilm.setText(getIntent().getStringExtra("title"));
        tvDetailRilisFilm.setText(getIntent().getStringExtra("release"));
        tvDetailDeskFilm.setText(getIntent().getStringExtra("overview"));
        iniID = getIntent().getStringExtra("_ID");
    }

    private void favoriteSet() {
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

        /*Log.d("cobacoba", "id: " + iniID);
        Log.d("cobacoba", "judulnya: " + judulnya);
        Log.d("cobacoba", "desknya: " + desknya);
        Log.d("cobacoba", "rilisnya: " + rilisnya);
        Log.d("cobacoba", "gambar: " + imgPath);*/
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
    }

}
