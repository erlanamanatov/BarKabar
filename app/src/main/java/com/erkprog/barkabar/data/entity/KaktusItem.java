package com.erkprog.barkabar.data.entity;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root(name = "item", strict = false)
public class KaktusItem {

  @Element(name = "title", data = true, required = false)
  String mTitle;

  @Element(name = "link", required = false)
  String mLink;

  @Element(name = "pubDate", required = false)
  String createdDate;

  @Element(name = "description", data = true, required = false)
  String description;

  @Path("enclosure")
  @Attribute(name = "url", required = false)
  String imgUrl;

  public String getTitle() {
    return mTitle;
  }

  public void setTitle(String title) {
    mTitle = title;
  }

  public String getLink() {
    return mLink;
  }

  public void setLink(String link) {
    mLink = link;
  }

  public String getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }
}
