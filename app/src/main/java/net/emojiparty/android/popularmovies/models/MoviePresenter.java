package net.emojiparty.android.popularmovies.models;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.os.AsyncTask;
import android.text.format.DateFormat;
import java.text.DecimalFormat;
import net.emojiparty.android.popularmovies.activities.DetailMovieActivity;
import net.emojiparty.android.popularmovies.repository.LocalDatabase;

public class MoviePresenter {
  private Movie movie;
  private Context context;

  public MoviePresenter(Movie movie, Context context) {
    this.movie = movie;
    this.context = context;
  }

  public String title() {
    return movie.getTitle();
  }

  public String overview() {
    return movie.getOverview();
  }

  public String posterUrl() {
    return "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();
  }

  public ObservableBoolean isFavorite() {
    return movie.getFavorite();
  }

  // http://www.java67.com/2014/06/how-to-format-float-or-double-number-java-example.html#ixzz5FOpsJS5l
  public String formattedVoteAverage() {
    DecimalFormat df = new DecimalFormat("#.##");
    return df.format(movie.getVoteAverage());
  }

  public String formattedReleaseDate() {
    return DateFormat.getMediumDateFormat(context).format(movie.getReleaseDate());
  }

  public void onMovieClicked() {
    Intent detailIntent = new Intent(context, DetailMovieActivity.class);
    detailIntent.putExtra(DetailMovieActivity.MOVIE_FOR_DETAIL, movie);
    context.startActivity(detailIntent);
  };

  public void onFavoriteFABClicked() {
    AsyncTask.execute(new Runnable() {
      @Override public void run() {
        LocalDatabase localDb = LocalDatabase.getInstance(context);
        if (movie.getFavorite().get()) {
          localDb.movieDao().deleteFavoriteMovie(movie);
          movie.setFavorite(false);
        } else {
          localDb.movieDao().insertFavoriteMovie(movie);
          movie.setFavorite(true);
        }
      }
    });
  }
}
