package com.dangkang.cbrn.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dangkang.cbrn.R;
import com.dangkang.cbrn.db.TaintInfo;

import java.util.List;

public class RadiationAdapter extends RecyclerView.Adapter<RadiationAdapter.ViewHolder> {

    private List<TaintInfo> data;
    public RadiationAdapter(List<TaintInfo> data){
        this.data = data;
    }
    @NonNull
    @Override
    public RadiationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_raditation,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RadiationAdapter.ViewHolder holder, int position) {
        holder.taint_dis.setText(data.get(0).getTaint_dis()+"");
        holder.taint_loc.setText(data.get(0).getTaint_loc()+"");
        holder.taint_max.setText(data.get(0).getTaint_max()+"");
        holder.taint_num.setText(data.get(0).getTaint_num()+"");
        holder.taint_sim.setText(data.get(0).getTaint_sim()+"");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        EditText taint_num;
        EditText taint_loc;
        EditText taint_sim;
        EditText taint_dis;
        EditText taint_max;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taint_num = itemView.findViewById(R.id.taint_num);
            taint_loc = itemView.findViewById(R.id.taint_loc);
            taint_sim = itemView.findViewById(R.id.taint_sim);
            taint_dis = itemView.findViewById(R.id.taint_dis);
            taint_max = itemView.findViewById(R.id.taint_max);
        }
    }
}
