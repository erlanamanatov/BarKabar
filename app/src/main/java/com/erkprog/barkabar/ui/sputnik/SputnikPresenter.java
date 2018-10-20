package com.erkprog.barkabar.ui.sputnik;

import android.support.annotation.NonNull;
import android.util.Log;

import com.erkprog.barkabar.data.entity.SputnikFeed;
import com.erkprog.barkabar.data.entity.SputnikItem;
import com.erkprog.barkabar.data.network.sputnikRepository.SputnikApi;
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

public class SputnikPresenter implements SputnikContract.Presenter {
  private static final String TAG = "SputnikPresenter";

  private SputnikContract.View mView;
  private SputnikApi mService;
  private DatabaseReference mDatabase;


  SputnikPresenter(SputnikApi service) {
    mService = service;
    mDatabase = FirebaseDatabase.getInstance().getReference();
  }

  @Override
  public void loadData() {
    mView.showProgress();

    DatabaseReference items = mDatabase.child("test");
    items.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        mView.dismissProgress();
        List<SputnikItem> data = new ArrayList<>();
        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
          SputnikItem item = postSnapshot.getValue(SputnikItem.class);
          Log.d(TAG, "onDataChange: " + item.toString());
          data.add(item);
        }

        Collections.reverse(data);
        mView.showFeed(data);
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });


//    if (mService != null) {
//      mService.loadSputnikRss().enqueue(new Callback<SputnikFeed>() {
//        @Override
//        public void onResponse(Call<SputnikFeed> call, Response<SputnikFeed> response) {
//          if (isAttached()) {
//            mView.dismissProgress();
//
//            if (response.isSuccessful() && response.body() != null
//                && response.body().getChannel() != null) {
//
//              List<SputnikItem> data = response.body().getChannel().getItems();
//              if (data != null) {
//                if (data.size() > 50) {
//                  data = data.subList(0, 50);
//                }
//                mView.showFeed(data);
//              } else {
//                Log.d(TAG, "onResponse: List<SputnikItem> is null");
//                mView.showErrorLoadingData();
//              }
//            } else {
//              Log.d(TAG, "onResponse: check response || body || channel ");
//              mView.showErrorLoadingData();
//            }
//          }
//        }
//
//        @Override
//        public void onFailure(Call<SputnikFeed> call, Throwable t) {
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

  }

  @Override
  public void onItemClick(SputnikItem item) {
    if (item.getLink() != null) {
      mView.openArticle(item.getLink());
    }
  }

  @Override
  public boolean isAttached() {
    return mView != null;
  }

  @Override
  public void bind(SputnikContract.View view) {
    mView = view;
  }

  @Override
  public void unBind() {
    mView = null;
  }
}
