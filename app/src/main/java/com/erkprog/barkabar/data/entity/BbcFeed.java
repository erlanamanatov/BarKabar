package com.erkprog.barkabar.data.entity;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "rss", strict = false)
public class BbcFeed {

  @Path("channel")
  @ElementList(name = "item", inline = true, required = false)
  List<BbcItem> mData;

  public List<BbcItem> getData() {
    return mData;
  }

  public void setData(List<BbcItem> data) {
    mData = data;
  }
}
