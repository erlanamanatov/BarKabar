package com.erkprog.barkabar.ui.sputnik;

import com.erkprog.barkabar.data.entity.sputnik.SputnikItem;
import com.erkprog.barkabar.ui.BasePresenter;
import com.erkprog.barkabar.ui.BaseView;

import java.util.List;

public class SputnikContract {

  interface View extends BaseView {

    void showFeed(List<SputnikItem> items);
  }

  interface Presenter extends BasePresenter<View> {

    void loadData();
  }
}
