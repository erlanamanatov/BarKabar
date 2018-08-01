package com.erkprog.barkabar.ui.kloop;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.erkprog.barkabar.R;
import com.erkprog.barkabar.data.entity.KloopItem;
import com.erkprog.barkabar.ui.OnClickListener;
import com.erkprog.barkabar.util.DateFormatter;
import com.erkprog.barkabar.util.Utils;

import java.util.List;

public class KloopAdapter extends RecyclerView.Adapter<KloopAdapter.KloopViewHolder> {

  private List<KloopItem> mData;
  private OnClickListener<KloopItem> mListener;

  KloopAdapter(List<KloopItem> data, OnClickListener<KloopItem> listener) {
    mData = data;
    mListener = listener;
  }

  @NonNull
  @Override
  public KloopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.kloop_item, parent, false);
    return new KloopViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull KloopViewHolder holder, int position) {
    final KloopItem item = mData.get(position);

    if (item != null) {
      holder.title.setText(item.getTitle());
      holder.author.setText(item.getCreatedBy());
      String description = item.getDescription();
      holder.description.setText(description != null ? Utils.getKloopDescription(description) : "");
      String date = item.getCreatedDate();
      holder.createdDate.setText(DateFormatter.getFormattedDate(date));

      holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (mListener != null) {
            mListener.onItemClick(item);
          }
        }
      });
    }

  }

  @Override
  public int getItemCount() {
    return mData != null ? mData.size() : 0;
  }

  static class KloopViewHolder extends RecyclerView.ViewHolder {
    private TextView title;
    private TextView description;
    private TextView author;
    private TextView createdDate;

    KloopViewHolder(View itemView) {
      super(itemView);
      title = itemView.findViewById(R.id.kl_item_title);
      description = itemView.findViewById(R.id.kl_item_description);
      author = itemView.findViewById(R.id.kl_item_created_by);
      createdDate = itemView.findViewById(R.id.kl_item_date);
    }
  }
}
