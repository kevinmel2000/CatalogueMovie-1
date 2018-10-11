package com.example.muas.cataloguemovie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import com.example.muas.cataloguemovie.Model.MovieCursorItems;
import com.example.muas.cataloguemovie.R;
import com.example.muas.cataloguemovie.ui.activity.DetailMovieActivity;

import butterknife.BindView;

public class MovieCursorAdapter extends RecyclerView.Adapter<MovieCursorAdapter.ViewHolder> {


    @BindView(R.id.btn_detail)
    Button btnDetail;
    @BindView(R.id.btn_share)
    Button btnShare;
    private Cursor mData;
    private Context context;

    public MovieCursorAdapter(Context context) {
        this.context = context;
    }

    public void setListFavorit(Cursor items) {
        mData = items;
        notifyDataSetChanged();
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
    public void onBindViewHolder(@NonNull final MovieCursorAdapter.ViewHolder holder, final int position) {

        final MovieCursorItems movieItems = getItem(position);
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
                MovieCursorItems movieItems1 = getItem(position);
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
                MovieCursorItems movieItems1 = getItem(position);
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
        if (mData == null) return 0;
        return mData.getCount();
    }

    private MovieCursorItems getItem(int position) {
        if (!mData.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new MovieCursorItems(mData);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageFilm;
        TextView judulFilm, overview, release_date;
        Button btnDetail, btnShare;


        public ViewHolder(View itemView, MovieCursorAdapter movieAdapter) {
            super(itemView);
            imageFilm = (ImageView) itemView.findViewById(R.id.iv_gambarFilm);
            judulFilm = (TextView) itemView.findViewById(R.id.tv_judulFilm);
            overview = (TextView) itemView.findViewById(R.id.tv_deskFilm);
            release_date = (TextView) itemView.findViewById(R.id.tv_rilisFilm);
            btnDetail = (Button) itemView.findViewById(R.id.btn_detail);
            btnShare = (Button) itemView.findViewById(R.id.btn_share);
        }

    }

}