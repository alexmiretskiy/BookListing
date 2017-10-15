package com.example.miret.booklisting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {

  public BookAdapter(@NonNull Context context, @NonNull List<Book> books) {
    super(context, 0, books);
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    View listItemView = convertView;

    if (listItemView == null) {
      listItemView = LayoutInflater.from(getContext())
          .inflate(R.layout.book_list_item, parent, false);

      Book currentBook = getItem(position);

      TextView titleTextView = (TextView) listItemView.findViewById(R.id.title_text_view);

      titleTextView.setText(currentBook.getTitle());

      ImageView posterImageView = (ImageView) listItemView.findViewById(R.id.poster_image_view);
      if (currentBook.getPoster() != null ) {
        Picasso.with(getContext()).load(currentBook.getPoster()).into(posterImageView);
      }
    }
    return listItemView;
  }
}
