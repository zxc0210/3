package com.example.myapplication2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyShopActivity extends AppCompatActivity {

    private String phone;
    private RecyclerView recyclerView1;

    public static MyShopActivity b = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shop);

        b = this;
        phone = getIntent().getStringExtra("phone");

        recyclerView1 = findViewById(R.id.recyclerView1);
        recyclerView1.setHasFixedSize(true);

        List<Map<String, Object>> list = new ArrayList<>();
        MyHelper helper = new MyHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();//打开数据库
        Cursor cursor = database.query("user_shop", new String[]{"shop_name", "phone"}, "phone=?", new String[]{phone}, null, null, null);
        if (cursor.moveToFirst()) {
            String shop_name2 = cursor.getString(cursor.getColumnIndex("shop_name"));
            String phone2 = cursor.getString(cursor.getColumnIndex("phone"));
            Map<String, Object> map2 = new HashMap<>();
            map2.put("shop_name", shop_name2);
            map2.put("phone", phone2);
            list.add(map2);
            while (cursor.moveToNext()) {
                String shop_name1 = cursor.getString(cursor.getColumnIndex("shop_name"));
                String phone1 = cursor.getString(cursor.getColumnIndex("phone"));
                Map<String, Object> map = new HashMap<>();
                map.put("shop_name", shop_name1);
                map.put("phone", phone1);
                list.add(map);
            }
        }
        cursor.close();
        database.close();
        recyclerView1.setLayoutManager(new LinearLayoutManager(MyShopActivity.this));//垂直排列 , Ctrl+P
        recyclerView1.setAdapter(new MyAdapter2(MyShopActivity.this, list));//绑定适配器

    }
}
