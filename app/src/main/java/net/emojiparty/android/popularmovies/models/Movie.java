package net.emojiparty.android.popularmovies.models;

import com.google.gson.annotations.SerializedName;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Movie {
  private String title;
  private String overview;

  @SerializedName("poster_path")
  private String posterPath;

  @SerializedName("vote_average")
  private float voteAverage;

  @SerializedName("release_date")
  private Date releaseDate;

  public Movie() { }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getPosterPath() {
    return "https://image.tmdb.org/t/p/w500" + posterPath;
  }

  public void setPosterPath(String posterPath) {
    this.posterPath = posterPath;
  }

  public String getOverview() {
    return overview;
  }

  public void setOverview(String overview) {
    this.overview = overview;
  }

  public float getVoteAverage() {
    return voteAverage;
  }

  public void setVoteAverage(float voteAverage) {
    this.voteAverage = voteAverage;
  }

  public Date getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(Date releaseDate) {
    this.releaseDate = releaseDate;
  }

  public String formattedReleaseDate() {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd, yyyy");
    return simpleDateFormat.format(this.releaseDate);
  }

}
