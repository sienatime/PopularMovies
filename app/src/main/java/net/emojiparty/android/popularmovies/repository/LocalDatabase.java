package net.emojiparty.android.popularmovies.repository;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import net.emojiparty.android.popularmovies.models.Movie;

// referenced Udacity Exercises 9b To-do app

@Database(entities = { Movie.class }, version = 5, exportSchema = false)
@TypeConverters({ DateConverter.class })
public abstract class LocalDatabase extends RoomDatabase {
  private static final Object LOCK = new Object();
  private static final String DATABASE_NAME = "popular_movies";
  private static LocalDatabase instance;

  public static LocalDatabase getInstance(Context context) {
    if (instance == null) {
      synchronized (LOCK) {
        instance = Room.databaseBuilder(context.getApplicationContext(), LocalDatabase.class,
            LocalDatabase.DATABASE_NAME).fallbackToDestructiveMigration().build();
      }
    }
    return instance;
  }

  public abstract MovieDao movieDao();
}
