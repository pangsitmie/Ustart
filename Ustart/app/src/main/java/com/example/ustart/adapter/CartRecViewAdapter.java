package com.example.ustart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ustart.Items;
import com.example.ustart.MainActivity;
import com.example.ustart.R;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class CartRecViewAdapter extends RecyclerView.Adapter<CartRecViewAdapter.ViewHolder> {

    private static final String TAG = "CartRecViewAdapter";
    private Context mContext;
    private ArrayList<Items> cartList = new ArrayList<>();

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
        String vender = cartList.get(position).getiVender();
        String itemTitle = cartList.get(position).getnName();
        Double originalPrice = cartList.get(position).getqPrice();
        Double currentPrice = cartList.get(position).getdFinalPrice();
        LocalDate expDate = cartList.get(position).getdLineDate();
        int quantity = cartList.get(position).getqQuantity();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = expDate.toString();

        //Glide.with(mContext).load(imgUrl).into(holder.itemImg);
        holder.itemTitle.setText(itemTitle);
        holder.itemPrice.setText(currentPrice +" NT");
        holder.expDate.setText("Exp: "+strDate);
        holder.qQuantity.setText(String.valueOf(quantity));
        holder.iVender.setText(vender);

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ipd =String.valueOf(cartList.get(position).getIpd());
                for (int i=0;i<MainActivity.cartList.size();i++){
                    String checkIpd =String.valueOf(MainActivity.cartList.get(i).getIpd());
                    if(ipd.equals(checkIpd)){
                        MainActivity.cartList.get(i).addQuantity();
                        int tempQty = MainActivity.cartList.get(i).getqQuantity();
                        cartList.get(position).setqQuantity(tempQty);
                        holder.qQuantity.setText(String.valueOf(tempQty));
                        Toast.makeText(mContext,String.valueOf(tempQty) , Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        holder.btnMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ipd =String.valueOf(cartList.get(position).getIpd());
                for (int i=0;i<MainActivity.cartList.size();i++){
                    String checkIpd =String.valueOf(MainActivity.cartList.get(i).getIpd());
                    if(ipd.equals(checkIpd)){
                        MainActivity.cartList.get(i).minQuantity();
                        int tempQty = MainActivity.cartList.get(i).getqQuantity();
                        cartList.get(position).setqQuantity(tempQty);
                        holder.qQuantity.setText(String.valueOf(tempQty));
                        Toast.makeText(mContext,String.valueOf(tempQty) , Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        double totalPrice = currentPrice * cartList.get(position).getqQuantity();
        holder.totalPrice.setText(String.valueOf(totalPrice));

    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public void setCartList(ArrayList<Items> cartList) {
        this.cartList = cartList;
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
