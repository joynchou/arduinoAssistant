package com.example.joyh.arduinoAssistant.presentation.ui.activities.hardwareInfo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joyh.arduinoAssistant.R;
import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModel;
import com.example.joyh.arduinoAssistant.domain.model.impl.SharableBeanModel;

import java.util.List;

/**
 * Created by joyn on 2018/9/15 0015.
 */

public class BoardDetailRecyclerViewAdapter extends RecyclerView.Adapter<BoardDetailRecyclerViewAdapter.ViewHolder>{

    public interface Callback{

        void onShareBtnClicked(BoardBeanModel boardBeanModel);
        void onItemClicked(BoardBeanModel boardBeanModel);
    }
    private BoardBeanModel resourceList;
    private Context context;
    private Callback callback;

    public BoardDetailRecyclerViewAdapter(Context context, BoardBeanModel resourceList,Callback callback) {
        this.resourceList = resourceList;
        this.context=context;
        this.callback=callback;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView resourceName;
        private ImageView resourceIcon;
        private ImageButton shareBtn;
        private ConstraintLayout mainLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            resourceName=itemView.findViewById(R.id.resourceName);
            resourceIcon=itemView.findViewById(R.id.resourceIcon);
            shareBtn=itemView.findViewById(R.id.btn_share);
            mainLayout=itemView.findViewById(R.id.mainLayout);
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
        switch(position){
            case 0:
                if(resourceList.getSchematicPath()!=null){
                    holder.resourceName.setText("原理图");
                    holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            callback.onItemClicked(resourceList);
                        }
                    });
                    holder.shareBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            callback.onShareBtnClicked(resourceList);
                        }
                    });
                }
                break;
            case 1:
                if(resourceList.getPicPath()!=null){
                    holder.resourceName.setText("真机图");
                    holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;

        }





    }

    @Override
    public int getItemCount() {
        return resourceList.getResourceNum();

    }
}
