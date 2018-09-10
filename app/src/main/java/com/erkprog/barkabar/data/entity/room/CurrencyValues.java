package com.erkprog.barkabar.data.entity.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class CurrencyValues {

  @NonNull
  @PrimaryKey
  private String date;
  private String usd;
  private String eur;
  private String kzt;
  private String rub;

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getUsd() {
    return usd;
  }

  public void setUsd(String usd) {
    this.usd = usd;
  }

  public String getEur() {
    return eur;
  }

  public void setEur(String eur) {
    this.eur = eur;
  }

  public String getKzt() {
    return kzt;
  }

  public void setKzt(String kzt) {
    this.kzt = kzt;
  }

  public String getRub() {
    return rub;
  }

  public void setRub(String rub) {
    this.rub = rub;
  }

  @Override
  public String toString() {
    return "CurrencyValues{" +
        "date='" + date + '\'' +
        ", usd='" + usd + '\'' +
        ", eur='" + eur + '\'' +
        ", kzt='" + kzt + '\'' +
        ", rub='" + rub + '\'' +
        '}';
  }
}
