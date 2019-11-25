package com.example.myapplication2;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.util.RegexUtil;

public class RegisteredActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText register_phone;
    private EditText register_password;
    private EditText register_name;
    private EditText register_retrieve_password;
    private String sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);

        register_phone = findViewById(R.id.register_phone);
        register_password = findViewById(R.id.register_password);
        register_name = findViewById(R.id.register_name);
        register_retrieve_password = findViewById(R.id.register_retrieve_password);
        Button bt_register = findViewById(R.id.bt_register);

        bt_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        databaseInsert();
    }

    private void databaseInsert() {
        MyHelper helper = new MyHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();//打开数据库

        String phone = register_phone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!RegexUtil.isPhone(phone)) {
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        //判断是否重复注册
        Cursor cursor = database.query("user", new String[]{"phone", "password"}, "phone=?", new String[]{phone}, null, null, null);
        if (cursor.moveToFirst()) {
            String phone1 = cursor.getString(cursor.getColumnIndex("phone"));
            if (phone1.isEmpty()) {
            } else {
                Toast.makeText(this, "该手机号已经注册过", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        cursor.close();

        String password = register_password.getText().toString().trim();
        String retrieve_password = register_retrieve_password.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请输入你的密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6 || password.length() > 15) {
            Toast.makeText(this, "请输入6~15位密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (retrieve_password.equals(password)) {
        } else {
            Toast.makeText(this, "两次输入的密码不相同", Toast.LENGTH_SHORT).show();
            return;
        }

        String name = register_name.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请输入昵称", Toast.LENGTH_SHORT).show();
            return;
        }//昵称不能重复
        Cursor cursor1 = database.query("user", new String[]{"phone", "name"}, "name=?", new String[]{name}, null, null, null);
        if (cursor1.moveToFirst()) {
            String name1 = cursor1.getString(cursor1.getColumnIndex("name"));
            if (name1.isEmpty()) {
            } else {
                Toast.makeText(this, "啊偶,淘保里已经有这个名字了", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        cursor1.close();

        database.execSQL("insert into user(name,phone,password,gender,birthday,career,address) values('" + name + "','" + phone + "','" + password + "',null,null,null,null);");
        database.close();
        showDialog();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("    注册成功！");
        builder.setPositiveButton("去登录→",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(RegisteredActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        LoginActivity.e.finish();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
