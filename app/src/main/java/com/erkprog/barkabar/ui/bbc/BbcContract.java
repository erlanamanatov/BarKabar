package com.erkprog.barkabar.ui.bbc;

import com.erkprog.barkabar.data.entity.BbcItem;
import com.erkprog.barkabar.ui.BasePresenter;
import com.erkprog.barkabar.ui.BaseView;

import java.util.List;

interface BbcContract {

  interface View extends BaseView {

    void showFeed(List<BbcItem> data);
  }

  interface Presenter extends BasePresenter<View> {

  }
}