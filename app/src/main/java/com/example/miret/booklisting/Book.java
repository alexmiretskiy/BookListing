package com.example.miret.booklisting;

class Book {

  private String mTitle;
  private String mPoster;
  private String[] mAuthors;
  private String mInfoLink;

  Book(String mTitle, String mPoster, String[] mAuthors, String mInfoLink) {
    this.mTitle = mTitle;
    this.mPoster = mPoster;
    this.mAuthors = mAuthors;
    this.mInfoLink = mInfoLink;
  }

  String getPoster() {
    return mPoster;
  }

  String getTitle() {
    return mTitle;
  }

  String[] getAuthors() {
    return mAuthors;
  }

  String getInfoLink() {
    return mInfoLink;
  }
}

