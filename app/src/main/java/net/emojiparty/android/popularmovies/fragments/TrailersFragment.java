package net.emojiparty.android.popularmovies.fragments;

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
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import net.emojiparty.android.popularmovies.R;
import net.emojiparty.android.popularmovies.adapters.DataBindingAdapter;
import net.emojiparty.android.popularmovies.models.Movie;
import net.emojiparty.android.popularmovies.models.Trailer;
import net.emojiparty.android.popularmovies.network.TheMovieDb;
import net.emojiparty.android.popularmovies.network.VideosResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrailersFragment extends Fragment {
  private List<Trailer> trailers = new ArrayList<>();
  private DataBindingAdapter listAdapter;
  private ProgressBar loadingIndicator;

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_trailers, container, false);
    loadingIndicator = view.findViewById(R.id.trailers_loading);
    instantiateRecyclerView(view);
    loadOneTrailer();
    //Movie movie = getArguments().getParcelable(MOVIE_FOR_DETAIL);
    //loadTrailers(movie);
    return view;
  }

  private void loadTrailers(Movie movie) {
    startLoading();
    TheMovieDb theMovieDb = new TheMovieDb();
    theMovieDb.loadTrailersForMovie(movie.getId()).enqueue(new Callback<VideosResponse>() {
      @Override
      public void onResponse(Call<VideosResponse> call, Response<VideosResponse> response) {
        stopLoading();
        if (response.isSuccessful()) {
          trailers.clear();
          trailers.addAll(response.body().getResults());
          listAdapter.notifyDataSetChanged();
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

  private void instantiateRecyclerView(View view) {
    RecyclerView reviewsRecyclerView = view.findViewById(R.id.trailers_recycler_view);
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    reviewsRecyclerView.setLayoutManager(layoutManager);
    listAdapter = new DataBindingAdapter(trailers, R.layout.list_item_trailer);
    reviewsRecyclerView.setAdapter(listAdapter);
  }

  private void loadOneTrailer() {
    Trailer trailer = new Trailer();
    trailer.setId("5a200baa925141033608f5f0");
    trailer.setKey("6ZfuNTqbHE8");
    trailer.setName("Official Trailer");
    trailer.setSite("YouTube");
    trailer.setType("Trailer");
    trailers.add(trailer);
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
