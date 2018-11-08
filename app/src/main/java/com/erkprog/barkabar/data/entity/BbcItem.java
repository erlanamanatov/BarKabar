package com.erkprog.barkabar.data.entity;

import com.erkprog.barkabar.data.entity.room.FeedItem;

public class BbcItem extends FeedItem {

  private String link;

  private String pubDate;

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getPubDate() {
    return pubDate;
  }

  public void setPubDate(String pubDate) {
    this.pubDate = pubDate;
  }

  @Override
  public String toString() {
    return "BbcItem {" +
        "title='" + super.getTitle() + '\'' +
        ", link='" + link + '\'' +
        ", pubDate='" + pubDate + '\'' +
        ", description='" + super.getDescription() + '\'' +
        ", imgUrl='" + super.getImgPath() + '\'' +
        ", guid='" + super.getGuid() + '\'' +
        '}';
  }
}
