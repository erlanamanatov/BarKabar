package com.erkprog.barkabar.ui.sputnik;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.erkprog.barkabar.AppApplication;
import com.erkprog.barkabar.R;
import com.erkprog.barkabar.data.entity.Defaults;
import com.erkprog.barkabar.data.entity.SputnikItem;
import com.erkprog.barkabar.ui.BaseFragment;
import com.erkprog.barkabar.ui.OnClickListener;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.List;

public class SputnikFragment extends BaseFragment implements SputnikContract.View,
    OnClickListener<SputnikItem> {

  private static final String TAG = "SputnikFragment";

  SputnikContract.Presenter mPresenter;
  SputnikAdapter mAdapter;
  RecyclerView mRecyclerView;
  ProgressBar mProgressBar;
  private TextView errorText;
  private ImageView errorImage;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initPresenter();
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_sputnik, container, false);
    mProgressBar = v.findViewById(R.id.sputnik_progress_bar);
    dismissProgress();
    errorText = v.findViewById(R.id.sputnik_error_text);
    errorImage = v.findViewById(R.id.sputnik_error_img);
    hideError();
    initRecyclerView(v);
    return v;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mPresenter.loadData();
    mPresenter.deleteOldItemsInDB();
  }

  private void initPresenter() {
    mPresenter = new SputnikPresenter(AppApplication.getInstance().getLocalRepository());
    mPresenter.bind(this);
  }

  private void initRecyclerView(View v) {
    mRecyclerView = v.findViewById(R.id.sputnik_recycler_view);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
    mRecyclerView.setLayoutManager(layoutManager);
  }

  @Override
  public void showFeed(List<SputnikItem> items) {
    mAdapter = new SputnikAdapter(items, this);
    mRecyclerView.setAdapter(mAdapter);
  }

  @Override
  public void showErrorLoadingData() {
    errorImage.setVisibility(View.VISIBLE);
    errorText.setVisibility(View.VISIBLE);
  }

  private void hideError() {
    errorImage.setVisibility(View.GONE);
    errorText.setVisibility(View.GONE);
  }

  @Override
  public void openArticle(String link) {
    new FinestWebView.Builder(getActivity()).show(link);
  }

  @Override
  public void showMessage(String message) {
    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
  }

  public static SputnikFragment newInstance() {
    return new SputnikFragment();
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
  public String getTitle() {
    return "Sputnik.kg";
  }

  @Override
  public void onItemClick(SputnikItem item) {
    mPresenter.onItemClick(item);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mPresenter.unBind();
  }

  @Override
  public void customizeTab(PagerSlidingTabStrip tab) {
    if (tab != null) {
      tab.setBackgroundResource(R.color.sputnikBackground);
      tab.setTextColorResource(R.color.colorBlack);
      tab.setIndicatorColor(Color.BLACK);
      tab.setDividerColorResource(R.color.colorBlack);
    }
  }

  @Override
  public String getSourceName() {
    return Defaults.SPUTNIK_SOURCE_NAME;
  }
}