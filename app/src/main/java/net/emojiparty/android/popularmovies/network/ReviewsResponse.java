package net.emojiparty.android.popularmovies.network;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import net.emojiparty.android.popularmovies.models.Review;

public class ReviewsResponse {
  private Integer page;
  @SerializedName("total_pages")
  private Integer totalPages;
  @SerializedName("total_results")
  private Integer totalResults;
  private List<Review> results;

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(Integer totalPages) {
    this.totalPages = totalPages;
  }

  public Integer getTotalResults() {
    return totalResults;
  }

  public void setTotalResults(Integer totalResults) {
    this.totalResults = totalResults;
  }

  public List<Review> getResults() {
    return results;
  }

  public void setResults(List<Review> results) {
    this.results = results;
  }
}
