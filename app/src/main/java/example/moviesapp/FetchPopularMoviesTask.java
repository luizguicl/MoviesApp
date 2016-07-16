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

public class FetchPopularMoviesTask extends AsyncTask<Void, Void, List<String>> {

    private final String TAG = FetchPopularMoviesTask.class.getSimpleName();
    private ArrayAdapter<String> adapter;

    public FetchPopularMoviesTask(ArrayAdapter<String> adapter) {
        this.adapter = adapter;
    }

    @Override
    protected List<String> doInBackground(Void... voids) {

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String popularMoviesJsonStr = null;

        try {

            // Construct the URL for the MovieDatabase api request
            final String MOVIES_BASE_URL = "http://api.themoviedb.org/3/movie/popular?";
            final String APPID_PARAM = "api_key";

            Uri builtUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                    .appendQueryParameter(APPID_PARAM,  BuildConfig.THE_MOVIE_DB_API_KEY)
                    .build();

            URL url = new URL(builtUri.toString());

            Log.v(TAG, "Built URI " + builtUri.toString());

            // Create the request to TheMovieDatabase API, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            popularMoviesJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e(TAG, "Error ", e);

            return null;
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }

        try {
            return getMovieDataFromJson(popularMoviesJsonStr);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<String> result) {
        if (result != null && adapter != null) {
            adapter.clear();
            for(String movieInfoStr : result) {
                adapter.add(movieInfoStr);
            }
        }
    }

    private List<String> getMovieDataFromJson(String popularMoviesJsonStr) throws JSONException {

        // These are the names of the JSON objects that need to be extracted.
        final String MDB_RESULTS = "results";
        final String MDB_POSTER = "poster_path";
        final String MDB_TITLE = "title";
        final String MDB_OVERVIEW = "overview";
        final String MDB_VOTE_AVERAGE = "vote_average";


        JSONObject popularMoviesJson = new JSONObject(popularMoviesJsonStr);
        JSONArray moviesArray = popularMoviesJson.getJSONArray(MDB_RESULTS);

        List<String> moviesResult = new ArrayList<>();

        for (int i = 0; i < moviesArray.length(); i++) {

            // Get the JSON object representing a movie
            JSONObject movieObject = moviesArray.getJSONObject(i);

            String title = movieObject.getString(MDB_TITLE);
            Double voteAverage = movieObject.getDouble(MDB_VOTE_AVERAGE);

            moviesResult.add(TextUtils.concat(title, " \n ", "Vote average : ", voteAverage.toString()).toString());

        }

        return moviesResult;
    }
}
