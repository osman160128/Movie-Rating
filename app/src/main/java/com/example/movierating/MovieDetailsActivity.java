package com.example.movierating;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.movierating.modelclass.CastModelClass;
import com.example.movierating.modelclass.MovieDetailsModelClass;
import com.example.movierating.modelclass.MovieModelClass;
import com.example.movierating.recylerview.CastsAdapter;
import com.example.movierating.recylerview.PopulerMovieRecylerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetailsActivity extends AppCompatActivity {

    ImageView movieCoverImage,movieImg;
    TextView txtMovieName,txtMovieType,txtMovieRelaseDate,txtMovieDuration,txtMovieLanguage,txtOverView;

    RecyclerView movieVedioRecylerView,movieCastRecylerView,movieRecommededRecylerView;
    RatingBar ratingBar;

    Retrofit retrofit;
    ApiClass apiClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        movieCoverImage = findViewById(R.id.movie_details_cover_image);
        movieImg = findViewById(R.id.movie_details_image);
        txtMovieName = findViewById(R.id.movie_details_name);
        txtMovieType = findViewById(R.id.movie_detials_type);
        txtMovieRelaseDate = findViewById(R.id.movie_details_calander);
        txtMovieDuration = findViewById(R.id.movie_details_duratin);
        txtMovieLanguage = findViewById(R.id.movie_details_language);
        txtOverView = findViewById(R.id.movie_details_over_view);
        ratingBar = findViewById(R.id.movie_detials_rating_bar);
        movieCastRecylerView = findViewById(R.id.movie_details_cast);
        movieRecommededRecylerView = findViewById(R.id.movie_details_recommendation);



        movieCastRecylerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        movieRecommededRecylerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        apiClass = retrofit.create(ApiClass.class);
        Intent intent = getIntent();
        int movie_id = intent.getIntExtra("id",0);


        GetMovieDetails(movie_id);
        GetCastNames(movie_id);
        GetRecommendationMovie(movie_id);


    }

    private void GetRecommendationMovie(int movie_id) {
        Call<MovieModelClass> call = apiClass.getRecommendatioMovie(movie_id,"9057a6cde80db019e4b77353500a2743");
        call.enqueue(new Callback<MovieModelClass>() {
            @Override
            public void onResponse(Call<MovieModelClass> call, Response<MovieModelClass> response) {
                MovieModelClass movieModelClass = response.body();

                List<MovieModelClass.Results> results = movieModelClass.getResults();
                PopulerMovieRecylerView populerRecyclerViewAdapter = new PopulerMovieRecylerView(MovieDetailsActivity.this,results);
                movieRecommededRecylerView.setAdapter(populerRecyclerViewAdapter);
            }

            @Override
            public void onFailure(Call<MovieModelClass> call, Throwable t) {

            }
        });
    }

    //get cast name
    private void GetCastNames(int movie_id) {

        Call<CastModelClass> call = apiClass.getCasetsName(movie_id,"9057a6cde80db019e4b77353500a2743");
        call.enqueue(new Callback<CastModelClass>() {
            @Override
            public void onResponse(Call<CastModelClass> call, Response<CastModelClass> response) {
                CastModelClass castModelClass = response.body();

                List<CastModelClass.Cast> casts = castModelClass.getCast();
                CastsAdapter castsAdapter = new CastsAdapter(MovieDetailsActivity.this,casts);
                movieCastRecylerView.setAdapter(castsAdapter);

            }

            @Override
            public void onFailure(Call<CastModelClass> call, Throwable t) {

            }
        });

    }

    //start this will show the data from movie id
    private void GetMovieDetails(int movie_id) {
        Call<MovieDetailsModelClass> call = apiClass.getMovieDetails(movie_id,"9057a6cde80db019e4b77353500a2743");


        call.enqueue(new Callback<MovieDetailsModelClass>() {
            @Override
            public void onResponse(Call<MovieDetailsModelClass> call, Response<MovieDetailsModelClass> response) {

                     MovieDetailsModelClass movieDetailsModelClass = response.body();
                     String movie_cover_img =  movieDetailsModelClass.getBackdrop_path();
                     String movie_img  =  movieDetailsModelClass.getPoster_path();
                     String movie_name  = movieDetailsModelClass.getTitle();
                      Log.d("movie_name",movie_name);
                     double movie_rate  = movieDetailsModelClass.getVote_average();
                     String movie_realse_date = movieDetailsModelClass.getRelease_date();
                     int movie_duration = movieDetailsModelClass.getRuntime();
                     String movie_language = movieDetailsModelClass.getOriginal_language();
                     String movie_overview = movieDetailsModelClass.getOverview();

                     List<MovieDetailsModelClass.Genres> movie_types = movieDetailsModelClass.getGenres();
                     for (MovieDetailsModelClass.Genres movie_type:movie_types){
                         txtMovieType.append(" "+movie_type.getName());
                     }



                Picasso.get().load("https://image.tmdb.org/t/p/w200"+movie_cover_img).into(movieCoverImage);
                Picasso.get().load("https://image.tmdb.org/t/p/w200"+movie_img).into(movieImg);
                txtMovieName.setText(movie_name);
                ratingBar.setRating((float) movie_rate/2);
                txtMovieRelaseDate.setText(movie_realse_date);
                txtMovieDuration.setText(""+movie_duration);
                txtMovieLanguage.setText(movie_language);
                txtOverView.setText(movie_overview);


            }

            @Override
            public void onFailure(Call<MovieDetailsModelClass> call, Throwable t) {
                Log.e("API_CALL_FAILURE", "Error: " + t.getMessage());
            }
        });
    }
    //end this will show the data from movie id


}