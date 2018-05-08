package net.emojiparty.android.popularmovies.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import java.io.IOException;
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

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movies);
    instantiateRecyclerView();
    //loadPopularMovies();
    loadOneMovie();
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
  private void loadPopularMovies() {
    TheMovieDb theMovieDb = new TheMovieDb();
    theMovieDb.loadPopularMovies().enqueue(new Callback<TheMovieDbResponse>() {
      @Override
      public void onResponse(Call<TheMovieDbResponse> call, Response<TheMovieDbResponse> response) {
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

  private void loadOneMovie() {
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
    movies.add(movie);
    moviesAdapter.notifyDataSetChanged();
  }
}
