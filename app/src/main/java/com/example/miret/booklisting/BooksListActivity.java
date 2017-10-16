package com.example.miret.booklisting;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class BooksListActivity extends AppCompatActivity implements LoaderCallbacks<List<Book>> {

  private static final int BOOK_LOADER_ID = 1;
  private BookAdapter adapter;
  String requestUrl;

  TextView emptyStateTextView;

  @Override

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.books_list_activity);

    Intent intent = getIntent();
    requestUrl = intent.getStringExtra("requestUrl");

    ListView bookListView = (ListView) findViewById(R.id.list_view);

    emptyStateTextView = (TextView) findViewById(R.id.empty_view);
    bookListView.setEmptyView(emptyStateTextView);

    adapter = new BookAdapter(this, new ArrayList<Book>());
    bookListView.setAdapter(adapter);

    bookListView.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Book currentBook = adapter.getItem(position);

        Uri bookUri = Uri.parse(currentBook.getInfoLink());

        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);
        startActivity(websiteIntent);
      }
    });

    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(
        Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

    if (networkInfo != null && networkInfo.isConnected()) {
      getLoaderManager().initLoader(BOOK_LOADER_ID, null, this);
    } else {
      View loadingIndicator = findViewById(R.id.loading_indicator);
      loadingIndicator.setVisibility(View.GONE);

      emptyStateTextView.setText(R.string.No_internet_connection);
    }
  }

  @Override
  public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
    return new BookLoader(this, requestUrl);
  }

  @Override
  public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
    View loadingIndicator = findViewById(R.id.loading_indicator);
    loadingIndicator.setVisibility(View.GONE);

    emptyStateTextView.setText(R.string.No_books_found);
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
