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
import com.example.myapplication2.util.SharedPreferencesUtil;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_password;
    private EditText et_phone;
    private Button bt_login;
    private Button bt_registered;
    private Button bt_forget_password;
    private SharedPreferencesUtil sp;

    public static LoginActivity e = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        e = this;
        et_phone = findViewById(R.id.et_phone);
        et_password = findViewById(R.id.et_password);
        bt_login = findViewById(R.id.bt_login);
        bt_registered = findViewById(R.id.bt_registered);
        bt_forget_password = findViewById(R.id.bt_forget_password);
        bt_login.setOnClickListener(this);
        bt_registered.setOnClickListener(this);
        bt_forget_password.setOnClickListener(this);

        sp = SharedPreferencesUtil.getInstance(getApplicationContext());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                correct();
                break;
            case R.id.bt_registered:
                registered();
                break;
            case R.id.bt_forget_password:
                forget_password();
                break;
        }
    }


    private void correct() {
        String phone = et_phone.getText().toString().trim();

        String password = et_password.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!RegexUtil.isPhone(phone)) {
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        MyHelper helper = new MyHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();//打开数据库

        Cursor cursor = database.query("user", new String[]{"phone", "password"}, "phone=?", new String[]{phone}, null, null, null);
        if (cursor.moveToFirst()) {
            String password1 = cursor.getString(cursor.getColumnIndex("password"));
            if (password1.equals(password)) {
                sp.setLogin(true);//保存登录状态
                Intent intent = new Intent(LoginActivity.this, DrawerLayoutActivity.class);
                sp.setAccountId(phone);
                startActivity(intent);
                finish();
            } else {
                showDialog();
                return;
            }
            cursor.close();
            database.close();
        }
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("     手机号或密码错误！");
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void registered() {
        Intent intent = new Intent(LoginActivity.this, RegisteredActivity.class);
        startActivity(intent);
    }


    private void forget_password() {
        Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
        startActivity(intent);
    }
}
