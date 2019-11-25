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

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView5 = findViewById(R.id.recyclerView5);
        recyclerView5.setHasFixedSize(true);
        List<Map<String, Object>> list = new ArrayList<>();
        MyHelper helper = new MyHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();//打开数据库
        Cursor cursor = database.query("history", new String[]{"shop_name", "goods_name", "goods_price", "number"}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            String shop_name2 = cursor.getString(cursor.getColumnIndex("shop_name"));
            String goods_name2 = cursor.getString(cursor.getColumnIndex("goods_name"));
            String goods_price2 = cursor.getString(cursor.getColumnIndex("goods_price"));
            String number2 = cursor.getString(cursor.getColumnIndex("number"));
            Map<String, Object> map2 = new HashMap<>();
            map2.put("shop_name", shop_name2);
            map2.put("goods_name", goods_name2);
            map2.put("goods_price", goods_price2);
            map2.put("number", number2);
            list.add(map2);
            while (cursor.moveToNext()) {
                String shop_name1 = cursor.getString(cursor.getColumnIndex("shop_name"));
                String goods_name1 = cursor.getString(cursor.getColumnIndex("goods_name"));
                String goods_price1 = cursor.getString(cursor.getColumnIndex("goods_price"));
                String number1 = cursor.getString(cursor.getColumnIndex("number"));
                Map<String, Object> map = new HashMap<>();
                map.put("shop_name", shop_name1);
                map2.put("goods_name", goods_name1);
                map2.put("goods_price", goods_price1);
                map2.put("number", number1);
                list.add(map);
            }
        }
        cursor.close();
        database.close();
        recyclerView5.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));//垂直排列 , Ctrl+P
        recyclerView5.setAdapter(new MyAdapter5(HistoryActivity.this, list));//绑定适配器
    }
}
