package com.example.herogame.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.herogame.R;
import com.example.herogame.model.Cart;
import com.example.herogame.model.Products;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Cart> cartList;


    public CartAdapter(List<Cart> cartList){

        this.cartList=cartList;

    }
    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false);
        return new CartAdapter.CartViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, int position) {
        Cart cart=cartList.get(position);
        if(cart==null){
            return;
        }else {
            holder.tvName.setText("Name: " + cart.getName_product());
            holder.tvPrice.setText("Price: " + cart.getPrice());
            holder.tvQuantity.setText("" + cart.getQuantity());
        }


    }

    @Override
    public int getItemCount() {
        if(cartList!=null){
            return cartList.size();
        }
        return 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{

        private TextView tvName;
        private TextView tvPrice;

        private TextView tvQuantity;

        public CartViewHolder(@NonNull View itemView){
            super(itemView);
            tvQuantity=itemView.findViewById(R.id.quantity);
            tvName=itemView.findViewById(R.id.tvCartName);
            tvPrice=itemView.findViewById(R.id.tvCartPrice);


        }
    }
}


