package com.erkprog.barkabar.data.entity.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.erkprog.barkabar.data.entity.Defaults;
import com.erkprog.barkabar.data.entity.KaktusItem;

import java.util.Calendar;
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

  @Ignore
  public FeedItem(KaktusItem item) {
    this.guid = item.getGuid();
    this.title = item.getTitle();
    this.description = item.getDescription() != null ? item.getDescription() : "";
    this.imgPath = item.getImgSource();
    this.feedSource = Defaults.KAKTUS_SOURCE_NAME;
    savedDate = Calendar.getInstance().getTime();
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

  public Date getSavedDate() {
    return savedDate;
  }

  public void setSavedDate(Date savedDate) {
    this.savedDate = savedDate;
  }
}
