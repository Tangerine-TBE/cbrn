package com.dangkang.cbrn.adapter.setting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dangkang.cbrn.R;
import com.dangkang.cbrn.dao.DaoTool;
import com.dangkang.cbrn.db.TypeInfo;

import java.util.List;

public class RadiationTypeAdapter extends RecyclerView.Adapter<RadiationTypeAdapter.ViewHolder> {
    private final Context mContext;
    private final List<TypeInfo> values;
    private final RadiationTypeAdapter.OnItemClickListener mOnItemClickListener;
    public RadiationTypeAdapter(List<TypeInfo> values, Context context, OnItemClickListener onItemClickListener){
        this.values = values;
        this.mContext = context;
        this.mOnItemClickListener = onItemClickListener;
    }
    @NonNull
    @Override
    public RadiationTypeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_biologics_type,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RadiationTypeAdapter.ViewHolder holder, int position) {
        if (values.get(holder.getAdapterPosition()).getType() == 0){
            holder.tvValue.setText("添加");
            holder.tvValue.setTextColor(ContextCompat.getColor(mContext,R.color.blue));
        }else{
            holder.tvValue.setText(values.get(position).getName());
            holder.tvValue.setTextColor(ContextCompat.getColor(mContext,R.color.un_select_color));
        }
        holder.tvValue.setOnClickListener(v -> mOnItemClickListener.onItemClicked(values.get(holder.getAdapterPosition()).getType()));
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
    public interface OnItemClickListener{
        void onItemClicked(int value);
    }
    public final void addItem(String value){
        DaoTool.addTypeInfo(1,value,System.currentTimeMillis(),2);
        values.add(1,new TypeInfo(1,value));
        notifyItemInserted(1);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvValue;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvValue = itemView.findViewById(R.id.value);
        }
    }
}
