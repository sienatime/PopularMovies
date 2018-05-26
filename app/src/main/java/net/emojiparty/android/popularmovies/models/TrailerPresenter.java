package net.emojiparty.android.popularmovies.models;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class TrailerPresenter {
  private Trailer trailer;
  private Context context;
  public String name;
  public String type;
  public String site;
  private final String YOUTUBE = "YouTube";

  public TrailerPresenter(Trailer trailer, Context context) {
    this.trailer = trailer;
    this.context = context;
    this.name = trailer.getName();
    this.type = trailer.getType();
    this.site = trailer.getSite();
  }

  //https://stackoverflow.com/questions/574195/android-youtube-app-play-video-intent
  public void onTrailerClicked() {
    Intent webIntent = new Intent(Intent.ACTION_VIEW,
        Uri.parse("http://www.youtube.com/watch?v=" + this.trailer.getKey()));

    if (type.equals(YOUTUBE)) {
      Intent youTubeIntent =
          new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + this.trailer.getKey()));
      try {
        context.startActivity(youTubeIntent);
      } catch (ActivityNotFoundException ex) {
        context.startActivity(webIntent);
      }
    } else {
      context.startActivity(webIntent);
    }
  }
}
