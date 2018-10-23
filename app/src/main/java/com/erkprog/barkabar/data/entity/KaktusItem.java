package com.erkprog.barkabar.data.entity;

import com.google.firebase.database.PropertyName;

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

  @Element(name = "guid", required = false)
  String guid;

  @Element(name = "pubDate", required = false)
  String createdDate;

  @Element(name = "description", data = true, required = false)
  String description;

  @Path("enclosure")
  @Attribute(name = "url", required = false)
  String imgSource;

  boolean locallyAvailable;

  @PropertyName("title")
  public String getTitle() {
    return mTitle;
  }

  @PropertyName("title")
  public void setTitle(String title) {
    mTitle = title;
  }

  @PropertyName("link")
  public String getLink() {
    return mLink;
  }

  @PropertyName("link")
  public void setLink(String link) {
    mLink = link;
  }

  @PropertyName("pubDate")
  public String getCreatedDate() {
    return createdDate;
  }

  @PropertyName("pubDate")
  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @PropertyName("imgUrl")
  public String getImgSource() {
    return imgSource;
  }

  @PropertyName("imgUrl")
  public void setImgSource(String imgSource) {
    this.imgSource = imgSource;
  }

  public String getGuid() {
    return guid;
  }

  public void setGuid(String guid) {
    this.guid = guid;
  }

  public boolean isLocallyAvailable() {
    return locallyAvailable;
  }

  public void setLocallyAvailable(boolean locallyAvailable) {
    this.locallyAvailable = locallyAvailable;
  }

  @Override
  public String toString() {
    return "KaktusItem{" +
        "mTitle='" + mTitle + '\'' +
        ", mLink='" + mLink + '\'' +
        ", guid='" + guid + '\'' +
        ", createdDate='" + createdDate + '\'' +
        ", description='" + description + '\'' +
        ", imgSource='" + imgSource + '\'' +
        ", locallyAvailable=" + locallyAvailable +
        '}';
  }
}
