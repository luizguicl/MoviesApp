package example.moviesapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import example.moviesapp.restClients.TheMovieDbClient;
import example.moviesapp.restClients.TheMovieDbClientFactory;
import example.moviesapp.restClients.models.MovieData;
import retrofit2.Call;
import retrofit2.Callback;
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

        Call<List<MovieData>> call = theMovieDbClient.getPopularMovies();

        List<Movie> result = new ArrayList<>();

        try {
            Response<List<MovieData>> response = call.execute();
            for (MovieData m : response.body()) {
                result.add(new Movie(m.id, m.title, m.overview, m.posterPath, m.releaseDate, m.voteAverage));
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
