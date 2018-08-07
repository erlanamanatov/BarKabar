package com.erkprog.barkabar.util;

import com.erkprog.barkabar.ui.kaktus.KaktusFragment;
import com.erkprog.barkabar.ui.kloop.KloopFragment;
import com.erkprog.barkabar.ui.sputnik.SputnikFragment;

import java.util.ArrayList;
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


  public static ArrayList<String> getDefaultTabOrder() {
    ArrayList<String> tabOrder = new ArrayList<>();
    tabOrder.add(KloopFragment.class.getSimpleName());
    tabOrder.add(SputnikFragment.class.getSimpleName());
    tabOrder.add(KaktusFragment.class.getSimpleName());
    return tabOrder;
  }
}
