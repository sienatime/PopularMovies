package net.emojiparty.android.popularmovies.repository;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;
import java.util.List;
import net.emojiparty.android.popularmovies.models.Movie;
import net.emojiparty.android.popularmovies.network.MoviesResponse;
import net.emojiparty.android.popularmovies.network.TheMovieDb;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
  private Context context;
  private LifecycleOwner lifecycleOwner;
  private LocalDatabase localDatabase;
  private TheMovieDb theMovieDb;
  private LiveData<List<Movie>> liveMoviesFromDb;
  private Observer<List<Movie>> liveMoviesObserver;
  public String lastLoaded = "";
  final static public String POPULAR = "popular";
  final static public String TOP_RATED = "top_rated";
  final static public String FAVORITES = "favorites";

  public MovieRepository(Context context, LifecycleOwner lifecycleOwner) {
    this.context = context;
    this.lifecycleOwner = lifecycleOwner;
  }

  private TheMovieDb getTheMovieDb() {
    if (theMovieDb == null) {
      theMovieDb = new TheMovieDb();
    }
    return theMovieDb;
  }

  private LocalDatabase getLocalDatabase() {
    if (localDatabase == null) {
      localDatabase = LocalDatabase.getInstance(context);
    }
    return localDatabase;
  }

  private void removeFavoritesObserver() {
    // otherwise, when a movie is favorited, the list will refresh with current favorites
    // regardless of what sort was previously selected
    if (liveMoviesFromDb != null) {
      liveMoviesFromDb.removeObserver(liveMoviesObserver);
    }
  }

  public void loadPopularMovies(final MoviesLoadedCallback moviesLoadedCallback) {
    lastLoaded = POPULAR;
    removeFavoritesObserver();
    getTheMovieDb().loadPopularMovies().enqueue(onMoviesLoaded(moviesLoadedCallback));
  }

  public void loadTopRatedMovies(final MoviesLoadedCallback moviesLoadedCallback) {
    lastLoaded = TOP_RATED;
    removeFavoritesObserver();
    getTheMovieDb().loadTopRatedMovies().enqueue(onMoviesLoaded(moviesLoadedCallback));
  }

  public void loadFavoriteMovies(final MoviesLoadedCallback moviesLoadedCallback) {
    lastLoaded = FAVORITES;
    liveMoviesFromDb = getLocalDatabase().movieDao().loadAllFavoriteMovies();
    liveMoviesObserver = new Observer<List<Movie>>() {
      @Override public void onChanged(@Nullable List<Movie> favoriteMovies) {
        moviesLoadedCallback.onSuccess(favoriteMovies);
      }
    };;
    liveMoviesFromDb.observe(lifecycleOwner, liveMoviesObserver);
  }

  // https://futurestud.io/tutorials/retrofit-synchronous-and-asynchronous-requests
  private Callback<MoviesResponse> onMoviesLoaded(final MoviesLoadedCallback moviesLoadedCallback) {
    return new Callback<MoviesResponse>() {
      @Override
      public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
        if (response.isSuccessful()) {
          moviesLoadedCallback.onSuccess(response.body().getResults());
        } else {
          moviesLoadedCallback.onError(response.toString());
        }
      }

      @Override public void onFailure(Call<MoviesResponse> call, Throwable t) {
        moviesLoadedCallback.onError(t.toString());
      }
    };
  }

  public interface MoviesLoadedCallback {
    void onSuccess(List<Movie> movies);
    void onError(String message);
  }
}
