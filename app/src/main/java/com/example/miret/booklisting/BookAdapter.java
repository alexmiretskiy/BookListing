package com.example.miret.booklisting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

class BookAdapter extends ArrayAdapter<Book> {

  BookAdapter(Context context, List<Book> books) {
    super(context, 0, books);
  }

  @NonNull
  @Override
  public View getView(int position, View convertView, @NonNull ViewGroup parent) {
    View listItemView = convertView;

    if (listItemView == null) {
      listItemView = LayoutInflater.from(getContext())
          .inflate(R.layout.book_list_item, parent, false);
    }
    Book currentBook = getItem(position);

    TextView titleTextView = (TextView) listItemView.findViewById(R.id.title_text_view);
    titleTextView.setText(currentBook.getTitle());

    TextView authorsTextView = (TextView) listItemView.findViewById(R.id.author_text_view);
    authorsTextView.setText(getAuthorsString(currentBook.getAuthors()));

    ImageView posterImageView = (ImageView) listItemView.findViewById(R.id.poster_image_view);

    new DownloadImageTask(posterImageView).execute(currentBook.getPoster());
//    if (currentBook.getPoster() != null) {
//      Picasso.with(getContext()).load(currentBook.getPoster()).into(posterImageView);
//    }

    return listItemView;
  }

  private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    ImageView thumbnailImageView;

    DownloadImageTask(ImageView thumbnailImageView) {
      this.thumbnailImageView = thumbnailImageView;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
      String urlDisplay = urls[0];
      Bitmap bitmapImage = null;

      try {
        InputStream inputStream = new java.net.URL(urlDisplay).openStream();
        bitmapImage = BitmapFactory.decodeStream(inputStream);
      } catch (IOException e) {
        Log.e(BookAdapter.class.getSimpleName(), "Image transmission error", e);
      }
      return bitmapImage;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
      thumbnailImageView.setImageBitmap(result);
    }
  }

  private String getAuthorsString(String[] authorsArray) {
    if (authorsArray != null) {
      StringBuilder output = new StringBuilder();
      for (int i = 0; i < authorsArray.length; i++) {
        if (authorsArray.length > 1 && i < authorsArray.length - 1) {
          output.append(authorsArray[i]).append(", ");
        } else {
          output.append(authorsArray[i]).append(".");
        }
      }
      return output.toString();
    } else {
      return null;
    }
  }
}
