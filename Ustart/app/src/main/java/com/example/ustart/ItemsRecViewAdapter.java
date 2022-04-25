package com.example.ustart;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ItemsRecViewAdapter extends RecyclerView.Adapter<ItemsRecViewAdapter.ViewHolder> {

    private static final String TAG = "ItemsRecViewAdapter";
    private Context mContext;
    private ArrayList<Items> itemsList = new ArrayList<>();

    //constructor
    public ItemsRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public ItemsRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.explore_card, parent, false);
        return new ItemsRecViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsRecViewAdapter.ViewHolder holder, int position) {
        String imgUrl = itemsList.get(position).getImgURL();
        String itemTitle = itemsList.get(position).getTitle();
        Double originalPrice = itemsList.get(position).getOriginalPrice();
        Double currentPrice = itemsList.get(position).getCurrentPrice();
        String desc = itemsList.get(position).getDesc();
        LocalDate expDate = itemsList.get(position).getExpDate();

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = expDate.toString();

        Glide.with(mContext).load(imgUrl).into(holder.itemImg);
        holder.itemTitle.setText(itemTitle);
        holder.originalPrice.setText(String.valueOf(originalPrice));
        holder.currentPrice.setText(String.valueOf(currentPrice));
        holder.desc.setText(desc);
        holder.expDate.setText(strDate);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, itemsList.get(holder.getAdapterPosition()).getTitle()+"Clickled", Toast.LENGTH_SHORT).show();
                showDialog();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public void setItemsList(ArrayList<Items> itemsList) {
        this.itemsList = itemsList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView parent;
        ImageView itemImg;
        TextView itemTitle, dcPercent, originalPrice, currentPrice, desc, expDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.parent);
            itemImg = itemView.findViewById(R.id.img);
            itemTitle = itemView.findViewById(R.id.itemTitle);
            dcPercent = itemView.findViewById(R.id.dcPercent);
            originalPrice = itemView.findViewById(R.id.originalPrice);
            currentPrice = itemView.findViewById(R.id.currentPrice);
            desc = itemView.findViewById(R.id.desc);
            expDate = itemView.findViewById(R.id.expTV);
        }
    }
    public void showDialog(){
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_layout);

        ImageView img = dialog.findViewById(R.id.img);
        TextView title = dialog.findViewById(R.id.title);
        TextView desc = dialog.findViewById(R.id.desc);
        TextView originalPrice = dialog.findViewById(R.id.originalPrice);
        TextView currentPrice = dialog.findViewById(R.id.currentPrice);
        Button btnMinTransaction = dialog.findViewById(R.id.btnMinTransaction);
        TextView amount = dialog.findViewById(R.id.amount);
        Button btnAddTransaction = dialog.findViewById(R.id.btnAddTransaction);

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }
}
