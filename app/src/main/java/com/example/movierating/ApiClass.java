package com.example.movierating;

import com.example.movierating.modelclass.CastModelClass;
import com.example.movierating.modelclass.MovieDetailsModelClass;
import com.example.movierating.modelclass.MovieModelClass;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiClass {

    //get data prom populer movie
    @GET("movie/popular")
    Call<MovieModelClass> getPopulerMovie(
            @Query("api_key") String api_key
    );

    //thid is for top rated movie
    @GET("movie/top_rated")
    Call<MovieModelClass> getTopRatedMovie(
            @Query("api_key") String api_key
    );
    //thid is for top rated movie
    @GET("movie/upcoming")
    Call<MovieModelClass> getUpcomigMovie(
            @Query("api_key") String api_key
    );
    //get movie details for given movie id
    @GET("movie/{movieId}")
    Call<MovieDetailsModelClass> getMovieDetails(
            @Path("movieId") int movieId,
            @Query("api_key") String api_key
    );
    //movie casts for given movie id
    @GET("movie/{movieId}/credits")
    Call<CastModelClass> getCasetsName(
            @Path("movieId") int movieId,
            @Query("api_key") String api_key
    );

    //recommendation movie for given movie id
    @GET("movie/{movieId}/recommendations")
    Call<MovieModelClass> getRecommendatioMovie(
            @Path("movieId") int movieId,
            @Query("api_key") String api_key
    );



}
