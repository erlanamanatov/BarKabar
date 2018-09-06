package com.erkprog.barkabar.ui.exchange;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.erkprog.barkabar.R;
import com.erkprog.barkabar.data.entity.ExchangeRatesResponse;
import com.erkprog.barkabar.data.network.exchangeRatesRepository.ExRatesClient;
import com.erkprog.barkabar.data.network.exchangeRatesRepository.ExchangeRatesApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ExchangeRatesFragment extends Fragment implements ExRatesContract.View {
  private static final String TAG = "ExchangeRatesFragment";

  private ExRatesContract.Presenter mPresenter;
  private TextView usdValue;
  private TextView eurValue;
  private TextView kztValue;
  private TextView rubValue;


  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mPresenter = new ExRatesPresenter(ExRatesClient.getClient());
    mPresenter.bind(this);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_exchange_rates, container, false);
    usdValue = v.findViewById(R.id.exch_usd_value);
    eurValue = v.findViewById(R.id.exch_eur_value);
    kztValue = v.findViewById(R.id.exch_kzt_value);
    rubValue = v.findViewById(R.id.exch_rub_value);
    return v;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mPresenter.loadData();
  }

  public static ExchangeRatesFragment newInstance() {
    return new ExchangeRatesFragment();
  }

  @Override
  public void showDate(String date) {
    Log.d(TAG, "showDate: " + date);
  }

  @Override
  public void showErrorDate() {

  }

  @Override
  public void showCurrencies(List<ExchangeRatesResponse.Currency> currencyList) {
    for (ExchangeRatesResponse.Currency currency : currencyList) {
      switch (currency.getIsoCode()) {
        case "USD":
          usdValue.setText(currency.getValue());
          break;

        case "EUR":
          eurValue.setText(currency.getValue());
          break;

        case "KZT":
          kztValue.setText(currency.getValue());
          break;

        case "RUB":
          rubValue.setText(currency.getValue());
          break;
      }

    }

  }

  @Override
  public void showErrorCurrencies() {

  }

  @Override
  public void showMessage(String message) {
    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

  }

  @Override
  public void showProgress() {

  }

  @Override
  public void dismissProgress() {

  }
}
