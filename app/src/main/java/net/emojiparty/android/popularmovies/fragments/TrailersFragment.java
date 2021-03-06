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
import java.util.ArrayList;
import java.util.List;
import net.emojiparty.android.popularmovies.R;
import net.emojiparty.android.popularmovies.adapters.DataBindingAdapter;
import net.emojiparty.android.popularmovies.models.Movie;
import net.emojiparty.android.popularmovies.models.Trailer;
import net.emojiparty.android.popularmovies.network.TheMovieDb;
import net.emojiparty.android.popularmovies.network.VideosResponse;
import net.emojiparty.android.popularmovies.presenters.TrailerPresenter;
import net.emojiparty.android.popularmovies.viewmodels.ListViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static net.emojiparty.android.popularmovies.activities.DetailMovieActivity.MOVIE_FOR_DETAIL;

public class TrailersFragment extends Fragment {
  private DataBindingAdapter listAdapter;
  private ListViewModel listViewModel;
  private ProgressBar loadingIndicator;
  private TextView noTrailers;

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_trailers, container, false);
    loadingIndicator = view.findViewById(R.id.trailers_loading);
    noTrailers = view.findViewById(R.id.no_trailers);
    instantiateRecyclerView(view);
    setupViewModel();
    loadInitialTrailers();
    return view;
  }

  private void loadInitialTrailers() {
    if (listViewModel.getList().getValue() == null || listViewModel.getList().getValue().size() == 0) {
      Movie movie = getArguments().getParcelable(MOVIE_FOR_DETAIL);
      loadTrailers(movie);
    }
  }

  private void setupViewModel() {
    listViewModel = ViewModelProviders.of(TrailersFragment.this).get(ListViewModel.class);
    listViewModel.getList().observe(TrailersFragment.this, new Observer<List<?>>() {
      @Override public void onChanged(@Nullable List<?> trailers) {
        listAdapter.setItems(trailers);
      }
    });
  }

  private void loadTrailers(Movie movie) {
    startLoading();
    TheMovieDb theMovieDb = new TheMovieDb();
    theMovieDb.loadTrailersForMovie(movie.getId()).enqueue(new Callback<VideosResponse>() {
      @Override
      public void onResponse(Call<VideosResponse> call, Response<VideosResponse> response) {
        stopLoading();
        if (response.isSuccessful()) {
          List<TrailerPresenter> trailers = mapTrailersToPresenters(response.body().getResults());
          listViewModel.setList(trailers);
          noTrailers.setVisibility(trailers.size() == 0 ? View.VISIBLE : View.INVISIBLE);
        } else {
          stopLoading();
          showError(response.toString());
        }
      }

      @Override public void onFailure(Call<VideosResponse> call, Throwable t) {
        stopLoading();
        showError(t.getMessage());
      }
    });
  }

  private List<TrailerPresenter> mapTrailersToPresenters(List<Trailer> trailers) {
    List<TrailerPresenter> presenters = new ArrayList<>();
    for (int i = 0; i < trailers.size(); i++) {
      Trailer trailer = trailers.get(i);
      presenters.add(new TrailerPresenter(trailer, getContext()));
    }
    return presenters;
  }

  private void instantiateRecyclerView(View view) {
    RecyclerView reviewsRecyclerView = view.findViewById(R.id.trailers_recycler_view);
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    reviewsRecyclerView.setLayoutManager(layoutManager);
    listAdapter = new DataBindingAdapter(R.layout.list_item_trailer);
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
