package com.erkprog.barkabar.ui.exchange;

import android.util.Log;

import com.erkprog.barkabar.data.entity.ExchangeRatesResponse;
import com.erkprog.barkabar.data.network.exchangeRatesRepository.ExchangeRatesApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExRatesPresenter implements ExRatesContract.Presenter {
  private static final String TAG = "ExRatesPresenter";

  private ExRatesContract.View mView;
  private ExchangeRatesApi mService;

  ExRatesPresenter(ExchangeRatesApi api) {
    mService = api;
  }

  @Override
  public boolean isAttached() {
    return mView != null;
  }

  @Override
  public void loadData() {
    if (mService != null) {
      mView.showProgress();

      mService.getExchangeRates().enqueue(new Callback<ExchangeRatesResponse>() {
        @Override
        public void onResponse(Call<ExchangeRatesResponse> call, Response<ExchangeRatesResponse> response) {
          if (isAttached()) {
            mView.dismissProgress();

            if (response.isSuccessful() && response.body() != null) {
              if (response.body().getDate() != null) {
                mView.showDate(response.body().getDate());
              } else {
                mView.showErrorDate();
              }

              if (response.body().getCurrencyList() != null) {
                mView.showCurrencies(response.body().getCurrencyList());
              } else {
                mView.showErrorCurrencies();
              }

            } else {
              Log.d(TAG, "onResponse: response is not successful or body is null");
            }
          }
        }

        @Override
        public void onFailure(Call<ExchangeRatesResponse> call, Throwable t) {
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
  public void bind(ExRatesContract.View view) {
    mView = view;
  }

  @Override
  public void unBind() {
    mView = null;
  }
}
