package com.example.herogame.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.herogame.db.DbHelper;
import com.example.herogame.model.Products;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class ProductsDAO {
    DbHelper helper;
//    public ProductsDAO(Context context){
//        helper=new DbHelper(context);
//    }
//    public ArrayList<Products> getAll(){
//        ArrayList<Products> ds=new ArrayList<>();
//        SQLiteDatabase db=helper.getReadableDatabase();
//        String sql="select * from Products";
//        Cursor cs=db.rawQuery(sql,null);
//        cs.moveToFirst();
//        while(cs.isAfterLast()==false){
//            int ma=cs.getInt(0);
//            String name=cs.getString(1);
//            int price=cs.getInt(2);
//            int id_type=cs.getInt(3);
//
//            Products pd=new Products(ma,name,price,id_type);
//            ds.add(pd);
//            cs.moveToNext();
//        }
//        cs.close();
//        db.close();
//        return ds;
//    }
//
//    public boolean insert(Products products){
//        SQLiteDatabase db=helper.getWritableDatabase();
//        ContentValues values=new ContentValues();
//        values.put("Name",products.getName_product());
//        values.put("Price",products.getPrice());
//        values.put("Type",products.getId_type());
//        long row=db.insert("PRODUCTS",null,values);
//        return (row>0);
//    }
//    public boolean update(Products products){
//        String sql="update Products set TieuDe=?,Ngay=?,Tien=?,MoTa=?,MaLoai=?, where MaGD=?";
//        SQLiteDatabase db=helper.getWritableDatabase();
//        ContentValues values=new ContentValues();
//        values.put("Name",products.getName_product());
//        values.put("Price",products.getPrice());
//        values.put("Type",products.getId_type());
//
//        int row=db.update("PRODUCTS",values," id_product=?",new String[]{products.getId_product()+""});
//        return (row>0);
//    }
//    public boolean delete(int id_product){
//        SQLiteDatabase db=helper.getWritableDatabase();
//        int row= db.delete("PRODUCTS"," id_product=?",new String[]{id_product+""});
//        return (row>0);
//    }

}
