package com.erkprog.barkabar.ui.kloop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.erkprog.barkabar.R;
import com.erkprog.barkabar.data.entity.KloopFeed;
import com.erkprog.barkabar.data.network.kloopRepository.KloopApi;
import com.erkprog.barkabar.data.network.kloopRepository.KloopClient;
import com.erkprog.barkabar.ui.BaseFragment;
import com.erkprog.barkabar.util.DateFormatter;
import com.erkprog.barkabar.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KloopFragment extends BaseFragment implements KloopContract.View {

  private KloopContract.Presenter mPresenter;

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
    return v;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mPresenter.loadData();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mPresenter.unBind();
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

  }

  @Override
  public void dismissProgress() {

  }
}
