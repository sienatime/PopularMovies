package net.emojiparty.android.popularmovies;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

//https://android.jlelse.eu/loading-images-with-data-binding-and-picasso-555dad683fdc
public class BindingMethods {
  @BindingAdapter({"imageUrl"})
  public static void loadImageFromUrl(ImageView view, String url) {
    Picasso.get()
        .load(url)
        .placeholder(R.color.primaryColor)
        .error(R.color.secondaryColor)
        .into(view);
  }
}
