package com.erkprog.barkabar.ui.bbc;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.erkprog.barkabar.R;
import com.erkprog.barkabar.data.entity.BbcFeed;
import com.erkprog.barkabar.data.entity.BbcItem;
import com.erkprog.barkabar.data.entity.Defaults;
import com.erkprog.barkabar.data.network.bbcRepository.BbcApi;
import com.erkprog.barkabar.data.network.bbcRepository.BbcClient;
import com.erkprog.barkabar.ui.BaseFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BbcFragment extends BaseFragment {
  private static final String TAG = "BbcFragment";

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_bbc, container, false);
    return v;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    BbcApi api = BbcClient.getClient();
    api.loadBbcFeed().enqueue(new Callback<BbcFeed>() {
      @Override
      public void onResponse(Call<BbcFeed> call, Response<BbcFeed> response) {
        Log.d(TAG, "onResponse: starts");
        if (response.isSuccessful()) {
           BbcItem item  = response.body().getData().get(0);
          Log.d(TAG, "onResponse: " + item.getTitle());
          Log.d(TAG, "onResponse: " + item.getDescription());
          Log.d(TAG, "onResponse: " + item.getImgUrl());
          Log.d(TAG, "onResponse: " + item.getLink());
        }
      }

      @Override
      public void onFailure(Call<BbcFeed> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
      }
    });
  }

  public static BbcFragment newInstance() {
    return new BbcFragment();
  }

  @Override
  public String getTitle() {
    return "BBC";
  }

  @Override
  public String getSourceName() {
    return Defaults.BBC_SOURCE_NAME;
  }

  @Override
  public void customizeTab(PagerSlidingTabStrip tab) {

  }
}
