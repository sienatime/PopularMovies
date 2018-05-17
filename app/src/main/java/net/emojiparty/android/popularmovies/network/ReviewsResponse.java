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
}
