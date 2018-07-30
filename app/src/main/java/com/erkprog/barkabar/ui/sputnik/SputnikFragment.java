package com.erkprog.barkabar.ui.sputnik;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erkprog.barkabar.R;
import com.erkprog.barkabar.data.entity.sputnik.SputnikItem;
import com.erkprog.barkabar.data.network.sputnikRepository.SputnikClient;

import java.util.List;

public class SputnikFragment extends Fragment implements SputnikContract.View {

  SputnikContract.Presenter mPresenter;
  SputnikAdapter mAdapter;
  RecyclerView mRecyclerView;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initPresenter();
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_sputnik, container, false);
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
    mAdapter = new SputnikAdapter(items);
    mRecyclerView.setAdapter(mAdapter);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mPresenter.unBind();
  }

  @Override
  public void showMessage(String message) {

  }

  @Override
  public void showProgress() {

  }

  @Override
  public void dismissProgress() {

  }
}
