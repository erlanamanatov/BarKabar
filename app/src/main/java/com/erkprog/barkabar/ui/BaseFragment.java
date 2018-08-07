package com.erkprog.barkabar.ui;

import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment implements TabCustomizer {
  public abstract String getTitle();

  public abstract String getSourceName();

}
