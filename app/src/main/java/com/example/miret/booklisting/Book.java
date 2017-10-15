package com.example.miret.booklisting;

public class Book {

  private String mTitle;
  private String mPoster;
  private String[] mAuthors;

  public Book(String mTitle, String mPoster, String[] authors) {
    this.mTitle = mTitle;
    this.mPoster = mPoster;
    this.mAuthors = authors;
  }

  public String getPoster() {
    return mPoster;
  }

  public String getTitle() {
    return mTitle;
  }

  public String[] getAuthors() {
    return mAuthors;
  }
}

