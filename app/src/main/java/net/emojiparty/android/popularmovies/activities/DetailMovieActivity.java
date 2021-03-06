package net.emojiparty.android.popularmovies.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import net.emojiparty.android.popularmovies.BR;
import net.emojiparty.android.popularmovies.R;
import net.emojiparty.android.popularmovies.adapters.DetailPagerAdapter;
import net.emojiparty.android.popularmovies.databinding.ActivityDetailMovieBinding;
import net.emojiparty.android.popularmovies.models.Movie;
import net.emojiparty.android.popularmovies.presenters.MoviePresenter;
import net.emojiparty.android.popularmovies.repository.MovieRepository;

public class DetailMovieActivity extends AppCompatActivity {
  public static final String MOVIE_FOR_DETAIL = "MOVIE_FOR_DETAIL";

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityDetailMovieBinding binding =
        DataBindingUtil.setContentView(this, R.layout.activity_detail_movie);
    binding.setLifecycleOwner(DetailMovieActivity.this); // for LiveData usage inside Presenter

    final Movie movie = getIntent().getParcelableExtra(MOVIE_FOR_DETAIL);
    configureViewWithMovie(movie);
    setupPresenter(movie, binding);
  }

  private void configureViewWithMovie(Movie movie) {
    configureActionBar(movie);
    initializePager(movie);
  }

  private void setupPresenter(Movie movie, ActivityDetailMovieBinding binding) {
    MoviePresenter moviePresenter = new MoviePresenter(movie, DetailMovieActivity.this);
    new MovieRepository(this, this).syncFavoriteWithLocalDatabase(moviePresenter);
    binding.setVariable(BR.presenter, moviePresenter);
  }

  private void configureActionBar(Movie movie) {
    ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setTitle(movie.getTitle());
  }

  //http://www.gadgetsaint.com/android/create-viewpager-tabs-android/#.Wv2g89OFPOQ
  private void initializePager(Movie movie) {
    ViewPager pager = findViewById(R.id.view_pager);
    pager.setAdapter(new DetailPagerAdapter(getSupportFragmentManager(), movie, getResources()));
    TabLayout tabLayout = findViewById(R.id.tabs);
    tabLayout.setupWithViewPager(pager);
  }

  @Override public boolean onSupportNavigateUp() {
    finish();
    return true;
  }
}
