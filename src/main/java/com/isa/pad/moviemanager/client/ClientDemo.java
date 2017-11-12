package com.isa.pad.moviemanager.client;

import com.isa.pad.moviemanager.model.Movie;
import com.isa.pad.moviemanager.util.Request;

import java.util.List;

/**
 * Created by Faust on 11/12/2017.
 */
public class ClientDemo {
    public static void main(String[] args) {
        Client client = new Client(new ClientConfig());

        Request r = Request.newRequest()
                .filterBy("author == 'Guy Ritchie'")
                .groupBy("year")
                .orderBy("year")
                .build();

        List<Movie> movies = client.sendRequest(r);

        System.out.println(movies);
    }
}
