package net.emojiparty.android.popularmovies.activities;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import net.emojiparty.android.popularmovies.R;
import net.emojiparty.android.popularmovies.adapters.MoviesAdapter;
import net.emojiparty.android.popularmovies.models.Movie;
import net.emojiparty.android.popularmovies.network.TheMovieDb;
import net.emojiparty.android.popularmovies.network.TheMovieDbResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesActivity extends AppCompatActivity {

  private final List<Movie> movies = new ArrayList<>();
  private MoviesAdapter moviesAdapter;
  private boolean isSortedByPopular = true;
  private boolean requestInProgress = false;
  private TheMovieDb theMovieDb;
  private ProgressBar loadingIndicator;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    theMovieDb = new TheMovieDb();
    setContentView(R.layout.activity_movies);
    loadingIndicator = findViewById(R.id.movies_loading);
    instantiateRecyclerView();
    loadPopularMovies();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.movies_menu, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.toggle_sort && !requestInProgress) {
      if (isSortedByPopular) {
        isSortedByPopular = false;
        loadTopRatedMovies();
      } else {
        isSortedByPopular = true;
        loadPopularMovies();
      }
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void instantiateRecyclerView() {
    RecyclerView moviesRecyclerView = findViewById(R.id.movies_recycler_view);
    GridLayoutManager layoutManager = new GridLayoutManager(MoviesActivity.this, columnsForGridLayout());
    moviesRecyclerView.setLayoutManager(layoutManager);
    moviesAdapter = new MoviesAdapter(movies);
    moviesRecyclerView.setAdapter(moviesAdapter);
  }

  private int columnsForGridLayout() {
    int MINIMUM_COLUMN_WIDTH = 500;
    int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    return width / MINIMUM_COLUMN_WIDTH;
  }

  private void showError(String message) {
    Toast.makeText(MoviesActivity.this, getString(R.string.error, message), Toast.LENGTH_LONG)
        .show();
  }

  // https://futurestud.io/tutorials/retrofit-synchronous-and-asynchronous-requests
  private Callback<TheMovieDbResponse> onMoviesLoaded() {
    return new Callback<TheMovieDbResponse>() {
      @Override
      public void onResponse(Call<TheMovieDbResponse> call, Response<TheMovieDbResponse> response) {
        stopLoading();
        if (response.isSuccessful()) {
          List<Movie> results = response.body().getResults();
          movies.clear();
          movies.addAll(results);
          moviesAdapter.notifyDataSetChanged();
        } else {
          showError(response.toString());
        }
      }

      @Override public void onFailure(Call<TheMovieDbResponse> call, Throwable t) {
        stopLoading();
        showError(t.toString());
      }
    };
  }

  private void startLoading() {
    requestInProgress = true;
    loadingIndicator.setVisibility(View.VISIBLE);
  }

  private void stopLoading() {
    requestInProgress = false;
    loadingIndicator.setVisibility(View.INVISIBLE);
  }

  private void loadTopRatedMovies() {
    startLoading();
    Toast.makeText(this, R.string.loading_top_movies, Toast.LENGTH_SHORT).show();
    theMovieDb.loadTopRatedMovies().enqueue(onMoviesLoaded());
  }

  private void loadPopularMovies() {
    startLoading();
    Toast.makeText(this, R.string.loading_popular_movies, Toast.LENGTH_SHORT).show();
    theMovieDb.loadPopularMovies().enqueue(onMoviesLoaded());
  }
}
