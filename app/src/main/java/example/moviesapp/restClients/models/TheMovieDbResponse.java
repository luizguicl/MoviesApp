package example.moviesapp.restClients.models;

import java.util.ArrayList;
import java.util.List;

public class TheMovieDbResponse {
    public int page;
    public List<Movie> results = new ArrayList<Movie>();
    public int totalResults;
    public int totalPages;

}
