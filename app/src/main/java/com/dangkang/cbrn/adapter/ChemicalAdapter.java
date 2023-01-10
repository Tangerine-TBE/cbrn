package com.dangkang.cbrn.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dangkang.cbrn.R;

/**
 * @author:Administrator
 * @date:2023/1/10
 */
public class ChemicalAdapter extends RecyclerView.Adapter<ChemicalAdapter.ViewHolder> {
    @NonNull
    @Override
    public ChemicalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chemical,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChemicalAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
