package com.isa.pad.moviemanager.util;

import com.isa.pad.moviemanager.model.Movie;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by Faust on 11/12/2017.
 */
public class DslExecutor {
    private Logger logger = Logger.getLogger(DslExecutor.class.getName());

    private final Request request;
    private List<Movie> movies;

    public DslExecutor(Request request, List<Movie> movies) {
        this.request = request;
        this.movies = movies;
    }

    public List<Movie> execute() {
        try {
            filter();
            group();
            sort();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while parsing the dsl", e);
        }
        return movies;
    }

    public void sort() {
        if (request.getOrderBy() != null && !request.getOrderBy().isEmpty()) {
            switch (request.getOrderBy().toLowerCase()) {
                case "name":
                    movies.sort((m1, m2) -> m1.getName().compareTo(m2.getName()));
                    break;
                case "author":
                    movies.sort((m1, m2) -> m1.getAuthor().compareTo(m2.getAuthor()));
                    break;
                case "year":
                    movies.sort((m1, m2) -> Integer.compare(m1.getYear(), m2.getYear()));
                    break;
            }
        }
    }

    public void group() {
        if (request.getGroupBy() != null && !request.getGroupBy().isEmpty()) {
            switch (request.getGroupBy().toLowerCase()) {
                case "name": {
                    Map<String, List<Movie>> collect = movies.stream().collect(Collectors.groupingBy(Movie::getName));
                    movies.clear();
                    for (Map.Entry<String, List<Movie>> e : collect.entrySet()) {
                        movies.add(e.getValue().get(0));
                    }
                    break;
                }
                case "author": {
                    Map<String, List<Movie>> collect = movies.stream().collect(Collectors.groupingBy(Movie::getAuthor));
                    movies.clear();
                    for (Map.Entry<String, List<Movie>> e : collect.entrySet()) {
                        movies.add(e.getValue().get(0));
                    }
                    break;
                }
                case "year": {
                    Map<Integer, List<Movie>> collect = movies.stream().collect(Collectors.groupingBy(Movie::getYear));
                    movies.clear();
                    for (Map.Entry<Integer, List<Movie>> e : collect.entrySet()) {
                        movies.add(e.getValue().get(0));
                    }
                    break;
                }
            }
        }
    }


    public void filter() {
        Pattern pattern = Pattern.compile("(name|year|author)+\\s*(==|!=|>|<|>=|<=)\\s*(\\w+|'.+')");
        if (request.getFilterBy() != null && !request.getFilterBy().isEmpty()) {
            Matcher m = pattern.matcher(request.getFilterBy());
            if(m.find()){
                String field = m.group(1);
                String operation = m.group(2);
                String value = m.group(3);
                switch (field.toLowerCase()) {
                    case "name": {
                        filterByName(operation, value.substring(1, value.length() - 1));
                        break;
                    }
                    case "author": {
                        filterByAuthor(operation, value.substring(1, value.length() - 1));
                        break;
                    }
                    case "year": {
                        filterByYear(operation, value);
                        break;
                    }
                }
            }
        }

    }

    private void filterByYear(String operation, String value) {
        switch (operation) {
            case "<":
                movies = movies.stream().filter((m1) -> m1.getYear() < Integer.parseInt(value)).collect(Collectors.toList());
                break;
            case ">":
                movies = movies.stream().filter((m1) -> m1.getYear() > Integer.parseInt(value)).collect(Collectors.toList());
                break;
            case ">=":
                movies = movies.stream().filter((m1) -> m1.getYear() >= Integer.parseInt(value)).collect(Collectors.toList());
                break;
            case "<=":
                movies = movies.stream().filter((m1) -> m1.getYear() <= Integer.parseInt(value)).collect(Collectors.toList());
                break;
            case "==":
                movies = movies.stream().filter((m1) -> m1.getYear() == Integer.parseInt(value)).collect(Collectors.toList());
                break;
            case "!=":
                movies = movies.stream().filter((m1) -> m1.getYear() != Integer.parseInt(value)).collect(Collectors.toList());
                break;
        }
    }

    private void filterByAuthor(String operation, String value) {
        switch (operation) {
            case "<":
                movies = movies.stream().filter((m1) -> m1.getAuthor().compareTo(value) < 0).collect(Collectors.toList());
                break;
            case ">":
                movies = movies.stream().filter((m1) -> m1.getAuthor().compareTo(value) > 0).collect(Collectors.toList());
                break;
            case ">=":
                movies = movies.stream().filter((m1) -> m1.getAuthor().compareTo(value) >= 0).collect(Collectors.toList());
                break;
            case "<=":
                movies = movies.stream().filter((m1) -> m1.getAuthor().compareTo(value) <= 0).collect(Collectors.toList());
                break;
            case "==":
                movies = movies.stream().filter((m1) -> m1.getAuthor().equals(value)).collect(Collectors.toList());
                break;
            case "!=":
                movies = movies.stream().filter((m1) -> !m1.getAuthor().equals(value)).collect(Collectors.toList());
                break;
        }
    }

    private void filterByName(String operation, String value) {
        switch (operation) {
            case "<":
                movies = movies.stream().filter((m1) -> m1.getName().compareTo(value) < 0).collect(Collectors.toList());
                break;
            case ">":
                movies = movies.stream().filter((m1) -> m1.getName().compareTo(value) > 0).collect(Collectors.toList());
                break;
            case ">=":
                movies = movies.stream().filter((m1) -> m1.getName().compareTo(value) >= 0).collect(Collectors.toList());
                break;
            case "<=":
                movies = movies.stream().filter((m1) -> m1.getName().compareTo(value) <= 0).collect(Collectors.toList());
                break;
            case "==":
                movies = movies.stream().filter((m1) -> m1.getName().equals(value)).collect(Collectors.toList());
                break;
            case "!=":
                movies = movies.stream().filter((m1) -> !m1.getName().equals(value)).collect(Collectors.toList());
                break;
        }
    }


}
