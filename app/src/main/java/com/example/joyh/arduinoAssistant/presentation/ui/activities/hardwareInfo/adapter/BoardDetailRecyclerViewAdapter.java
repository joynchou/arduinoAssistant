package com.example.joyh.arduinoAssistant.presentation.ui.activities.hardwareInfo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.joyh.arduinoAssistant.R;
import com.example.joyh.arduinoAssistant.domain.model.impl.SharableBeanModel;

import java.util.List;

/**
 * Created by joyn on 2018/9/15 0015.
 */

public class BoardDetailRecyclerViewAdapter extends RecyclerView.Adapter<BoardDetailRecyclerViewAdapter.ViewHolder>{

    public interface Callback{

    }
    private List<SharableBeanModel> resourceList;
    private Context context;

    public BoardDetailRecyclerViewAdapter(Context context, List<SharableBeanModel> resourceList) {
        this.resourceList = resourceList;
        this.context=context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_board_resource, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return resourceList.size();
    }
}
