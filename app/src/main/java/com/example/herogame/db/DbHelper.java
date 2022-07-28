package com.example.herogame.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context){
        super(context,"ASM",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table USER(id_user integer primary key autoincrement,"+"name_user text, password_user text, email_user text, phone_user integer,role_user text)";
        db.execSQL(sql);
        sql="insert into USER values(null,'Tuan','123','xtjvjxx@gmail.com',0344511167,'admin')";
        db.execSQL(sql);
        sql="insert into USER values(null,'Quan','123','quankun@gmail.com',0451254429,'admin')";
        db.execSQL(sql);
        sql="insert into USER values(null,'Tang','123','tangdong@gmail.com',0344511167,'admin')";
        db.execSQL(sql);
        sql="insert into USER values(null,'Vu','123','vutrontiet@gmail.com',0344511167,'admin')";
        db.execSQL(sql);
        sql="create table TYPE(id_type integer primary key autoincrement,"+"name_type text)";
        db.execSQL(sql);
        sql="insert into TYPE values(null,'Handheld')";
        db.execSQL(sql);
        sql="insert into TYPE values(null,'Console')";
        db.execSQL(sql);
        sql="insert into TYPE values(null,'Mini pc')";
        db.execSQL(sql);
        sql="create table PRODUCTS(id_product integer primary key autoincrement,"+"name_product text, price integer, id_type integer)";
        db.execSQL(sql);
        sql="insert into PRODUCTS values(null,'Switch','6000000000','0')";
        db.execSQL(sql);
        sql="insert into PRODUCTS values(null,'PS5',150000000,'1')";
        db.execSQL(sql);
        sql="insert into PRODUCTS values(null,'PS4',5000000,'1')";
        db.execSQL(sql);


//        sql="create table GIAODICH(MaGD integer primary key autoincrement,"+
//                "TieuDe text, Ngay date, Tien float, MoTa text,"
//                +"MaLoai integer references PHANLOAI(MaLoai))";
//        db.execSQL(sql);
//        sql="insert into GIAODICH values(null,'Lương tháng 1','2021-01-07',5000,'Lương tháng 1, giảm vì covid',1)";
//        db.execSQL(sql);
//        sql="insert into GIAODICH values(null,'Mua thức ăn','2021-01-07',5000,'Siêu thị',3)";
//        db.execSQL(sql);
//        sql="insert into GIAODICH values(null,'Mua thức ăn cho mèo','2021-01-07',5000,'Pet pro',5)";
//        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists USER");
        db.execSQL("drop table if exists GIAODICH");
        onCreate(db);
    }
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }
}