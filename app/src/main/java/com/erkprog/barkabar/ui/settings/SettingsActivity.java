package com.erkprog.barkabar.ui.settings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

public class SettingsActivity extends AppCompatActivity implements OnStartDragListener {

  private static final String TAG = "SettingsActivity";

  SharedPreferences mSharedPreferences;
  private RecyclerView mRecyclerView;
  private SourceAdapter mAdapter;
  ItemTouchHelper mItemTouchHelper;


  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);

    mSharedPreferences = getSharedPreferences(Defaults.SETTINGS, MODE_PRIVATE);

    List<String> savedOrder = Utils.getTabOrder(mSharedPreferences);
    Log.d(TAG, "onCreate: " + savedOrder);

    mRecyclerView = findViewById(R.id.settings_recycler_view);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    mRecyclerView.setLayoutManager(layoutManager);

    ArrayList<SourceItem> sourceItems = getSourceItems(savedOrder);
    mAdapter = new SourceAdapter(sourceItems, this);
    mRecyclerView.setAdapter(mAdapter);

    ItemDragHelper dragHelper = new ItemDragHelper(mAdapter);
    mItemTouchHelper = new ItemTouchHelper(dragHelper);
    mItemTouchHelper.attachToRecyclerView(mRecyclerView);

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
    ArrayList<String> tabOrder = getCurrentTabOrder();

    Log.d(TAG, "saveOrderSettings: " + tabOrder);

    Gson gson = new Gson();
    String order = gson.toJson(tabOrder);
    SharedPreferences.Editor editor = mSharedPreferences.edit();
    editor.putString(Defaults.TAB_ORDER, order);
    editor.apply();

    Toast.makeText(this, R.string.new_order_saved, Toast.LENGTH_SHORT).show();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

  @Override
  public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
    mItemTouchHelper.startDrag(viewHolder);
  }

  @Override
  public void onBackPressed() {
    List<String> savedOrder = Utils.getTabOrder(mSharedPreferences);
    if (Utils.tabsReOrdered(savedOrder, getCurrentTabOrder())) {
      showTabsReOrderedDialog();
    } else {
      super.onBackPressed();
    }
  }

  private void showTabsReOrderedDialog() {
    new AlertDialog.Builder(this)
        .setTitle(R.string.unsaved_changes)
        .setMessage(R.string.back_to_main)
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            finish();
          }
        })
        .setNegativeButton("Cancel", null)
        .create()
        .show();
  }

  public ArrayList<String> getCurrentTabOrder() {
    ArrayList<String> tabOrder = new ArrayList<>();
    for (SourceItem item : mAdapter.getData()) {
      tabOrder.add(item.getSource());
    }
    return tabOrder;
  }
}
