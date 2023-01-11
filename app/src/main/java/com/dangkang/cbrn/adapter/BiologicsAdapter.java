package com.dangkang.cbrn.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dangkang.cbrn.R;
import com.dangkang.cbrn.bean.DeviceBean;

import java.util.List;

/**
 * @author:Administrator
 * @date:2023/1/11
 */
public class BiologicsAdapter extends RecyclerView.Adapter<BiologicsAdapter.ViewHolder> {

    public List<DeviceBean> deviceBeans;
    public Context mContext;

    public BiologicsAdapter(List<DeviceBean> deviceBeans, Context context){
        this.deviceBeans = deviceBeans;
        this.mContext = context;
    }
    @NonNull
    @Override
    public BiologicsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_biologics,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BiologicsAdapter.ViewHolder holder, int position) {




    }

    @Override
    public int getItemCount() {
        return deviceBeans.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView whatFor;
        TextView id;
        TextView result;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            whatFor = itemView.findViewById(R.id.whatFor);
            id = itemView.findViewById(R.id.id);
            result = itemView.findViewById(R.id.result);
        }
    }
}
