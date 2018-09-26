package com.erkprog.barkabar.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.erkprog.barkabar.data.entity.room.FeedItem;

import java.util.List;

@Dao
public interface FeedItemDao {

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  void addItem(FeedItem item);

  @Query("select * from FeedItem where guid = :guid")
  FeedItem findByGuid(String guid);

  @Query("SELECT count(*) FROM FeedItem where feedSource = :feedSource")
  Integer getCount(String feedSource);

  @Query("SELECT * FROM "
      + "FeedItem WHERE savedDate <= "
        + "(SELECT savedDate FROM FeedItem "
        + "WHERE feedSource = :feedSource "
        + "ORDER BY savedDate DESC "
        + "LIMIT 1 OFFSET :feedItemLimit) "
      + "AND feedSource = :feedSource")
  List<FeedItem> getFeedItemOverLimit(String feedSource, int feedItemLimit);

  @Delete
  void deleteFeedItem(FeedItem item);

}
