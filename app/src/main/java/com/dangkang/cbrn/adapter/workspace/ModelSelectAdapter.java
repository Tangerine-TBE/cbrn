package com.dangkang.cbrn.adapter.workspace;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dangkang.cbrn.R;
import com.dangkang.cbrn.bean.ModelDeviceBean;
import com.dangkang.cbrn.service.AbstractDevice;

import java.util.List;

public class ModelSelectAdapter extends RecyclerView.Adapter<ModelSelectAdapter.ViewHolder> {
    private List<ModelDeviceBean> mList;

    public ModelSelectAdapter(List<ModelDeviceBean> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public ModelSelectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_mode, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ModelSelectAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
