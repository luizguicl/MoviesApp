package example.moviesapp;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import example.moviesapp.restClients.TheMovieDbClient;
import example.moviesapp.restClients.TheMovieDbClientFactory;
import example.moviesapp.restClients.models.Movie;
import example.moviesapp.restClients.models.TheMovieDbResponse;
import retrofit2.Call;
import retrofit2.Response;

public class FetchPopularMoviesTask extends AsyncTask<Void, Void, List<Movie>> {

    private final String TAG = FetchPopularMoviesTask.class.getSimpleName();
    private ArrayAdapter<Movie> adapter;

    public FetchPopularMoviesTask(ArrayAdapter<Movie> adapter) {
        this.adapter = adapter;
    }

    @Override
    protected List<Movie> doInBackground(Void... voids) {

        TheMovieDbClient theMovieDbClient = TheMovieDbClientFactory.createInstance();

        Call<TheMovieDbResponse> call = theMovieDbClient.getPopularMovies();

        List<Movie> result = new ArrayList<>();

        try {
            Response<TheMovieDbResponse> response = call.execute();
            for (Movie m : response.body().results) {
                result.add(m);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(List<Movie> result) {
        if (result != null && adapter != null) {
            adapter.clear();
            adapter.addAll(result);
        }
    }
}
