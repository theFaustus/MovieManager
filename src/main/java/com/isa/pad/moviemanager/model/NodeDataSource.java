package com.isa.pad.moviemanager.model;

import com.isa.pad.moviemanager.node.NodeNames;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Created by Faust on 11/11/2017.
 */
public enum NodeDataSource {
    INSTANCE();

    private HashMap<String, List<Movie>> movies = new HashMap<>();
    private HashMap<String, URI> nodeAdresses = new HashMap<>();
    private HashMap<String, Set<URI>> connections = new HashMap<>();


    NodeDataSource() {
        supplyNodeMovies();
        supplyNodePorts();
        supplyNodeConnections();

    }

    private void supplyNodePorts() {
        try {
            nodeAdresses.put(NodeNames.FMOVIES.name(), new URI("tcp://localhost:4001"));
            nodeAdresses.put(NodeNames.HDEUROPIX.name(), new URI("tcp://localhost:4002"));
            nodeAdresses.put(NodeNames.IVI.name(), new URI("tcp://localhost:4003"));
            nodeAdresses.put(NodeNames.IOMOVIES.name(), new URI("tcp://localhost:4004"));
            nodeAdresses.put(NodeNames.PUTLOCKER.name(), new URI("tcp://localhost:4005"));
            nodeAdresses.put(NodeNames.YESMOVIES.name(), new URI("tcp://localhost:4006"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void supplyNodeMovies() {
        movies.put(NodeNames.FMOVIES.name(), getFmoviesDataList());
        movies.put(NodeNames.HDEUROPIX.name(), getHdeurpoixDataList());
        movies.put(NodeNames.IVI.name(), getIviDataList());
        movies.put(NodeNames.IOMOVIES.name(), getIomoviesDataList());
        movies.put(NodeNames.PUTLOCKER.name(), getPutlockerDataList());
        movies.put(NodeNames.YESMOVIES.name(), getYesmoviesDataList());
    }

    private void supplyNodeConnections() {
        try {
            connections.put(NodeNames.FMOVIES.name(),
                    new HashSet<>(Arrays.asList(
                            new URI("tcp://localhost:4001"),
                            new URI("tcp://localhost:4002"),
                            new URI("tcp://localhost:4003"),
                            new URI("tcp://localhost:4004"))));
            connections.put(NodeNames.HDEUROPIX.name(),
                    new HashSet<>(Arrays.asList(
                            new URI("tcp://localhost:4005"))));
            connections.put(NodeNames.IVI.name(), new HashSet<>());
            connections.put(NodeNames.IOMOVIES.name(), new HashSet<>());
            connections.put(NodeNames.PUTLOCKER.name(), new HashSet<>());
            connections.put(NodeNames.YESMOVIES.name(), new HashSet<>());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public List<Movie> getFmoviesDataList() {
        List<Movie> list = new ArrayList<>();
        list.add(new Movie("Pulp Fiction", "Quentin Tarantion", 1994));
        list.add(new Movie("Lock, Stock and Two Smoking Barrels", "Guy Ritchie", 1998));
        list.add(new Movie("Snatch", "Guy Ritchie", 2000));
        return list;
    }

    public List<Movie> getHdeurpoixDataList() {
        List<Movie> list = new ArrayList<>();
        list.add(new Movie("The Wolf of Wall Street", "Martin Scorsese", 2013));
        list.add(new Movie("Goodfellas", "Martin Scorsese", 1990));
        return list;
    }


    public List<Movie> getIviDataList() {
        List<Movie> list = new ArrayList<>();
        list.add(new Movie("Brother", "Alexei Balabanov", 2000));
        list.add(new Movie("Ballad of Soldier", "Grigoriy Chukhray", 1959));
        list.add(new Movie("The Diamond Arm", "Leonid Gayday", 1969));
        list.add(new Movie("Three from Prostokvashino", "Vladimir Popov", 1969));
        return list;
    }

    public List<Movie> getIomoviesDataList() {
        List<Movie> list = new ArrayList<>();
        list.add(new Movie("The Hangover", "Tod Phillips", 2009));
        list.add(new Movie("21 Jump Street", "Phil Lord", 2012));
        list.add(new Movie("Rush Hour", "Brett Ratner", 1998));
        return list;
    }

    public List<Movie> getPutlockerDataList() {
        List<Movie> list = new ArrayList<>();
        list.add(new Movie("IT", "Andy Muschietti", 2017));
        list.add(new Movie("Split", "M. Night Shyamalan", 2016));
        list.add(new Movie("The Babadook", "Jennifer Kent", 2014));
        return list;
    }

    public List<Movie> getYesmoviesDataList() {
        List<Movie> list = new ArrayList<>();
        list.add(new Movie("The Shawshank Redemption", "Frank Darabont", 1972));
        list.add(new Movie("The Godfather", "Francis Ford Coppola", 1972));
        list.add(new Movie("Schinmdler`s List", "Steven Spielberg", 1993));
        return list;
    }

    public int getNodeDataListSizeFor(String nodeName) {
        return this.movies.get(nodeName).size();
    }

    public int getNodePortFor(String nodeName) {
        return this.nodeAdresses.get(nodeName).getPort();
    }

    public String getNodeAddressFor(String nodeName) {
        return this.nodeAdresses.get(nodeName).getHost();
    }

    public List<Movie> getNodeDataListFor(String nodeName) {
        return this.movies.get(nodeName);
    }

    public int getNodeNumberOfConnectionsFor(String nodeName) {
        return this.connections.get(nodeName).size();
    }
}