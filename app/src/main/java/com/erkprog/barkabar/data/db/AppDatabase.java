package com.erkprog.barkabar.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.erkprog.barkabar.data.entity.room.CurrencyValues;
import com.erkprog.barkabar.data.entity.room.FeedImage;
import com.erkprog.barkabar.data.entity.room.FeedItem;

@Database(entities = {CurrencyValues.class, FeedItem.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

  public abstract CurrencyValuesDao currencyValuesDao();

//  public abstract ImageDao imageDao();

  public abstract FeedItemDao feedItemDao();
}
