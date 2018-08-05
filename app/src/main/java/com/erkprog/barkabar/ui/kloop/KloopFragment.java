package com.erkprog.barkabar.ui.kloop;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.erkprog.barkabar.R;
import com.erkprog.barkabar.data.entity.KloopItem;
import com.erkprog.barkabar.data.network.kloopRepository.KloopClient;
import com.erkprog.barkabar.ui.BaseFragment;
import com.erkprog.barkabar.ui.OnClickListener;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.List;

public class KloopFragment extends BaseFragment implements KloopContract.View,
    OnClickListener<KloopItem> {

  private KloopContract.Presenter mPresenter;
  private RecyclerView mRecyclerView;
  private KloopAdapter mAdapter;
  private ProgressBar mProgressBar;

  private static final String TAG = "KloopFragment";

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mPresenter = new KloopPresenter(KloopClient.getClient(requireContext()));
    mPresenter.bind(this);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_kloop, container, false);
    initRecyclerView(v);
    mProgressBar = v.findViewById(R.id.kloop_progress_bar);
    dismissProgress();
    return v;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mPresenter.loadData();
  }

  private void initRecyclerView(View v) {
    mRecyclerView = v.findViewById(R.id.kloop_recycler_vew);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
    mRecyclerView.setLayoutManager(layoutManager);
  }

  @Override
  public void showFeed(List<KloopItem> data) {
    mAdapter = new KloopAdapter(data, this);
    mRecyclerView.setAdapter(mAdapter);
  }

  @Override
  public void showArticle(String url) {
    new FinestWebView.Builder(requireContext()).show(url);
  }

  public static KloopFragment newInstance() {
    return new KloopFragment();
  }

  @Override
  public String getTitle() {
    return "kloop.kg";
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

  @Override
  public void onItemClick(KloopItem item) {
    mPresenter.onItemClick(item);
  }

  @Override
  public void customizeTab(PagerSlidingTabStrip tab) {
    if (tab != null) {
      tab.setBackgroundResource(R.color.kloopBackground);
      tab.setTextColorResource(R.color.colorWhite);
      tab.setIndicatorColor(Color.RED);
      tab.setDividerColorResource(R.color.colorWhite);
    }
  }
}
