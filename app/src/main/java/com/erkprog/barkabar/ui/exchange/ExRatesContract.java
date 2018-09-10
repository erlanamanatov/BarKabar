package com.erkprog.barkabar.ui.exchange;

import com.erkprog.barkabar.data.entity.ExchangeRatesResponse;
import com.erkprog.barkabar.ui.BasePresenter;
import com.erkprog.barkabar.ui.BaseView;

import java.util.List;

public interface ExRatesContract {
  
  interface View extends BaseView {

    void showDate(String date);

    void showErrorDate();

    void showCurrencies(List<ExchangeRatesResponse.Currency> currencyList);

    void showErrorCurrencies();

  }
  
  interface Presenter extends BasePresenter<View> {
    
  }
}
