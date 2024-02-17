package com.example.movierating.modelclass;

import java.util.ArrayList;
import java.util.List;

public class MovieModelClass {

    List<Results>  results = new ArrayList<>();


    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }

    public static class Results {
        String backdrop_path;
        int id;
        String orginal_title;
        String overview;
        String poster_path;

        String title ;


        String vote_average;


        public String getBackdrop_path() {
            return backdrop_path;
        }

        public void setBackdrop_path(String backdrop_path) {
            this.backdrop_path = backdrop_path;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrginal_title() {
            return orginal_title;
        }

        public void setOrginal_title(String orginal_title) {
            this.orginal_title = orginal_title;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getPoster_path() {
            return poster_path;
        }

        public void setPoster_path(String poster_path) {
            this.poster_path = poster_path;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getVote_average() {
            return vote_average;
        }

        public void setVote_average(String vote_average) {
            this.vote_average = vote_average;
        }
    }

}
