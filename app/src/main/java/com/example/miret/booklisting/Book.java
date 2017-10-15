package com.example.miret.booklisting;

public class Book {

  private String mTitle;
  private String mPoster;


  public Book(String mTitle, String mPoster) {
    this.mTitle = mTitle;
    this.mPoster = mPoster;
  }

  public String getPoster() {
    return mPoster;
  }

  public String getTitle() {
    return mTitle;
  }
}
