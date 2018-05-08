package net.emojiparty.android.popularmovies.activities;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import net.emojiparty.android.popularmovies.R;
import net.emojiparty.android.popularmovies.BR;
import net.emojiparty.android.popularmovies.databinding.ActivityDetailMovieBinding;
import net.emojiparty.android.popularmovies.models.Movie;

public class DetailMovieActivity extends AppCompatActivity {
  public static final String MOVIE_FOR_DETAIL = "MOVIE_FOR_DETAIL";

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityDetailMovieBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_movie);
    // TODO: use real movie
    binding.setVariable(BR.movie, Movie.offlineMovie());
  }

//  TODO: back arrow in menu
}
