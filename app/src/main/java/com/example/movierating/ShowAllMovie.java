package com.example.movierating;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.movierating.modelclass.MovieModelClass;
import com.example.movierating.recylerview.MovieCoverRecylerView;
import com.example.movierating.recylerview.PopulerMovieRecylerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ShowAllMovie extends AppCompatActivity {

    RecyclerView showAllMovieRecylerView;
    List<MovieModelClass.Results> results = new ArrayList<>();
    Call<MovieModelClass> call;
    String movieType = null;
    ApiClass apiClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_movie);

        TextView title = findViewById(R.id.title);
        showAllMovieRecylerView = findViewById(R.id.showALlMovie);
        showAllMovieRecylerView.setLayoutManager(new GridLayoutManager(this,2));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiClass = retrofit.create(ApiClass.class);

        Intent intent = getIntent();
          //when i click show button in main actvity it will movie type like  populer,recommend or up coming
        if(intent!=null){
           movieType = intent.getStringExtra("show");
        }

        if(movieType.equals("Popular")){
            PopulerMovie();
            title.setText(movieType);
        }
        else if (movieType.equals("Top Rated")) {
            TopRatedMovie();
            title.setText(movieType);
        }
        else if (movieType.equals("Up Coming")) {
            UpComingMovie();
            title.setText(movieType);

        }

    }


    private void UpComingMovie() {
        Log.d("title","up coming");
        call = apiClass.getUpcomigMovie("9057a6cde80db019e4b77353500a2743");

        call.enqueue(new Callback<MovieModelClass>() {
            @Override
            public void onResponse(Call<MovieModelClass> call, Response<MovieModelClass> response) {
                MovieModelClass movieModelClass = response.body();
                results = movieModelClass.getResults();
                Log.d("success","up coming");
                //this for populer movie
                PopulerMovieRecylerView populerRecyclerViewAdapter = new PopulerMovieRecylerView(ShowAllMovie.this,results);
                showAllMovieRecylerView.setAdapter(populerRecyclerViewAdapter);

            }

            @Override
            public void onFailure(Call<MovieModelClass> call, Throwable t) {

                Log.d("fails", "onFailure: "+t.getMessage().toString());
            }
        });
    }

    private void TopRatedMovie() {
        call = apiClass.getTopRatedMovie("9057a6cde80db019e4b77353500a2743");

        call.enqueue(new Callback<MovieModelClass>() {
            @Override
            public void onResponse(Call<MovieModelClass> call, Response<MovieModelClass> response) {
                MovieModelClass movieModelClass = response.body();
                results = movieModelClass.getResults();

                PopulerMovieRecylerView populerRecyclerViewAdapter = new PopulerMovieRecylerView(ShowAllMovie.this,results);
                showAllMovieRecylerView.setAdapter(populerRecyclerViewAdapter);
            }

            @Override
            public void onFailure(Call<MovieModelClass> call, Throwable t) {

                Log.d("title", "onFailure: "+t.getMessage().toString());
            }
        });

    }

    private void PopulerMovie() {

        call = apiClass.getPopulerMovie("9057a6cde80db019e4b77353500a2743");
        call.enqueue(new Callback<MovieModelClass>() {
            @Override
            public void onResponse(Call<MovieModelClass> call, Response<MovieModelClass> response) {
                MovieModelClass movieModelClass = response.body();
                results = movieModelClass.getResults();

                PopulerMovieRecylerView populerRecyclerViewAdapter = new PopulerMovieRecylerView(ShowAllMovie.this,results);
                showAllMovieRecylerView.setAdapter(populerRecyclerViewAdapter);

            }

            @Override
            public void onFailure(Call<MovieModelClass> call, Throwable t) {

                Log.d("title", "onFailure: "+t.getMessage().toString());
            }
        });
    }
}