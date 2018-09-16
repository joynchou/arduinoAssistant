package com.example.joyh.arduinoAssistant.presentation.ui.activities.hardwareInfo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.joyh.arduinoAssistant.R;
import com.example.joyh.arduinoAssistant.data.impl.BoardRepositoryImpl;
import com.example.joyh.arduinoAssistant.domain.executor.MainThread;
import com.example.joyh.arduinoAssistant.threading.MainThreadImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.impl.DownloadBoardResourceInteractorImpl.DOWNLOAD_STATE_DOWNLOADING;
import static com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.impl.DownloadBoardResourceInteractorImpl.DOWNLOAD_STATE_FINISH;
import static com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.impl.DownloadBoardResourceInteractorImpl.DOWNLOAD_STATE_PAUSE;
import static com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.impl.DownloadBoardResourceInteractorImpl.DOWNLOAD_STATE_RESUME;
import static com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.impl.DownloadBoardResourceInteractorImpl.DOWNLOAD_STATE_RETRY;
import static com.example.joyh.arduinoAssistant.domain.interactors.impl.hardwareinfo.impl.DownloadBoardResourceInteractorImpl.DOWNLOAD_STATE_UNDOWNLOAD;

/**
 * Created by joyn on 2018/8/24 0024.
 * 可下载的板子recyclerview的adapter
 * activity或者fragment需要实现这个适配器的接口
 */

public class DownloadableRecyclerViewAdapter extends
        RecyclerView.Adapter<DownloadableRecyclerViewAdapter.ViewHolder> implements
        DownloadableRecyclerViewAdapterInterface {
    private List<String> boardName;
    private List<String> boardImage;
    private List<Integer> downloadState;
    private List<Integer> downloadPresent;
    private List<String> content;
    private MainThread mainThread;
    private DownloadableRecyclerViewAdapterInterface.Callback callback;
    private Context context;
    private BoardRepositoryImpl boardRepository;
    private int sysVersion = Integer.parseInt(Build.VERSION.SDK);

    public DownloadableRecyclerViewAdapter(Context context, List<String> images, List<String> boardName, List<String> contents, BoardRepositoryImpl boardRepository, DownloadableRecyclerViewAdapterInterface.Callback callback) {
        this.boardName = boardName;
        this.boardImage = images;
        this.content = contents;
        this.callback = callback;
        this.context = context;
        this.boardRepository = boardRepository;
        int presentSize = boardName.size();
        Log.i("presentSize", "size=" + presentSize);
        this.downloadPresent = new ArrayList<>();
        this.downloadState = new ArrayList<>();
        mainThread = MainThreadImpl.getInstance();
        for (int i = 0; i < presentSize; i++) {
            downloadPresent.add(0);
            downloadState.add(0);
        }
        Log.i("downloadPresent", "size=" + downloadPresent.size());

    }


    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_downloadable, parent, false);

        return new ViewHolder(view);
    }

    private void changeDownloadProgress(String boardName, int progress) {
        Log.i("downloadProgressArray", "boardName:" + boardName);
        final int realPosition = this.boardName.indexOf(boardName);
        if (realPosition != -1) {
            Log.w("downloadProgressArray", "realPosition:" + realPosition);
            this.downloadPresent.set(realPosition, progress);
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    notifyItemChanged(realPosition);
                }
            });
        } else {
            Log.w("downloadProgressArray", "no such element:" + boardName);
        }
    }

    private void changeDownloadState(String boardName, int state) {
        Log.i("downloadStateArray", "boardName:" + boardName);
        final int realPosition = this.boardName.indexOf(boardName);
        if (realPosition != -1) {
            Log.w("downloadStateArray", "realPosition:" + realPosition);
            this.downloadState.set(realPosition, state);
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    notifyItemChanged(realPosition);
                }
            });
        } else {
            Log.w("downloadStateArray", "no such element:" + boardName);
        }
    }

    private void deleteItem(String name) {
        int realPosition = this.boardName.indexOf(name);
        if (realPosition != -1) {
            Log.i("deleteItem", "element:" + realPosition + " boardName:" + name);
            this.boardName.remove(realPosition);
            this.boardImage.remove(realPosition);
            this.downloadState.remove(realPosition);
            this.downloadPresent.remove(realPosition);

            Log.i("downloadPresentList", downloadPresent.toString());
        } else {
            Log.w("deleteItem", "no such element:" + boardName);
        }

    }

    private void deletefile(String fileName) {
        try {
            // 找到文件所在的路径并删除该文件
            File file = new File(boardRepository.boardDownloadDeletePath(fileName), fileName);
            if (file.exists()) {
                deleteFile(file);
                Log.i("fileDelete", "deletefile: " + file.toString());
            } else {
                Log.i("fileDelete", "no such file: " + file.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteFile(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

    private File saveImage(Bitmap bmp, String name) {
        String path = Environment.getExternalStorageDirectory().getPath()
                + File.separator
                + "ArduinoResource"
                + File.separator
                + "boardResource";
        File appDir = new File(path, name);
        if (!appDir.exists()) {
            appDir.mkdir();
            Log.i("创建目录", appDir.toString());
        }
        String fileName = name + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return appDir;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        //TODO 这里不能处理保存图片的逻辑，需要在后期更改，现在是临时的方法
        SimpleTarget<Bitmap> mSimpleTarget = new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                holder.backgroundImage.setImageBitmap(resource);
                Bitmap bm = ((BitmapDrawable) holder.backgroundImage.getDrawable()).getBitmap();
                saveImage(bm, boardName.get(position));
            }
        };
        holder.title.setText(boardName.get(position));



        //TODO:glide 中的很多方法都用不了，不清楚原因是什么

        Glide.with(context).asBitmap().load(boardImage.get(position)).into(mSimpleTarget);



        switch (downloadState.get(position)) {
            case DOWNLOAD_STATE_DOWNLOADING:
                holder.download.setText("暂停");
                break;
            case DOWNLOAD_STATE_FINISH:
                holder.download.setText("查看");
                break;
            case DOWNLOAD_STATE_PAUSE:
                holder.download.setText("继续");
                break;
            case DOWNLOAD_STATE_UNDOWNLOAD:
                holder.download.setText("下载");
                break;
            case DOWNLOAD_STATE_RESUME:
                holder.download.setText("暂停");
                break;
            case DOWNLOAD_STATE_RETRY:
                holder.download.setText("重试");
                break;
        }


        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("cardview", "onClick: download " + position + "has been pressed" + "state:" + downloadState.get(position));
                callback.onDownloadBoard(boardName.get(position), position, downloadState.get(position));

            }
        });
        //TODO :这个操作是删除，但是这里没有用interactor来实现，是错误的做法，后期需要将其逻辑
        //移到interactor之中

        if (downloadPresent.get(position) != 0) {
            Log.i("downloadPresent:", "position:" + position + "  progress" + downloadPresent.get(position).toString());
            holder.progressBar.setVisibility(View.VISIBLE);
            holder.progressBar.setProgress(downloadPresent.get(position));
        } else {
            holder.progressBar.setVisibility(View.GONE);
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView backgroundImage;
        public TextView title;
        public View view;
        private ProgressBar progressBar;
        private CardView cardView;
        private Button download;
        private ConstraintLayout constraintLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            title = itemView.findViewById(R.id.title);
            backgroundImage = itemView.findViewById(R.id.backgroundImage);
            if (sysVersion > 18) {
                cardView = itemView.findViewById(R.id.cardview);
            } else {
                constraintLayout = itemView.findViewById(R.id.constraionlayout);
            }

            progressBar = itemView.findViewById(R.id.download_progress);
            download = itemView.findViewById(R.id.btn_download);


        }
    }


    @Override
    public int getItemCount() {
        return boardName.size();
    }

    //////此处的接口实现是用来改变view的，即ui/////////////////////////

    public void onProgressBarChanged(String boardName, int listPosition, int progress) {
        this.changeDownloadProgress(boardName, progress);
        this.changeDownloadState(boardName, DOWNLOAD_STATE_DOWNLOADING);

    }


    public void onBoardDownloadFailed(String boardName, int listPosition) {
        this.changeDownloadState(boardName, DOWNLOAD_STATE_RETRY);
    }


    public void onBoardDownloadFinish(String boardName, int listPosition) {
        changeDownloadProgress(boardName, 100);
        this.changeDownloadState(boardName, DOWNLOAD_STATE_FINISH);

    }

    //下载暂停，按钮显示继续

    public void onBoardDownloadPause(String boardName, int listPosition) {
        this.changeDownloadState(boardName, DOWNLOAD_STATE_PAUSE);
    }


    public void onBoardDownloadResume(String boardName, int listPosition) {
        this.changeDownloadState(boardName, DOWNLOAD_STATE_RESUME);
    }


    public void onBoardDownloadRetry(String boardName, int listPosition) {
        this.changeDownloadState(boardName, DOWNLOAD_STATE_RETRY);
    }

    //开始下载

    public void onBoardDownloadStarted(String boardName, int listPosition) {
        this.changeDownloadState(boardName, DOWNLOAD_STATE_DOWNLOADING);
    }
}
