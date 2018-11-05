package com.erkprog.barkabar.ui.sputnik;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.erkprog.barkabar.R;
import com.erkprog.barkabar.data.entity.SputnikItem;
import com.erkprog.barkabar.ui.OnClickListener;
import com.erkprog.barkabar.util.DateFormatter;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class SputnikAdapter extends RecyclerView.Adapter<SputnikAdapter.SputnikViewHolder> {

  private static final String TAG = "SputnikAdapter";

  private List<SputnikItem> mData;
  private OnClickListener<SputnikItem> mlistener;

  SputnikAdapter(List<SputnikItem> data, OnClickListener<SputnikItem> listener) {
    mData = data;
    mlistener = listener;
  }

  @NonNull
  @Override
  public SputnikViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sputnik_item, parent,
        false);

    return new SputnikViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull SputnikViewHolder holder, int position) {
    holder.title.setText("");
    holder.description.setText("");
    holder.category.setText("");
    holder.created.setText("");
    holder.mImageView.setImageResource(R.drawable.ic_image_holder);

    if (mData != null) {
      final SputnikItem item = mData.get(position);

      if (item != null) {
        holder.title.setText(item.getTitle());
        holder.description.setText(item.getDescription());
        holder.category.setText(item.getCategory());
        if (item.getImgPath() != null) {
          if (item.isLocallyAvailable()) {
            Log.d(TAG, "onBindViewHolder: load image from storage");
            Picasso.get()
                .load(new File(item.getImgPath()))
                .error(R.drawable.ic_image_holder)
                .placeholder(R.drawable.ic_image_holder)
                .into(holder.mImageView);

          } else {
            Log.d(TAG, "onBindViewHolder: image not available from db, loading new from " +
                item.getImgPath());
            Picasso.get()
                .load(item.getImgPath())
                .placeholder(R.drawable.ic_image_holder)
                .error(R.drawable.ic_image_holder)
                .into(holder.mImageView);
          }
        } else {
          holder.mImageView.setVisibility(View.GONE);
        }

        if (item.getPubDate() != null) {
          try {
            holder.created.setText(DateFormatter.getFormattedDate(item.getPubDate()));
          } catch (IllegalArgumentException e) {
            Log.d(TAG, "onBindViewHolder: date : illegalArgumentException");
          }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            if (mlistener != null) {
              mlistener.onItemClick(item);
            }
          }
        });

      }
    }
  }

  @Override
  public int getItemCount() {
    return mData != null ? mData.size() : 0;
  }

  class SputnikViewHolder extends RecyclerView.ViewHolder {

    ImageView mImageView;
    TextView title;
    TextView description;
    TextView category;
    TextView created;

    SputnikViewHolder(View itemView) {
      super(itemView);

      mImageView = itemView.findViewById(R.id.sp_item_image);
      title = itemView.findViewById(R.id.sp_item_title);
      description = itemView.findViewById(R.id.sp_item_description);
      category = itemView.findViewById(R.id.sp_item_category);
      created = itemView.findViewById(R.id.sp_item_date);
    }
  }
}
