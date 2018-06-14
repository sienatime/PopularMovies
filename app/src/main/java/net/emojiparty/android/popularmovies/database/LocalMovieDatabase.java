package net.emojiparty.android.popularmovies.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// referenced Sunshine 9.1 Content Provider Foundation
public class LocalMovieDatabase extends SQLiteOpenHelper {
  private static final int VERSION = 0;
  private static final String DATABASE_NAME = "popularMovies.db";

  public LocalMovieDatabase(Context context) {
    super(context, DATABASE_NAME, null, VERSION);
  }

  @Override public void onCreate(SQLiteDatabase sqLiteDatabase) {
    final String SQL_CREATE_TABLE =

        "CREATE TABLE " + LocalMovieRow.TABLE_NAME + " (" +

            /*
             * WeatherEntry did not explicitly declare a column called "_ID". However,
             * WeatherEntry implements the interface, "BaseColumns", which does have a field
             * named "_ID". We use that here to designate our table's primary key.
             */
            LocalMovieRow._ID               + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

            LocalMovieRow.COLUMN_TMDB_ID       + " VARCHAR(255) NOT NULL, "                 +

            LocalMovieRow.COLUMN_TITLE + " VARCHAR(255) NOT NULL,"                  +

            LocalMovieRow.COLUMN_OVERVIEW   + " REAL NOT NULL, "                    +
            LocalMovieRow.COLUMN_POSTER_PATH   + " VARCHAR(255) NOT NULL, "                    +

            LocalMovieRow.COLUMN_VOTE_AVERAGE   + " FLOAT NOT NULL, "                    +
            LocalMovieRow.COLUMN_RELEASE_DATE   + " INT NOT NULL, "                    +

            " UNIQUE (" + LocalMovieRow.COLUMN_TMDB_ID + ") ON CONFLICT REPLACE);";

    sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
  }

  @Override public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

  }
}
