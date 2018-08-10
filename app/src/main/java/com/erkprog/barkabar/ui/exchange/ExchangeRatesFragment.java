package com.erkprog.barkabar.ui.exchange;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erkprog.barkabar.R;
import com.erkprog.barkabar.data.entity.ExchangeRatesResponse;
import com.erkprog.barkabar.data.network.exchangeRatesRepository.ExRatesClient;
import com.erkprog.barkabar.data.network.exchangeRatesRepository.ExchangeRatesApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ExchangeRatesFragment extends Fragment {
  private static final String TAG = "ExchangeRatesFragment";


  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_exchange_rates, container, false);
    return v;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    ExchangeRatesApi api = ExRatesClient.getClient();
    api.getExchangeRates().enqueue(new Callback<ExchangeRatesResponse>() {
      @Override
      public void onResponse(Call<ExchangeRatesResponse> call, Response<ExchangeRatesResponse> response) {
        Log.d(TAG, "onResponse: starts");
        if (response.body().getCurrencyList() != null) {
          List<ExchangeRatesResponse.Currency> list = response.body().getCurrencyList();
          ExchangeRatesResponse.Currency currency = list.get(0);
          Log.d(TAG, "onResponse: " + currency.getIsoCode() + " : " + currency.getValue());
        } else {
          Log.d(TAG, "onResponse: list is null");
        }
      }

      @Override
      public void onFailure(Call<ExchangeRatesResponse> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
      }
    });
  }

  public static ExchangeRatesFragment newInstance() {
    return new ExchangeRatesFragment();
  }
}
