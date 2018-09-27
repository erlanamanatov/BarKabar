package com.erkprog.barkabar.ui.bbc;

import com.erkprog.barkabar.data.entity.BbcItem;
import com.erkprog.barkabar.ui.BasePresenter;
import com.erkprog.barkabar.ui.BaseView;

import java.util.List;

interface BbcContract {

  interface View extends BaseView {

    void showFeed(List<BbcItem> data);

    void showArticle(String link);

    void showErrorLoadingData();
  }

  interface Presenter extends BasePresenter<View> {

    void onItemClick(BbcItem item);
  }
}
