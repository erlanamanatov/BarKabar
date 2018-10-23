package com.erkprog.barkabar.ui.kloop;

import android.support.annotation.NonNull;
import android.util.Log;

import com.erkprog.barkabar.data.entity.KloopFeed;
import com.erkprog.barkabar.data.entity.KloopItem;
import com.erkprog.barkabar.data.entity.SputnikItem;
import com.erkprog.barkabar.data.network.kloopRepository.KloopApi;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KloopPresenter implements KloopContract.Presenter {

  private static final String TAG = "KloopPresenter";

  private KloopContract.View mView;
  private KloopApi mService;
  private DatabaseReference mDatabase;

  KloopPresenter(KloopApi service) {
    mService = service;
    mDatabase = FirebaseDatabase.getInstance().getReference();
  }

  @Override
  public boolean isAttached() {
    return mView != null;
  }

  @Override
  public void loadData() {
    mView.showProgress();

    DatabaseReference items = mDatabase.child("feed").child("kloop");
    Query query = items.orderByChild("id").limitToLast(20);
    query.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if (isAttached()) {
          mView.dismissProgress();
          List<KloopItem> data = new ArrayList<>();
          for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
            KloopItem item = postSnapshot.getValue(KloopItem.class);
            Log.d(TAG, "item onDataChange: " + item.toString());
            data.add(item);
          }

          Collections.reverse(data);
          mView.showFeed(data);
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

//    mService.loadKloopRss().enqueue(new Callback<KloopFeed>() {
//      @Override
//      public void onResponse(Call<KloopFeed> call, Response<KloopFeed> response) {
//        if (isAttached()) {
//          mView.dismissProgress();
//          if (response.isSuccessful() && response.body() != null
//              && response.body().getData() != null) {
//            mView.showFeed(response.body().getData());
//          } else {
//            Log.d(TAG, "onResponse: check response || body || data");
//            mView.showErrorLoadingData();
//          }
//        }
//      }
//
//      @Override
//      public void onFailure(Call<KloopFeed> call, Throwable t) {
//        if (isAttached()) {
//          mView.dismissProgress();
//          Log.d(TAG, "onFailure: " + t.getMessage());
//          mView.showErrorLoadingData();
//        }
//      }
//    });
  }

  @Override
  public void deleteOldItemsInDB() {

  }

  @Override
  public void bind(KloopContract.View view) {
    mView = view;
  }

  @Override
  public void unBind() {
    mView = null;
  }

  @Override
  public void onItemClick(KloopItem item) {
    if (item.getLink() != null) {
      mView.showArticle(item.getLink());
    }
  }
}
