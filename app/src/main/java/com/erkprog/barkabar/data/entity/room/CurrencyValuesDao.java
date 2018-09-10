package com.erkprog.barkabar.data.entity.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

@Dao
public interface CurrencyValuesDao {

  @Insert
  void addValues(CurrencyValues currencyValues);
}
