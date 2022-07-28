package com.example.herogame.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.herogame.R;
import com.example.herogame.model.Products;

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
    }

    @Override
    public int getItemCount() {
        if(listProducts!=null){
            return listProducts.size();
        }
        return 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        private TextView tvId;
        private TextView tvName;
        private TextView tvPrice;
        private TextView tvType;
        private Button btnUpdate;
        private Button btnDelete;
        public ProductViewHolder(@NonNull View itemView){
            super(itemView);
            tvId=itemView.findViewById(R.id.tvId);
            tvName=itemView.findViewById(R.id.tvName);
            tvPrice=itemView.findViewById(R.id.tvPrice);
            tvType=itemView.findViewById(R.id.tvType);
            btnDelete=itemView.findViewById(R.id.btnDeleteProduct);
            btnUpdate=itemView.findViewById(R.id.btnUpdate);
        }
    }

}
