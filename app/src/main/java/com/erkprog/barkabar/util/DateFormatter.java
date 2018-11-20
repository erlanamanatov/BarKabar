package com.erkprog.barkabar.util;


import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatter {
  private static final String TAG = "DateFormatter";

  private static Date getDate(String stringDate) {
    if (stringDate == null) {
      throw new IllegalArgumentException("Parsing error");
    }
    Locale locale = new Locale("en");
    SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM y H:mm:ss Z", locale);
    try {
      return formatter.parse(stringDate);
    } catch (ParseException e) {
      throw new IllegalArgumentException("Parsing error");
    }
  }

  public static String getFormattedDate(String resourceDate) throws IllegalArgumentException {
    Log.d(TAG, "getFormattedDate: resource date " + resourceDate);
    SimpleDateFormat formatter = new SimpleDateFormat("d/MM/yy H:mm");
    Date date = getDate(resourceDate);
    return formatter.format(date);

  }
}

