package net.emojiparty.android.popularmovies.repository;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

// from Udacity Exercises 9b To-do app

public class DateConverter {
  @TypeConverter
  public static Date toDate(Long timestamp) {
    return timestamp == null ? null : new Date(timestamp);
  }

  @TypeConverter
  public static Long toTimestamp(Date date) {
    return date == null ? null : date.getTime();
  }
}
