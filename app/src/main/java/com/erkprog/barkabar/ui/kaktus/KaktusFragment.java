package com.erkprog.barkabar.ui.kaktus;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.erkprog.barkabar.R;
import com.erkprog.barkabar.data.network.kaktusRepository.KaktusClient;
import com.erkprog.barkabar.ui.BaseFragment;

public class KaktusFragment extends BaseFragment implements KaktusContract.View {
  private static final String TAG = "KaktusFragment";
  private KaktusContract.Presenter mPresenter;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initPresenter();
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_kaktus, container, false);
    return v;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mPresenter.loadData();
  }

  private void initPresenter() {
    mPresenter = new KaktusPresenter(KaktusClient.getClient());
    mPresenter.bind(this);
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

  }

  @Override
  public void dismissProgress() {

  }
}
