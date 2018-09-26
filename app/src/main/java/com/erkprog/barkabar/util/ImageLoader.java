package com.erkprog.barkabar.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.erkprog.barkabar.AppApplication;
import com.erkprog.barkabar.data.db.AppDatabase;
import com.erkprog.barkabar.data.entity.room.FeedImage;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class ImageLoader implements Target {
  private static final String TAG = "ImageLoader";
  private final String name;
  private ImageView imageView;
  private Context context;
  private String guid;

  private AppDatabase mDatabase;

  public ImageLoader(String name, ImageView imageView, String guid, Context context) {
    this.name = name;
    this.imageView = imageView;
    this.guid = guid;
    this.context = context;
    mDatabase = AppApplication.getInstance().getImageRepository().getDatabase();
  }

  @Override
  public void onPrepareLoad(Drawable arg0) {
  }

  @Override
  public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom arg1) {
    Log.d(TAG, "onBitmapLoaded: starts");
    File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
        genName(name));
    try {
      imageView.setImageBitmap(bitmap);
      file.createNewFile();
      FileOutputStream ostream = new FileOutputStream(file);
      bitmap.compress(Bitmap.CompressFormat.JPEG, 90, ostream);
      ostream.close();
//      saveImagePathToDB(new FeedImage(guid, file.getAbsolutePath()));
      Log.d(TAG, "onBitmapLoaded: ends");
    } catch (Exception e) {
      e.printStackTrace();
      Log.d(TAG, "onBitmapLoaded: exception: " + e.getMessage());
    }
  }

  @Override
  public void onBitmapFailed(Exception e, Drawable errorDrawable) {

  }

  private String genName(String feed) {
    String fileName = feed + (int) (Math.random() * 5000) + ".jpeg";
    File file = new File(fileName);

    if (file.exists()) {
      return genName(feed);
    }

    return fileName;
  }

//  private void saveImagePathToDB(final FeedImage image) {
//    Completable.fromAction(new Action() {
//      @Override
//      public void run() throws Exception {
//        mDatabase.imageDao().addImage(image);
//      }
//    }).observeOn(AndroidSchedulers.mainThread())
//        .subscribeOn(Schedulers.io())
//        .subscribe(new CompletableObserver() {
//          @Override
//          public void onSubscribe(Disposable d) {
//          }
//
//          @Override
//          public void onComplete() {
//            Log.d(TAG, "save imagePath to DB: onComplete: path saved to DB");
//          }
//
//          @Override
//          public void onError(Throwable e) {
//            Log.d(TAG, "save imagePath to DB: onError: " + e.getMessage());
//          }
//        });
//  }

}
