package com.erkprog.barkabar.ui.sputnik;

import android.util.Log;

import com.erkprog.barkabar.data.entity.SputnikFeed;
import com.erkprog.barkabar.data.entity.SputnikItem;
import com.erkprog.barkabar.data.network.sputnikRepository.SputnikApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SputnikPresenter implements SputnikContract.Presenter {
  private static final String TAG = "SputnikPresenter";

  private SputnikContract.View mView;
  private SputnikApi mService;

  SputnikPresenter(SputnikApi service) {
    mService = service;
  }

  @Override
  public void loadData() {
    mView.showProgress();
    if (mService != null) {
      mService.loadSputnikRss().enqueue(new Callback<SputnikFeed>() {
        @Override
        public void onResponse(Call<SputnikFeed> call, Response<SputnikFeed> response) {
          if (isAttached()) {
            mView.dismissProgress();
            if (response.isSuccessful()) {
              if (response.body() != null && response.body().getChannel() != null) {

                List<SputnikItem> data = response.body().getChannel().getItems();
                if (data != null) {
                  if (data.size() > 50) {
                    data = data.subList(0, 50);
                  }
                  mView.showFeed(data);
                } else {
                  Log.d(TAG, "onResponse: List<SputnikItem> is null");
                  mView.showMessage("Loading error");
                }
              }
            } else {
              Log.d(TAG, "onResponse: not successful");
            }
          }
        }

        @Override
        public void onFailure(Call<SputnikFeed> call, Throwable t) {
          if (isAttached()) {
            mView.dismissProgress();
            Log.d(TAG, "onFailure: " + t.getMessage());
            mView.showMessage(t.getMessage());
          }

        }
      });
    }

  }

  @Override
  public void deleteOldItemsInDB() {

  }

  @Override
  public void onItemClick(SputnikItem item) {
    mView.openArticle(item.getLink());
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
