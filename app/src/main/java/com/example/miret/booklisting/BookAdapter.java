package com.example.miret.booklisting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {

  public BookAdapter(Context context, List<Book> books) {
    super(context, 0, books);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View listItemView = convertView;

    if (listItemView == null) {
      listItemView = LayoutInflater.from(getContext())
          .inflate(R.layout.book_list_item, parent, false);
    }
      Book currentBook = getItem(position);

      TextView titleTextView = (TextView) listItemView.findViewById(R.id.title_text_view);
      titleTextView.setText(String.format("%s.", currentBook.getTitle()));

      TextView authorsTextView = (TextView) listItemView.findViewById(R.id.author_text_view);
      authorsTextView.setText(getAuthorsString(currentBook.getAuthors()));

      ImageView posterImageView = (ImageView) listItemView.findViewById(R.id.poster_image_view);
      if (currentBook.getPoster() != null) {
        Picasso.with(getContext()).load(currentBook.getPoster()).into(posterImageView);
      }

    return listItemView;
  }

  String getAuthorsString(String[] authorsArray) {
    StringBuilder output = new StringBuilder();
    String[] authors = authorsArray;
    for (int i = 0; i < authors.length; i++) {
      if (authors.length > 1 && i < authors.length - 1) {
        output.append(authors[i] + ", ");
      } else {
        output.append(authors[i] + ".");
      }
    }
    return output.toString();
  }
}
