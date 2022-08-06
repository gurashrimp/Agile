package com.example.herogame.ui.home;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    FloatingActionButton fb;
    private RecyclerView rcvProducts;
    private ProductsAdapter productAdapter;
    private List<Products> productList;
    String strName,strPrice, strTyp, strId;
    private Uri imgUri;
    ProgressBar progressBar;
    private  Button btnUpload;
    private ImageView ivProduct;
    private  StorageReference strRef= FirebaseStorage.getInstance().getReference();
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
                String newId = (edtId.getText().toString().trim());
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
        btnUpload=view.findViewById(R.id.btnUpload);
        ivProduct= view.findViewById(R.id.ivProduct);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase database = FirebaseDatabase.getInstance("https://herogame-f7abd-default-rtdb.asia-southeast1.firebasedatabase.app/");
                DatabaseReference myRef = database.getReference("products");
                strId= myRef.push().getKey();
                strName=edtName.getText().toString().trim();
                strPrice= edtPrice.getText().toString().trim();
                strTyp=edtType.getText().toString().trim();
                if(imgUri!=null){
                    uploadToFirebase(imgUri);
                }else{
                    Toast.makeText(getActivity(),"Bạn cần chọn 1 tấm",Toast.LENGTH_SHORT).show();
                }
                Products product=new Products(""+strId,""+strName,Integer.parseInt(strPrice)+0,""+strTyp,""+imgUri.toString());
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
        ivProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent=new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,2);
            }
        });

        dialog.show();

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2 && resultCode==RESULT_OK && data!=null){
            imgUri=data.getData();
            ivProduct.setImageURI(imgUri);
        }
    }
    private void uploadToFirebase(Uri uri){
        StorageReference fileRef=strRef.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Products product=new Products(""+strId,""+strName,Integer.parseInt(strPrice)+0,""+strTyp,""+imgUri.toString() );
                            Toast.makeText(getActivity(),"Tải lên thành công",Toast.LENGTH_SHORT).show();
                        }
                    });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getActivity(),"Tải lên thất bại",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private String getFileExtension(Uri mUri){
        ContentResolver cr= getActivity().getContentResolver();
        MimeTypeMap mime= MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
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