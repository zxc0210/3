package com.example.myapplication2;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.util.SharedPreferencesUtil;

public class BuyActivity extends AppCompatActivity implements View.OnClickListener {

    private String st_goods_name;
    private String st_goods_price;
    private String st_goods_number;
    private TextView goods_name;
    private TextView goods_price;
    private TextView goods_number;
    private TextView tv_add;
    private TextView tv_minus;
    private TextView tv_number;
    private Button confirm_buy;
    private int amount;
    private int number;
    private String shop_name;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        intUI();

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(BuyActivity.this);
        phone = sharedPreferencesUtil.getAccountId();

        shop_name = getIntent().getStringExtra("shop_name");
        Log.d("zxc", phone);
        amount = 1;
        tv_number.setText("   " + amount + "   ");

        confirm_buy.setOnClickListener(this);
    }

    private void intUI() {
        st_goods_name = getIntent().getStringExtra("goods_name");
        st_goods_price = getIntent().getStringExtra("goods_price");
        st_goods_number = getIntent().getStringExtra("goods_number");
        number = Integer.parseInt(st_goods_number);

        goods_name = findViewById(R.id.goods_name);
        goods_name.setText(st_goods_name);
        goods_price = findViewById(R.id.goods_price);
        goods_price.setText("¥ " + st_goods_price);
        goods_number = findViewById(R.id.goods_number);
        goods_number.setText("库存: " + st_goods_number);
        tv_add = findViewById(R.id.tv_add);
        tv_minus = findViewById(R.id.tv_minus);
        tv_number = findViewById(R.id.tv_number);
        confirm_buy = findViewById(R.id.confirm_buy);
    }

    @Override
    public void onClick(View v) {
        if (number == 0) {
            showDialog1();
        } else {
            MyHelper helper = new MyHelper(this);
            SQLiteDatabase database = helper.getReadableDatabase();//打开数据库

            //判断是不是本人的店铺
            Cursor cursor1 = database.query("goods", new String[]{"phone", "shop_name"}, "shop_name=?", new String[]{shop_name}, null, null, null);
            if (cursor1.moveToFirst()) {
                String phone1 = cursor1.getString(cursor1.getColumnIndex("phone"));
                Log.d("zxc1", phone1);
                if (phone1.equals(phone)) {
                    showDialog2();
                } else {
                    number = (number - amount);
                    ContentValues values = new ContentValues();
                    values.put("number", number);
                    database.update("goods", values, "name=?", new String[]{st_goods_name});
                    database.execSQL("insert into history(goods_name,goods_price,number,shop_name) values('" + st_goods_name + "','" + st_goods_price + "','" + amount + "','" + shop_name + "');");

                    showDialog();
                }
            }
            cursor1.close();
            database.close();
        }
    }


    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("    购买成功！");
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(BuyActivity.this, DrawerLayoutActivity.class);
                        startActivity(intent);
                        finish();
                        DrawerLayoutActivity.a.finish();
                        ShopActivity.d.finish();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showDialog1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("   抱歉,库存不足");
        builder.setPositiveButton("我知道了",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showDialog2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("   去别的店铺里逛逛吧!");
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(BuyActivity.this, DrawerLayoutActivity.class);
                        startActivity(intent);
                        finish();
                        DrawerLayoutActivity.a.finish();
                        ShopActivity.d.finish();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onClick1(View view) {
        if (amount > 1) {
            amount--;
            tv_number.setText("   " + amount + "   ");
        } else {
            Toast.makeText(this, "不能再少啦", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick2(View view) {
        if (amount < number) {
            amount++;
            tv_number.setText("   " + amount + "   ");
        } else {
            Toast.makeText(this, "大佬,没有更多了", Toast.LENGTH_SHORT).show();
        }
    }
}
