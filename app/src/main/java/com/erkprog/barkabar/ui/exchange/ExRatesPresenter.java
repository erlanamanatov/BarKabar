package com.erkprog.barkabar.ui.exchange;

import android.util.Log;

import com.erkprog.barkabar.data.db.AppDatabase;
import com.erkprog.barkabar.data.entity.Defaults;
import com.erkprog.barkabar.data.entity.ExchangeRatesResponse;
import com.erkprog.barkabar.data.entity.room.CurrencyValues;
import com.erkprog.barkabar.data.network.exchangeRatesRepository.ExchangeRatesApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExRatesPresenter implements ExRatesContract.Presenter {
  private static final String TAG = "ExRatesPresenter";

  private ExRatesContract.View mView;
  private ExchangeRatesApi mService;
  private AppDatabase mDatabase;

  ExRatesPresenter(ExchangeRatesApi api, AppDatabase database) {
    mService = api;
    mDatabase = database;
  }

  @Override
  public boolean isAttached() {
    return mView != null;
  }

  @Override
  public void loadData() {
    mView.showProgress();

    String date = getCurrentDate();
    Log.d(TAG, "loadData: current date formatted to String : " + date);

    mDatabase.currencyValuesDao().getByDate(date)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new DisposableMaybeObserver<CurrencyValues>() {
          @Override
          public void onSuccess(CurrencyValues currencyValues) {
            if (isAttached()) {
              Log.d(TAG, "onSuccess: currencies available from DB");
              mView.dismissProgress();
              mView.showDate(currencyValues.getDate());
              mView.showCurrencies(formList(currencyValues));
            }
          }

          @Override
          public void onError(Throwable e) {
            if (isAttached()) {
              Log.d(TAG, "onError: DB error");
              getDataFromServer();
            }
          }

          @Override
          public void onComplete() {
            if (isAttached()) {
              Log.d(TAG, "onComplete: no matches in DB");
              getDataFromServer();
            }
          }
        });
  }

  private String getCurrentDate() {
    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
    return formatter.format(Calendar.getInstance().getTime());
  }

  private void getDataFromServer() {
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
                saveCurrenciesToDB(response.body().getCurrencyList(), response.body().getDate());
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
    } else {
      mView.dismissProgress();
    }
  }

  private List<ExchangeRatesResponse.Currency> formList(CurrencyValues currencyValues) {
    List<ExchangeRatesResponse.Currency> result = new ArrayList<>();
    result.add(new ExchangeRatesResponse.Currency(Defaults.USD, currencyValues.getUsd()));
    result.add(new ExchangeRatesResponse.Currency(Defaults.EUR, currencyValues.getEur()));
    result.add(new ExchangeRatesResponse.Currency(Defaults.KZT, currencyValues.getKzt()));
    result.add(new ExchangeRatesResponse.Currency(Defaults.RUB, currencyValues.getRub()));
    return result;
  }

  private void saveCurrenciesToDB(List<ExchangeRatesResponse.Currency> currencies, String date) {
    CurrencyValues currencyValues = new CurrencyValues();

    currencyValues.setDate(date != null ? date : "");

    for (ExchangeRatesResponse.Currency currency : currencies) {
      switch (currency.getIsoCode()) {
        case Defaults.USD:
          currencyValues.setUsd(currency.getValue());
          break;

        case Defaults.EUR:
          currencyValues.setEur(currency.getValue());
          break;

        case Defaults.KZT:
          currencyValues.setKzt(currency.getValue());
          break;

        case Defaults.RUB:
          currencyValues.setRub(currency.getValue());
          break;
      }
    }

    mDatabase.currencyValuesDao().addValues(currencyValues);

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
