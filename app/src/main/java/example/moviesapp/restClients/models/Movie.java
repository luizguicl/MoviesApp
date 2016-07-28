package example.moviesapp.restClients.models;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    public String posterPath;
    public boolean adult;
    public String overview;
    public String releaseDate;
    public List<Integer> genreIds = new ArrayList<Integer>();
    public int id;
    public String originalTitle;
    public String originalLanguage;
    public String title;
    public String backdropPath;
    public double popularity;
    public int voteCount;
    public boolean video;
    public double voteAverage;

}
