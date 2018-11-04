package com.erkprog.barkabar.data.entity.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import com.google.firebase.database.PropertyName;
import java.util.Date;


@Entity
public class FeedItem {

  @NonNull
  @PrimaryKey
  private String guid;
  private String title;
  private String description;
  private String imgPath;
  private String feedSource;
  private Date savedDate;

  public FeedItem() {

  }

  public String getGuid() {
    return guid;
  }

  public void setGuid(String guid) {
    this.guid = guid;
  }

  @PropertyName("title")
  public String getTitle() {
    return title;
  }

  @PropertyName("title")
  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @PropertyName("imgUrl")
  public String getImgPath() {
    return imgPath;
  }

  @PropertyName("imgUrl")
  public void setImgPath(String imgPath) {
    this.imgPath = imgPath;
  }

  public String getFeedSource() {
    return feedSource;
  }

  public void setFeedSource(String feedSource) {
    this.feedSource = feedSource;
  }

  public Date getSavedDate() {
    return savedDate;
  }

  public void setSavedDate(Date savedDate) {
    this.savedDate = savedDate;
  }
}
