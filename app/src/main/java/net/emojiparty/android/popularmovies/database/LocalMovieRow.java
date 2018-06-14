package net.emojiparty.android.popularmovies.database;

import android.provider.BaseColumns;

public class LocalMovieRow implements BaseColumns {
  public static final String TABLE_NAME = "movies";
  public static final String COLUMN_TMDB_ID = "tmdb_id";
  public static final String COLUMN_TITLE = "title";
  public static final String COLUMN_OVERVIEW = "overview";
  public static final String COLUMN_POSTER_PATH = "poster_path";
  public static final String COLUMN_VOTE_AVERAGE = "vote_average";
  public static final String COLUMN_RELEASE_DATE = "release_date";
}
