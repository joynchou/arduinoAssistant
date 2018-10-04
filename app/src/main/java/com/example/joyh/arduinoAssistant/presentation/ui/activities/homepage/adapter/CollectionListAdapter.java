package com.example.joyh.arduinoAssistant.presentation.ui.activities.homepage.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joyh.arduinoAssistant.R;
import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModel;
import com.example.joyh.arduinoAssistant.domain.model.impl.CollectionModel;
import com.example.joyh.arduinoAssistant.domain.model.impl.ResourceBeanModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by joyn on 2018/9/17 0017.
 */

public class CollectionListAdapter extends RecyclerView.Adapter<CollectionListAdapter.ViewHolder> {
    public interface Callback {
        void onError(String error);
        void onItemClicked(CollectionModel collectionModel);
    }

    public CollectionListAdapter(List<CollectionModel> resourceBeanList, Callback callback) {
        this.resourceBeanList = resourceBeanList;
        this.callback=callback;
        resourceType=new ArrayList<>(resourceBeanList.size()+1);
        resourceType.add(1);
        for(int i=1;i<resourceBeanList.size();i++){
            resourceType.add(0);
        }
    }

    private List<CollectionModel> resourceBeanList;
    private List<Integer> resourceType;

    private Callback callback;
    class ViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout mainLayout;
        private TextView itemName;
        private CircleImageView imageView;


        public ViewHolder(View itemView) {
            super(itemView);
            mainLayout=itemView.findViewById(R.id.mainLayout);
            itemName=itemView.findViewById(R.id.resourceName);
            imageView=itemView.findViewById(R.id.resourceIcon);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=null;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recyclerview_collection_list, parent, false);
                break;
            case 1:view=LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recyclerview_category, parent, false);
                break;


        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        switch (holder.getItemViewType()){
            case 0:
                BoardBeanModel boardBeanModel=(BoardBeanModel)(resourceBeanList.get(position).getCollectionBean()) ;
                if(boardBeanModel!=null) {
                    holder.itemName.setText(boardBeanModel.getBoardName());
                    Bitmap jpg = BitmapFactory.decodeFile(boardBeanModel.getPicPath());
                    if (jpg != null) {
                        holder.imageView.setImageBitmap(jpg);
                    } else {
                        Log.e("jpg", "jpg为null");
                    }
                    holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            callback.onItemClicked(resourceBeanList.get(position));
                        }
                    });
                }
                break;
            case 1:
                break;

        }
    }

    @Override
    public int getItemViewType(int position) {
        return resourceType.get(position);
    }

    @Override
    public int getItemCount() {

      return resourceType.size();
    }
    private void setResourceType(){

    }
}
