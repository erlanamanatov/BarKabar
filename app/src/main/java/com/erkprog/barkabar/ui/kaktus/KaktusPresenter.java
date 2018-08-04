package com.erkprog.barkabar.ui.kaktus;

import android.util.Log;

import com.erkprog.barkabar.data.entity.KaktusFeed;
import com.erkprog.barkabar.data.entity.KaktusItem;
import com.erkprog.barkabar.data.network.kaktusRepository.KaktusApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KaktusPresenter implements KaktusContract.Presenter {
  private static final String TAG = "KaktusPresenter";

  private KaktusContract.View mView;
  private KaktusApi mService;

  public KaktusPresenter(KaktusApi service) {
    mService = service;
  }


  @Override
  public boolean isAttached() {
    return mView != null;
  }

  @Override
  public void loadData() {
    mView.showProgress();
    if (mService != null) {

      mService.loadKaktusFeed().enqueue(new Callback<KaktusFeed>() {
        @Override
        public void onResponse(Call<KaktusFeed> call, Response<KaktusFeed> response) {
          if (isAttached()) {
            mView.dismissProgress();

            if (response.isSuccessful() && response.body() != null) {
              Log.d(TAG, "onResponse: response successful");

              if (response.body().getData() != null && response.body().getData().size() != 0) {
                mView.showFeed(response.body().getData());
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
