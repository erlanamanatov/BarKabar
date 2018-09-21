package com.erkprog.barkabar.data.repository;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.erkprog.barkabar.data.db.AppDatabase;
import com.erkprog.barkabar.data.entity.KaktusItem;
import com.erkprog.barkabar.data.entity.room.FeedImage;

import java.io.File;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class ImageRepository {

  private static final String TAG = "ImageRepository";

  private AppDatabase mDatabase;
  private Context mContext;

  public ImageRepository(Context context) {
    mDatabase = Room.databaseBuilder(context, AppDatabase.class, "db").build();
    mContext = context;
  }

  public AppDatabase getDatabase() {
    return mDatabase;
  }

  public void downloadImage(final KaktusItem item) {
    Log.d(TAG, "downloadImage image: " + item.getImgSource() + "\n" +
        item.getTitle());
    final String dirPath = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath();
    final String fileName = genName("kaktus");
    int downloadId = PRDownloader.download(item.getImgSource(), dirPath, fileName)
        .build()
        .setOnStartOrResumeListener(new OnStartOrResumeListener() {
          @Override
          public void onStartOrResume() {

          }
        })
        .setOnPauseListener(new OnPauseListener() {
          @Override
          public void onPause() {

          }
        })
        .setOnCancelListener(new OnCancelListener() {
          @Override
          public void onCancel() {

          }
        })
        .setOnProgressListener(new OnProgressListener() {
          @Override
          public void onProgress(Progress progress) {

          }
        })
        .start(new OnDownloadListener() {
          @Override
          public void onDownloadComplete() {
            Log.d(TAG, "onDownloadComplete: starting saving to DB");
            item.setLocallyAvailable(true);
            item.setImgSource(dirPath + "/" + fileName);
            saveImagePathToDB(new FeedImage(item.getGuid(), dirPath + "/" + fileName));
          }

          @Override
          public void onError(Error error) {
            Log.d(TAG, "onDownloadImageListener, onError: " + error.toString());
          }
        });
  }

  private void saveImagePathToDB(final FeedImage image) {
    Completable.fromAction(new Action() {
      @Override
      public void run() throws Exception {
        mDatabase.imageDao().addImage(image);
      }
    }).observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new CompletableObserver() {
          @Override
          public void onSubscribe(Disposable d) {
          }

          @Override
          public void onComplete() {
            Log.d(TAG, "save imagePath to DB: onComplete: path saved to DB");
          }

          @Override
          public void onError(Throwable e) {
            Log.d(TAG, "save imagePath to DB: onError: " + e.getMessage());
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
}
