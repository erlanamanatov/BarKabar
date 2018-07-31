package com.erkprog.barkabar.data.entity;

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

  @Element(name = "dc:creator", required = false)
  private String createdBy;

  @Element(name = "description", required = false)
  private String description;

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

  public String getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}