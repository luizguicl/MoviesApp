package example.moviesapp.restClients;

import java.util.List;

import example.moviesapp.Movie;
import example.moviesapp.restClients.models.MovieData;
import retrofit2.Call;
import retrofit2.http.GET;

public interface TheMovieDbClient {

    @GET("/3/movie/popular")
    Call<List<MovieData>> getPopularMovies();


}
