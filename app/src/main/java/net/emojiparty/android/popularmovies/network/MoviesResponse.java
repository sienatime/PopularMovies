package net.emojiparty.android.popularmovies.network;

import java.util.ArrayList;
import java.util.List;
import net.emojiparty.android.popularmovies.models.Movie;

public class MoviesResponse {
  private Integer page;
  private List<Movie> results;

  public MoviesResponse() {
    results = new ArrayList<>();
  }

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public List<Movie> getResults() {
    return results;
  }

  public void setResults(List<Movie> results) {
    this.results = results;
  }
}
