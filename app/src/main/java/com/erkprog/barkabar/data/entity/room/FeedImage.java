package com.erkprog.barkabar.data.entity.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class FeedImage {

  @NonNull
  @PrimaryKey
  String id;

  String path;

  public FeedImage() {

  }

  @Ignore
  public FeedImage(String id, String path) {
    this.id = id;
    this.path = path;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }
}
