package com.example.ustart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ustart.Markets;
import com.example.ustart.R;

import java.util.ArrayList;

public class MarketsRecViewAdapter extends RecyclerView.Adapter<MarketsRecViewAdapter.ViewHolder> {
    private static final String TAG = "MarketsRecViewAdapter";
    private Context mContext;
    private ArrayList<Markets> marketsList = new ArrayList<>();

    //constructor
    public MarketsRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.market_card,parent,false);
        return new MarketsRecViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = marketsList.get(position).getName();
        String imgUrl = marketsList.get(position).getImgURL();


        holder.marketName.setText(name);
        Glide.with(mContext).load(imgUrl).into(holder.marketIcon);
    }

    @Override
    public int getItemCount() {
        return marketsList.size();
    }
    //set arraylist setter
    public void setMarketsList(ArrayList<Markets> marketList) {
        this.marketsList = marketList;
        notifyDataSetChanged();
    }

//    VIEWHOLDER FOR TOP MarketsRecViewAdapter.ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView parent;
        ImageView marketIcon;
        TextView marketName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.parent);
            marketIcon = itemView.findViewById(R.id.marketIcon);
            marketName = itemView.findViewById(R.id.marketName);



        }
    }
}
