package com.erkprog.barkabar.data.entity;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "rss", strict = false)
public class KaktusFeed {

  @Path("channel")
  @ElementList(name = "item", inline = true)
  List<KaktusItem> data;

  public List<KaktusItem> getData() {
    return data;
  }

  public void setData(List<KaktusItem> data) {
    this.data = data;
  }
}
