package com.dangkang.cbrn.adapter.setting;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dangkang.cbrn.R;
import com.dangkang.cbrn.db.ChemicalInfo;

import java.util.List;

/**
 * @author:Administrator
 * @date:2023/1/11
 */
public class ChemicalCwaAdapter extends RecyclerView.Adapter<ChemicalCwaAdapter.ViewHolder> {
    private Context mContext;
    private List<ChemicalInfo> values;

    public ChemicalCwaAdapter(Context context, List<ChemicalInfo> values) {
        this.values = values;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ChemicalCwaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_cwa, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChemicalCwaAdapter.ViewHolder holder, int position) {
        ChemicalInfo cwaBean = values.get(position);
        holder.chart.setText(cwaBean.getCharts());
       Drawable drawable =  ContextCompat.getDrawable(mContext, cwaBean.getIcon());
        assert drawable != null;
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        holder.icon.setCompoundDrawables(drawable,null,null,null);
        holder.name.setText(cwaBean.getChemicalName());
        holder.minValue.setText(cwaBean.getMinValue());
        holder.midValue.setText(cwaBean.getMidValue());
        holder.maxvalue.setText(cwaBean.getMaxValue());
        holder.unit.setText(cwaBean.getUnit());
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView chart;
        TextView icon;
        TextView name;
        TextView minValue;
        TextView midValue;
        TextView maxvalue;
        TextView unit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chart = itemView.findViewById(R.id.chart);
            icon = itemView.findViewById(R.id.icon);
            name = itemView.findViewById(R.id.name);
            minValue = itemView.findViewById(R.id.min);
            midValue = itemView.findViewById(R.id.mid);
            maxvalue = itemView.findViewById(R.id.max);
            unit = itemView.findViewById(R.id.unit);
        }
    }
}
