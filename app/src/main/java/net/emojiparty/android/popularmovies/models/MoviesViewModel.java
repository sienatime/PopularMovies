package net.emojiparty.android.popularmovies.models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import java.util.List;

public class MoviesViewModel extends AndroidViewModel {
  private MutableLiveData<List<Movie>> movies = new MutableLiveData<>();

  public MoviesViewModel(Application application) {
    super(application);
  }

  public void setMovies(List<Movie> movies) {
    this.movies.setValue(movies);
  }

  public LiveData<List<Movie>> getMovies() {
    return movies;
  }
}
