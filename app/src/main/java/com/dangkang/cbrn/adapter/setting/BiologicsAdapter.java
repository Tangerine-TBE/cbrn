package com.dangkang.cbrn.adapter.setting;

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

import com.clj.fastble.data.BleDevice;
import com.dangkang.cbrn.R;
import com.dangkang.cbrn.bean.BleDeviceBean;
import com.dangkang.cbrn.dao.DaoTool;
import com.dangkang.cbrn.db.DeviceInfo;
import com.dangkang.cbrn.dialog.DataSelectWindow;
import com.dangkang.core.utils.GsonUtil;

import java.lang.invoke.CallSite;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author:Administrator
 * @date:2023/1/11
 */
public class BiologicsAdapter extends RecyclerView.Adapter<BiologicsAdapter.ViewHolder> {

    public List<DeviceInfo> deviceBeans = new ArrayList<>();
    public Context mContext;
    private final List<String> mStrings = new ArrayList<>();
    private DataSelectWindow dataSelectWindow;
    private final BiologicsDeviceAdapter adapter;

    public BiologicsAdapter(Context context, BiologicsDeviceAdapter adapter, List<DeviceInfo> deviceBeans) {
        this.mContext = context;
        this.mStrings.addAll(Arrays.asList(mContext.getResources().getStringArray(R.array.biglogics_type)));
        this.mStrings.remove(0);
        this.adapter = adapter;
        this.deviceBeans.addAll(deviceBeans);
    }

    @NonNull
    @Override
    public BiologicsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_biologics, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BiologicsAdapter.ViewHolder holder, int position) {
        holder.editText.setText(deviceBeans.get(position).getLocation());
        holder.id.setText(deviceBeans.get(position).getBrand());
        holder.result.setText(deviceBeans.get(position).getResult());
        holder.whatFor.setText(deviceBeans.get(position).getType());
        holder.whatFor.setOnClickListener(v -> {
            List<String> strings = DaoTool.queryAllTypeInfoName(1);
            strings.addAll(mStrings);
            dataSelectWindow = new DataSelectWindow(mContext, strings, value -> {
                holder.whatFor.setText(value);
                deviceBeans.get(holder.getAdapterPosition()).setType(value);
            }, holder.whatFor.getText().toString(), holder.whatFor.getWidth(), false);
            dataSelectWindow.showPopupWindow(holder.whatFor);
        });
        holder.result.setOnClickListener(v -> {
            dataSelectWindow = new DataSelectWindow(mContext, Arrays.asList(mContext.getResources().getStringArray(R.array.biglogics_result)), new DataSelectWindow.OnValueSelected() {
                @Override
                public void valueSelected(String value) {
                    holder.result.setText(value);
                    deviceBeans.get(holder.getAdapterPosition()).setResult(value);
                }
            }, holder.result.getText().toString(), holder.result.getWidth(), true);
            dataSelectWindow.showPopupWindow(holder.result);
        });
        holder.id.setOnClickListener(v -> {
            ArrayList<String> arrayList = new ArrayList<>();
            for (BleDeviceBean bleDevice : adapter.data()) {
                if (!bleDevice.isSelected) {
                    arrayList.add(bleDevice.getName());
                }
            }
            dataSelectWindow = new DataSelectWindow(mContext, arrayList, value -> {
                holder.id.setText(value);
                for (int i = 0; i < adapter.data().size(); i++) {
                    if (adapter.data().get(i).getName().equals(value)) {
                        deviceBeans.get(holder.getAdapterPosition()).setBleDevice(adapter.data().get(i).getDevice().getAddress());
                        adapter.data().get(i).isSelected = true;
                    }
                }
                deviceBeans.get(holder.getAdapterPosition()).setBrand(value);
            }, holder.id.getText().toString(), holder.id.getWidth(), false);
            dataSelectWindow.showPopupWindow(holder.id);
        });
        holder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                deviceBeans.get(holder.getAdapterPosition()).setLocation(s.toString());
            }
        });
        holder.delete.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos < 0) {
                return;
            }
            DeviceInfo deviceInfo = deviceBeans.get(pos);
            for (BleDeviceBean bleDevice : adapter.data()) {
                if (bleDevice.getName().equals(deviceInfo.getBrand())) {
                    bleDevice.isSelected = false;
                }
            }

            deviceBeans.remove(pos);
            notifyItemRemoved(pos);
        });
    }

    @Override
    public int getItemCount() {
        return deviceBeans.size();
    }


    public final void addItem(DeviceInfo deviceBean) {
        if (dataSelectWindow != null) {
            if (dataSelectWindow.isShowing()) {
                dataSelectWindow.dismiss();
            }
        }
        deviceBeans.add(0, deviceBean);
        notifyItemInserted(0);
    }

    public final List<DeviceInfo> data() {
        return deviceBeans;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView whatFor;
        TextView id;
        TextView result;
        EditText editText;
        ImageView delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            whatFor = itemView.findViewById(R.id.whatFor);
            id = itemView.findViewById(R.id.id);
            result = itemView.findViewById(R.id.result);
            editText = itemView.findViewById(R.id.editText);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
