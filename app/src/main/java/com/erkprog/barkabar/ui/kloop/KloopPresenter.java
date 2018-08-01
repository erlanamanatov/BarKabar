package com.erkprog.barkabar.ui.kloop;

import android.util.Log;

import com.erkprog.barkabar.data.entity.KloopFeed;
import com.erkprog.barkabar.data.network.kloopRepository.KloopApi;
import com.erkprog.barkabar.util.DateFormatter;
import com.erkprog.barkabar.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KloopPresenter implements KloopContract.Presenter {

  private static final String TAG = "KloopPresenter";

  private KloopContract.View mView;
  private KloopApi mService;

  KloopPresenter(KloopApi service) {
    mService = service;
  }

  @Override
  public boolean isAttached() {
    return mView != null;
  }

  @Override
  public void loadData() {
    mView.showProgress();

    mService.loadKloopRss().enqueue(new Callback<KloopFeed>() {
      @Override
      public void onResponse(Call<KloopFeed> call, Response<KloopFeed> response) {
        if (isAttached()) {
          mView.dismissProgress();
          if (response.isSuccessful() && response.body().getData() != null) {
            Log.d(TAG, "onResponse: Starts");
            Log.d(TAG, "onResponse: " + response.body().getData().size());
            Log.d(TAG, "onResponse: " + response.body().getData().get(0).getTitle());
            Log.d(TAG, "onResponse: " + response.body().getData().get(0).getCreatedBy());
            String description = response.body().getData().get(0).getDescription();
            Log.d(TAG, "onResponse:  " + Utils.getKloopDescription(description));
            String date = response.body().getData().get(0).getCreatedDate();
            Log.d(TAG, "onResponse: " + DateFormatter.getFormattedDate(date));
          } else {
            Log.d(TAG, "onResponse:response is not successful or item list is null");
          }
        }
      }

      @Override
      public void onFailure(Call<KloopFeed> call, Throwable t) {
        if (isAttached()) {
          mView.dismissProgress();
          Log.d(TAG, "onFailure: " + t.getMessage());
          mView.showMessage(t.getMessage());
        }
      }
    });

  }

  @Override
  public void bind(KloopContract.View view) {
    mView = view;
  }

  @Override
  public void unBind() {
    mView = null;
  }
}
