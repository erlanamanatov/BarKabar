package com.erkprog.barkabar.ui.exchange;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.erkprog.barkabar.AppApplication;
import com.erkprog.barkabar.R;
import com.erkprog.barkabar.data.entity.Defaults;
import com.erkprog.barkabar.data.entity.ExchangeRatesResponse;
import com.erkprog.barkabar.data.entity.room.CurrencyValues;
import com.erkprog.barkabar.data.network.exchangeRatesRepository.ExRatesClient;

import java.util.List;

public class ExchangeRatesFragment extends Fragment implements ExRatesContract.View {
  private static final String TAG = "ExchangeRatesFragment";

  private ExRatesContract.Presenter mPresenter;
  private TextView infoText;
  private TextView usdValue;
  private TextView eurValue;
  private TextView kztValue;
  private TextView rubValue;
  private ProgressBar mProgressBar;
  private CurrencyValues mCurrencyValues;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mPresenter = new ExRatesPresenter(ExRatesClient.getClient(), AppApplication.getInstance()
        .getLocalRepository().getDatabase());
    mPresenter.bind(this);
    mCurrencyValues = new CurrencyValues();
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_exchange_rates, container, false);
    infoText = v.findViewById(R.id.ex_rates_text);
    usdValue = v.findViewById(R.id.exch_usd_value);
    eurValue = v.findViewById(R.id.exch_eur_value);
    kztValue = v.findViewById(R.id.exch_kzt_value);
    rubValue = v.findViewById(R.id.exch_rub_value);
    mProgressBar = v.findViewById(R.id.exch_progress_bar);
    dismissProgress();
    return v;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (savedInstanceState == null) {
      mPresenter.loadData();
    } else {
      usdValue.setText(savedInstanceState.getString(Defaults.USD));
      eurValue.setText(savedInstanceState.getString(Defaults.EUR));
      kztValue.setText(savedInstanceState.getString(Defaults.KZT));
      rubValue.setText(savedInstanceState.getString(Defaults.RUB));
    }
  }

  @Override
  public void onSaveInstanceState(@NonNull Bundle outState) {
    saveCurrencyValues(outState);
    super.onSaveInstanceState(outState);
  }

  private void saveCurrencyValues(Bundle outState) {
    outState.putString(Defaults.USD, usdValue.getText().toString());
    outState.putString(Defaults.EUR, eurValue.getText().toString());
    outState.putString(Defaults.KZT, kztValue.getText().toString());
    outState.putString(Defaults.RUB, rubValue.getText().toString());
  }

  public static ExchangeRatesFragment newInstance() {
    return new ExchangeRatesFragment();
  }

  @Override
  public void showDate(String date) {
    mCurrencyValues.setDate(date);
    Log.d(TAG, "showDate: " + date);
  }

  @Override
  public void showErrorDate() {

  }

  @Override
  public void showCurrencies(List<ExchangeRatesResponse.Currency> currencyList) {
    for (ExchangeRatesResponse.Currency currency : currencyList) {
      switch (currency.getIsoCode()) {
        case Defaults.USD:
          usdValue.setText(currency.getValue());
          mCurrencyValues.setUsd(currency.getValue());
          break;

        case Defaults.EUR:
          eurValue.setText(currency.getValue());
          mCurrencyValues.setEur(currency.getValue());
          break;

        case Defaults.KZT:
          kztValue.setText(currency.getValue());
          mCurrencyValues.setKzt(currency.getValue());
          break;

        case Defaults.RUB:
          rubValue.setText(currency.getValue());
          mCurrencyValues.setRub(currency.getValue());
          break;
      }
    }
    Log.d(TAG, "showCurrencies: currencyValues " + mCurrencyValues);
  }


  @Override
  public void showErrorCurrencies() {
    infoText.setText(R.string.error_loading_data);
  }

  @Override
  public void showMessage(String message) {
    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void showProgress() {
    mProgressBar.setVisibility(View.VISIBLE);
  }

  @Override
  public void dismissProgress() {
    mProgressBar.setVisibility(View.GONE);
  }
}
