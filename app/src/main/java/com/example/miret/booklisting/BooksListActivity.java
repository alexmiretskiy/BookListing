package com.example.miret.booklisting;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class BooksListActivity extends AppCompatActivity implements LoaderCallbacks<List<Book>> {

  private static final int BOOK_LOADER_ID = 1;
  private BookAdapter adapter;
  String requestUrl;

  @Override

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.books_list_activity);

    Intent intent = getIntent();
    requestUrl = intent.getStringExtra("requestUrl");

    ListView bookListView = (ListView) findViewById(R.id.list_view);
    adapter = new BookAdapter(this, new ArrayList<Book>());
    bookListView.setAdapter(adapter);
    getLoaderManager().initLoader(BOOK_LOADER_ID, null, this);
  }

  @Override
  public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
    return new BookLoader(this, requestUrl);
  }

  @Override
  public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
    adapter.clear();
    if (books != null && !books.isEmpty()) {
      adapter.addAll(books);
    }
  }

  @Override
  public void onLoaderReset(Loader<List<Book>> loader) {
    adapter.clear();
  }
}
