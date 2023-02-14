package com.dangkang.cbrn.adapter.setting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.clj.fastble.data.BleDevice;
import com.dangkang.cbrn.R;
import com.dangkang.cbrn.db.DeviceInfo;

import java.util.ArrayList;
import java.util.List;

public class BiologicsDeviceAdapter extends RecyclerView.Adapter<BiologicsDeviceAdapter.ViewHolder> {
    private final List<BleDevice> values  =new ArrayList<>();
    public  BiologicsDeviceAdapter(){
    }

    @NonNull
    @Override
    public BiologicsDeviceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_biologics_type, parent,false);
        return new ViewHolder(view);
    }
    public final List<BleDevice> data(){
        return values;
    }

    @Override
    public void onBindViewHolder(@NonNull BiologicsDeviceAdapter.ViewHolder holder, int position) {
        String value = values.get(position).getName();
        if (value == null  ){
            value = "";
        }
        holder.tvValue.setText(value);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
    public final void addItem(BleDevice bleDevice){
        values.add(0,bleDevice);
        notifyItemInserted(0);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvValue;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvValue =  itemView.findViewById(R.id.value);
        }
    }
}
