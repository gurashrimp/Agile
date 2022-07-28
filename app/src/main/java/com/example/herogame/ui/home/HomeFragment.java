package com.example.herogame.ui.home;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.herogame.R;
import com.example.herogame.adapter.ProductsAdapter;
import com.example.herogame.databinding.FragmentHomeBinding;
import com.example.herogame.model.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    FloatingActionButton fb;
    private RecyclerView rcvProducts;
    private ProductsAdapter productAdapter;
    private List<Products> productList;
    String strName,strPrice, strTyp, strId;
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rcvProducts = view.findViewById(R.id.rcvProduct);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcvProducts.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        rcvProducts.addItemDecoration(dividerItemDecoration);
        productList = new ArrayList<Products>();
        productAdapter = new ProductsAdapter(productList,new ProductsAdapter.IClickListener() {
            @Override
            public void onClickUpdateProduct(Products product) {
                openDialogUpdateProduct(product);
            }

            @Override
            public void onClickDeleteProduct(Products product) {
                onClickDeleteData(product);
            }
        });
        rcvProducts.setAdapter(productAdapter);
        fb=view.findViewById(R.id.fbAdd);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogAddProduct();
            }
        });
        getAll();
        return view;
    }

    private void getAll() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://herogame-f7abd-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("products");
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    Product product=dataSnapshot.getValue(Product.class);
//                    productList.add(product);
//                    Log.e("any",product.toString());
//                }
//
//                productAdapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(all_products.this,"Không lấy được danh sách sản phẩm",Toast.LENGTH_LONG).show();
//            }
//        });
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Products product = snapshot.getValue(Products.class);
                if (product != null) {
                    productList.add(product);
                    productAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Products product = snapshot.getValue(Products.class);
                if (product == null || productList.isEmpty()) {
                    return;
                }
                for (int i = 0; i < productList.size(); i++) {
                    if (product.getId_product() == productList.get(i).getId_product()) {
                        productList.set(i, product);
                        break;
                    }

                }
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Products product = snapshot.getValue(Products.class);
                if (product == null || productList == null || productList.isEmpty()) {
                    return;
                }
                for (int i = 0; i < productList.size(); i++) {
                    if (product.getId_product() == productList.get(i).getId_product()) {
                        productList.remove(productList.get(i));
                        break;
                    }
                }
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void openDialogUpdateProduct(Products product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inf = this.getLayoutInflater();
        View view = inf.inflate(R.layout.sp_update, null);
        builder.setView(view);
        Dialog dialog = builder.create();

        EditText edtId = view.findViewById(R.id.etIdUpdate);
        EditText edtName = view.findViewById(R.id.etTenUpdate);
        EditText edtPrice = view.findViewById(R.id.etGiaUpdate);
        EditText edtType = view.findViewById(R.id.etMotaUpdate);
        Button btnSave = view.findViewById(R.id.btnEditGD);
        Button btnCancel = view.findViewById(R.id.btnCancelGDUpdate);

        edtId.setText("" + product.getId_product());
        edtName.setText("" + product.getName_product());
        edtPrice.setText("" + product.getPrice());
        edtType.setText("" + product.getType());
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://herogame-f7abd-default-rtdb.asia-southeast1.firebasedatabase.app/");
                DatabaseReference myRef = database.getReference("products");
                int newId = Integer.parseInt(edtId.getText().toString().trim());
                String newName = edtName.getText().toString().trim();
                int newPrice = Integer.parseInt(edtPrice.getText().toString().trim());
                String newType = edtType.getText().toString().trim();
                String pathObject = String.valueOf(product.getId_product());
                product.setId_product(newId);
                product.setName_product(newName);
                product.setPrice(newPrice);
                product.setType(newType);
                myRef.child(pathObject).updateChildren(product.toMap(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(getActivity(), "Cập nhật thành công", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        productAdapter.notifyDataSetChanged();
                    }
                });

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void openDialogAddProduct() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inf = this.getLayoutInflater();
        View view = inf.inflate(R.layout.sp_add, null);
        builder.setView(view);
        Dialog dialog = builder.create();

        EditText edtId = view.findViewById(R.id.etId);
        EditText edtName = view.findViewById(R.id.etTen);
        EditText edtPrice = view.findViewById(R.id.etGia);
        EditText edtType = view.findViewById(R.id.etMota);
        Button btnSave = view.findViewById(R.id.btnAddGD);
        Button btnCancel = view.findViewById(R.id.btnCancelGD);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://herogame-f7abd-default-rtdb.asia-southeast1.firebasedatabase.app/");
                DatabaseReference myRef = database.getReference("products");
                strId=edtId.getText().toString().trim();
                strName=edtName.getText().toString().trim();
                strPrice= edtPrice.getText().toString().trim();
                strTyp=edtType.getText().toString().trim();
                Products product=new Products(0+Integer.parseInt(strId),""+strName,Integer.parseInt(strPrice)+0,""+strTyp);
                String pathObject = String.valueOf(product.getId_product());

                myRef.child(pathObject).updateChildren(product.toMap(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(getActivity(), "Cập nhật thành công", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        productAdapter.notifyDataSetChanged();
                    }
                });

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void onClickDeleteData(Products product) {
        new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.app_name))
                .setMessage("Bạn có chắc muốn xóa sản phẩm này không?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance("https://herogame-f7abd-default-rtdb.asia-southeast1.firebasedatabase.app/");
                        DatabaseReference myRef = database.getReference("products");
                        String pathObject = String.valueOf(product.getId_product());
                        myRef.child(pathObject).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(getActivity(), "Đã xóa thành công", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                })
                .setNegativeButton("No", null)
                .show();


    }
}