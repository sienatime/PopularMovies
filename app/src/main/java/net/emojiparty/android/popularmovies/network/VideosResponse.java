package net.emojiparty.android.popularmovies.network;

import java.util.ArrayList;
import java.util.List;
import net.emojiparty.android.popularmovies.models.Trailer;

public class VideosResponse {
  private List<Trailer> results;

  public VideosResponse() {
    results = new ArrayList<>();
  }

  public List<Trailer> getResults() {
    return results;
  }

  public void setResults(List<Trailer> results) {
    this.results = results;
  }
}
