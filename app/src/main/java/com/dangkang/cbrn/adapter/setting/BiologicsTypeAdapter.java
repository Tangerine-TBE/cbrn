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

public class BiologicsTypeAdapter extends RecyclerView.Adapter<BiologicsTypeAdapter.ViewHolder> {
    private final Context mContext;
    /**
     * String value was inserted to the list Ui*/
    private final List<TypeInfo> values;
    private final OnItemClickListener mOnItemClickListener;
    public BiologicsTypeAdapter(List<TypeInfo> values,Context context,OnItemClickListener onItemClickListener){
        this.values = values;
        this.mContext = context;
        this.mOnItemClickListener = onItemClickListener;
    }
    @NonNull
    @Override
    public BiologicsTypeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_biologics_type,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BiologicsTypeAdapter.ViewHolder holder, int position) {
        if (values.get(holder.getAdapterPosition()).getType() == 0){
            holder.tvValue.setText("添加");
            holder.tvValue.setTextColor(ContextCompat.getColor(mContext,R.color.blue));
        }else{
            holder.tvValue.setText(values.get(position).getName());
            holder.tvValue.setTextColor(ContextCompat.getColor(mContext,R.color.un_select_color));
        }
        holder.tvValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClicked(values.get(holder.getAdapterPosition()).getType());
            }
        });
    }
    public interface OnItemClickListener{
        void onItemClicked(int value);
    }
    @Override
    public int getItemCount() {
        return values.size();
    }
    public final void addItem(String value){
        DaoTool.addTypeInfo(1,value,System.currentTimeMillis());
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
