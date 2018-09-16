package com.erkprog.barkabar.ui.kaktus;

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
import com.erkprog.barkabar.AppApplication;
import com.erkprog.barkabar.R;
import com.erkprog.barkabar.data.entity.Defaults;
import com.erkprog.barkabar.data.entity.KaktusItem;
import com.erkprog.barkabar.data.network.kaktusRepository.KaktusClient;
import com.erkprog.barkabar.ui.BaseFragment;
import com.erkprog.barkabar.ui.OnClickListener;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.List;

public class KaktusFragment extends BaseFragment implements KaktusContract.View,
    OnClickListener<KaktusItem> {
  private static final String TAG = "KaktusFragment";
  private KaktusContract.Presenter mPresenter;
  private RecyclerView mRecyclerView;
  private KaktusAdapter mAdapter;
  private ProgressBar mProgressBar;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initPresenter();
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_kaktus, container, false);
    initRecyclerView(v);
    mProgressBar = v.findViewById(R.id.kaktus_progress_bar);
    dismissProgress();
    return v;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mPresenter.loadData();
    mPresenter.bind(this);
  }

  private void initPresenter() {
    mPresenter = new KaktusPresenter(KaktusClient.getClient(), AppApplication.getInstance().getDatabase());
    mPresenter.bind(this);
  }

  private void initRecyclerView(View v) {
    mRecyclerView = v.findViewById(R.id.kaktus_recycler_view);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
    mRecyclerView.setLayoutManager(layoutManager);
  }

  @Override
  public void showFeed(List<KaktusItem> data) {
    mAdapter = new KaktusAdapter(data, this, requireContext());
    mRecyclerView.setAdapter(mAdapter);
  }

  @Override
  public void showArticle(String link) {
    new FinestWebView.Builder(getActivity()).show(link);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mPresenter.unBind();
  }

  @Override
  public String getTitle() {
    return "kaktus.media";
  }

  public static KaktusFragment newInstance() {
    return new KaktusFragment();
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
  public void onItemClick(KaktusItem item) {
    mPresenter.onItemClick(item);
  }

  @Override
  public void customizeTab(PagerSlidingTabStrip tab) {
    if (tab != null) {
      tab.setBackgroundResource(R.color.kaktusBackground);
      tab.setTextColorResource(R.color.colorBlack);
      tab.setIndicatorColor(Color.BLACK);
      tab.setDividerColorResource(R.color.colorBlack);
    }
  }

  @Override
  public String getSourceName() {
    return Defaults.KAKTUS_SOURCE_NAME;
  }
}
