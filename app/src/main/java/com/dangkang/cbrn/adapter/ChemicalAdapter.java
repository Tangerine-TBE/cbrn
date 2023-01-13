package com.dangkang.cbrn.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dangkang.cbrn.R;
import com.dangkang.cbrn.db.TaintInfo;
import com.dangkang.cbrn.dialog.DataSelectWindow;
import com.dangkang.core.utils.L;

import java.util.Arrays;
import java.util.List;

/**
 * @author:Administrator
 * @date:2023/1/10
 */
public class ChemicalAdapter extends RecyclerView.Adapter<ChemicalAdapter.ViewHolder> {
    private final List<TaintInfo> data;
    private final Context mContext;
    public ChemicalAdapter(List<TaintInfo> data,Context context){
        this.data = data;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ChemicalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chemical,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChemicalAdapter.ViewHolder holder, int position) {
        holder.taint_num.setText(data.get(position).getTaint_num()+"");
        holder.taint_dis.setText(data.get(position).getTaint_dis()+"");
        holder.taint_loc.setText(data.get(position).getTaint_loc()+"");
        holder.taint_max.setText(data.get(position).getTaint_max()+"");
        holder.taint_sim.setText(data.get(position).getTaint_sim()+"");
        holder.delete.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos < 0){
                return;
            }
            data.remove(pos);
            notifyItemRemoved(pos);
        });
        holder.taint_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                L.e(s.toString());
                data.get(holder.getAdapterPosition()).setTaint_num(Integer.parseInt(s.toString()));
            }
        });
        holder.taint_dis.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                L.e(s.toString());
                data.get(holder.getAdapterPosition()).setTaint_dis(s.toString());
            }
        });
        holder.taint_loc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                L.e(s.toString());
                data.get(holder.getAdapterPosition()).setTaint_loc(s.toString());
            }
        });
        holder.taint_max.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                L.e(s.toString());
                data.get(holder.getAdapterPosition()).setTaint_max(s.toString());
            }
        });
        holder.taint_sim.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                L.e(s.toString());
                data.get(holder.getAdapterPosition()).setTaint_sim(s.toString());
            }
        });
        holder.taint_sim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DataSelectWindow(mContext, Arrays.asList(mContext.getResources().getStringArray(R.array.radiation_sim)), new DataSelectWindow.OnValueSelected() {
                    @Override
                    public void valueSelected(String value) {
                        holder.taint_sim.setText(value);
                        data.get(holder.getAdapterPosition()).setTaint_sim(value);
                    }
                }, holder.taint_sim.getText().toString(), holder.taint_sim.getWidth()).showPopupWindow(holder.taint_sim);
            }
        });
        holder.unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DataSelectWindow(mContext, Arrays.asList(mContext.getResources().getStringArray(R.array.radiation_unit)), new DataSelectWindow.OnValueSelected() {
                    @Override
                    public void valueSelected(String value) {
                        holder.unit.setText(value);
                        data.get(holder.getAdapterPosition()).setTaint_unit(value);
                    }
                }, holder.unit.getText().toString(), holder.unit.getWidth()).showPopupWindow(holder.unit);
            }
        });
        holder.taint_dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DataSelectWindow(mContext, Arrays.asList(mContext.getResources().getStringArray(R.array.radiation_dis)), new DataSelectWindow.OnValueSelected() {
                    @Override
                    public void valueSelected(String value) {
                        holder.taint_dis.setText(value);
                        data.get(holder.getAdapterPosition()).setTaint_unit(value);
                    }
                }, holder.taint_dis.getText().toString(), holder.taint_dis.getWidth()).showPopupWindow(holder.taint_dis);
            }
        });
    }
    public final List<TaintInfo> data(){
        return data;
    }
    public final void addItem(TaintInfo taintInfo){
        data.add(0,taintInfo);
        notifyItemInserted(0);
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
        TextView unit;
        ImageView delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taint_num = itemView.findViewById(R.id.taint_num);
            taint_loc = itemView.findViewById(R.id.taint_loc);
            taint_sim = itemView.findViewById(R.id.taint_sim);
            unit = itemView.findViewById(R.id.unit);
            taint_dis = itemView.findViewById(R.id.taint_dis);
            taint_max = itemView.findViewById(R.id.taint_max);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
