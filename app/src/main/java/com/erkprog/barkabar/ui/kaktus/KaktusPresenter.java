package com.erkprog.barkabar.ui.kaktus;

import android.util.Log;

import com.erkprog.barkabar.data.entity.Defaults;
import com.erkprog.barkabar.data.entity.KaktusFeed;
import com.erkprog.barkabar.data.entity.KaktusItem;
import com.erkprog.barkabar.data.entity.room.FeedItem;
import com.erkprog.barkabar.data.network.kaktusRepository.KaktusApi;
import com.erkprog.barkabar.data.repository.LocalRepository;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KaktusPresenter implements KaktusContract.Presenter {
  private static final String TAG = "KaktusPresenter";

  private KaktusContract.View mView;
  private KaktusApi mService;
  private LocalRepository mRepository;

  KaktusPresenter(KaktusApi service, LocalRepository repository) {
    mService = service;
    mRepository = repository;
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
                checkItemsInDB(response.body().getData());
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

  @Override
  public void deleteOldItemsInDB() {
    Completable.fromAction(new Action() {
      @Override
      public void run() throws Exception {
        Log.d(TAG, "start checking items count in DB");

        int count = mRepository.getDatabase().feedItemDao().getCount(Defaults.KAKTUS_SOURCE_NAME);
        Log.d(TAG, "there are " + count + " items in DB");

        if (count > Defaults.FEED_ITEM_LIMIT) {
          List<FeedItem> itemsOverLimit = mRepository.getDatabase().feedItemDao()
              .getFeedItemOverLimit(Defaults.KAKTUS_SOURCE_NAME, Defaults.FEED_ITEM_LIMIT);
          Log.d(TAG, "items to be deleted: " + itemsOverLimit.size());

          for (FeedItem item : itemsOverLimit) {
            new File(item.getImgPath()).delete();
            mRepository.getDatabase().feedItemDao().deleteFeedItem(item);
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
            Log.d(TAG, "on Error {delete old items in DB} ");
          }
        });
  }

  private void checkItemsInDB(final List<KaktusItem> data) {
    Log.d(TAG, "checkItemsInDB: starts");

    Completable.fromAction(new Action() {
      @Override
      public void run() throws Exception {
        Log.d(TAG, "checkItemsInDB: items count from server: " + data.size());
        for (KaktusItem item : data) {
          FeedItem dbItem = mRepository.getDatabase().feedItemDao().findByGuid(item.getGuid());
          if (dbItem != null) {
            item.setImgSource(dbItem.getImgPath());
            item.setLocallyAvailable(true);
          } else {
            if (item.getGuid() != null && item.getImgSource() != null) {
              //downloadFeedItem image
              mRepository.downloadFeedItem(item);
            }
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
