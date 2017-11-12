package com.isa.pad.moviemanager.util;

import com.isa.pad.moviemanager.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Faust on 11/12/2017.
 */
public class TcpResponse {
    private List<Movie> movies = new ArrayList<>();

    public TcpResponse() {
    }

    public TcpResponse(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
