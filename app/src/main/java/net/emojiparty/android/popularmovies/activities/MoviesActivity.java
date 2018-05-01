package net.emojiparty.android.popularmovies.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.emojiparty.android.popularmovies.R;
import net.emojiparty.android.popularmovies.models.Movie;
import net.emojiparty.android.popularmovies.network.TheMovieDb;
import net.emojiparty.android.popularmovies.network.TheMovieDbResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesActivity extends AppCompatActivity {

  private TheMovieDb theMovieDb;
  private final List<Movie> movies = new ArrayList<>();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movies);
    theMovieDb = new TheMovieDb();
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
