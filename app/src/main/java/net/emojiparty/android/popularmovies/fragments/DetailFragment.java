package net.emojiparty.android.popularmovies.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import net.emojiparty.android.popularmovies.BR;
import net.emojiparty.android.popularmovies.R;
import net.emojiparty.android.popularmovies.databinding.FragmentDetailBinding;
import net.emojiparty.android.popularmovies.models.Movie;
import net.emojiparty.android.popularmovies.models.MoviePresenter;

import static net.emojiparty.android.popularmovies.activities.DetailMovieActivity.MOVIE_FOR_DETAIL;

public class DetailFragment extends Fragment {
  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    FragmentDetailBinding binding =
        DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
    Movie movie = getArguments().getParcelable(MOVIE_FOR_DETAIL);
    binding.setVariable(BR.presenter, new MoviePresenter(movie, getContext()));
    return binding.getRoot();
  }
}
