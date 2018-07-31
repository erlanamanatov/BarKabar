package com.erkprog.barkabar.ui.sputnik;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.erkprog.barkabar.R;
import com.erkprog.barkabar.data.entity.sputnik.SputnikItem;
import com.erkprog.barkabar.ui.OnClickListener;
import com.erkprog.barkabar.util.DateFormatter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SputnikAdapter extends RecyclerView.Adapter<SputnikAdapter.SputnikViewHolder> {

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
    if (mData != null) {
      final SputnikItem item = mData.get(position);

      if (item != null) {
        holder.title.setText(item.getTitle());
        holder.description.setText(item.getDescription());
        holder.category.setText(item.getCategory());
        if (item.getImgUrl() != null) {
          Picasso.get()
              .load(item.getImgUrl())
              .placeholder(R.drawable.ic_image_holder)
              .error(R.drawable.ic_image_holder)
              .into(holder.mImageView);
        }

        String date;
        try {
          date = DateFormatter.getFormattedDate(item.getCreated());
        } catch (IllegalArgumentException e) {
          date = "";
        }

        holder.created.setText(date);

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

    public SputnikViewHolder(View itemView) {
      super(itemView);

      mImageView = itemView.findViewById(R.id.sp_item_image);
      title = itemView.findViewById(R.id.sp_item_title);
      description = itemView.findViewById(R.id.sp_item_description);
      category = itemView.findViewById(R.id.sp_item_category);
      created = itemView.findViewById(R.id.sp_item_date);
    }
  }
}
