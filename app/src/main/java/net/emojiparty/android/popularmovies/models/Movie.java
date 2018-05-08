package net.emojiparty.android.popularmovies.models;

import com.google.gson.annotations.SerializedName;
import java.text.ParseException;
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

  public static Movie offlineMovie() {
    Movie movie = new Movie();
    movie.setTitle("Avengers: Infinity War");
    movie.setPosterPath("/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg");
    movie.setOverview("As the Avengers and their allies have continued to protect the world from threats too large for any one hero to handle, a new danger has emerged from the cosmic shadows: Thanos. A despot of intergalactic infamy, his goal is to collect all six Infinity Stones, artifacts of unimaginable power, and use them to inflict his twisted will on all of reality. Everything the Avengers have fought for has led up to this moment - the fate of Earth and existence itself has never been more uncertain.");
    SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
    try {
      movie.setReleaseDate(dateFormat.parse("2018-04-25"));
    } catch (ParseException e) {
      movie.setReleaseDate(new Date());
    }
    movie.setVoteAverage(8.7f);
    return movie;
  }

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
