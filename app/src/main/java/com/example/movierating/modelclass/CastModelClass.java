package com.example.movierating.modelclass;

import java.util.ArrayList;
import java.util.List;

public class CastModelClass {

    List<Cast> cast = new ArrayList<>();

    public List<Cast> getCast() {
        return cast;
    }

    public static class Cast{
        String name;
        String profile_path;

        public String getName() {
            return name;
        }

        public String getProfile_path() {
            return profile_path;
        }
    }

}
