package net.emojiparty.android.popularmovies.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import java.io.IOException;
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

  private TheMovieDb theMovieDb;
  private final List<Movie> movies = new ArrayList<>();
  private RecyclerView moviesRecyclerView;
  private GridLayoutManager layoutManager;
  private MoviesAdapter moviesAdapter;
  private final int GRID_COLUMN_COUNT = 2;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movies);
    theMovieDb = new TheMovieDb();
    moviesRecyclerView = findViewById(R.id.movies_recycler_view);
    layoutManager = new GridLayoutManager(MoviesActivity.this, GRID_COLUMN_COUNT);
    moviesRecyclerView.setLayoutManager(layoutManager);
    moviesAdapter = new MoviesAdapter(movies);
    moviesRecyclerView.setAdapter(moviesAdapter);
    loadPopularMovies();
  }

  private void showError(String message) {
    Toast.makeText(MoviesActivity.this, getString(R.string.error, message), Toast.LENGTH_LONG).show();
  }

  // https://futurestud.io/tutorials/retrofit-synchronous-and-asynchronous-requests
  private void loadPopularMovies() {
    theMovieDb.loadPopularMovies().enqueue(new Callback<TheMovieDbResponse>() {
      @Override public void onResponse(Call<TheMovieDbResponse> call,
          Response<TheMovieDbResponse> response) {
        if (response.isSuccessful()) {
          List<Movie> results = response.body().getResults();
          movies.addAll(results);
          moviesAdapter.notifyDataSetChanged();
        } else {
          showError(response.toString());
        }
      }

      @Override public void onFailure(Call<TheMovieDbResponse> call, Throwable t) {
        showError(t.toString());
      }
    });
  }
}
