package example.moviesapp.restClients;

import example.moviesapp.restClients.models.TheMovieDbResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface TheMovieDbClient {

    @GET("/3/movie/popular")
    Call<TheMovieDbResponse> getPopularMovies();
}
