package com.example.myapplication2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopActivity extends AppCompatActivity {

    private String shop_name;
    private String phone;
    private RecyclerView recyclerView3;
    private TextView goods;

    public static ShopActivity d = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        d = this;
        shop_name = getIntent().getStringExtra("shop_name");
        phone = getIntent().getStringExtra("phone");
        goods = findViewById(R.id.goods);
        goods.setText(shop_name + "的商品");

        MyHelper helper = new MyHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();//打开数据库
        recyclerView3 = findViewById(R.id.recyclerView3);
        recyclerView3.setHasFixedSize(true);
        List<Map<String, Object>> list = new ArrayList<>();
        Cursor cursor = database.query("goods", new String[]{"shop_name", "name", "price", "number", "phone"}, "shop_name=?", new String[]{shop_name}, null, null, null);
        if (cursor.moveToFirst()) {
            String name1 = cursor.getString(cursor.getColumnIndex("name"));
            String price1 = cursor.getString(cursor.getColumnIndex("price"));
            String number1 = cursor.getString(cursor.getColumnIndex("number"));
            String shop_name1 = cursor.getString(cursor.getColumnIndex("shop_name"));
            String phone1 = cursor.getString(cursor.getColumnIndex("phone"));
            Map<String, Object> map2 = new HashMap<>();
            map2.put("goods_name", name1);
            map2.put("goods_price", price1);
            map2.put("goods_number", number1);
            map2.put("shop_name", shop_name1);
            map2.put("phone", phone1);
            list.add(map2);
            while (cursor.moveToNext()) {
                String name2 = cursor.getString(cursor.getColumnIndex("name"));
                String price2 = cursor.getString(cursor.getColumnIndex("price"));
                String number2 = cursor.getString(cursor.getColumnIndex("number"));
                String shop_name2 = cursor.getString(cursor.getColumnIndex("shop_name"));
                String phone2 = cursor.getString(cursor.getColumnIndex("phone"));
                Map<String, Object> map = new HashMap<>();
                map.put("goods_name", name2);
                map.put("goods_price", price2);
                map.put("goods_number", number2);
                map.put("shop_name", shop_name2);
                map.put("phone", phone2);
                list.add(map);
            }
        }
        cursor.close();
        database.close();
        recyclerView3.setLayoutManager(new LinearLayoutManager(ShopActivity.this));//垂直排列 , Ctrl+P
        recyclerView3.setAdapter(new MyAdapter4(ShopActivity.this, list));//绑定适配器
    }
}
