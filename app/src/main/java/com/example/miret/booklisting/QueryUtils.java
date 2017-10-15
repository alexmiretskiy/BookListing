package com.example.miret.booklisting;

import android.text.TextUtils;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class QueryUtils {

  public static final String LOG_TAG = QueryUtils.class.getSimpleName();

  private QueryUtils() {
  }

  public static List<Book> fetchBookData(String requestUrl) {
    URL url = createUrl(requestUrl);
    String jsonResponse = null;
    try {
      jsonResponse = makeHttpRequest(url);
    } catch (IOException e) {
      Log.e(LOG_TAG, "Problem making the HTTP request", e);
    }

    return extractFeatureFromJson(jsonResponse);
  }

  private static URL createUrl(String stringUrl) {
    URL url = null;
    try {
      url = new URL(stringUrl);
    } catch (MalformedURLException e) {
      Log.e(LOG_TAG, "Problem building the URL", e);
    }
    return url;
  }

  private static String makeHttpRequest(URL url) throws IOException {
    String jsonResponse = "";
    if (url == null) {
      return jsonResponse;
    }

    HttpURLConnection urlConnection = null;
    InputStream inputStream = null;
    try {
      urlConnection = (HttpURLConnection) url.openConnection();
      urlConnection.setReadTimeout(1000);
      urlConnection.setConnectTimeout(15000);
      urlConnection.setRequestMethod("GET");
      urlConnection.connect();

      if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        inputStream = urlConnection.getInputStream();
        jsonResponse = readFromStream(inputStream);
      } else {
        Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
      }
    } catch (IOException e) {
      Log.e(LOG_TAG, "Problem retrieving the book JSON results.", e);
    } finally {
      if (urlConnection != null) {
        urlConnection.disconnect();
      }
      if (inputStream != null) {
        inputStream.close();
      }
    }
    return jsonResponse;
  }

  private static String readFromStream(InputStream inputStream) throws IOException {
    StringBuilder output = new StringBuilder();
    if (inputStream != null) {
      InputStreamReader inputStreamReader = new InputStreamReader(inputStream,
          Charset.forName("UTF-8"));
      BufferedReader reader = new BufferedReader(inputStreamReader);
      String line = reader.readLine();
      while (line != null) {
        output.append(line);
        line = reader.readLine();
      }
    }
    return output.toString();
  }

  private static List<Book> extractFeatureFromJson(String booksJSON) {
    if (TextUtils.isEmpty(booksJSON)) {
      return null;
    }

    List<Book> books = new ArrayList<>();

    try {
      JSONObject baseJsonResponse = new JSONObject(booksJSON);

      JSONArray booksArray = baseJsonResponse.getJSONArray("items");

      for (int i = 0; i < booksArray.length(); i++) {
        JSONObject currentBook = booksArray.getJSONObject(i);

        JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");

        String title = volumeInfo.getString("title");
        String poster;
        try {
          JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
          poster = imageLinks.getString("smallThumbnail");
        } catch (JSONException e) {
          poster = null;
        }

        Book book = new Book(title, poster);
        books.add(book);
      }
    } catch (JSONException e) {
      Log.e(LOG_TAG, "Problem parsing the book JSON results", e);
    }
    return books;
  }
}
