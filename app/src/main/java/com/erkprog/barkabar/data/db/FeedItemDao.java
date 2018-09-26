package com.erkprog.barkabar.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.erkprog.barkabar.data.entity.room.FeedItem;

@Dao
public interface FeedItemDao {

  @Insert (onConflict = OnConflictStrategy.IGNORE)
  void addItem(FeedItem item);

  @Query("select * from FeedItem where guid = :guid")
  public FeedItem findByGuid(String guid);
}
