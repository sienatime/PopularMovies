package net.emojiparty.android.popularmovies.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import net.emojiparty.android.popularmovies.R;
import net.emojiparty.android.popularmovies.models.Movie;

public class MoviesAdapter extends RecyclerView.Adapter {
  private List<Movie> movies;

  public MoviesAdapter(List<Movie> movies) {
    this.movies = movies;
  }

  @NonNull @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie, parent, false);
    return new MovieViewHolder(view);
  }

  @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    Movie movie = movies.get(position);
    MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
    movieViewHolder.movieTitle.setText(movie.getTitle());
  }

  @Override public int getItemCount() {
    return movies.size();
  }

  static class MovieViewHolder extends RecyclerView.ViewHolder {
    public TextView movieTitle;
    public ImageView moviePoster;

    MovieViewHolder(View view) {
      super(view);
      movieTitle = view.findViewById(R.id.list_item_movie_title);
      moviePoster = view.findViewById(R.id.list_item_movie_poster);
    }
  }
}
