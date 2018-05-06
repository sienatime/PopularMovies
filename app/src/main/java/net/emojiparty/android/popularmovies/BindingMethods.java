package net.emojiparty.android.popularmovies;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

//https://android.jlelse.eu/loading-images-with-data-binding-and-picasso-555dad683fdc
public class BindingMethods {
  @BindingAdapter({"bind:imageUrl"})
  public static void loadImageFromUrl(ImageView view, String url) {
    Picasso.get()
        .load(url)
        .placeholder(R.color.colorPrimaryDark)
        .error(R.color.colorAccent)
        .into(view);
  }
}
