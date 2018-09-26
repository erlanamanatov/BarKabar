package com.erkprog.barkabar.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.erkprog.barkabar.data.entity.room.Converters;
import com.erkprog.barkabar.data.entity.room.CurrencyValues;
import com.erkprog.barkabar.data.entity.room.FeedItem;

@Database(entities = {CurrencyValues.class, FeedItem.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

  public abstract CurrencyValuesDao currencyValuesDao();

  public abstract FeedItemDao feedItemDao();
}
