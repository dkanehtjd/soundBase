package com.defaultcompany.soundbase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustumViewHolder> {


    private ArrayList<MusicProfile> arrayList;
    private Context context;

    public CustomAdapter(ArrayList<MusicProfile> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        CustumViewHolder holder = new CustumViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull CustumViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getProfile())
                .into(holder.iv_profile);
        holder.tv_song.setText(arrayList.get(position).getSong());
        holder.tv_userName.setText(arrayList.get(position).getUserName());
        holder.tv_type.setText(arrayList.get(position).getType());
        holder.btn_play.setText(arrayList.get(position).getMusic());

    }

    @Override
    public int getItemCount() {
        //삼항 연산자
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustumViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_profile;
        TextView tv_song;
        TextView tv_userName;
        TextView tv_type;
        TextView btn_play;



        public CustumViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = itemView.findViewById(R.id.iv_profile);
            this.tv_song = itemView.findViewById(R.id.tv_song);
            this.tv_userName = itemView.findViewById(R.id.tv_userName);
            this.tv_type = itemView.findViewById(R.id.tv_type);
            this.btn_play = itemView.findViewById(R.id.btn_play);
        }
    }
}
