package com.example.ustart.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
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
import com.example.ustart.Items;
import com.example.ustart.MainActivity;
import com.example.ustart.R;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class ItemsRecViewAdapter extends RecyclerView.Adapter<ItemsRecViewAdapter.ViewHolder> {

    private static final String TAG = "ItemsRecViewAdapter";
    private Context mContext;
    private ArrayList<Items> itemsList = new ArrayList<>();
    private int itemSelected;
    int itemSelectedAmount =1;
    private static final StrikethroughSpan STRIKE_THROUGH_SPAN = new StrikethroughSpan();

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
        //String vender = itemsList.get(position).getiVender();
        String itemTitle = itemsList.get(position).getnName();
        Double originalPrice = itemsList.get(position).getqPrice();
        Double currentPrice = itemsList.get(position).getdFinalPrice();
//        String desc = itemsList.get(position).getDesc();
        LocalDate expDate = itemsList.get(position).getdLineDate();
        int quantity = itemsList.get(position).getqQuantity();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = expDate.toString();

        Glide.with(mContext).load(imgUrl).into(holder.itemImg);
        holder.itemTitle.setText(itemTitle);
        holder.desc.setText(itemsList.get(position).getDesc());
        holder.currentPrice.setText(currentPrice +" NT");
        holder.expDate.setText(strDate);


        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemSelected = holder.getAdapterPosition();
                Toast.makeText(mContext, itemsList.get(itemSelected).getnName()+" Selected", Toast.LENGTH_SHORT).show();
                showDialog(itemSelected);
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
        TextView itemTitle, currentPrice, expDate, desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.parent);
            itemImg = itemView.findViewById(R.id.img);
            itemTitle = itemView.findViewById(R.id.nName);
            desc = itemView.findViewById(R.id.desc);
            expDate = itemView.findViewById(R.id.expDate);
            currentPrice = itemView.findViewById(R.id.currentPrice);
        }
    }
    public void showDialog(int itemSelected){
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_layout);

        ImageView img = dialog.findViewById(R.id.img);
        TextView title = dialog.findViewById(R.id.title);
        TextView desc = dialog.findViewById(R.id.desc);
        TextView  ivender= dialog.findViewById(R.id.ivender);
        TextView expDate = dialog.findViewById(R.id.expDate);
        TextView originalPrice = dialog.findViewById(R.id.qPrice);
        TextView currentPrice = dialog.findViewById(R.id.currentPrice);
        Button btnMinTransaction = dialog.findViewById(R.id.btnMinTransaction);
        TextView amount = dialog.findViewById(R.id.amount);
        Button btnAddTransaction = dialog.findViewById(R.id.btnAddTransaction);
        Button btnAddToCart = dialog.findViewById(R.id.btnAddtoCart);

        //set
        itemSelectedAmount=1;
        title.setText(itemsList.get(itemSelected).getnName());
        desc.setText(itemsList.get(itemSelected).getDesc());
        expDate.setText(itemsList.get(itemSelected).getdLineDate().toString());
        ivender.setText(itemsList.get(itemSelected).getiVender());
//        originalPrice.setText( );
        String oPrice = String.valueOf(itemsList.get(itemSelected).getqPrice());
        originalPrice.setText((oPrice), TextView.BufferType.SPANNABLE);
        Spannable spannable = (Spannable) originalPrice.getText();
        spannable.setSpan(STRIKE_THROUGH_SPAN, 0, oPrice.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        currentPrice.setText( String.valueOf(itemsList.get(itemSelected).getdFinalPrice()));
        amount.setText(String.valueOf(itemSelectedAmount));
        String imgUrl = itemsList.get(itemSelected).getImgURL();

        Glide.with(mContext).load(imgUrl).into(img);


        btnMinTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemSelectedAmount--;
                amount.setText(String.valueOf(itemSelectedAmount));
            }
        });

        btnAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemSelectedAmount++;
                amount.setText(String.valueOf(itemSelectedAmount));
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemsList.get(itemSelected).setqQuantity(itemSelectedAmount);
                MainActivity.cartList.add(itemsList.get(itemSelected));
                MainActivity.checkCartCount();
                dialog.dismiss();
            }
        });


        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }
}
