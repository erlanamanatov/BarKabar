package com.erkprog.barkabar.ui.kaktus;

import com.erkprog.barkabar.data.entity.KaktusItem;
import com.erkprog.barkabar.ui.BasePresenter;
import com.erkprog.barkabar.ui.BaseView;

import java.util.List;

public interface KaktusContract {
  interface View extends BaseView {

    void showFeed(List<KaktusItem> data);
  }

  interface Presenter extends BasePresenter<View> {

  }

}
