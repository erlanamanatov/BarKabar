package com.erkprog.barkabar.data.entity;

import com.erkprog.barkabar.data.entity.room.FeedItem;
import com.google.firebase.database.PropertyName;

public class SputnikItem extends FeedItem {

  private String link;

  private String pubDate;

  private String category;

  @PropertyName("link")
  public String getLink() {
    return link;
  }

  @PropertyName("link")
  public void setLink(String link) {
    this.link = link;
  }

  public String getPubDate() {
    return pubDate;
  }

  public void setPubDate(String pubDate) {
    this.pubDate = pubDate;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  @Override
  public String toString() {
    return "SputnikItem{" +
        "title='" + super.getTitle() + '\'' +
        ", link='" + link + '\'' +
        ", pubDate='" + pubDate + '\'' +
        ", description='" + super.getDescription() + '\'' +
        ", category='" + category + '\'' +
        ", imgUrl='" + super.getImgPath() + '\'' +
        ", guid='" + super.getGuid() + '\'' +
        '}';
  }
}
