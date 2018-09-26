package com.erkprog.barkabar.data.entity;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root(name = "item", strict = false)
public class SputnikItem {

  @Element(name = "title", required = false)
  private String title;

  @Element(name = "link", required = false)
  private String link;

  @Element(name = "pubDate", required = false)
  private String created;

  @Element(name = "description", required = false)
  private String description;

  @Element(name = "category", required = false)
  private String category;

  @Path("enclosure")
  @Attribute(name = "url", required = false)
  private String imgUrl;

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

  public String getCreated() {
    return created;
  }

  public void setCreated(String created) {
    this.created = created;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }

  public String getGuid() {
    return guid;
  }

  public void setGuid(String guid) {
    this.guid = guid;
  }
}
