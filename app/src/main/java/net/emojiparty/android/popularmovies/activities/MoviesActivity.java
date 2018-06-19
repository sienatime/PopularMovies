package net.emojiparty.android.popularmovies.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import net.emojiparty.android.popularmovies.R;
import net.emojiparty.android.popularmovies.adapters.DataBindingAdapter;
import net.emojiparty.android.popularmovies.models.Movie;
import net.emojiparty.android.popularmovies.presenters.MoviePresenter;
import net.emojiparty.android.popularmovies.repository.MovieRepository;
import net.emojiparty.android.popularmovies.viewmodels.ListViewModel;

public class MoviesActivity extends AppCompatActivity {

  private DataBindingAdapter moviesAdapter;
  private boolean requestInProgress = false;
  private ProgressBar loadingIndicator;
  private TextView noFavorites;
  private ListViewModel moviesViewModel;
  private MovieRepository movieRepository;
  private MovieRepository.MoviesLoadedCallback moviesLoadedCallback;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movies);
    loadingIndicator = findViewById(R.id.movies_loading);
    noFavorites = findViewById(R.id.no_movies);
    movieRepository = new MovieRepository(this, this);
    instantiateRecyclerView();
    setupViewModel();
    loadInitialMovies();
  }

  private MovieRepository.MoviesLoadedCallback getMoviesLoadedCallback() {
    if (moviesLoadedCallback == null) {
      moviesLoadedCallback = new MovieRepository.MoviesLoadedCallback() {
        @Override public void onSuccess(List<Movie> movies) {
          stopLoading();
          moviesViewModel.setList(movies);
          if (movieRepository.lastLoaded.equals(MovieRepository.FAVORITES) && movies.size() == 0) {
            noFavorites.setVisibility(View.VISIBLE);
          }
        }

        @Override public void onError(String message) {
          stopLoading();
          Toast.makeText(MoviesActivity.this, getString(R.string.error, message), Toast.LENGTH_LONG)
              .show();
        }
      };
    }
    return moviesLoadedCallback;
  }

  private void loadInitialMovies() {
    // only need to do this the first time, on rotation there will probably be movies here
    if (moviesViewModel.getList().getValue() == null || moviesViewModel.getList().getValue().size() == 0) {
      loadPopularMovies();
    }
  }

  private void setupViewModel() {
    moviesViewModel = ViewModelProviders.of(this).get(ListViewModel.class);
    moviesViewModel.getList().observe(this, new Observer<List<?>>() {
      @Override public void onChanged(@Nullable List<?> movies) {
        moviesAdapter.setItems(mapMoviesToPresenters((List<Movie>) movies));
      }
    });
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.movies_menu, menu);
    return true;
  }

  @Override
  public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
    super.onCreateContextMenu(menu, v, menuInfo);
    MenuInflater menuInflater = getMenuInflater();
    menuInflater.inflate(R.menu.movies_context_menu, menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (!requestInProgress) {
      if (item.getItemId() == R.id.top_rated) {
        loadTopRatedMovies();
      } else if (item.getItemId() == R.id.popular) {
        loadPopularMovies();
      } else if (item.getItemId() == R.id.my_favorites) {
        loadFavoriteMovies();
      }
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void instantiateRecyclerView() {
    RecyclerView moviesRecyclerView = findViewById(R.id.movies_recycler_view);
    GridLayoutManager layoutManager = new GridLayoutManager(MoviesActivity.this, columnsForGridLayout());
    moviesRecyclerView.setLayoutManager(layoutManager);
    moviesAdapter = new DataBindingAdapter(R.layout.list_item_movie);
    moviesRecyclerView.setAdapter(moviesAdapter);
  }

  private int columnsForGridLayout() {
    int MINIMUM_COLUMN_WIDTH = 500;
    int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    return width / MINIMUM_COLUMN_WIDTH;
  }

  private List<MoviePresenter> mapMoviesToPresenters(List<Movie> movies) {
    List<MoviePresenter> presenters = new ArrayList<>();
    for (int i = 0; i < movies.size(); i++) {
      Movie movie = movies.get(i);
      presenters.add(new MoviePresenter(movie, this));
    }
    return presenters;
  }

  private void startLoading() {
    requestInProgress = true;
    noFavorites.setVisibility(View.INVISIBLE);
    loadingIndicator.setVisibility(View.VISIBLE);
  }

  private void stopLoading() {
    requestInProgress = false;
    loadingIndicator.setVisibility(View.INVISIBLE);
  }

  private void loadTopRatedMovies() {
    startLoading();
    Toast.makeText(this, R.string.loading_top_movies, Toast.LENGTH_SHORT).show();
    movieRepository.loadTopRatedMovies(getMoviesLoadedCallback());
  }

  private void loadPopularMovies() {
    startLoading();
    Toast.makeText(this, R.string.loading_popular_movies, Toast.LENGTH_SHORT).show();
    movieRepository.loadPopularMovies(getMoviesLoadedCallback());
  }

  private void loadFavoriteMovies() {
    startLoading();
    Toast.makeText(this, R.string.loading_favorite_movies, Toast.LENGTH_SHORT).show();
    movieRepository.loadFavoriteMovies(getMoviesLoadedCallback());
  }
}
