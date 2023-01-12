package com.dangkang.cbrn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dangkang.cbrn.R;
import com.dangkang.cbrn.db.DeviceInfo;
import com.dangkang.cbrn.db.TaintInfo;
import com.dangkang.cbrn.dialog.DataSelectWindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author:Administrator
 * @date:2023/1/11
 */
public class BiologicsAdapter extends RecyclerView.Adapter<BiologicsAdapter.ViewHolder> {

    public List<DeviceInfo> deviceBeans = new ArrayList<>();
    public Context mContext;
    private DataSelectWindow dataSelectWindow;
    public BiologicsAdapter( Context context){
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

        holder.id.setText(deviceBeans.get(position).getBrand());
        holder.result.setText(deviceBeans.get(position).getResult());
        holder.whatFor.setText(deviceBeans.get(position).getType());
        holder.whatFor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSelectWindow =   new DataSelectWindow(mContext, Arrays.asList(mContext.getResources().getStringArray(R.array.biglogics_type)), new DataSelectWindow.OnValueSelected() {
                    @Override
                    public void valueSelected(String value) {
                        holder.whatFor.setText(value);
                        deviceBeans.get(holder.getAdapterPosition()).setType(value);
                    }
                }, holder.whatFor.getText().toString(), holder.whatFor.getWidth());
                dataSelectWindow.showPopupWindow(holder.whatFor);
            }
        });
        holder.result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSelectWindow =    new DataSelectWindow(mContext, Arrays.asList(mContext.getResources().getStringArray(R.array.biglogics_result)), new DataSelectWindow.OnValueSelected() {
                    @Override
                    public void valueSelected(String value) {
                        holder.result.setText(value);
                        deviceBeans.get(holder.getAdapterPosition()).setResult(value);
                    }
                }, holder.result.getText().toString(), holder.result.getWidth());
                dataSelectWindow .showPopupWindow(holder.result);
            }
        });
    }

    @Override
    public int getItemCount() {
        return deviceBeans.size();
    }


    public final void addItem(DeviceInfo deviceBean){
        if (dataSelectWindow != null){
            if (dataSelectWindow.isShowing()){
                dataSelectWindow.dismiss();
            }
        }
        deviceBeans.add(0,deviceBean);
        notifyItemInserted(0);
    }
    public final List<DeviceInfo> data(){
        return deviceBeans;
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
