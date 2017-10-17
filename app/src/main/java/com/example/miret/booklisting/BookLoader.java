package com.example.miret.booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;
import java.util.List;

class BookLoader extends AsyncTaskLoader<List<Book>> {

  private String mUrl;

  BookLoader(Context context, String mUrl) {
    super(context);
    this.mUrl = mUrl;
  }

  @Override
  protected void onStartLoading() {
    forceLoad();
  }

  @Override
  public List<Book> loadInBackground() {
    if (mUrl == null) {
      return null;
    }
    return QueryUtils.fetchBookData(mUrl);
  }
}
