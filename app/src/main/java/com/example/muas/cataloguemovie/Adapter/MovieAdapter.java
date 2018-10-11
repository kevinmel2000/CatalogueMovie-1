package com.example.muas.cataloguemovie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.muas.cataloguemovie.ItemClickSupport;
import com.example.muas.cataloguemovie.Model.MovieItems;
import com.example.muas.cataloguemovie.R;
import com.example.muas.cataloguemovie.ui.activity.DetailMovieActivity;

import java.util.ArrayList;

import butterknife.BindView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {


    @BindView(R.id.btn_detail)
    Button btnDetail;
    @BindView(R.id.btn_share)
    Button btnShare;
    private ArrayList<MovieItems> mData;
    private Context context;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.movie_items, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final MovieItems movieItems = mData.get(position);
        String gambarFilm = movieItems
                .getPoster_path();
        Glide.with(context)
                .load("http://image.tmdb.org/t/p/w500/" + gambarFilm)
                .into(holder.imageFilm);

        holder.judulFilm
                .setText(movieItems.getOriginal_title());

        holder.overview
                .setText(movieItems.getOverview());

        holder.release_date
                .setText(movieItems.getRelease_date());

        holder.btnDetail.setOnClickListener(new ItemClickSupport(position, new ItemClickSupport.OnItemClick() {
            @Override
            public void onItemClicked(View view, int position) {
                MovieItems movieItems1 = getmData().get(position);
                Intent i = new Intent(context, DetailMovieActivity.class);
                i.putExtra("_ID", movieItems1.getId());
                i.putExtra("title", movieItems1.getOriginal_title());
                i.putExtra("backdrop", movieItems1.getPoster_path());
                i.putExtra("overview", movieItems1.getOverview());
                i.putExtra("release", movieItems1.getRelease_date());
                context.startActivity(i);
            }
        }));

        holder.btnShare.setOnClickListener(new ItemClickSupport(position, new ItemClickSupport.OnItemClick() {
            @Override
            public void onItemClicked(View view, int position) {
                MovieItems movieItems1 = getmData().get(position);
                /*Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("**");
                *//*intent.setData(Uri.parse("mailto:")); // only email apps should handle this*//*
                intent.putExtra(Intent.EXTRA_EMAIL, movieItems1.getOriginal_title());
                intent.putExtra(Intent.EXTRA_SUBJECT, movieItems1.getOverview());
                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intent);
                }*/

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, movieItems1.getOriginal_title() + movieItems1.getOverview());
                sendIntent.setType("text/plain");
                context.startActivity(sendIntent);
            }
        }));

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageFilm;
        TextView judulFilm, overview, release_date;
        Button btnDetail, btnShare;


        public ViewHolder(View itemView, MovieAdapter movieAdapter) {
            super(itemView);
            imageFilm = (ImageView) itemView.findViewById(R.id.iv_gambarFilm);
            judulFilm = (TextView) itemView.findViewById(R.id.tv_judulFilm);
            overview = (TextView) itemView.findViewById(R.id.tv_deskFilm);
            release_date = (TextView) itemView.findViewById(R.id.tv_rilisFilm);
            btnDetail = (Button) itemView.findViewById(R.id.btn_detail);
            btnShare = (Button) itemView.findViewById(R.id.btn_share);
        }

    }

    public ArrayList<MovieItems> getmData() {
        return mData;
    }

    public void setmData(ArrayList<MovieItems> mData) {
        this.mData = mData;
        /*notifyDataSetChanged() berfungsi untuk mengabari adapter bahwa ada data baru yang telah diterima.
            Ketika fungsi ini dijalankan, maka Recyclerview yang didaftarkan pada adapter
        akan menampilkan data tersebut*/
        notifyDataSetChanged();
        /*ingat tambahkan selalu ini, krn kalau tidak, akan muncul pesan
            Skipped 37 frames! The application may be doing too much work on its main thread.*/
    }
}
