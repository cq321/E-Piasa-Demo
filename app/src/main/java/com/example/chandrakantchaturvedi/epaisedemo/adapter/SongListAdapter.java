package com.example.chandrakantchaturvedi.epaisedemo.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.chandrakantchaturvedi.epaisedemo.R;
import com.example.chandrakantchaturvedi.epaisedemo.Tasks.ImageDownloaderTask;
import com.example.chandrakantchaturvedi.epaisedemo.Utils;
import com.example.chandrakantchaturvedi.epaisedemo.model.SongItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chandrakantchaturvedi on 29/5/18.
 */

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.MyViewHolder> {
    private Context context;
    private List<SongItem> list = new ArrayList<>();
    private launchScreen launchScreenlistnear;

    public SongListAdapter(Context context, List<SongItem> list, launchScreen launchScreen) {
        this.context = context;
        this.list = list;
        launchScreenlistnear = launchScreen;
    }

    @Override
    public SongListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_item_cell, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(SongListAdapter.MyViewHolder holder, final int position) {

        holder.mArtistName.setText(list.get(position).getArtistName());
        holder.mTrackName.setText(list.get(position).getTrackName());
        holder.tvDate.setText(Utils.getTime(list.get(position).getReleaseDate()));

        if (list.get(position).getArtworkUrl60() != null) {
            holder.mImg.setTag(list.get(position).getArtworkUrl60());
            //holder.mImg.setImageBitmap(null);
            holder.mImg.setTag(list.get(position).getArtworkUrl60());
            new ImageDownloaderTask(holder.mImg).execute(list.get(position).getArtworkUrl60());
        } else {
            //holder.mImg.setBackground(context.getResources().getDrawable(R.mipmap.ic_launcher));
        }

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchScreenlistnear.passModel(list.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, mTrackName, mArtistName;
        ImageView mImg;
        RelativeLayout mainLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvDate = (TextView) itemView.findViewById(R.id.date);
            mArtistName = (TextView) itemView.findViewById(R.id.singer_name);
            mTrackName = (TextView) itemView.findViewById(R.id.track_name);
            mImg = (ImageView) itemView.findViewById(R.id.thumbImage);
            mainLayout = (RelativeLayout) itemView.findViewById(R.id.rel_lay);

        }
    }

    public interface launchScreen {
        void passModel(SongItem songItem);
    }
}
