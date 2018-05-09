package net.emojiparty.android.popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import net.emojiparty.android.popularmovies.R;
import net.emojiparty.android.popularmovies.BR;
import net.emojiparty.android.popularmovies.activities.DetailMovieActivity;
import net.emojiparty.android.popularmovies.databinding.ListItemMovieBinding;
import net.emojiparty.android.popularmovies.models.Movie;

public class MoviesAdapter extends RecyclerView.Adapter {
  private List<Movie> movies;

  public MoviesAdapter(List<Movie> movies) {
    this.movies = movies;
  }

  @NonNull @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    Context context = parent.getContext();
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    ListItemMovieBinding binding =
        DataBindingUtil.inflate(layoutInflater, R.layout.list_item_movie, parent, false);
    return new MovieViewHolder(binding, context);
  }

  @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
    movieViewHolder.bind(movies.get(position));
  }

  @Override public int getItemCount() {
    return movies.size();
  }

  // https://medium.com/google-developers/android-data-binding-recyclerview-db7c40d9f0e4
  static class MovieViewHolder extends RecyclerView.ViewHolder {
    ListItemMovieBinding binding;
    Movie movie;
    Context context;

    MovieViewHolder(ListItemMovieBinding binding, Context context) {
      super(binding.getRoot());
      this.binding = binding;
      this.context = context;
    }

    void bind(Movie movie) {
      this.movie = movie;
      binding.setVariable(BR.movie, movie);
      binding.executePendingBindings();
      setOnClickListener();
    }

    void setOnClickListener() {
      binding.getRoot().setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          Intent detailIntent = new Intent(context, DetailMovieActivity.class);
          detailIntent.putExtra(DetailMovieActivity.MOVIE_FOR_DETAIL, movie);
          context.startActivity(detailIntent);
        }
      });
    }
  }
}
