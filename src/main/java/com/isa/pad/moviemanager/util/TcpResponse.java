package com.isa.pad.moviemanager.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.isa.pad.moviemanager.model.Movie;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Faust on 11/12/2017.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder("movies")
@XmlRootElement
public class TcpResponse {
    @JsonProperty("movies")
    private List<Movie> movies = new ArrayList<>();

    public TcpResponse() {
    }

    public TcpResponse(List<Movie> movies) {
        this.movies = movies;
    }

    @JsonProperty("movies")
    public List<Movie> getMovies() {
        return movies;
    }

    @JsonProperty("movies")
    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
