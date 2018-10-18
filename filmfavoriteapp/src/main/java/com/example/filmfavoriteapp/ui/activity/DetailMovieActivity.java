package com.example.filmfavoriteapp.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.filmfavoriteapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMovieActivity extends AppCompatActivity {

    @BindView(R.id.iv_detailGambarFilm)
    ImageView ivDetailGambarFilm;
    @BindView(R.id.tv_detailJudulFilm)
    TextView tvDetailJudulFilm;
    @BindView(R.id.tv_detailDeskFilm)
    TextView tvDetailDeskFilm;
    @BindView(R.id.tv_detailRilisFilm)
    TextView tvDetailRilisFilm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        ivDetailGambarFilm = (ImageView) findViewById(R.id.iv_detailGambarFilm);
        tvDetailJudulFilm = (TextView) findViewById(R.id.tv_detailJudulFilm);
        tvDetailDeskFilm = (TextView) findViewById(R.id.tv_detailDeskFilm);
        tvDetailRilisFilm = (TextView) findViewById(R.id.tv_detailRilisFilm);

        /*memanggil metode getIntentData()*/
        getIntentData();
        ButterKnife.bind(this);

    }

    /*metode ini digunakan untuk menerima data yang dikirim dari MainActivity
    * berdasarkan RecyclerView yang diklik*/
    private void getIntentData(){
        String imgPath = "http://image.tmdb.org/t/p/w185" + getIntent().getStringExtra("poster_path");
        Glide.with(getApplicationContext())
                .load(imgPath)
                .into(ivDetailGambarFilm);
        tvDetailJudulFilm.setText(getIntent().getStringExtra("title"));
        tvDetailRilisFilm.setText(getIntent().getStringExtra("release"));
        tvDetailDeskFilm.setText(getIntent().getStringExtra("overview"));
    }

}
