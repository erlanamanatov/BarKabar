package com.erkprog.barkabar.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.erkprog.barkabar.data.entity.room.CurrencyValues;

import io.reactivex.Maybe;

@Dao
public interface CurrencyValuesDao {

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  void addValues(CurrencyValues currencyValues);

  @Query("SELECT * FROM CurrencyValues WHERE date = :date")
  Maybe<CurrencyValues> getByDate(String date);
}
