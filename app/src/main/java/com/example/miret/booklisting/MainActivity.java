package com.example.miret.booklisting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

  private String primaryRequestUrl = "https://www.googleapis.com/books/v1/volumes?q=example&maxResults=30";

  Button button;

  @Override

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    button = (Button) findViewById(R.id.button);

    button.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        EditText editText = (EditText) findViewById(R.id.edit_text_view);
        String findWord = editText.getText().toString();
        String finalRequest = primaryRequestUrl.replace("example", findWord);
        Intent booksListActivityIntent = new Intent(MainActivity.this, BooksListActivity.class);
        booksListActivityIntent.putExtra("requestUrl", finalRequest);
        startActivity(booksListActivityIntent);
      }
    });
  }
}
