package com.erkprog.barkabar;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.erkprog.barkabar.data.db.AppDatabase;

public class AppApplication extends Application {

  private static AppApplication instance;

  private AppDatabase mDatabase;

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
    mDatabase = Room.databaseBuilder(this, AppDatabase.class, "db").build();
  }

  public static AppApplication getInstance() {
    return instance;
  }

  public AppDatabase getDatabase() {
    return mDatabase;
  }
}
