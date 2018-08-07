package com.erkprog.barkabar.ui.settings;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.erkprog.barkabar.R;
import com.erkprog.barkabar.data.entity.SourceItem;

import java.util.ArrayList;
import java.util.List;

public class SourceAdapter extends RecyclerView.Adapter<SourceAdapter.SourceViewHolder> {

  private List<SourceItem> mData;

  SourceAdapter(List<SourceItem> data) {
    mData = data;
  }

  @NonNull
  @Override
  public SourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.source_item, parent, false);
    return new SourceViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull SourceViewHolder holder, int position) {
    SourceItem item = mData.get(position);
    if (item != null) {
      holder.sourceName.setText(item.getSource());
    }
  }

  @Override
  public int getItemCount() {
    return mData.size();
  }

  static class SourceViewHolder extends RecyclerView.ViewHolder {
    private TextView sourceName;

    public SourceViewHolder(View itemView) {
      super(itemView);
      sourceName = itemView.findViewById(R.id.sourceName);
    }
  }
}
