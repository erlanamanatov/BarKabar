package com.erkprog.barkabar.ui.kloop;

import com.erkprog.barkabar.data.entity.KloopItem;
import com.erkprog.barkabar.ui.BasePresenter;
import com.erkprog.barkabar.ui.BaseView;

import java.util.List;

class KloopContract {

  interface View extends BaseView {

    void showFeed(List<KloopItem> data);

    void showArticle(String url);

    void showErrorLoadingData();
  }

  interface Presenter extends BasePresenter<View> {

    void onItemClick(KloopItem item);
  }
}
