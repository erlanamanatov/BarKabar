package com.erkprog.barkabar.ui.kaktus;

import android.support.annotation.NonNull;
import android.util.Log;

import com.erkprog.barkabar.data.entity.Defaults;
import com.erkprog.barkabar.data.entity.KaktusItem;
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

public class KaktusPresenter implements KaktusContract.Presenter {
  private static final String TAG = "KaktusPresenter";

  private KaktusContract.View mView;
  private LocalRepository mRepository;
  private DatabaseReference mFirebaseDatabase;

  KaktusPresenter(LocalRepository repository) {
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
            if (item != null) {
              item.setFeedSource(Defaults.KAKTUS_SOURCE_NAME);
              mRepository.checkItemInDB(item);
              Log.d(TAG, "onDataChange: " + item.toString());
              data.add(item);
            }
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
  }

  @Override
  public void deleteOldItemsInDB() {
    mRepository.deleteOldItems(Defaults.KAKTUS_SOURCE_NAME);
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
