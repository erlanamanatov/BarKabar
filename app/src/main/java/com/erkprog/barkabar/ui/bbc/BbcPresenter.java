package com.erkprog.barkabar.ui.bbc;


import android.util.Log;

import com.erkprog.barkabar.data.entity.BbcFeed;
import com.erkprog.barkabar.data.entity.BbcItem;
import com.erkprog.barkabar.data.network.bbcRepository.BbcApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BbcPresenter implements BbcContract.Presenter {
  private static final String TAG = "BbcPresenter";

  private BbcContract.View mView;
  private BbcApi mService;

  BbcPresenter(BbcApi service) {
    mService = service;
  }

  @Override
  public boolean isAttached() {
    return mView != null;
  }

  @Override
  public void loadData() {
    if (mService != null) {
      mView.showProgress();

      mService.loadBbcFeed().enqueue(new Callback<BbcFeed>() {
        @Override
        public void onResponse(Call<BbcFeed> call, Response<BbcFeed> response) {
          if (isAttached()) {
            mView.dismissProgress();

            if (response.isSuccessful() && response.body() != null) {
              if (response.body().getData() != null) {
                mView.showFeed(response.body().getData());
              } else {
                mView.showMessage("Error loading data");
              }
            } else {
              Log.d(TAG, "onResponse: response is not successful or body is null");
            }
          }
        }

        @Override
        public void onFailure(Call<BbcFeed> call, Throwable t) {
          Log.d(TAG, "onFailure: " + t.getMessage());
          if (isAttached()) {
            mView.dismissProgress();
            mView.showMessage(t.getMessage());
          }
        }
      });
    }

  }

  @Override
  public void bind(BbcContract.View view) {
    mView = view;
  }

  @Override
  public void unBind() {
    mView = null;
  }

  @Override
  public void onItemClick(BbcItem item) {
    if (item.getLink() != null) {
      mView.showArticle(item.getLink());
    } else {
      mView.showMessage("Article is not available");
    }
  }
}
