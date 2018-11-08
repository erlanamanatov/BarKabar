package com.erkprog.barkabar.ui.bbc;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.erkprog.barkabar.R;
import com.erkprog.barkabar.data.entity.BbcItem;
import com.erkprog.barkabar.ui.OnClickListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class BbcAdapter extends RecyclerView.Adapter<BbcAdapter.BbcViewHolder> {

  private static final String TAG = "BbcAdapter";

  private List<BbcItem> mData;
  private OnClickListener<BbcItem> mListener;

  BbcAdapter(List<BbcItem> data, OnClickListener<BbcItem> listener) {
    mData = data;
    mListener = listener;
  }

  @NonNull
  @Override
  public BbcViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bbc_item, parent, false);
    return new BbcViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull BbcViewHolder holder, int position) {
    holder.title.setText("");
    holder.description.setText("");
    holder.image.setImageResource(R.drawable.ic_image_holder);

    final BbcItem item = mData.get(position);
    if (item != null) {
      holder.title.setText(item.getTitle());
      holder.description.setText(item.getDescription());
      if (item.getImgPath() != null) {
        if (item.isLocallyAvailable()){
          Log.d(TAG, "onBindViewHolder: load image from storage");
          Picasso.get()
              .load(new File(item.getImgPath()))
              .error(R.drawable.ic_image_holder)
              .placeholder(R.drawable.ic_image_holder)
              .into(holder.image);
        } else {
          Log.d(TAG, "onBindViewHolder: image not available from db, loading new from " +
              item.getImgPath());
          Picasso.get()
              .load(item.getImgPath())
              .error(R.drawable.ic_image_holder)
              .placeholder(R.drawable.ic_image_holder)
              .into(holder.image);
        }
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
    return mData.size();
  }

  static class BbcViewHolder extends RecyclerView.ViewHolder {
    private TextView title;
    private TextView description;
    private ImageView image;

    BbcViewHolder(View itemView) {
      super(itemView);
      title = itemView.findViewById(R.id.bbc_item_title);
      description = itemView.findViewById(R.id.bbc_item_description);
      image = itemView.findViewById(R.id.bbc_item_img);
    }
  }
}
