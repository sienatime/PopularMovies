package net.emojiparty.android.popularmovies.activities;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import net.emojiparty.android.popularmovies.R;
import net.emojiparty.android.popularmovies.BR;
import net.emojiparty.android.popularmovies.databinding.ActivityDetailMovieBinding;
import net.emojiparty.android.popularmovies.models.Movie;
import net.emojiparty.android.popularmovies.models.MoviePresenter;

public class DetailMovieActivity extends AppCompatActivity {
  public static final String MOVIE_FOR_DETAIL = "MOVIE_FOR_DETAIL";

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    ActivityDetailMovieBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_movie);
    Movie movie = getIntent().getParcelableExtra(MOVIE_FOR_DETAIL);
    binding.setVariable(BR.presenter, new MoviePresenter(movie, DetailMovieActivity.this));
  }

  @Override public boolean onSupportNavigateUp() {
    finish();
    return true;
  }
}
