package com.example.myapplication2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication2.util.RegexUtil;

public class AddGoodsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button confirm_add;
    private EditText et_goods_name;
    private EditText et_goods_price;
    private EditText et_goods_number;
    private String shop_name;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goods);

        shop_name = getIntent().getStringExtra("shop_name");
        phone = getIntent().getStringExtra("phone");
        intUI();

        confirm_add.setOnClickListener(this);
    }

    private void intUI() {
        et_goods_name = findViewById(R.id.et_goods_name);
        et_goods_price = findViewById(R.id.et_goods_price);
        et_goods_number = findViewById(R.id.et_goods_number);
        confirm_add = findViewById(R.id.confirm_add);
    }

    public void onclick(View view) {
        Toast.makeText(this, "你点击了头像", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        MyHelper helper = new MyHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();

        String goods_name=et_goods_name.getText().toString().trim();
        if (TextUtils.isEmpty(goods_name)) {
            Toast.makeText(this, "请输入商品名", Toast.LENGTH_SHORT).show();
            return;
        }
        Cursor cursor1 = database.query("goods", new String[]{"name", "shop_name"}, "shop_name=?", new String[]{shop_name}, null, null, null);
        if (cursor1.moveToFirst()) {
            String name1 = cursor1.getString(cursor1.getColumnIndex("name"));
            if (TextUtils.isEmpty(name1)) {
            } else if (name1.equals(goods_name)) {
                Toast.makeText(this, "该商品已存在!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        String goods_price=et_goods_price.getText().toString().trim();
        if (TextUtils.isEmpty(goods_price)) {
            Toast.makeText(this, "请输入商品价格", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!RegexUtil.isNumber(goods_price)){
            Toast.makeText(this, "请输入合适的价格", Toast.LENGTH_SHORT).show();
            return;
        }


        String goods_number=et_goods_number.getText().toString().trim();
        if (TextUtils.isEmpty(goods_number)) {
            Toast.makeText(this, "请输入商品起始数量", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!RegexUtil.isNumber(goods_number)){
            Toast.makeText(this, "请输入合适的数量", Toast.LENGTH_SHORT).show();
            return;
        }

        database.execSQL("insert into goods(name,price,number,shop_name,phone) values('" + goods_name + "','" + goods_price + "','" + goods_number + "','" + shop_name + "','" + phone + "');");

        showDialog();
    }


    private void showDialog() { AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("     添加商品成功！");
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(AddGoodsActivity.this, DrawerLayoutActivity.class);
                        DrawerLayoutActivity.a.finish();
                        MyShopActivity.b.finish();
                        GoodsManagerActivity.c.finish();
                        startActivity(intent);
                        finish();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    }
