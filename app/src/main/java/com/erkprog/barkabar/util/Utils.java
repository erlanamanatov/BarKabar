package com.erkprog.barkabar.util;

import android.content.SharedPreferences;

import com.erkprog.barkabar.data.entity.Defaults;
import com.erkprog.barkabar.ui.BaseFragment;
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


  private static ArrayList<String> getDefaultTabOrder() {
    ArrayList<String> tabOrder = new ArrayList<>();
    tabOrder.add(Defaults.SPUTNIK_SOURCE_NAME);
    tabOrder.add(Defaults.KAKTUS_SOURCE_NAME);
    tabOrder.add(Defaults.KLOOP_SOURCE_NAME);
    return tabOrder;
  }

  public static ArrayList<String> getTabOrder(SharedPreferences sharedPreferences) {
    //return tab order from settings, returns default order if settings not found
    if (sharedPreferences.contains(Defaults.TAB_ORDER)) {
      ArrayList<String> tabOrder = new ArrayList<>();
      return tabOrder;
    } else {
      return getDefaultTabOrder();
    }
  }

  public static BaseFragment getFragmentBySourceName(String sourceName) {
    BaseFragment fragment = null;
    switch (sourceName) {
      case Defaults.KLOOP_SOURCE_NAME:
        fragment = KloopFragment.newInstance();
        break;

      case Defaults.SPUTNIK_SOURCE_NAME:
        fragment = SputnikFragment.newInstance();
        break;

      case Defaults.KAKTUS_SOURCE_NAME:
        fragment = KaktusFragment.newInstance();
        break;
    }

    return fragment;
  }
}
