package com.erkprog.barkabar.ui.kloop;

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

import com.erkprog.barkabar.R;
import com.erkprog.barkabar.data.entity.KloopFeed;
import com.erkprog.barkabar.data.entity.KloopItem;
import com.erkprog.barkabar.data.network.kloopRepository.KloopApi;
import com.erkprog.barkabar.data.network.kloopRepository.KloopClient;
import com.erkprog.barkabar.ui.BaseFragment;
import com.erkprog.barkabar.util.DateFormatter;
import com.erkprog.barkabar.util.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KloopFragment extends BaseFragment implements KloopContract.View {

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
    mAdapter = new KloopAdapter(data);
    mRecyclerView.setAdapter(mAdapter);
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
}
