package com.example.myapplication2;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class ChangePersonalDataActivity extends AppCompatActivity implements View.OnClickListener {

    private String phone;
    private EditText et_name;
    private RadioButton rb_male;
    private RadioButton rb_female;
    private EditText et_birthday;
    private EditText et_career;
    private EditText et_address;
    private Button bt_finish;
    private TextView header_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_personal_data);

        intUI();

        phone = getIntent().getStringExtra("phone");
        bt_finish.setOnClickListener(this);

    }

    private void intUI() {
        et_name = findViewById(R.id.et_name);
        rb_male = findViewById(R.id.rb_male);
        rb_female = findViewById(R.id.rb_female);
        et_birthday = findViewById(R.id.et_birthday);
        et_career = findViewById(R.id.et_career);
        et_address = findViewById(R.id.et_address);
        bt_finish = findViewById(R.id.bt_finish);

        LayoutInflater factory = LayoutInflater.from(ChangePersonalDataActivity.this);
        //获取header_layout内的控件
        final View textEntryView = factory.inflate(R.layout.header, null);
        header_name = textEntryView.findViewById(R.id.header_name);

    }


    @Override
    public void onClick(View v) {
        MyHelper helper = new MyHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();//打开数据库

        String name1 = et_name.getText().toString();
        if (!name1.isEmpty()) {
            Cursor cursor1 = database.query("user", new String[]{"phone", "name"}, "name=?", new String[]{name1}, null, null, null);
            if (cursor1.moveToFirst()) {
                String name2 = cursor1.getString(cursor1.getColumnIndex("name"));
                if (name2.isEmpty()) {
                } else {
                    Toast.makeText(this, "换个名字试试吧", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            cursor1.close();
        }

        //修改昵称
        String name = et_name.getText().toString();
        if (name.isEmpty()) {
        } else {
            ContentValues values = new ContentValues();
            values.put("name", name);
            database.update("user", values, "phone=?", new String[]{phone});
            ContentValues values2 = new ContentValues();
            values2.put("user_name", name);
            database.update("user_shop", values2, "phone=?", new String[]{phone});

        }


        //修改性别
        if (rb_male.isChecked()) {
            String gender = "男";
            ContentValues values = new ContentValues();
            values.put("gender", gender);
            database.update("user", values, "phone=?", new String[]{phone});
        } else {
            String gender = "女";
            ContentValues values = new ContentValues();
            values.put("gender", gender);
            database.update("user", values, "phone=?", new String[]{phone});
        }

        //修改出生日期
        String birthday = et_birthday.getText().toString();
        if (birthday.isEmpty()) {
        } else {
            ContentValues values = new ContentValues();
            values.put("birthday", birthday);
            database.update("user", values, "phone=?", new String[]{phone});
        }

        //修改个人简介
        String career = et_career.getText().toString();
        if (career.isEmpty()) {
        } else {
            ContentValues values = new ContentValues();
            values.put("career", career);
            database.update("user", values, "phone=?", new String[]{phone});
        }

        //修改地址
        String address = et_address.getText().toString();
        if (address.isEmpty()) {
        } else {
            ContentValues values = new ContentValues();
            values.put("address", address);
            database.update("user", values, "phone=?", new String[]{phone});
        }

        database.close();
        showDialog();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("      修改成功！");
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(ChangePersonalDataActivity.this, DrawerLayoutActivity.class);
                        DrawerLayoutActivity.a.finish();
                        startActivity(intent);
                        finish();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}