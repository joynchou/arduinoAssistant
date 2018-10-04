package com.example.joyh.arduinoAssistant.presentation.ui.activities.hardwareInfo.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joyh.arduinoAssistant.R;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;
import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModel;
import com.example.joyh.arduinoAssistant.threading.MainThreadImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joyn on 2018/9/3 0003.
 * 开发板卡片展示界面recyclerview的adapter
 */

public class AvailableBoardsRecyclerViewAdapter extends RecyclerView.Adapter<AvailableBoardsRecyclerViewAdapter.viewHolder> {
    private List<String> boardImgList;
    private List<String> boardNameList;

    private List<Boolean> boardCollectionState;
    private List<BoardBeanModel> boardBeanModelList;


    private MainThread mainThread;
    private AvailableBoardsRecyclerViewAdapterInterface.Callback callback;

    public void changBoardCollectionState(String name,boolean state){
        final int position =this.boardNameList.indexOf(name);
        if(position!=-1){
            boardCollectionState.set(position,state);
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    notifyItemChanged(position);
                }
            });
        }
    }
    public AvailableBoardsRecyclerViewAdapter(List<BoardBeanModel> boardBeanModelList,List<Boolean> collectionStateList,AvailableBoardsRecyclerViewAdapterInterface.Callback callback) {


        this.boardBeanModelList=boardBeanModelList;
        this.boardCollectionState=collectionStateList;
        this.callback = callback;

        boardNameList = new ArrayList<>();
         boardImgList = new ArrayList<>();
        for (int i = 0; i < boardBeanModelList.size(); i++) {
            boardNameList.add(boardBeanModelList.get(i).getBoardName());
            boardImgList.add(boardBeanModelList.get(i).getPicPath());
        }
        mainThread= MainThreadImpl.getInstance();

        for(int i=0;i<boardImgList.size();i++){
            boardCollectionState.add(false);
        }
    }

    class viewHolder extends RecyclerView.ViewHolder {
        private ImageView boardImg;
        private TextView boardName;
        private ImageButton starButton;
        private CardView cardView;

        public viewHolder(View itemView) {
            super(itemView);
            boardImg = itemView.findViewById(R.id.backgroundImage);
            boardName = itemView.findViewById(R.id.title);
            starButton=itemView.findViewById(R.id.btn_star);
            cardView=itemView.findViewById(R.id.cardview);
        }
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_available_boards, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {
        holder.boardName.setText(boardNameList.get(position));
        Log.i("jpg name:", boardImgList.get(position));
        Bitmap jpg = BitmapFactory.decodeFile(boardImgList.get(position));
        if (jpg != null) {
            holder.boardImg.setImageBitmap(jpg);
        } else {
            Log.e("jpg", "jpg为null");
        }
        holder.starButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onStarButtonClicked(boardBeanModelList.get(position));
            }
        });
        if(boardCollectionState.get(position)){
            holder.starButton.setImageResource(R.drawable.ic_favorite_red);
        }
        else{
            holder.starButton.setImageResource(R.drawable.ic_favorite_border);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onCardClicked(boardNameList.get(position));
            }
        });




    }

    @Override
    public int getItemCount() {
        return boardNameList.size();
    }
}
