package com.erkprog.barkabar.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.erkprog.barkabar.data.entity.room.CurrencyValues;

@Dao
public interface CurrencyValuesDao {

  @Insert
  void addValues(CurrencyValues currencyValues);
}
