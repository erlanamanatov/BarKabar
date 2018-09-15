package com.erkprog.barkabar.data.entity;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "CurrencyRates", strict = false)
public class ExchangeRatesResponse {

  @Attribute(name = "Date", required = false)
  private String date;

  @ElementList(name = "Currency", inline = true, required = false)
  List<Currency> mCurrencyList;

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public List<Currency> getCurrencyList() {
    return mCurrencyList;
  }

  public void setCurrencyList(List<Currency> currencyList) {
    mCurrencyList = currencyList;
  }

  @Root(name = "Currency", strict = false)
  public static class Currency {

    @Attribute(name = "ISOCode", required = false)
    private String isoCode;

    @Element(name = "Value", required = false)
    private String value;

    public Currency() {

    }

    public Currency(String isoCode, String value) {
      this.isoCode = isoCode;
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }

    public String getIsoCode() {
      return isoCode;
    }

    public void setIsoCode(String isoCode) {
      this.isoCode = isoCode;
    }
  }
}
