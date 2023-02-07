package com.dangkang.cbrn.adapter.workspace;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RadiationWSAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<?> list;
    private Context mContext;
    private int mViewType; /*1.simple 2.details*/
    public RadiationWSAdapter(){

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mViewType;
    }
    public final void setViewType(int viewType){
        this.mViewType = viewType;
        notifyItemRangeChanged(0,list.size());
    }

    public static class TypeOneViewHolder extends RecyclerView.ViewHolder{

        public TypeOneViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    public static class TypeTwoViewHolder extends RecyclerView.ViewHolder{

        public TypeTwoViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
