package com.negd.umangwebview.data.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.negd.umangwebview.R;
import com.negd.umangwebview.data.model.biomodel.DeviceData;

import java.util.List;

/**
 * Created by sunil on 3-DEC-22.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    List<DeviceData> deviceList;
    RDClickListener rdClickListener;
    Context mContext;

    public RecyclerViewAdapter(Context context, List<DeviceData> hospitalses, RDClickListener hospitalClickListener) {
        this.deviceList = hospitalses;
        this.rdClickListener = hospitalClickListener;
        this.mContext = context;
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jp_device_item_view, parent, false);
        return new RecyclerViewHolder(view, rdClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.textViewName.setText(deviceList.get(position).getName());
        holder.textViewModel.setText(deviceList.get(position).getMake());
        Glide.with(mContext)
                .load(deviceList.get(position).getImg())
                .placeholder(R.drawable.fingerprint_acc_setting)
                .centerCrop()
                .into(holder.imgLogo);
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewName, textViewModel;
        ImageView imgLogo;
        RDClickListener rdClickListener;

        public RecyclerViewHolder(View itemView, RDClickListener rdClikItem) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.bioDeviceTxt);
            textViewModel = itemView.findViewById(R.id.bioDeviceMakeTxt);
            imgLogo = itemView.findViewById(R.id.bioDeviceImg);
            this.rdClickListener = rdClikItem;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            rdClickListener.getItem(getAdapterPosition());
        }
    }

   public interface RDClickListener {
        void getItem(int position);
    }
}


