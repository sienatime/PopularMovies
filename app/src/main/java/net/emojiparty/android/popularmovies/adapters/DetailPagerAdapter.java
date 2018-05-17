package net.emojiparty.android.popularmovies.adapters;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import net.emojiparty.android.popularmovies.R;
import net.emojiparty.android.popularmovies.models.Movie;

import static net.emojiparty.android.popularmovies.activities.DetailMovieActivity.MOVIE_FOR_DETAIL;

public class DetailPagerAdapter extends FragmentPagerAdapter {
  private static final int NUMBER_OF_TABS = 3;
  private Movie movie;
  private String[] titles;
  private Class<?>[] fragments;

  public DetailPagerAdapter(FragmentManager fragmentManager, Movie movie, Resources resources) {
    super(fragmentManager);
    this.movie = movie;
    try {
      this.fragments = new Class<?>[]{
          Class.forName("net.emojiparty.android.popularmovies.fragments.DetailFragment"),
          Class.forName("net.emojiparty.android.popularmovies.fragments.TrailersFragment"),
          Class.forName("net.emojiparty.android.popularmovies.fragments.ReviewsFragment")
      };
    } catch (ClassNotFoundException e) {
      this.fragments = new Class<?>[]{};
    }
    this.titles = new String[] {
        resources.getString(R.string.details),
        resources.getString(R.string.trailers),
        resources.getString(R.string.reviews)
    };
  }

  @Override public Fragment getItem(int position) {
    try {
      Fragment fragment = (Fragment) fragments[position].newInstance();
      Bundle bundle = new Bundle();
      bundle.putParcelable(MOVIE_FOR_DETAIL, movie);
      fragment.setArguments(bundle);
      return fragment;
    }
    catch (InstantiationException | IllegalArgumentException | IllegalAccessException e) {
      return null;
    }
  }

  @Nullable @Override public CharSequence getPageTitle(int position) {
    return titles[position];
  }

  @Override public int getCount() {
    return NUMBER_OF_TABS;
  }
}
