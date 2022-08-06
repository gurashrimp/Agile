package com.example.herogame.ui.products;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.herogame.R;
import com.example.herogame.adapter.CartAdapter;
import com.example.herogame.model.Cart;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductsFragment extends Fragment {
    private RecyclerView rcvCart;
    private CartAdapter cartAdapter;
    private List<Cart> cartList;
    String strName,strPrice, strTyp, strId;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        rcvCart = view.findViewById(R.id.rcvCart);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcvCart.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        rcvCart.addItemDecoration(dividerItemDecoration);

        cartList = new ArrayList<Cart>();
        cartAdapter=new CartAdapter(cartList);
        rcvCart.setAdapter(cartAdapter);

        getAll();
        return view;

    }

    private void getAll() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://herogame-f7abd-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("cart");
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    Cart cart=dataSnapshot.getValue(Cart.class);
//                    cartList.add(cart);
//                    Log.e("any",cart.toString());
//                }
//                cartAdapter.notifyDataSetChanged();
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getActivity(),"Không lấy được danh sách sản phẩm",Toast.LENGTH_LONG).show();
//            }
//        });
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Cart cart = snapshot.getValue(Cart.class);
                if (cart != null) {
                    cartList.add(cart);
                    cartAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Cart cart = snapshot.getValue(Cart.class);
                if (cart == null || cartList.isEmpty()) {
                    return;
                }
                for (int i = 0; i < cartList.size(); i++) {
                    if (cart.getId_product() == cartList.get(i).getId_product()) {
                        cartList.set(i, cart);
                        break;
                    }

                }
                cartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Cart cart = snapshot.getValue(Cart.class);
                if (cart == null || cartList == null || cartList.isEmpty()) {
                    return;
                }
                for (int i = 0; i < cartList.size(); i++) {
                    if (cart.getId_product() == cartList.get(i).getId_product()) {
                        cartList.remove(cartList.get(i));
                        break;
                    }
                }
                cartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}