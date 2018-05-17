package net.emojiparty.android.popularmovies.network;

import java.io.IOException;
import net.emojiparty.android.popularmovies.BuildConfig;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class TheMovieDb {
  private static final String URL = "https://api.themoviedb.org/";
  private static final String API_KEY_PARAM_NAME = "api_key";
  private static final String API_KEY_PARAM_VALUE = BuildConfig.THE_MOVIE_DB_API_KEY;
  private TheMovieDbService service;

  public TheMovieDb() {
    //https://stackoverflow.com/questions/32948083/is-there-a-way-to-add-query-parameter-to-every-request-with-retrofit-2
    Retrofit retrofit = buildRetrofit();
    this.service = retrofit.create(TheMovieDbService.class);
  }

  public Call<MoviesResponse> loadPopularMovies() {
    return service.moviesPopular();
  }

  public Call<MoviesResponse> loadTopRatedMovies() {
    return service.moviesTopRated();
  }

  public Call<VideosResponse> loadTrailersForMovie(int movieId) {
    return service.videosForMovie(movieId);
  }

  public Call<ReviewsResponse> loadReviewsForMovie(int movieId) {
    return service.reviewsForMovie(movieId);
  }

  private OkHttpClient buildClient() {
    return new OkHttpClient.Builder().addInterceptor(new Interceptor() {
      @Override public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url().newBuilder().addQueryParameter(API_KEY_PARAM_NAME, API_KEY_PARAM_VALUE).build();
        request = request.newBuilder().url(url).build();
        return chain.proceed(request);
      }
    }).build();
  }

  private Retrofit buildRetrofit() {
    OkHttpClient client = buildClient();
    return new Retrofit.Builder()
        .baseUrl(URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }

  interface TheMovieDbService {
    @GET("/3/movie/popular") Call<MoviesResponse> moviesPopular();

    @GET("/3/movie/top_rated") Call<MoviesResponse> moviesTopRated();

    @GET("/3/movie/{id}/videos") Call<VideosResponse> videosForMovie(@Path("id") int movieId);

    @GET("/3/movie/{id}/reviews") Call<ReviewsResponse> reviewsForMovie(@Path("id") int movieId);
  }
}
