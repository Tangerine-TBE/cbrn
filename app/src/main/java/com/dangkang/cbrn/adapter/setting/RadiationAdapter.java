package com.dangkang.cbrn.adapter.setting;

import android.annotation.SuppressLint;
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

import com.dangkang.Constant;
import com.dangkang.cbrn.R;
import com.dangkang.cbrn.dao.DaoTool;
import com.dangkang.cbrn.db.TaintInfo;
import com.dangkang.cbrn.dialog.DataSelectWindow;
import com.dangkang.core.utils.L;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RadiationAdapter extends RecyclerView.Adapter<RadiationAdapter.ViewHolder>  {

    private final List<TaintInfo> data;
    private final Context mContext;
    private final List<String> mStrings = new ArrayList<>();
    private  String mUniType;
    public RadiationAdapter(List<TaintInfo> data,Context context,String unitType){
        this.data = data;
        this.mContext = context;
        this.mUniType = unitType;
        this.mStrings.addAll(Arrays.asList(mContext.getResources().getStringArray(R.array.radiation_type)));
        this.mStrings.remove(0);
    }
    @NonNull
    @Override
    public RadiationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_raditation,parent,false);
        return new ViewHolder(view);
    }
    public final void setUniType(String value){
        this.mUniType = value;
        /*修改所有TaintInfo的单位 为所在的单位中的默认第一个*/
        if (Constant.MEASUREMENT_UNIT_1.equals(mUniType)){
           String unit_value  =  mContext.getResources().getStringArray(R.array.radiation_unit_1)[0];
            for (TaintInfo taintInfo : data){
                taintInfo.setTaint_unit(unit_value);
            }
        }else{
            String unit_value  =  mContext.getResources().getStringArray(R.array.radiation_unit_2)[0];
            for (TaintInfo taintInfo : data){
                taintInfo.setTaint_unit(unit_value);
            }
        }

        notifyItemRangeChanged(0,data.size());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RadiationAdapter.ViewHolder holder, int position) {
        holder.taint_num.setText(data.get(position).getTaint_num()+"");
        holder.taint_dis.setText(data.get(position).getTaint_dis()+"");
        holder.taint_loc.setText(data.get(position).getTaint_loc()+"");
        holder.taint_max.setText(data.get(position).getTaint_max()+"");
        holder.taint_sim.setText(data.get(position).getTaint_sim()+"");
        holder.taint_sim_dis.setText(data.get(position).getTaint_sim_dis()+"");
        holder.unit.setText(data.get(position).getTaint_unit()+"");
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
                        if (value.equals(mContext.getResources().getStringArray(R.array.radiation_sim)[0])){
                            holder.taint_sim_dis.setText(mContext.getResources().getStringArray(R.array.radiation_sim_dis)[0]);
                            data.get(holder.getAdapterPosition()).setTaint_sim_dis(mContext.getResources().getStringArray(R.array.radiation_sim_dis)[0]);
                        }else{
                            holder.taint_sim_dis.setText(mContext.getResources().getStringArray(R.array.radiation_sim_dis)[1]);
                            data.get(holder.getAdapterPosition()).setTaint_sim_dis(mContext.getResources().getStringArray(R.array.radiation_sim_dis)[1]);
                        }
                    }
                }, holder.taint_sim.getText().toString(), holder.taint_sim.getWidth(),false).showPopupWindow(holder.taint_sim);
            }
        });
        holder.unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constant.MEASUREMENT_UNIT_1.equals(mUniType)){
                    new DataSelectWindow(mContext, Arrays.asList(mContext.getResources().getStringArray(R.array.radiation_unit_1)), new DataSelectWindow.OnValueSelected() {
                        @Override
                        public void valueSelected(String value) {
                            holder.unit.setText(value);
                            data.get(holder.getAdapterPosition()).setTaint_unit(value);
                        }
                    }, holder.unit.getText().toString(), holder.unit.getWidth(),true).showPopupWindow(holder.unit);
                }else{
                    new DataSelectWindow(mContext, Arrays.asList(mContext.getResources().getStringArray(R.array.radiation_unit_2)), new DataSelectWindow.OnValueSelected() {
                        @Override
                        public void valueSelected(String value) {
                            holder.unit.setText(value);
                            data.get(holder.getAdapterPosition()).setTaint_unit(value);
                        }
                    }, holder.unit.getText().toString(), holder.unit.getWidth(),true).showPopupWindow(holder.unit);
                }

            }
        });
        holder.taint_dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> strings = DaoTool.queryAllTypeInfoName(2);
                strings.addAll(mStrings);
                new DataSelectWindow(mContext, strings, new DataSelectWindow.OnValueSelected() {
                    @Override
                    public void valueSelected(String value) {
                        holder.taint_dis.setText(value);
                        data.get(holder.getAdapterPosition()).setTaint_dis(value);
                    }
                }, holder.taint_dis.getText().toString(), holder.taint_dis.getWidth(),true).showPopupWindow(holder.taint_dis);
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
        TextView taint_num;
        EditText taint_loc;
        TextView taint_sim;
        TextView taint_dis;
        EditText taint_max;
        EditText taint_sim_dis;
        TextView unit;
        ImageView delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taint_num = itemView.findViewById(R.id.taint_num);
            taint_loc = itemView.findViewById(R.id.taint_loc);
            taint_sim = itemView.findViewById(R.id.taint_sim);
            unit = itemView.findViewById(R.id.unit);
            taint_dis = itemView.findViewById(R.id.taint_dis);
            taint_sim_dis = itemView.findViewById(R.id.taint_sim_dis);
            taint_max = itemView.findViewById(R.id.taint_max);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
