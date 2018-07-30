package com.erkprog.barkabar.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

  public static Date getDate(String stringDate) {
    if (stringDate == null) {
      throw new IllegalArgumentException("Parsing error");
    }
    SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM y H:mm:ss Z");
    try {
      return formatter.parse(stringDate);
    } catch (ParseException e) {
      throw new IllegalArgumentException("Parsing error");
    }
  }

  public static String getFormattedDate(String resourceDate) throws IllegalArgumentException {

    SimpleDateFormat formatter = new SimpleDateFormat("d/MM/yy H:mm");

    Date date = getDate(resourceDate);
    return formatter.format(date);

  }
}

