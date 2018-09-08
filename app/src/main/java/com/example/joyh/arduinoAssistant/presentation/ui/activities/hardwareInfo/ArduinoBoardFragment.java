package com.example.joyh.arduinoAssistant.presentation.ui.activities.hardwareInfo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joyh.arduinoAssistant.R;
import com.example.joyh.arduinoAssistant.domain.model.impl.BoardBeanModelImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joyn on 2018/8/18 0018.
 * 无效的fragment。可以作为展示作用
 *
 */

public class ArduinoBoardFragment extends Fragment {


private List<BoardBeanModelImpl> list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView view = (RecyclerView) inflater.inflate(R.layout.fragment_hardwarei_info, container, false);
        setupRecyclerView(view);
        return view;
    }


    private void setupRecyclerView(RecyclerView recyclerView) {
        List<String> titles = new ArrayList<>();
        List<String> imageViews = new ArrayList<>();
        List<String> contens = new ArrayList<>();
        boolean starRecord[] = new boolean[10];
        //这是一些测试数据，需要创建适配器来和仓库适配，以便读取数据库中的数据
        titles.add("nano");
        titles.add("mega");
        titles.add("uno");

        contens.add("The Arduino Nano is a small, complete, and breadboard-friendly board based on the ATmega328P; offers the same connectivity and specs of the UNO board in a smaller form factor.");
        contens.add("The Arduino Mega 2560 is a microcontroller board based on the ATmega2560. It has 54 digital input/output pins (of which 15 can be used as PWM outputs), 16 analog inputs, 4 UARTs (hardware serial ports)");
        contens.add("The UNO is the most used and documented board of the whole Arduino family.");
        starRecord[0] = true;
        starRecord[1] = false;
        starRecord[2] = true;
        //
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new APIInfoFragmentRecyclerViewAdapter(getActivity(), imageViews, titles, contens, starRecord));

    }

    public  class APIInfoFragmentRecyclerViewAdapter extends
            RecyclerView.Adapter<APIInfoFragmentRecyclerViewAdapter.ViewHolder> {

        private List<String> titles;
        private List<String> imageViews;
        private List<String> content;
        private boolean starRecord[];

        public APIInfoFragmentRecyclerViewAdapter(Context context, List<String> images, List<String> titles, List<String> contents, boolean starRecord[]) {

            this.titles = titles;
            imageViews = images;
            this.content = contents;
            this.starRecord = starRecord;

        }

        public  class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView backgroundImage;
            public TextView title;
            public View view;

            private TextView content;
            private ImageButton starButton;
            private CardView cardView;

            public ViewHolder(View itemView) {
                super(itemView);
                view = itemView;
                title = itemView.findViewById(R.id.title);

                backgroundImage = itemView.findViewById(R.id.backgroundImage);
                starButton = itemView.findViewById(R.id.btn_star);
                content = itemView.findViewById(R.id.content);
                cardView = itemView.findViewById(R.id.cardview);


            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cardview, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
            holder.title.setText(titles.get(position));
            //holder.backgroundImage.setImageBitmap(imageViews.get(position));
//            Glide.with(ArduinoBoardFragment.this)
//                    .load(list.get(position).getPicURL())
//                    .into(holder.backgroundImage);
            holder.content.setText(content.get(position));

            if (starRecord[position] == true) {
                holder.starButton.setImageResource(R.drawable.ic_star);
            }
            Bitmap bm = ((BitmapDrawable) holder.backgroundImage.getDrawable()).getBitmap();
            Palette.generateAsync(bm, new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(@NonNull Palette palette) {
                    Palette.Swatch swatch = palette.getVibrantSwatch();
                    if (null != swatch) {
                        holder.title.setBackgroundColor(swatch.getRgb());
                        holder.title.setTextColor(swatch.getTitleTextColor());
                    }

                }
            });

            holder.starButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (starRecord[position] == true) {
                        holder.starButton.setImageResource(R.drawable.ic_unstar);
                        starRecord[position] = false;
                    } else {
                        holder.starButton.setImageResource(R.drawable.ic_star);
                        starRecord[position] = true;
                    }
                    Log.i("starRecord", "=" + starRecord);
                }
            });
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("cardview", "onClick: card " + position + "has been pressed");
                }
            });

        }

        @Override
        public int getItemCount() {
            return titles.size();
        }
    }
}
