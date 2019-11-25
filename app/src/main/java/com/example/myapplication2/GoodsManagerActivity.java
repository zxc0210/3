package com.example.myapplication2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodsManagerActivity extends AppCompatActivity implements View.OnClickListener {

    private String shop_name;
    private String phone;
    private RecyclerView recyclerView2;
    private Button add_goods;

    public static GoodsManagerActivity c = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_manager);

        c = this;

        MyHelper helper = new MyHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();//打开数据库

        shop_name = getIntent().getStringExtra("shop_name");
        phone = getIntent().getStringExtra("phone");

        add_goods = findViewById(R.id.add_goods);

        recyclerView2 = findViewById(R.id.recyclerView2);
        recyclerView2.setHasFixedSize(true);

        List<Map<String, Object>> list = new ArrayList<>();
        Cursor cursor = database.query("goods", new String[]{"shop_name", "name", "price", "number", "phone"}, "shop_name=?", new String[]{shop_name}, null, null, null);
        if (cursor.moveToFirst()) {
            String name1 = cursor.getString(cursor.getColumnIndex("name"));
            String price1 = cursor.getString(cursor.getColumnIndex("price"));
            String number1 = cursor.getString(cursor.getColumnIndex("number"));
            String phone1 = cursor.getString(cursor.getColumnIndex("phone"));
            Map<String, Object> map2 = new HashMap<>();
            map2.put("goods_name", name1);
            map2.put("goods_price", price1);
            map2.put("goods_number", number1);
            map2.put("phone", phone1);
            list.add(map2);
            while (cursor.moveToNext()) {
                String name2 = cursor.getString(cursor.getColumnIndex("name"));
                String price2 = cursor.getString(cursor.getColumnIndex("price"));
                String number2 = cursor.getString(cursor.getColumnIndex("number"));
                String phone2 = cursor.getString(cursor.getColumnIndex("phone"));
                Map<String, Object> map = new HashMap<>();
                map.put("goods_name", name2);
                map.put("goods_price", price2);
                map.put("goods_number", number2);
                map.put("phone", phone2);
                list.add(map);
            }
        }
        cursor.close();
        database.close();
        recyclerView2.setLayoutManager(new LinearLayoutManager(GoodsManagerActivity.this));//垂直排列 , Ctrl+P
        recyclerView2.setAdapter(new MyAdapter3(GoodsManagerActivity.this, list));//绑定适配器

        add_goods.setOnClickListener(this);
    }

    private void skip() {

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(GoodsManagerActivity.this, AddGoodsActivity.class);
        intent.putExtra("shop_name", shop_name);
        intent.putExtra("phone", phone);
        startActivity(intent);
    }
}
