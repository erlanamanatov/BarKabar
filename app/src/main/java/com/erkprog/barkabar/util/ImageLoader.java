package com.erkprog.barkabar.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.erkprog.barkabar.AppApplication;
import com.erkprog.barkabar.data.db.AppDatabase;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;

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
    mDatabase = AppApplication.getInstance().getDatabase();
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
      file.createNewFile();
      FileOutputStream ostream = new FileOutputStream(file);
      bitmap.compress(Bitmap.CompressFormat.JPEG, 85, ostream);
      ostream.close();
      imageView.setImageBitmap(bitmap);
      Log.d(TAG, "onBitmapLoaded: ends");
    } catch (Exception e) {
      e.printStackTrace();
      Log.d(TAG, "onBitmapLoaded: exception starts " + e.getMessage());
    }
  }

  @Override
  public void onBitmapFailed(Exception e, Drawable errorDrawable) {

  }

  private String genName(String feed) {
    String fileName = feed + (int) (Math.random() * 1000) + ".jpg";
    File file = new File(fileName);

    if (file.exists()) {
      return genName(feed);
    }

    return fileName;
  }
}
