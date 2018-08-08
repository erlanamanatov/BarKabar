package com.erkprog.barkabar.ui.settings;

import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.erkprog.barkabar.R;
import com.erkprog.barkabar.data.entity.SourceItem;

import java.util.Collections;
import java.util.List;

public class SourceAdapter extends RecyclerView.Adapter<SourceAdapter.SourceViewHolder>
    implements ActionCompletionContract {

  private List<SourceItem> mData;
  private OnStartDragListener mDragListener;

  SourceAdapter(List<SourceItem> data, OnStartDragListener listener) {
    mData = data;
    mDragListener = listener;
  }

  @NonNull
  @Override
  public SourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.source_item, parent, false);
    return new SourceViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull final SourceViewHolder holder, int position) {
    SourceItem item = mData.get(position);
    if (item != null) {
      holder.sourceName.setText(item.getSource());

      holder.handleView.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
          if (MotionEventCompat.getActionMasked(event) ==
              MotionEvent.ACTION_DOWN) {
            mDragListener.onStartDrag(holder);
          }
          return false;
        }
      });
    }
  }

  public List<SourceItem> getData() {
    return mData;
  }

  @Override
  public int getItemCount() {
    return mData.size();
  }

  @Override
  public void onViewMoved(int fromPosition, int toPosition) {
    if (fromPosition < toPosition) {
      for (int i = fromPosition; i < toPosition; i++) {
        Collections.swap(mData, i, i + 1);
      }
    } else {
      for (int i = fromPosition; i > toPosition; i--) {
        Collections.swap(mData, i, i - 1);
      }
    }
    notifyItemMoved(fromPosition, toPosition);
  }

  static class SourceViewHolder extends RecyclerView.ViewHolder {
    private TextView sourceName;
    private ImageView handleView;

    SourceViewHolder(View itemView) {
      super(itemView);
      sourceName = itemView.findViewById(R.id.source_name);
      handleView = itemView.findViewById(R.id.source_handle);
    }
  }
}
