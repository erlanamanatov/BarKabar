package com.erkprog.barkabar.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.erkprog.barkabar.data.entity.room.CurrencyValues;

@Database(entities = {CurrencyValues.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

  public abstract CurrencyValuesDao currencyValuesDao();
}
