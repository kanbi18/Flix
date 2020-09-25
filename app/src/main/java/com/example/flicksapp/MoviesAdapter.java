package com.example.flicksapp;

import android.content.Context;
import android.content.res.Configuration;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.flicksapp.models.Movie;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private List<Movie> movies;
    Context context;

    public MoviesAdapter(Context context, List<Movie> movies) {

        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View movieItemsView = inflater.inflate(R.layout.movie_item, parent, false);

        return new ViewHolder(movieItemsView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView movieTitle;
        public TextView movieDescription;
        public ImageView moviePoster;

        public ViewHolder(View movieItemView) {
            super(movieItemView);

            movieTitle = (TextView) movieItemView.findViewById(R.id.movie_title);
            movieDescription = (TextView) movieItemView.findViewById(R.id.movie_description);
            moviePoster = (ImageView) movieItemView.findViewById(R.id.movie_poster);
        }

        public void bind(Movie movie){
            movieTitle.setText(movie.getTitle());
            movieDescription.setText(movie.getOverview());
            String imageUrl;
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageUrl = movie.getBackdropPath();
            }else{imageUrl = movie.getPosterPath();}

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.placeholder);

            Glide.with(context)
                    .setDefaultRequestOptions(requestOptions)
                    .load(imageUrl)
                    .into(moviePoster);
        }

    }
}
