package com.example.myapplication2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyHelper extends SQLiteOpenHelper {


    public MyHelper(@Nullable Context context) {
        super(context, "database", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table_user = "create table if not exists user(user_id INTEGER PRIMARY KEY autoincrement,name text,phone text,password text,gender text,birthday text,career text,address text)";
        db.execSQL(table_user);

        String Table_user = "create table if not exists user_shop(shop_id INTEGER PRIMARY KEY autoincrement,shop_name text,shop_introduce text,user_name text,phone text)";
        db.execSQL(Table_user);

        String Table_User = "create table if not exists goods(goods_id INTEGER PRIMARY KEY autoincrement,name text,price text,number integer,shop_name text,phone text)";
        db.execSQL(Table_User);

        String TableUser = "create table if not exists history(history_id INTEGER PRIMARY KEY autoincrement,goods_name text,goods_price text,number integer,shop_name text)";
        db.execSQL(TableUser);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user");
        db.execSQL("drop table if exists uesr_shop");
        db.execSQL("drop table if exists goods");
        db.execSQL("drop table if exists history");
        onCreate(db);
    }
}
