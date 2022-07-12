package com.example.ustart.adapter;

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

import com.example.ustart.Items;
import com.example.ustart.R;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class CartRecViewAdapter extends RecyclerView.Adapter<CartRecViewAdapter.ViewHolder> {

    private static final String TAG = "CartRecViewAdapter";
    private Context mContext;
    private ArrayList<Items> itemsList = new ArrayList<>();

    //constructor
    public CartRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public CartRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_card, parent, false);
        return new CartRecViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartRecViewAdapter.ViewHolder holder, int position) {
//        String imgUrl = itemsList.get(position).get();
        String vender = itemsList.get(position).getiVender();
        String itemTitle = itemsList.get(position).getnName();
        Double originalPrice = itemsList.get(position).getqPrice();
        Double currentPrice = itemsList.get(position).getdFinalPrice();
//        String desc = itemsList.get(position).getDesc();
        LocalDate expDate = itemsList.get(position).getdLineDate();
        int quantity = itemsList.get(position).getqQuantity();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = expDate.toString();

        //Glide.with(mContext).load(imgUrl).into(holder.itemImg);
        holder.itemTitle.setText(itemTitle);
        holder.itemPrice.setText(currentPrice +" NT");
//        holder.desc.setText(desc);
        holder.expDate.setText("Exp: "+strDate);
        holder.qQuantity.setText(String.valueOf(quantity));
        holder.iVender.setText(vender);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, itemsList.get(holder.getAdapterPosition()).getnName()+"Clickled", Toast.LENGTH_SHORT).show();
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
        TextView itemTitle, itemPrice, iVender, expDate, qQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.parent);
            itemImg = itemView.findViewById(R.id.img);
            itemTitle = itemView.findViewById(R.id.nName);
            iVender = itemView.findViewById(R.id.expDate);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            qQuantity = itemView.findViewById(R.id.qQuantity);
            expDate = itemView.findViewById(R.id.expTV);
        }
    }
    public void showDialog(){
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_layout);

        ImageView img = dialog.findViewById(R.id.img);
        TextView title = dialog.findViewById(R.id.title);
        TextView desc = dialog.findViewById(R.id.expDate);
        TextView originalPrice = dialog.findViewById(R.id.qPrice);
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
