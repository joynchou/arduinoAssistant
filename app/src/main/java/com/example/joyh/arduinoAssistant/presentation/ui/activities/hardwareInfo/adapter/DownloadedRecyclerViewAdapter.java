package com.example.joyh.arduinoAssistant.presentation.ui.activities.hardwareInfo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joyh.arduinoAssistant.R;
import com.example.joyh.arduinoAssistant.data.impl.BoardRepositoryImpl;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;
import com.example.joyh.arduinoAssistant.threading.MainThreadImpl;

import java.util.List;

/**
 * Created by joyn on 2018/9/8 0008.
 * 已下载的开发板列表适配器
 */

public class DownloadedRecyclerViewAdapter extends RecyclerView.Adapter<DownloadedRecyclerViewAdapter.ViewHolder> {
    private List<String> boardName;
    private List<String> boardImage;
    private List<String> content;

    private MainThread mainThread;
    private Context context;
    private BoardRepositoryImpl boardRepository;
    private int sysVersion = Integer.parseInt(Build.VERSION.SDK);
    private Callback callback;

    public interface Callback{
        void onDeleteClicked(String name);
    }

    public DownloadedRecyclerViewAdapter(List<String> boardName, List<String> boardImage, List<String> content, BoardRepositoryImpl boardRepository,Callback callback) {
        this.boardName = boardName;
        this.boardImage = boardImage;
        this.content = content;
        this.boardRepository = boardRepository;
        mainThread = MainThreadImpl.getInstance();
        this.callback=callback;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView backgroundImage;
        public TextView title;
        public View view;
        public TextView contents;
        private CardView cardView;
        private Button delete;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            title = itemView.findViewById(R.id.title);
            backgroundImage = itemView.findViewById(R.id.backgroundImage);
            cardView = itemView.findViewById(R.id.cardview);
            delete = itemView.findViewById(R.id.btn_delete);
            contents=itemView.findViewById(R.id.content);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.title.setText(boardName.get(position));
        Bitmap jpg = BitmapFactory.decodeFile(boardImage.get(position));
        if (jpg != null) {
            holder.backgroundImage.setImageBitmap(jpg);
        } else {
            Log.e("jpg", "jpg为null");
        }
        holder.contents.setText(content.get(position));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("delete", "onClick: "+position);
                callback.onDeleteClicked(boardName.get(position));

            }
        });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_downloaded_boards, parent, false);

        return new ViewHolder(view);
    }



    @Override
    public int getItemCount() {
        return boardName.size();
    }

    public void deleteBoard(String name){

        final int realPosition = this.boardName.indexOf(name);
        if (realPosition != -1) {
            Log.i("deleteItem", "element:" + realPosition+" boardName:"+name);
            this.boardName.remove(realPosition);
            this.boardImage.remove(realPosition);
            this.content.remove(realPosition);

            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    notifyItemRemoved(realPosition);
                    notifyItemRangeChanged(realPosition, boardName.size() - realPosition);
                }
            });
        } else {
            Log.w("deleteItem", "no such element:" + boardName);
        }

    }
}
