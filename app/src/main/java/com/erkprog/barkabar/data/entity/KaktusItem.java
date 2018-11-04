package com.erkprog.barkabar.data.entity;

import com.erkprog.barkabar.data.entity.room.FeedItem;
import com.google.firebase.database.PropertyName;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "item", strict = false)
public class KaktusItem extends FeedItem {

  @Element(name = "link", required = false)
  String link;

  @Element(name = "pubDate", required = false)
  String createdDate;

  @PropertyName("link")
  public String getLink() {
    return link;
  }

  @PropertyName("link")
  public void setLink(String link) {
    this.link = link;
  }

  @PropertyName("pubDate")
  public String getCreatedDate() {
    return createdDate;
  }

  @PropertyName("pubDate")
  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
  }

  @Override
  public String toString() {
    return "KaktusItem{" +
        "title='" + super.getTitle() + '\'' +
        ", link='" + link + '\'' +
        ", guid='" + super.getGuid() + '\'' +
        ", createdDate='" + createdDate + '\'' +
        ", description='" + super.getDescription() + '\'' +
        ", imgSource='" + super.getImgPath() + '\'' +
        ", locallyAvailable=" + super.isLocallyAvailable() +
        '}';
  }
}
