package com.erkprog.barkabar.ui.sputnik;

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

import com.erkprog.barkabar.R;
import com.erkprog.barkabar.data.entity.sputnik.SputnikItem;
import com.erkprog.barkabar.data.network.sputnikRepository.SputnikClient;
import com.erkprog.barkabar.ui.BaseFragment;
import com.erkprog.barkabar.ui.OnClickListener;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.List;

public class SputnikFragment extends BaseFragment implements SputnikContract.View,
    OnClickListener<SputnikItem> {

  SputnikContract.Presenter mPresenter;
  SputnikAdapter mAdapter;
  RecyclerView mRecyclerView;
  ProgressBar mProgressBar;

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
    initRecyclerView(v);
    return v;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mPresenter.loadData();
  }

  private void initPresenter() {
    mPresenter = new SputnikPresenter(SputnikClient.getClient(requireContext()));
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
  public void onDestroy() {
    super.onDestroy();
    mPresenter.unBind();
  }

  @Override
  public void showMessage(String message) {
    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

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
  public void openArticle(String link) {
    new FinestWebView.Builder(getActivity()).show(link);
  }
}