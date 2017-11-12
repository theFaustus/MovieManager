package com.isa.pad.moviemanager.client;

import com.isa.pad.moviemanager.model.Movie;

import java.util.List;

/**
 * Created by Faust on 11/12/2017.
 */
public class ClientDemo {
    public static void main(String[] args) {
        Client client = new Client(new ClientConfig());
        List<Movie> movies = client.sendRequest("get all");
        System.out.println(movies);
    }
}
