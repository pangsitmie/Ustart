package com.example.ustart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ustart.Items;
import com.example.ustart.R;

import com.example.ustart.database.entity.CartEntity;

import java.util.ArrayList;

public class CartRecViewAdapter extends RecyclerView.Adapter<CartRecViewAdapter.ViewHolder> {

    private static final String TAG = "CartRecViewAdapter";
    private Context mContext;
    private ArrayList<Items> data;

    //constructor
    public CartRecViewAdapter(Context mContext, ArrayList<Items> data) {
        this.mContext = mContext;
        this.data = data;
    }
    @NonNull
    @Override
    public CartRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_card, parent, false);
        return new CartRecViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartRecViewAdapter.ViewHolder holder, int position) {
        String title = data.get(position).getnName();
        String expDate =String.valueOf(data.get(position).getdLineDate());
        String price =String.valueOf(data.get(position).getdFinalPrice());
        String quantity =String.valueOf(data.get(position).getqQuantity());
        String vender =data.get(position).getiVender();
        String totalPrice = String.valueOf(data.get(position).getqPrice() * data.get(position).getqQuantity());
        String imgUrl = data.get(position).getImgURL();

        holder.itemTitle.setText(title);
        holder.expDate.setText(expDate);
        holder.itemPrice.setText(price);
        holder.qQuantity.setText(quantity);
        holder.iVender.setText(vender);
        holder.totalPrice.setText(totalPrice);

        Glide.with(mContext).load(imgUrl).into(holder.itemImg);
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setCartList(ArrayList<Items> cartList) {
        data = cartList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView parent;
        ImageView itemImg;
        TextView itemTitle, itemPrice, iVender, expDate, qQuantity, totalPrice;
        Button  btnAdd, btnMin;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.parent);
            itemImg = itemView.findViewById(R.id.img);
            itemTitle = itemView.findViewById(R.id.nName);
            iVender = itemView.findViewById(R.id.expDate);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            totalPrice = itemView.findViewById(R.id.totalPrice);
            qQuantity = itemView.findViewById(R.id.qQuantity);
            expDate = itemView.findViewById(R.id.expTV);
            btnAdd = itemView.findViewById(R.id.btnAdd);
            btnMin = itemView.findViewById(R.id.btnMin);
        }
        void bind(CartEntity cartEntity) {
            itemTitle.setText(String.valueOf(cartEntity.getName()));
            iVender.setText(String.valueOf(cartEntity.getIvender()));
            itemPrice.setText(String.valueOf(cartEntity.getPrice()));
            qQuantity.setText(String.valueOf(cartEntity.getQquantity()));
            expDate.setText(String.valueOf(cartEntity.getExpdate()));
            qQuantity.setText(String.valueOf(cartEntity.getQquantity()));
            Glide.with(parent.getContext()).load(cartEntity.getImgURL()).into(itemImg);

        }

    }
//    public void showDialog(){
//        final Dialog dialog = new Dialog(mContext);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.bottom_sheet_layout);
//
//        ImageView img = dialog.findViewById(R.id.img);
//        TextView title = dialog.findViewById(R.id.title);
//        TextView desc = dialog.findViewById(R.id.expDate);
//        TextView originalPrice = dialog.findViewById(R.id.qPrice);
//        TextView currentPrice = dialog.findViewById(R.id.currentPrice);
//        Button btnMinTransaction = dialog.findViewById(R.id.btnMinTransaction);
//        TextView amount = dialog.findViewById(R.id.amount);
//        Button btnAddTransaction = dialog.findViewById(R.id.btnAddTransaction);
//
//        dialog.show();
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//        dialog.getWindow().setGravity(Gravity.BOTTOM);
//
//    }
}
