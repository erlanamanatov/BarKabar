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
import com.erkprog.barkabar.data.entity.room.FeedItem;

import java.io.File;
import java.util.Calendar;
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

  public void downloadFeedItem(final FeedItem item) {
    Log.d(TAG, "downloadFeedItem image: " + item.getImgPath() + ", guid " + item.getGuid());
    final String dirPath = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath();
    final String fileName = genName(item.getFeedSource());
    int downloadId = PRDownloader.download(item.getImgPath(), dirPath, fileName)
        .build()
        .start(new OnDownloadListener() {
          @Override
          public void onDownloadComplete() {
            Log.d(TAG, "onDownloadComplete: start saving to DB " + item.getGuid());
            item.setLocallyAvailable(true);
            item.setImgPath(dirPath + "/" + fileName);
            item.setSavedDate(Calendar.getInstance().getTime());
            saveFeedItemToDB(item);
          }

          @Override
          public void onError(Error error) {
            Log.d(TAG, "onDownloadListener Error, item guid: " + item.getGuid()
                + ", onError: " + error.toString());
          }
        });
  }

  public void checkItemInDB(final FeedItem item) {
    Log.d(TAG, "checkItemInDB: starts, guid " + item.getGuid());

    Completable.fromAction(new Action() {
      @Override
      public void run() throws Exception {
          FeedItem dbItem = getDatabase().feedItemDao().findByGuid(item.getGuid());
          if (dbItem != null) {
            item.setImgPath(dbItem.getImgPath());
            item.setLocallyAvailable(true);
          } else {
            if (item.getGuid() != null && item.getImgPath() != null) {
              //download FeedItem
              downloadFeedItem(item);
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
          }

          @Override
          public void onError(Throwable e) {
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
            Log.d(TAG, "save FeedItem onComplete: item (guid " + item.getGuid() + ") saved to DB");
          }

          @Override
          public void onError(Throwable e) {
            Log.d(TAG, "save FeedItem to DB: onError: " + e.getMessage() + " , guid: "
              + item.getGuid());
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
