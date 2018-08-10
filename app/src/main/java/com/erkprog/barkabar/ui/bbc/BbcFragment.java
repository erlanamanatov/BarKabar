package com.erkprog.barkabar.ui.bbc;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
  private RecyclerView mRecyclerView;
  private BbcAdapter mAdapter;
  private ProgressBar mProgressBar;

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
    init(v);
    return v;
  }

  private void init(View v) {
    mRecyclerView = v.findViewById(R.id.bbc_recycler_view);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    mProgressBar = v.findViewById(R.id.bbc_progress_bar);
    dismissProgress();
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
    if (tab != null) {
      tab.setBackgroundResource(R.color.bbcBackground);
      tab.setTextColorResource(R.color.colorWhite);
      tab.setIndicatorColor(Color.WHITE);
      tab.setDividerColorResource(R.color.colorWhite);
    }
  }

  @Override
  public void showFeed(List<BbcItem> data) {
    mAdapter = new BbcAdapter(data);
    mRecyclerView.setAdapter(mAdapter);
  }

  @Override
  public void showMessage(String message) {
    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void showProgress() {
    mProgressBar.setVisibility(View.VISIBLE);
  }

  @Override
  public void dismissProgress() {
    mProgressBar.setVisibility(View.GONE);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mPresenter.unBind();
  }
}
