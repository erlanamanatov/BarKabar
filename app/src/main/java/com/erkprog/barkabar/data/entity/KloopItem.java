package com.erkprog.barkabar.data.entity;

import com.google.firebase.database.PropertyName;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "item", strict = false)
public class KloopItem {

  @Element(name = "title", required = false)
  private String title;

  @Element(name = "link", required = false)
  private String link;

  @Element(name = "pubDate", required = false)
  private String createdDate;

  @Element(name = "creator", data = true, required = false)
  private String createdBy;

  @Element(name = "description", data = true, required = false)
  private String description;

  @Element(name = "guid", required = false)
  private String guid;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getLink() {
    return link;
  }

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

  @PropertyName("creator")
  public String getCreatedBy() {
    return createdBy;
  }

  @PropertyName("creator")
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getGuid() {
    return guid;
  }

  public void setGuid(String guid) {
    this.guid = guid;
  }

  @Override
  public String toString() {
    return "KloopItem{" +
        "title='" + title + '\'' +
        ", link='" + link + '\'' +
        ", createdDate='" + createdDate + '\'' +
        ", createdBy='" + createdBy + '\'' +
        ", description='" + description + '\'' +
        ", guid='" + guid + '\'' +
        '}';
  }
}
