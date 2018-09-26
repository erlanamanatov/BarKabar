package com.erkprog.barkabar.data.entity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "rss", strict = false)
public class SputnikFeed {

  @Element(name = "channel", required = false)
  private Channel mChannel;

  public Channel getChannel() {
    return mChannel;
  }

  @Root(name = "channel", strict = false)
  public static class Channel {

    @Element(name = "title", required = false)
    private String title;

    @ElementList(name = "item", inline = true, required = false)
    private List<SputnikItem> items;

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public List<SputnikItem> getItems() {
      return items;
    }

  }

}
