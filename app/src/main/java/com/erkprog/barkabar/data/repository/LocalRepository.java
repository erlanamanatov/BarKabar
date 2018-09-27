package com.erkprog.barkabar.data.repository;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.PRDownloader;
import com.erkprog.barkabar.data.db.AppDatabase;
import com.erkprog.barkabar.data.entity.Defaults;
import com.erkprog.barkabar.data.entity.KaktusItem;
import com.erkprog.barkabar.data.entity.room.FeedImage;
import com.erkprog.barkabar.data.entity.room.FeedItem;

import java.io.File;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class LocalRepository {

  private static final String TAG = "LocalRepository";

  private AppDatabase mDatabase;
  private Context mContext;

  public LocalRepository(Context context) {
    mDatabase = Room.databaseBuilder(context, AppDatabase.class, "db").build();
    mContext = context;
  }

  public AppDatabase getDatabase() {
    return mDatabase;
  }

  public void downloadFeedItem(final KaktusItem item) {
    Log.d(TAG, "downloadFeedItem image: " + item.getImgSource() + "\n" +
        item.getTitle());
    final String dirPath = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath();
    final String fileName = genName(Defaults.KAKTUS_SOURCE_NAME);
    int downloadId = PRDownloader.download(item.getImgSource(), dirPath, fileName)
        .build()
        .start(new OnDownloadListener() {
          @Override
          public void onDownloadComplete() {
            Log.d(TAG, "onDownloadComplete: starting saving to DB");
            item.setLocallyAvailable(true);
            item.setImgSource(dirPath + "/" + fileName);
            saveFeedItemToDB(new FeedItem(item));
          }

          @Override
          public void onError(Error error) {
            Log.d(TAG, "onDownloadImageListener, onError: " + error.toString());
          }
        });
  }

  private void saveFeedItemToDB(final FeedItem item) {
    Completable.fromAction(new Action() {
      @Override
      public void run() throws Exception {
        mDatabase.feedItemDao().addItem(item);
      }
    }).observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new CompletableObserver() {
          @Override
          public void onSubscribe(Disposable d) {
          }

          @Override
          public void onComplete() {
            Log.d(TAG, "save FeedItem onComplete: saved to DB");
          }

          @Override
          public void onError(Throwable e) {
            Log.d(TAG, "save FeedItem to DB: onError: " + e.getMessage());
          }
        });
  }


  private String genName(String feed) {
    String fileName = feed + (int) (Math.random() * 5000) + ".jpeg";
    File file = new File(fileName);

    if (file.exists()) {
      return genName(feed);
    }

    return fileName;
  }

  public void deleteOldItems(final String feedSource) {
    Completable.fromAction(new Action() {
      @Override
      public void run() throws Exception {
        Log.d(TAG, "start checking items count in DB, source = " + feedSource);

        int count = mDatabase.feedItemDao().getCount(feedSource);
        Log.d(TAG, "there are " + count + " items in DB");

        if (count > Defaults.FEED_ITEM_LIMIT) {
          List<FeedItem> itemsOverLimit = mDatabase.feedItemDao()
              .getFeedItemOverLimit(feedSource, Defaults.FEED_ITEM_LIMIT);
          Log.d(TAG, "items to be deleted: " + itemsOverLimit.size());

          for (FeedItem item : itemsOverLimit) {
            new File(item.getImgPath()).delete();
            mDatabase.feedItemDao().deleteFeedItem(item);
          }
        }
      }
    }).observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new CompletableObserver() {
          @Override
          public void onSubscribe(Disposable d) {
          }

          @Override
          public void onComplete() {
            Log.d(TAG, "onComplete: old items deleted from DB and storage");
          }

          @Override
          public void onError(Throwable e) {
            Log.d(TAG, "on Error {delete old items in DB} " + e.getMessage());
          }
        });
  }
}
