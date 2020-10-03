package com.example.flicksapp;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.flicksapp.models.Movie;

import org.parceler.Parcels;

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

        RelativeLayout container;
        TextView movieTitle;
        TextView movieDescription;
        ImageView moviePoster;

        public ViewHolder(View movieItemView) {
            super(movieItemView);

            movieTitle = (TextView) movieItemView.findViewById(R.id.movie_title);
            movieDescription = (TextView) movieItemView.findViewById(R.id.movie_description);
            moviePoster = (ImageView) movieItemView.findViewById(R.id.movie_poster);
            container = movieItemView.findViewById(R.id.container);
        }

        public void bind(final Movie movie){
            movieTitle.setText(movie.getTitle());
            movieDescription.setText(movie.getOverview());
            String imageUrl;
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageUrl = movie.getBackdropPath();
            }else{imageUrl = movie.getPosterPath();}

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.placeholder);

            int radius = 30; // corner radius, higher value = more rounded
            int margin = 10; // crop margin, set to 0 for corners with no crop

            Glide.with(context)
                    .setDefaultRequestOptions(requestOptions)
                    .load(imageUrl)
                    .into(moviePoster);

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, movie.getTitle(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, MovieDetailActivity.class);
                    intent.putExtra("movie", Parcels.wrap(movie));
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation((Activity) context, (View) movieTitle, "profile");
                    context.startActivity(intent, options.toBundle());
                }
            });
        }
    }
}
