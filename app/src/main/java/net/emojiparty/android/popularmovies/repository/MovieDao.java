package net.emojiparty.android.popularmovies.repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import java.util.List;
import net.emojiparty.android.popularmovies.models.Movie;

@Dao public interface MovieDao {
  @Query("SELECT * FROM movies WHERE favorite = 1 ORDER BY id") LiveData<List<Movie>> loadAllFavoriteMovies();

  @Insert(onConflict = OnConflictStrategy.REPLACE) void insertFavoriteMovie(Movie movie);

  @Query("SELECT * FROM movies where id = :id")
  LiveData<Movie> loadMovieById(int id);
}
