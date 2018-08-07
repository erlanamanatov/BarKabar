package com.erkprog.barkabar.data.entity;

public class SourceItem {

  private String source;

  public SourceItem() {
    source = "";
  }

  public SourceItem(String sourceName) {
    source = sourceName;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }
}
