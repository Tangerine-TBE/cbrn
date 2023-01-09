package com.dangkang.cbrn.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dangkang.cbrn.R;
import com.dangkang.cbrn.dao.DaoTool;
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
        holder.taint_dis.setText(data.get(position).getTaint_dis()+"");
        holder.taint_loc.setText(data.get(position).getTaint_loc()+"");
        holder.taint_max.setText(data.get(position).getTaint_max()+"");
        holder.taint_num.setText(data.get(position).getTaint_num()+"");
        holder.taint_sim.setText(data.get(position).getTaint_sim()+"");
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                DaoTool.removeTaintInfo(data.get(pos).getTaint_num());
                data.remove(pos);
                notifyItemRemoved(pos);
            }
        });

    }
    public final void addItems(List<TaintInfo> list){
        if (data != null){
            if (!data.isEmpty()){
                data.clear();
            }
            data.addAll(list);
            notifyItemInserted(0);

        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        EditText taint_num;
        EditText taint_loc;
        TextView taint_sim;
        TextView taint_dis;
        EditText taint_max;
        ImageView delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taint_num = itemView.findViewById(R.id.taint_num);
            taint_loc = itemView.findViewById(R.id.taint_loc);
            taint_sim = itemView.findViewById(R.id.taint_sim);
            taint_dis = itemView.findViewById(R.id.taint_dis);
            taint_max = itemView.findViewById(R.id.taint_max);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
