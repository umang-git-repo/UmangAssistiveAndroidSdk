package com.negd.umangwebview.ui.jeevan_pramaan.device_select_screen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.negd.umangwebview.BaseViewHolder;
import com.negd.umangwebview.data.model.jp.DeviceData;
import com.negd.umangwebview.databinding.JpDeviceItemViewBinding;

import java.util.List;


public class JPDeviceSelectAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<DeviceData> dataList;
    private Context context;

    public JPDeviceSelectAdapter(List<DeviceData> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        JpDeviceItemViewBinding binding= JpDeviceItemViewBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new JpDeviceViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void addItems(List<DeviceData> list) {
        dataList.addAll(list);
        notifyDataSetChanged();
    }

    public void clearItems() {
        dataList.clear();
    }

    public void setContext(Context  context){
        this.context=context;
    }

    public class JpDeviceViewHolder extends BaseViewHolder{

        private JPDeviceSelectItemViewModel itemViewModel;
        private JpDeviceItemViewBinding itemViewBinding;

        public JpDeviceViewHolder(JpDeviceItemViewBinding binding) {
            super(binding.getRoot());
            itemViewBinding=binding;
        }

        @Override
        public void onBind(int position) {
            final DeviceData deviceData=dataList.get(position);
            itemViewModel=new JPDeviceSelectItemViewModel(deviceData,context);
            itemViewBinding.setDevice(deviceData);
            itemViewBinding.setViewModel(itemViewModel);
            itemViewBinding.setPos(position);
            itemViewBinding.executePendingBindings();
        }
    }
}
