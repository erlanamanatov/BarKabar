package com.erkprog.barkabar.ui.kloop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class KloopFragment extends BaseFragment {

  private static final String TAG = "KloopFragment";

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
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

    KloopApi mApi = KloopClient.getClient(getActivity());
    mApi.loadSputnikRss().enqueue(new Callback<KloopFeed>() {
      @Override
      public void onResponse(Call<KloopFeed> call, Response<KloopFeed> response) {
        Log.d(TAG, "onResponse: Starts");
        Log.d(TAG, "onResponse: " + response.body().getData().size());
        Log.d(TAG, "onResponse: " + response.body().getData().get(0).getTitle());
        Log.d(TAG, "onResponse: " + response.body().getData().get(0).getCreatedBy());
        String description = response.body().getData().get(0).getDescription();
        Log.d(TAG, "onResponse:  " + Utils.getKloopDescription(description));
        String date = response.body().getData().get(0).getCreatedDate();
        Log.d(TAG, "onResponse: "+ DateFormatter.getFormattedDate(date));


      }

      @Override
      public void onFailure(Call<KloopFeed> call, Throwable t) {
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
    return null;
  }
}
