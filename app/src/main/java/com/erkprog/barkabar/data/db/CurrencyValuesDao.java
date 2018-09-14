package com.erkprog.barkabar.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import com.erkprog.barkabar.data.entity.room.CurrencyValues;

@Dao
public interface CurrencyValuesDao {

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  void addValues(CurrencyValues currencyValues);
}
