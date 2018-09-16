package com.erkprog.barkabar.ui.kaktus;

import android.util.Log;

import com.erkprog.barkabar.data.db.AppDatabase;
import com.erkprog.barkabar.data.entity.KaktusFeed;
import com.erkprog.barkabar.data.entity.KaktusItem;
import com.erkprog.barkabar.data.entity.room.FeedImage;
import com.erkprog.barkabar.data.network.kaktusRepository.KaktusApi;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KaktusPresenter implements KaktusContract.Presenter {
  private static final String TAG = "KaktusPresenter";

  private KaktusContract.View mView;
  private KaktusApi mService;
  private AppDatabase mDatabase;

  KaktusPresenter(KaktusApi service, AppDatabase database) {
    mService = service;
    mDatabase = database;
  }


  @Override
  public boolean isAttached() {
    return mView != null;
  }

  @Override
  public void loadData() {
    mView.showProgress();
    if (mService != null) {

      Log.d(TAG, "loadData: starting");

      mService.loadKaktusFeed().enqueue(new Callback<KaktusFeed>() {
        @Override
        public void onResponse(Call<KaktusFeed> call, Response<KaktusFeed> response) {
          if (isAttached()) {
            mView.dismissProgress();

            if (response.isSuccessful() && response.body() != null) {
              Log.d(TAG, "onResponse: response successful");

              if (response.body().getData() != null && response.body().getData().size() != 0) {
                checkForImagesInDB(response.body().getData());
//                mView.showFeed(response.body().getData());
              } else {
                Log.d(TAG, "onResponse: Data is null or size is 0");
              }
            } else {
              Log.d(TAG, "onResponse: response is not successful or body is null");
            }
          }
        }

        @Override
        public void onFailure(Call<KaktusFeed> call, Throwable t) {
          if (isAttached()) {
            mView.dismissProgress();
            Log.d(TAG, "onFailure: " + t.getMessage());
          }
        }
      });

    }
  }

  private void checkForImagesInDB(final List<KaktusItem> data) {

    Completable.fromAction(new Action() {
      @Override
      public void run() throws Exception {
        Log.d(TAG, "run: datasize: " + data.size());
        int i = 1;
        for (KaktusItem item : data) {
          Log.d(TAG, "run: " + i);
          FeedImage mImage = mDatabase.imageDao().findById(item.getGuid());
          if (mImage == null) {
            Log.d(TAG, "run: null");
          } else {
            Log.d(TAG, "run: " + mImage.getPath());
          }
          i++;
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
            Log.d(TAG, "checkForImages: onComplete: ");
            mView.showFeed(data);
          }

          @Override
          public void onError(Throwable e) {
            Log.d(TAG, "checkForImages: onError: " + e.getMessage());
          }
        });
  }

  @Override
  public void bind(KaktusContract.View view) {
    mView = view;
  }

  @Override
  public void unBind() {
    mView = null;
  }

  @Override
  public void onItemClick(KaktusItem item) {
    mView.showArticle(item.getLink());
  }
}
