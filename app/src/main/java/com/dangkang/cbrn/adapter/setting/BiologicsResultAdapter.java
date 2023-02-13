package com.dangkang.cbrn.adapter.setting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dangkang.cbrn.R;

import java.util.List;

public class BiologicsResultAdapter extends RecyclerView.Adapter<BiologicsResultAdapter.ViewHolder> {
    private List<String> values;
    public BiologicsResultAdapter(List<String> values){
        this.values =values;
    }
    @NonNull
    @Override
    public BiologicsResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_biologics_type,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BiologicsResultAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvValue;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvValue = itemView.findViewById(R.id.value);
        }
    }
}
