package com.erkprog.barkabar.ui.sputnik;

import com.erkprog.barkabar.data.entity.SputnikItem;
import com.erkprog.barkabar.ui.BasePresenter;
import com.erkprog.barkabar.ui.BaseView;

import java.util.List;

class SputnikContract {

  interface View extends BaseView {

    void showFeed(List<SputnikItem> items);

    void openArticle(String link);

    void showErrorLoadingData();
  }

  interface Presenter extends BasePresenter<View> {

    void onItemClick(SputnikItem item);
  }
}
