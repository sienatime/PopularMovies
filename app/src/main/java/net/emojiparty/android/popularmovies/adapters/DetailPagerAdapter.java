package net.emojiparty.android.popularmovies.adapters;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import net.emojiparty.android.popularmovies.R;
import net.emojiparty.android.popularmovies.fragments.DetailFragment;
import net.emojiparty.android.popularmovies.models.Movie;

import static net.emojiparty.android.popularmovies.activities.DetailMovieActivity.MOVIE_FOR_DETAIL;

public class DetailPagerAdapter extends FragmentPagerAdapter {
  private static final int NUMBER_OF_TABS = 3;
  private Movie movie;
  private String[] titles;

  public DetailPagerAdapter(FragmentManager fm, Movie movie, Resources resources) {
    super(fm);
    this.movie = movie;
    this.titles = new String[] {
        resources.getString(R.string.details),
        resources.getString(R.string.trailers),
        resources.getString(R.string.reviews)
    };
  }

  @Override public Fragment getItem(int position) {
    Fragment fragment = new DetailFragment();
    Bundle bundle = new Bundle();
    bundle.putParcelable(MOVIE_FOR_DETAIL, movie);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Nullable @Override public CharSequence getPageTitle(int position) {
    return titles[position];
  }

  @Override public int getCount() {
    return NUMBER_OF_TABS;
  }
}
