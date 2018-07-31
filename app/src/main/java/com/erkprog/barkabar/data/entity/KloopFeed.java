package com.erkprog.barkabar.data.entity;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name="rss", strict = false)
public class KloopFeed {

  @Path("channel")
  @ElementList(name = "item",inline = true, required = false)
  List<KloopItem> mData;

  public List<KloopItem> getData() {
    return mData;
  }

  public void setData(List<KloopItem> data) {
    mData = data;
  }
}
