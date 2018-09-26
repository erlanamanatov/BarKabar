package com.erkprog.barkabar.data.entity.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class FeedItem {

  @PrimaryKey
  private String guid;
  private String title;
  private String description;
  private String imgPath;
  private String feedSource;

  public FeedItem() {

  }

  public String getGuid() {
    return guid;
  }

  public void setGuid(String guid) {
    this.guid = guid;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getImgPath() {
    return imgPath;
  }

  public void setImgPath(String imgPath) {
    this.imgPath = imgPath;
  }

  public String getFeedSource() {
    return feedSource;
  }

  public void setFeedSource(String feedSource) {
    this.feedSource = feedSource;
  }
}
