package net.emojiparty.android.popularmovies.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import net.emojiparty.android.popularmovies.R;
import net.emojiparty.android.popularmovies.adapters.DataBindingAdapter;
import net.emojiparty.android.popularmovies.models.Movie;
import net.emojiparty.android.popularmovies.models.Review;
import net.emojiparty.android.popularmovies.network.ReviewsResponse;
import net.emojiparty.android.popularmovies.network.TheMovieDb;
import net.emojiparty.android.popularmovies.viewmodels.ListViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static net.emojiparty.android.popularmovies.activities.DetailMovieActivity.MOVIE_FOR_DETAIL;

public class ReviewsFragment extends Fragment {
  private DataBindingAdapter listAdapter;
  private ListViewModel listViewModel;
  private ProgressBar loadingIndicator;
  private TextView noReviews;

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_reviews, container, false);
    loadingIndicator = view.findViewById(R.id.reviews_loading);
    noReviews = view.findViewById(R.id.no_reviews);
    instantiateRecyclerView(view);
    setupViewModel();
    loadInitialReviews();
    return view;
  }

  private void loadInitialReviews() {
    if (listViewModel.getList().getValue() == null || listViewModel.getList().getValue().size() == 0) {
      Movie movie = getArguments().getParcelable(MOVIE_FOR_DETAIL);
      loadReviews(movie);
    }
  }

  private void setupViewModel() {
    listViewModel = ViewModelProviders.of(ReviewsFragment.this).get(ListViewModel.class);
    listViewModel.getList().observe(ReviewsFragment.this, new Observer<List<?>>() {
      @Override public void onChanged(@Nullable List<?> reviews) {
        listAdapter.setItems(reviews);
      }
    });
  }

  private void loadReviews(Movie movie) {
    startLoading();
    TheMovieDb theMovieDb = new TheMovieDb();
    theMovieDb.loadReviewsForMovie(movie.getId()).enqueue(new Callback<ReviewsResponse>() {
      @Override
      public void onResponse(Call<ReviewsResponse> call, Response<ReviewsResponse> response) {
        stopLoading();
        if (response.isSuccessful()) {
          List<Review> reviews = response.body().getResults();
          listViewModel.setList(reviews);
          noReviews.setVisibility(reviews.size() == 0 ? View.VISIBLE : View.INVISIBLE);
        } else {
          showError(response.toString());
        }
      }

      @Override public void onFailure(Call<ReviewsResponse> call, Throwable t) {
        stopLoading();
        showError(t.getMessage());
      }
    });
  }

  private void instantiateRecyclerView(View view) {
    RecyclerView reviewsRecyclerView = view.findViewById(R.id.reviews_recycler_view);
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    reviewsRecyclerView.setLayoutManager(layoutManager);
    listAdapter = new DataBindingAdapter(R.layout.list_item_review);
    reviewsRecyclerView.setAdapter(listAdapter);
  }

  private void showError(String message) {
    Toast.makeText(getContext(), getString(R.string.error, message), Toast.LENGTH_LONG)
        .show();
  }

  private void startLoading() {
    loadingIndicator.setVisibility(View.VISIBLE);
  }

  private void stopLoading() {
    loadingIndicator.setVisibility(View.INVISIBLE);
  }
}
