package net.emojiparty.android.popularmovies.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
  private final int GRID_COLUMN_COUNT = 2;
  private boolean isSortedByPopular = true;
  private boolean requestInProgress = false;
  private TheMovieDb theMovieDb;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    theMovieDb = new TheMovieDb();
    setContentView(R.layout.activity_movies);
    instantiateRecyclerView();
    loadPopularMovies();
    //loadOneMovie();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu, menu);
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
    GridLayoutManager layoutManager = new GridLayoutManager(MoviesActivity.this, GRID_COLUMN_COUNT);
    moviesRecyclerView.setLayoutManager(layoutManager);
    moviesAdapter = new MoviesAdapter(movies);
    moviesRecyclerView.setAdapter(moviesAdapter);
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
        requestInProgress = false;
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
        requestInProgress = false;
        showError(t.toString());
      }
    };
  }

  private void loadTopRatedMovies() {
    requestInProgress = true;
    Toast.makeText(this, "loading top rated movies", Toast.LENGTH_SHORT).show();
    //theMovieDb.loadTopRatedMovies().enqueue(onMoviesLoaded());
  }

  private void loadPopularMovies() {
    requestInProgress = true;
    Toast.makeText(this, "loading popular movies", Toast.LENGTH_SHORT).show();
    //theMovieDb.loadPopularMovies().enqueue(onMoviesLoaded());
  }

  public static Movie offlineMovie() {
    Movie movie = new Movie();
    movie.setTitle("Avengers: Infinity War");
    movie.setPosterPath("/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg");
    movie.setOverview("As the Avengers and their allies have continued to protect the world from threats too large for any one hero to handle, a new danger has emerged from the cosmic shadows: Thanos. A despot of intergalactic infamy, his goal is to collect all six Infinity Stones, artifacts of unimaginable power, and use them to inflict his twisted will on all of reality. Everything the Avengers have fought for has led up to this moment - the fate of Earth and existence itself has never been more uncertain.");
    SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
    try {
      movie.setReleaseDate(dateFormat.parse("2018-04-25"));
    } catch (ParseException e) {
      movie.setReleaseDate(new Date());
    }
    movie.setVoteAverage(8.7f);
    return movie;
  }

  private void loadOneMovie() {
    Movie offlineMovie = offlineMovie();
    movies.add(offlineMovie);
    moviesAdapter.notifyDataSetChanged();
  }

  private void openDetailMovie() {
    Log.i("MOVIES_ACTIVITY", "I made it!");
  }
}
