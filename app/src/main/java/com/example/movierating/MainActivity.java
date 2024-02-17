package com.example.movierating;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.movierating.modelclass.MovieModelClass;
import com.example.movierating.recylerview.MovieCoverRecylerView;
import com.example.movierating.recylerview.PopulerMovieRecylerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Call<MovieModelClass> call;

    RecyclerView populerRecyclerView,movieCoverRecylerView,movieTopRatedRecylerView,movieUpComing;

     List<MovieModelClass.Results> results = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    MovieCoverRecylerView movieCoverRecylerViewAdapter;
    TextView txtShowAllPopulerMovie,txtShowAllTopRatedMovie,txtShowAllUpcomingMovie;
    ApiClass apiClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiClass = retrofit.create(ApiClass.class);


        populerRecyclerView = findViewById(R.id.PopulerMovieRecylerView);
        movieCoverRecylerView = findViewById(R.id.MovieCoverRecylerView);
        movieTopRatedRecylerView = findViewById(R.id.TopRateMovieRecylerView);
        movieUpComing= findViewById(R.id.UpComingMovieRecylerView);
        txtShowAllPopulerMovie = findViewById(R.id.showAllPopulerMovie);
        txtShowAllTopRatedMovie=findViewById(R.id.showAllTopRated);
        txtShowAllUpcomingMovie=findViewById(R.id.showAllUpComing);

        //this is for poplur movie
        LinearLayoutManager populerlinearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        populerRecyclerView.setLayoutManager(populerlinearLayoutManager);

        //this is for cover movie
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        movieCoverRecylerView.setLayoutManager(linearLayoutManager);

        //this is for populer movie
        movieTopRatedRecylerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        
        //this is for upcoming movie
        movieUpComing.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        

        PopulerMovie();
        TopRatedMovie();
        UpComingMovie();

        //click show button start
        txtShowAllPopulerMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ShowAllMovie.class);
                intent.putExtra("show","Popular");
                startActivity(intent);

            }
        });
        txtShowAllTopRatedMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ShowAllMovie.class);
                intent.putExtra("show","Top Rated");
                startActivity(intent);
            }
        });
        txtShowAllUpcomingMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ShowAllMovie.class);
                intent.putExtra("show","Up Coming");
                startActivity(intent);
            }
        });
        //click show button end
    }

    private void UpComingMovie() {

        call = apiClass.getUpcomigMovie("9057a6cde80db019e4b77353500a2743");

        call.enqueue(new Callback<MovieModelClass>() {
            @Override
            public void onResponse(Call<MovieModelClass> call, Response<MovieModelClass> response) {
                MovieModelClass movieModelClass = response.body();
                results = movieModelClass.getResults();

                //this for populer movie
                PopulerMovieRecylerView populerRecyclerViewAdapter = new PopulerMovieRecylerView(MainActivity.this,results);
                movieUpComing.setAdapter(populerRecyclerViewAdapter);


            }

            @Override
            public void onFailure(Call<MovieModelClass> call, Throwable t) {

                Log.d("title", "onFailure: "+t.getMessage().toString());
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

                //this for populer movie
                PopulerMovieRecylerView populerRecyclerViewAdapter = new PopulerMovieRecylerView(MainActivity.this,results);
                movieTopRatedRecylerView.setAdapter(populerRecyclerViewAdapter);


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

                //this for populer movie
                PopulerMovieRecylerView populerRecyclerViewAdapter = new PopulerMovieRecylerView(MainActivity.this,results);
                populerRecyclerView.setAdapter(populerRecyclerViewAdapter);

                //this is for cover image
                movieCoverRecylerViewAdapter = new MovieCoverRecylerView(MainActivity.this,results);
                movieCoverRecylerView.setAdapter(movieCoverRecylerViewAdapter);
                MovieCoverAutoScrolling();
            }

            @Override
            public void onFailure(Call<MovieModelClass> call, Throwable t) {

                Log.d("title", "onFailure: "+t.getMessage().toString());
            }
        });
    }

    private void MovieCoverAutoScrolling() {
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(movieCoverRecylerView);

        Timer timer = new Timer();
        final boolean[] scrollToEnd = {true};

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int lastVisibleItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                int itemCount = movieCoverRecylerViewAdapter.getItemCount();

                if (scrollToEnd[0]) {
                    if (lastVisibleItemPosition < itemCount - 1) {
                        smoothScrollToPosition(lastVisibleItemPosition + 1, itemCount);
                    } else {
                        // Change direction to scroll from end to start
                        scrollToEnd[0] = false;
                    }
                } else {
                    if (lastVisibleItemPosition > 0) {
                        smoothScrollToPosition(lastVisibleItemPosition - 1, itemCount);
                    } else {
                        // Change direction to scroll from start to end
                        scrollToEnd[0] = true;
                    }
                }
            }
        }, 0, 3000);
    }

    private void smoothScrollToPosition(int targetPosition, int itemCount) {
        if (targetPosition >= 0 && targetPosition < itemCount) {
            LinearSmoothScroller smoothScroller = new LinearSmoothScroller(MainActivity.this) {
                @Override
                protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                    // Adjust this value to control the speed of scrolling
                    return 0.4f;
                }
            };

            smoothScroller.setTargetPosition(targetPosition);
            linearLayoutManager.startSmoothScroll(smoothScroller);
        }
    }

}