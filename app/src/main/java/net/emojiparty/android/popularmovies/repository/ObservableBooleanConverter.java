package net.emojiparty.android.popularmovies.repository;

import android.arch.persistence.room.TypeConverter;
import android.databinding.ObservableBoolean;
import java.util.Date;

public class ObservableBooleanConverter {
  @TypeConverter public static boolean toBoolean(ObservableBoolean observableBoolean) {
    return observableBoolean.get();
  }

  @TypeConverter public static ObservableBoolean toObservableBoolean(boolean bool) {
    return new ObservableBoolean(bool);
  }
}
