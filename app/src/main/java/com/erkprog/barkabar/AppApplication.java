package com.erkprog.barkabar;

import android.app.Application;

import com.downloader.PRDownloader;
import com.erkprog.barkabar.data.repository.LocalRepository;

public class AppApplication extends Application {

  private static AppApplication instance;
  private LocalRepository mRepository;

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
    mRepository = new LocalRepository(this);
    PRDownloader.initialize(getApplicationContext());
  }

  public static AppApplication getInstance() {
    return instance;
  }

  public LocalRepository getLocalRepository() {
    return mRepository;
  }
}
