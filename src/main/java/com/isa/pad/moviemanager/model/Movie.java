package com.isa.pad.moviemanager.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Faust on 11/11/2017.
 */

@XmlRootElement
public class Movie {
    private String name;
    private String author;
    private int year;

    public Movie(String name, String author, int year) {
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public Movie() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                '}';
    }
}
