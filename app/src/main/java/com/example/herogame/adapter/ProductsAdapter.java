package com.example.herogame.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.herogame.R;
import com.example.herogame.model.Cart;
import com.example.herogame.model.Products;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

    private List<Products> listProducts;
    private IClickListener iClickListener;
    public interface IClickListener{
        void onClickUpdateProduct(Products product);
        void onClickDeleteProduct(Products product);
    }
    public ProductsAdapter(List<Products> listProducts, IClickListener listener){

        this.listProducts=listProducts;
        this.iClickListener=listener;
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Products product=listProducts.get(position);
        if(product==null){
            return;
        }
        holder.tvId.setText("ID: "+product.getId_product());
        holder.tvName.setText("Name: "+product.getName_product());
        holder.tvPrice.setText("Price: "+product.getPrice());
        holder.tvType.setText("Type: "+product.getType());

        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickListener.onClickUpdateProduct(product);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickListener.onClickDeleteProduct(product);
            }
        });
        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart(product);

            }
        });
    }

    @Override
    public int getItemCount() {
        if(listProducts!=null){
            return listProducts.size();
        }
        return 0;
    }
    private void addToCart(Products product){
        DatabaseReference userCart=FirebaseDatabase
                .getInstance("https://herogame-f7abd-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("cart");
        userCart.child(product.getId_product())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Cart cart=new Cart();
                            cart.setName_product(product.getName_product());
                            cart.setPrice(product.getPrice());
                            cart.setQuantity(1);
                            cart.setTotal_price(product.getPrice());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        Cart cart=new Cart();
        cart.setName_product(product.getName_product());
        cart.setPrice(product.getPrice());
        cart.setQuantity(1);
        cart.setTotal_price(product.getPrice());
        String pathObject = String.valueOf(product.getId_product());

        userCart.child(pathObject).updateChildren(cart.toMap(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {


            }
        });
    }
    public class ProductViewHolder extends RecyclerView.ViewHolder{
        private TextView tvId;
        private TextView tvName;
        private TextView tvPrice;
        private TextView tvType;
        private Button btnUpdate;
        private Button btnDelete;
        private Button addToCart;
        public ProductViewHolder(@NonNull View itemView){
            super(itemView);
            tvId=itemView.findViewById(R.id.tvId);
            tvName=itemView.findViewById(R.id.tvName);
            tvPrice=itemView.findViewById(R.id.tvPrice);
            tvType=itemView.findViewById(R.id.tvType);
            btnDelete=itemView.findViewById(R.id.btnDeleteProduct);
            btnUpdate=itemView.findViewById(R.id.btnUpdate);
            addToCart=itemView.findViewById(R.id.addToCart);
        }
    }

}
