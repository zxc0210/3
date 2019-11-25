package com.example.myapplication2;

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
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AddShopActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_shop_name;
    private EditText et_shop_introduce;
    private Button confirm_add;
    private String phone;
    private String name1;
    private ImageView header_image;
    private String shop_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);

        phone = getIntent().getStringExtra("phone");

        intUI();
        confirm_add.setOnClickListener(this);
    }

    private void intUI() {
        et_shop_name = findViewById(R.id.et_shop_name);
        et_shop_introduce = findViewById(R.id.et_shop_introduce);
        header_image = findViewById(R.id.header_image);
        confirm_add = findViewById(R.id.confirm_add);
    }

    public void onclick(View view) {
        Toast.makeText(this, "你点击了头像", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {

        MyHelper helper = new MyHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();

        shop_name = et_shop_name.getText().toString().trim();
        if (TextUtils.isEmpty(shop_name)) {
            Toast.makeText(this, "请输入店铺名", Toast.LENGTH_SHORT).show();
            return;
        }
        Cursor cursor1 = database.query("user_shop", new String[]{"shop_name", "shop_introduce"}, "shop_name=?", new String[]{shop_name}, null, null, null);
        if (cursor1.moveToFirst()) {
            String shop_name1 = cursor1.getString(cursor1.getColumnIndex("shop_name"));
            if (shop_name1.isEmpty()) {
            } else {
                Toast.makeText(this, "该店铺名已经存在!", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        cursor1.close();

        String shop_introduce = et_shop_introduce.getText().toString().trim();
        if (TextUtils.isEmpty(shop_introduce)) {
            Toast.makeText(this, "请输入店铺简介", Toast.LENGTH_SHORT).show();
            return;
        }

        Cursor cursor = database.query("user", new String[]{"phone", "name"}, "phone=?", new String[]{phone}, null, null, null);
        if (cursor.moveToFirst()) {
            name1 = cursor.getString(cursor.getColumnIndex("name"));
        }
        cursor.close();

        database.execSQL("insert into user_shop(shop_name,shop_introduce,user_name,phone) values('" + shop_name + "','" + shop_introduce + "','" + name1 + "','" + phone + "');");
        database.close();
        Log.d("zxc", phone);
        showDialog();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("     添加店铺成功！");
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(AddShopActivity.this, DrawerLayoutActivity.class);
                        DrawerLayoutActivity.a.finish();
                        startActivity(intent);
                        finish();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
