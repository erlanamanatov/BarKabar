package com.erkprog.barkabar.ui.kaktus;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.erkprog.barkabar.R;
import com.erkprog.barkabar.data.entity.KaktusItem;
import com.erkprog.barkabar.ui.OnClickListener;
import com.erkprog.barkabar.util.DateFormatter;
import com.erkprog.barkabar.util.ImageLoader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class KaktusAdapter extends RecyclerView.Adapter<KaktusAdapter.KaktusViewHolder> {
  private static final String TAG = "KaktusAdapter";

  private List<KaktusItem> mData;
  private OnClickListener<KaktusItem> mListener;
  private Context mContext;

  KaktusAdapter(List<KaktusItem> data, OnClickListener<KaktusItem> listener, Context context) {
    mData = data;
    mListener = listener;
    mContext = context;
  }

  @NonNull
  @Override
  public KaktusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.kaktus_item, parent,
        false);
    return new KaktusViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull KaktusViewHolder holder, int position) {
    final KaktusItem item = mData.get(position);
    holder.title.setText("");
    holder.description.setText("");
    holder.date.setText("");
    holder.image.setImageResource(R.drawable.ic_image_holder);

    if (item != null) {
      holder.title.setText(item.getTitle());
      holder.description.setText(item.getDescription());
      if (item.getCreatedDate() != null) {
        try {
          holder.date.setText(DateFormatter.getFormattedDate(item.getCreatedDate()));
        } catch (IllegalArgumentException e) {

        }
      }
//      Log.d(TAG, "onBindViewHolder: date" + item.getCreatedDate());
      if (item.getImgUrl() != null) {
        Picasso.get()
            .load(item.getImgUrl())
            .error(R.drawable.ic_image_holder)
            .placeholder(R.drawable.ic_image_holder)
            .into(new ImageLoader("kaktus", holder.image, item.getGuid(), mContext));
      } else {
        holder.image.setVisibility(View.GONE);
      }
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

  static class KaktusViewHolder extends RecyclerView.ViewHolder {
    private TextView title;
    private TextView description;
    private ImageView image;
    private TextView date;

    KaktusViewHolder(View itemView) {
      super(itemView);
      title = itemView.findViewById(R.id.kt_item_title);
      description = itemView.findViewById(R.id.kt_item_description);
      image = itemView.findViewById(R.id.kt_item_img);
      date = itemView.findViewById(R.id.kt_item_created_date);
    }
  }
}
