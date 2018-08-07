package com.erkprog.barkabar.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.erkprog.barkabar.R;
import com.erkprog.barkabar.data.entity.Defaults;
import com.erkprog.barkabar.data.entity.SourceItem;
import com.erkprog.barkabar.ui.kaktus.KaktusFragment;
import com.erkprog.barkabar.ui.kloop.KloopFragment;
import com.erkprog.barkabar.ui.sputnik.SputnikFragment;
import com.erkprog.barkabar.util.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {

  private static final String TAG = "SettingsActivity";

  SharedPreferences mSharedPreferences;
  private RecyclerView mRecyclerView;
  private SourceAdapter mAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);

    mSharedPreferences = getSharedPreferences(Defaults.SETTINGS, MODE_PRIVATE);

    List<String> order = Utils.getTabOrder(mSharedPreferences);
    Log.d(TAG, "onCreate: " + order);

    mRecyclerView = findViewById(R.id.settings_recycler_view);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    mRecyclerView.setLayoutManager(layoutManager);

    ArrayList<SourceItem> sourceItems = getSourceItems(order);
    mAdapter = new SourceAdapter(sourceItems);
    mRecyclerView.setAdapter(mAdapter);

    ItemDragHelper dragHelper = new ItemDragHelper(mAdapter);
    ItemTouchHelper touchHelper = new ItemTouchHelper(dragHelper);
    touchHelper.attachToRecyclerView(mRecyclerView);

    findViewById(R.id.settings_save_button).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        saveOrderSettings(mAdapter.getData());
      }
    });


  }

  private ArrayList<SourceItem> getSourceItems(List<String> order) {
    if (order == null) {
      return null;
    }
    ArrayList<SourceItem> result = new ArrayList<>();
    for (String sourceName : order) {
      result.add(new SourceItem(sourceName));
    }
    return result;
  }

  public static Intent newIntent(Context context) {
    return new Intent(context, SettingsActivity.class);
  }

  private void saveOrderSettings(List<SourceItem> data) {
    ArrayList<String> tabOrder = new ArrayList<>();
    for (SourceItem item : data) {
      tabOrder.add(item.getSource());
    }

    Log.d(TAG, "saveOrderSettings: " + tabOrder);

    Gson gson = new Gson();
    String order = gson.toJson(tabOrder);
    SharedPreferences.Editor editor = mSharedPreferences.edit();
    editor.putString(Defaults.TAB_ORDER, order);
    editor.apply();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }
}
