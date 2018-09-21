package com.erkprog.barkabar;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.downloader.PRDownloader;
import com.erkprog.barkabar.data.db.AppDatabase;
import com.erkprog.barkabar.data.repository.ImageRepository;

public class AppApplication extends Application {

  private static AppApplication instance;
  private ImageRepository mRepository;

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
    mRepository = new ImageRepository(this);
    PRDownloader.initialize(getApplicationContext());
  }

  public static AppApplication getInstance() {
    return instance;
  }

  public ImageRepository getImageRepository() {
    return mRepository;
  }
}
