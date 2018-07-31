package com.erkprog.barkabar.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

  public static String getKloopDescription(String description) {
    final Pattern pattern = Pattern.compile("<p>(.+?)</p>");
    final Matcher matcher = pattern.matcher(description);
    if (matcher.find()) {
      return matcher.group(1);
    } else {
      return null;
    }

  }
}
