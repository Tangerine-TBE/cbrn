package com.dangkang.cbrn.adapter.workspace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dangkang.cbrn.R;
import com.dangkang.cbrn.db.DeviceInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:Administrator
 * @date:2022/1/31
 */
public class BiologicsWsAdapter extends RecyclerView.Adapter<BiologicsWsAdapter.ViewHolder> {
    public List<DeviceInfo> deviceBeans = new ArrayList<>();
    public Context mContext;
    private OnIconClickListener mOnIconClickListener;
    public BiologicsWsAdapter(Context context,OnIconClickListener onIconClickListener){
        this.mOnIconClickListener = onIconClickListener;
        this.mContext = context;
    }
    @NonNull
    @Override
    public BiologicsWsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_biologics_ws,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BiologicsWsAdapter.ViewHolder holder, int position) {
        holder.id.setText(deviceBeans.get(position).getBrand());
        holder.result.setText(deviceBeans.get(position).getResult());
        holder.whatFor.setText(deviceBeans.get(position).getType());
        int status = deviceBeans.get(position).getStatus();
        if (status == 0 ){
            //显示结果，阴性
            holder.icon.setImageDrawable(ContextCompat.getDrawable(mContext,R.mipmap.ic_green));
            holder.icon.setTag(0);
        }else if (status == 1){
            holder.icon.setImageDrawable(ContextCompat.getDrawable(mContext,R.mipmap.vector));
            holder.icon.setTag(1);

            //显示结果，阳性
        }else if ( status == 2){
            holder.icon.setImageDrawable(ContextCompat.getDrawable(mContext,R.mipmap.ic_before));
            holder.icon.setTag(2);

            //尚未测试
        }else if (status == 3){
            holder.icon.setImageDrawable(ContextCompat.getDrawable(mContext,R.mipmap.ic_after));
            holder.icon.setTag(3);
            //测试中
        }else{
            holder.icon.setImageDrawable(null);
        }
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (2 == (int)v.getTag()){
                    mOnIconClickListener.onItemClicked();
                }
            }
        });
    }
    public interface OnIconClickListener{
        void onItemClicked();
    }

    public final void addItem(DeviceInfo deviceBean){
        deviceBeans.add(0,deviceBean);
        notifyItemInserted(0);
    }

    @Override
    public int getItemCount() {
        return deviceBeans.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView whatFor;
        TextView id;
        TextView result;
        ImageView icon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            whatFor = itemView.findViewById(R.id.whatFor);
            id = itemView.findViewById(R.id.id);
            result = itemView.findViewById(R.id.result);
            icon = itemView.findViewById(R.id.icon);
        }
    }
}
