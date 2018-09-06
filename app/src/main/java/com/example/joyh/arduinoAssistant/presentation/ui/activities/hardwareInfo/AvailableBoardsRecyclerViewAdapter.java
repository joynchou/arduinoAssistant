package com.example.joyh.arduinoAssistant.presentation.ui.activities.hardwareInfo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
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
import com.example.joyh.arduinoAssistant.domain.model.BoardBeanModel;
import com.example.joyh.arduinoAssistant.threading.MainThreadImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joyn on 2018/9/3 0003.
 */

public class AvailableBoardsRecyclerViewAdapter extends RecyclerView.Adapter<AvailableBoardsRecyclerViewAdapter.viewHolder> {
    private List<String> boardImgList;
    private List<String> boardNameList;
    private List<Boolean> boardCollectionState;
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
    public AvailableBoardsRecyclerViewAdapter(List<String> boardNameList , List<String>  boardImgList, List<Boolean> collectionState,AvailableBoardsRecyclerViewAdapterInterface.Callback callback) {
        this.boardImgList = boardImgList;
        this.boardNameList = boardNameList;
        this.callback = callback;
        mainThread= MainThreadImpl.getInstance();
        boardCollectionState=collectionState;
        for(int i=0;i<boardImgList.size();i++){
            boardCollectionState.add(false);
        }
    }

    class viewHolder extends RecyclerView.ViewHolder {
        private ImageView boardImg;
        private TextView boardName;
        private ImageButton starButton;

        public viewHolder(View itemView) {
            super(itemView);
            boardImg = itemView.findViewById(R.id.backgroundImage);
            boardName = itemView.findViewById(R.id.title);
            starButton=itemView.findViewById(R.id.btn_star);
        }
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview, parent, false);

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
                callback.onStarButtonClicked(boardNameList.get(position));
            }
        });
        if(boardCollectionState.get(position)){
            holder.starButton.setImageResource(R.drawable.ic_star);
        }
        else{
            holder.starButton.setImageResource(R.drawable.ic_unstar);
        }



    }

    @Override
    public int getItemCount() {
        return boardNameList.size();
    }
}
