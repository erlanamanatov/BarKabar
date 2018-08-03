package com.erkprog.barkabar.ui.kaktus;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erkprog.barkabar.R;
import com.erkprog.barkabar.data.entity.KaktusFeed;
import com.erkprog.barkabar.data.entity.KaktusItem;
import com.erkprog.barkabar.data.network.kaktusRepository.KaktusApi;
import com.erkprog.barkabar.data.network.kaktusRepository.KaktusClient;
import com.erkprog.barkabar.data.network.kloopRepository.KloopClient;
import com.erkprog.barkabar.ui.BaseFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KaktusFragment extends BaseFragment {
  private static final String TAG = "KaktusFragment";

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
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

    KaktusApi mService = KaktusClient.getClient();
    mService.loadKaktusFeed().enqueue(new Callback<KaktusFeed>() {
      @Override
      public void onResponse(Call<KaktusFeed> call, Response<KaktusFeed> response) {
        Log.d(TAG, "onResponse: starts");
        KaktusItem item = response.body().getData().get(0);
        Log.d(TAG, "onResponse: " + item.getTitle());
        Log.d(TAG, "onResponse: " + item.getDescription());
        Log.d(TAG, "onResponse: " + item.getCreatedDate());
        Log.d(TAG, "onResponse: " + item.getLink());
        Log.d(TAG, "onResponse: " + item.getImgUrl());
      }

      @Override
      public void onFailure(Call<KaktusFeed> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
      }
    });
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
  }

  @Override
  public String getTitle() {
    return "kaktus.media";
  }

  public static KaktusFragment newInstance() {
    return new KaktusFragment();
  }
}
