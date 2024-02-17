package com.example.movierating.recylerview;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movierating.MovieDetailsActivity;
import com.example.movierating.R;
import com.example.movierating.modelclass.MovieModelClass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PopulerMovieRecylerView extends RecyclerView.Adapter<PopulerMovieRecylerView.ViewHolder> {

    Context context;

    List<MovieModelClass.Results> movieModelClasses = new ArrayList<>();

    public PopulerMovieRecylerView(Context context, List<MovieModelClass.Results> movieModelClasses) {
        this.context = context;
        this.movieModelClasses = movieModelClasses;
    }

    @NonNull
    @Override
    public PopulerMovieRecylerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopulerMovieRecylerView.ViewHolder holder, int position) {

        int id = movieModelClasses.get(position).getId();
        String title = movieModelClasses.get(position).getTitle();
        String rate =movieModelClasses.get(position).getVote_average();
        String img =movieModelClasses.get(position).getPoster_path();
        holder.MovieName.setText(title);
        holder.MovieRating.setText(rate);
        Picasso.get().load("https://image.tmdb.org/t/p/w200" +img).into(holder.MoviePoster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra("id",id);
                Log.d("id",""+id);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return movieModelClasses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView MoviePoster;
        TextView MovieName,MovieRating;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            MoviePoster = itemView.findViewById(R.id.movie_img);
            MovieName = itemView.findViewById(R.id.movie_name);
            MovieRating = itemView.findViewById(R.id.movie_rating);


        }
    }
}
