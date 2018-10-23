package com.erkprog.barkabar.ui.kaktus;

import android.support.annotation.NonNull;
import android.util.Log;

import com.erkprog.barkabar.data.entity.Defaults;
import com.erkprog.barkabar.data.entity.KaktusFeed;
import com.erkprog.barkabar.data.entity.KaktusItem;
import com.erkprog.barkabar.data.entity.room.FeedItem;
import com.erkprog.barkabar.data.network.kaktusRepository.KaktusApi;
import com.erkprog.barkabar.data.repository.LocalRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
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
  private LocalRepository mRepository;
  private DatabaseReference mFirebaseDatabase;

  KaktusPresenter(KaktusApi service, LocalRepository repository) {
    mService = service;
    mRepository = repository;
    mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
  }

  @Override
  public boolean isAttached() {
    return mView != null;
  }

  @Override
  public void loadData() {
    mView.showProgress();

    DatabaseReference items = mFirebaseDatabase.child("feed").child("kaktus");
    Query query = items.orderByChild("id").limitToLast(20);
    query.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if (isAttached()) {
          mView.dismissProgress();
          List<KaktusItem> data = new ArrayList<>();
          for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
            KaktusItem item = postSnapshot.getValue(KaktusItem.class);
            Log.d(TAG, "onDataChange: " + item.toString());
            data.add(item);
          }

          Collections.reverse(data);
          checkItemsInDB(data);
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {
        if (isAttached()) {
          mView.dismissProgress();
          Log.d(TAG, "load data onCancelled, databaseError: " + databaseError.getMessage());
          mView.showErrorLoadingData();
        }
      }
    });



//    if (mService != null) {
//
//      Log.d(TAG, "loadData: starting");
//
//      mService.loadKaktusFeed().enqueue(new Callback<KaktusFeed>() {
//        @Override
//        public void onResponse(Call<KaktusFeed> call, Response<KaktusFeed> response) {
//          if (isAttached()) {
//            mView.dismissProgress();
//
//            if (response.isSuccessful() && response.body() != null
//                && response.body().getData() != null && response.body().getData().size() != 0) {
//
//              checkItemsInDB(response.body().getData());
//
//            } else {
//              Log.d(TAG, "onResponse: check response || body || data ");
//              mView.showErrorLoadingData();
//            }
//          }
//        }
//
//        @Override
//        public void onFailure(Call<KaktusFeed> call, Throwable t) {
//          if (isAttached()) {
//            mView.dismissProgress();
//            Log.d(TAG, "onFailure: " + t.getMessage());
//            mView.showErrorLoadingData();
//          }
//        }
//      });
//    }
  }

  @Override
  public void deleteOldItemsInDB() {
    mRepository.deleteOldItems(Defaults.KAKTUS_SOURCE_NAME);
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
              //download FeedItem
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
            mView.showFeed(data);
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
    if (item.getLink() != null) {
      mView.showArticle(item.getLink());
    }
  }
}
