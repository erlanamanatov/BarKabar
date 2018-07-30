package com.erkprog.barkabar.ui.sputnik;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erkprog.barkabar.R;
import com.erkprog.barkabar.data.network.sputnikRepository.SputnikClient;

public class SputnikFragment extends Fragment implements SputnikContract.View{

  SputnikContract.Presenter mPresenter;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initPresenter();
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_sputnik, container, false);
    return  v;
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
