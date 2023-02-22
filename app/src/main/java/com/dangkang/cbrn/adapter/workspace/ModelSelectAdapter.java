package com.dangkang.cbrn.adapter.workspace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dangkang.cbrn.R;
import com.dangkang.cbrn.bean.ModelDeviceBean;
import com.dangkang.cbrn.service.AbstractDevice;

import java.util.List;

public class ModelSelectAdapter extends RecyclerView.Adapter<ModelSelectAdapter.ViewHolder> {
    private final List<ModelDeviceBean> mList;
    private final Context mContext;

    public ModelSelectAdapter(List<ModelDeviceBean> mList, Context context,OnModelSelected onModelSelected) {
        this.mList = mList;
        this.mOnModelSelected = onModelSelected;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ModelSelectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_mode, parent, false);

        return new ViewHolder(view);
    }
    public interface OnModelSelected{
        void onSelected(ModelDeviceBean modelDeviceBean);
    }
    private final OnModelSelected mOnModelSelected;


    @Override
    public void onBindViewHolder(@NonNull ModelSelectAdapter.ViewHolder holder, int position) {
        ModelDeviceBean modelDeviceBean = mList.get(position);
        holder.tvMac.setText(modelDeviceBean.getIp());
        holder.tvPower.setText(String.valueOf(modelDeviceBean.getPower()));
        holder.connect_type.setText(modelDeviceBean.getConnect_way());
        holder.tvName.setText(modelDeviceBean.getModelName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.checkbox.setChecked(!modelDeviceBean.selected
                );
            }
        });
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    modelDeviceBean.selected = true;
                    holder.tvMac.setTextColor(ContextCompat.getColor(mContext, R.color.color_37B48B));
                    holder.tvPower.setTextColor(ContextCompat.getColor(mContext, R.color.color_37B48B));
                    holder.connect_type.setTextColor(ContextCompat.getColor(mContext, R.color.color_37B48B));
                    holder.tvName.setTextColor(ContextCompat.getColor(mContext, R.color.color_37B48B));
                    mOnModelSelected.onSelected(modelDeviceBean);

                } else {
                    modelDeviceBean.selected = false;
                    holder.tvMac.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                    holder.tvPower.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                    holder.connect_type.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                    holder.tvName.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                }
            }
        });
        if (!modelDeviceBean.selected) {
            holder.tvMac.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            holder.tvPower.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            holder.connect_type.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            holder.tvName.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        } else {
            holder.tvMac.setTextColor(ContextCompat.getColor(mContext, R.color.color_37B48B));
            holder.tvPower.setTextColor(ContextCompat.getColor(mContext, R.color.color_37B48B));
            holder.connect_type.setTextColor(ContextCompat.getColor(mContext, R.color.color_37B48B));
            holder.tvName.setTextColor(ContextCompat.getColor(mContext, R.color.color_37B48B));
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMac;
        TextView tvName;
        TextView tvPower;
        TextView connect_type;
        CheckBox checkbox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkbox = itemView.findViewById(R.id.checkbox);
            tvMac = itemView.findViewById(R.id.tvMac);
            tvName = itemView.findViewById(R.id.tvName);
            tvPower = itemView.findViewById(R.id.tvPower);
            connect_type = itemView.findViewById(R.id.connect_type);
        }
    }
}
