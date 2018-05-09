package net.emojiparty.android.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Movie implements Parcelable {
  private String title;
  private String overview;

  @SerializedName("poster_path")
  private String posterPath;

  @SerializedName("vote_average")
  private float voteAverage;

  @SerializedName("release_date")
  private Date releaseDate;

  public Movie() { }

  private Movie(Parcel parcel) {
    this.title = parcel.readString();
    this.overview = parcel.readString();
    this.posterPath = parcel.readString();
    this.voteAverage = parcel.readFloat();
    this.releaseDate = new Date(parcel.readLong());
  }

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

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(this.title);
    parcel.writeString(this.overview);
    parcel.writeString(this.posterPath);
    parcel.writeFloat(this.voteAverage);
    parcel.writeLong(this.releaseDate.getTime());
  }

  // https://guides.codepath.com/android/using-parcelable
  public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
    @Override public Movie createFromParcel(Parcel parcel) {
      return new Movie(parcel);
    }

    @Override public Movie[] newArray(int i) {
      return new Movie[i];
    }
  };
}
