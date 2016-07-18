package example.moviesapp.restClients;

import java.io.IOException;

import example.moviesapp.BuildConfig;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;

public class TheMovieDbClientFactory {

    private static final String MOVIES_BASE_URL = "http://api.themoviedb.org";

    public static TheMovieDbClient createInstance(){

        OkHttpClient.Builder httpClient =
                new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();

                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("api_key", BuildConfig.THE_MOVIE_DB_API_KEY)
                        .build();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });


        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .baseUrl(MOVIES_BASE_URL)
                .build();

        return retrofit.create(TheMovieDbClient.class);
    }
}
