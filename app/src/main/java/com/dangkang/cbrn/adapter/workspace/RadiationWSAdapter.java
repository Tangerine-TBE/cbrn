package com.dangkang.cbrn.adapter.workspace;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dangkang.cbrn.R;
import com.dangkang.cbrn.db.TaintInfo;
import com.dangkang.core.utils.L;

import java.util.List;

public class RadiationWSAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<TaintInfo> mList;
    private int mViewType; /*1.simple 2.details*/ /*@setViewType can change the type */

    /*数据查找数据*/
    public RadiationWSAdapter(List<TaintInfo> list,int viewType) {
        this.mList = list;
        this.mViewType = viewType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view;
        if (mViewType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ws_radiation_simple, parent, false);
            return new TypeOneViewHolder(view);
        } else if (mViewType == 2) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ws_radiation_details, parent, false);
            return new TypeTwoViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ws_radiation_simple, parent, false);
            return new TypeOneViewHolder(view);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (mViewType == 1) {
            TypeOneViewHolder typeOneViewHolder = (TypeOneViewHolder) holder;
            typeOneViewHolder.taint_loc.setText(mList.get(position).getTaint_loc() + "");
            typeOneViewHolder.taint_max.setText(mList.get(position).getTaint_max() + "");
            typeOneViewHolder.taint_sim.setText(mList.get(position).getTaint_sim() + "");
        } else if (mViewType == 2) {
            TypeTwoViewHolder typeTwoViewHolder = (TypeTwoViewHolder) holder;
            typeTwoViewHolder.taint_num.setText(mList.get(position).getTaint_num() + "");
            typeTwoViewHolder.taint_dis.setText(mList.get(position).getTaint_dis() + "");
            typeTwoViewHolder.taint_loc.setText(mList.get(position).getTaint_loc() + "");
            typeTwoViewHolder.taint_max.setText(mList.get(position).getTaint_max() + "");
            typeTwoViewHolder.taint_sim.setText(mList.get(position).getTaint_sim() + "");
        } else {
            TypeOneViewHolder typeOneViewHolder = (TypeOneViewHolder) holder;
            typeOneViewHolder.taint_loc.setText(mList.get(position).getTaint_loc() + "");
            typeOneViewHolder.taint_max.setText(mList.get(position).getTaint_max() + "");
            typeOneViewHolder.taint_sim.setText(mList.get(position).getTaint_sim() + "");
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mViewType;
    }

    public final void setViewType(int viewType) {
        this.mViewType = viewType;
        notifyItemRangeChanged(0, mList.size());
    }

    public static class TypeOneViewHolder extends RecyclerView.ViewHolder {
        TextView taint_loc;
        TextView taint_sim;
        TextView taint_sim1;
        TextView taint_max;
        TextView unit;

        public TypeOneViewHolder(@NonNull View itemView) {
            super(itemView);
            taint_loc = itemView.findViewById(R.id.taint_loc);
            taint_sim = itemView.findViewById(R.id.taint_sim);
            taint_sim1 = itemView.findViewById(R.id.taint_sim1);
            taint_max = itemView.findViewById(R.id.taint_max);
            unit = itemView.findViewById(R.id.unit);
        }
    }

    public static class TypeTwoViewHolder extends RecyclerView.ViewHolder {
        TextView taint_num;
        TextView taint_loc;
        TextView taint_sim;
        TextView taint_sim1;
        TextView taint_dis;
        TextView taint_max;
        TextView unit;

        public TypeTwoViewHolder(@NonNull View itemView) {
            super(itemView);
            taint_num = itemView.findViewById(R.id.taint_num);
            taint_loc = itemView.findViewById(R.id.taint_loc);
            taint_sim = itemView.findViewById(R.id.taint_sim);
            taint_sim1 = itemView.findViewById(R.id.taint_sim1);
            taint_dis = itemView.findViewById(R.id.taint_dis);
            taint_max = itemView.findViewById(R.id.taint_max);
            unit = itemView.findViewById(R.id.unit);
        }
    }
}
