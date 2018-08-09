package com.erkprog.barkabar.ui.bbc;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.erkprog.barkabar.R;
import com.erkprog.barkabar.data.entity.Defaults;
import com.erkprog.barkabar.ui.BaseFragment;

public class BbcFragment extends BaseFragment {

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_bbc, container, false);
    return v;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  public static BbcFragment newInstance() {
    return new BbcFragment();
  }

  @Override
  public String getTitle() {
    return "BBC";
  }

  @Override
  public String getSourceName() {
    return Defaults.BBC_SOURCE_NAME;
  }

  @Override
  public void customizeTab(PagerSlidingTabStrip tab) {

  }
}
