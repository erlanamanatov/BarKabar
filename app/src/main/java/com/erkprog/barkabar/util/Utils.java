package com.erkprog.barkabar.util;

import android.content.SharedPreferences;

import com.erkprog.barkabar.data.entity.Defaults;
import com.erkprog.barkabar.ui.BaseFragment;
import com.erkprog.barkabar.ui.bbc.BbcFragment;
import com.erkprog.barkabar.ui.kaktus.KaktusFragment;
import com.erkprog.barkabar.ui.kloop.KloopFragment;
import com.erkprog.barkabar.ui.sputnik.SputnikFragment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    tabOrder.add(Defaults.BBC_SOURCE_NAME);
    tabOrder.add(Defaults.SPUTNIK_SOURCE_NAME);
    tabOrder.add(Defaults.KAKTUS_SOURCE_NAME);
    tabOrder.add(Defaults.KLOOP_SOURCE_NAME);
    return tabOrder;
  }

  public static ArrayList<String> getTabOrder(SharedPreferences sharedPreferences) {
    //return tab order from settings, returns default order if settings not found
    if (sharedPreferences.contains(Defaults.TAB_ORDER)) {
      List<String> tabOrder = new ArrayList<>();
      String order = sharedPreferences.getString(Defaults.TAB_ORDER, null);
      Gson gson = new Gson();
      String[] items = gson.fromJson(order, String[].class);
      tabOrder = Arrays.asList(items);
      return new ArrayList<String>(tabOrder);

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

      case Defaults.BBC_SOURCE_NAME:
        fragment = BbcFragment.newInstance();
        break;
    }

    return fragment;
  }

  public static boolean tabsReOrdered(List<String> savedOrder, List<String>
      currentOrder) {
    if (currentOrder == null) {
      return false;
    }
    if (savedOrder.size() != currentOrder.size()) {
      return true;
    }

    for (int i = 0; i < savedOrder.size(); i++) {
      if (!savedOrder.get(i).equals(currentOrder.get(i))) {
        return true;
      }
    }
    return false;
  }
}
