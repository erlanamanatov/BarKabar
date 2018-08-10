package com.erkprog.barkabar.ui.bbc;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.erkprog.barkabar.R;
import com.erkprog.barkabar.data.entity.BbcItem;
import com.erkprog.barkabar.data.entity.Defaults;
import com.erkprog.barkabar.data.network.bbcRepository.BbcClient;
import com.erkprog.barkabar.ui.BaseFragment;

import java.util.List;


public class BbcFragment extends BaseFragment implements BbcContract.View {
  private static final String TAG = "BbcFragment";

  private BbcPresenter mPresenter;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mPresenter = new BbcPresenter(BbcClient.getClient());
    mPresenter.bind(this);
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
    mPresenter.loadData();
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

  @Override
  public void showFeed(List<BbcItem> data) {
    BbcItem item = data.get(0);
    Log.d(TAG, "server data: " + item.getTitle());
    Log.d(TAG, "server data: " + item.getDescription());
    Log.d(TAG, "server data: " + item.getImgUrl());
    Log.d(TAG, "server data: " + item.getLink());
  }

  @Override
  public void showMessage(String message) {
    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void showProgress() {

  }

  @Override
  public void dismissProgress() {

  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mPresenter.unBind();
  }
}
