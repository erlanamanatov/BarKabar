package com.erkprog.barkabar.ui.sputnik;

import com.erkprog.barkabar.ui.BasePresenter;
import com.erkprog.barkabar.ui.BaseView;

public class SputnikContract {

  interface View extends BaseView {

  }

  interface Presenter extends BasePresenter<View> {

    void loadData();
  }
}
