package com.erkprog.barkabar.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.erkprog.barkabar.data.entity.room.FeedImage;

@Dao
public interface ImageDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void addImage(FeedImage image);

  @Query("select * from FeedImage where id = :id")
  public FeedImage findById(String id);

}
