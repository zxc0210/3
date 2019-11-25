package com.example.myapplication2;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.util.RegexUtil;

public class RetrievePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText verification_code;
    private EditText et_new_password;
    private EditText et_determine_password;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_password);


        bundle = this.getIntent().getExtras();
        TextView phone_user = findViewById(R.id.phone_user);
        String your_phone = bundle.getString("your_phone");
        phone_user.setText("亲爱的" + your_phone + "用户:");

        verification_code = findViewById(R.id.verification_code);
        et_new_password = findViewById(R.id.et_new_password);
        et_determine_password = findViewById(R.id.et_determine_password);
        Button finish = findViewById(R.id.finish);

        finish.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String st_verification_code = verification_code.getText().toString().trim();
        if (TextUtils.isEmpty(st_verification_code)) {
            Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (st_verification_code.length() != 6) {
            Toast.makeText(this, "验证码有误", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!RegexUtil.isNumber(st_verification_code)) {
            Toast.makeText(this, "请输入数字型验证码", Toast.LENGTH_SHORT).show();
            return;
        }

        String st_new_password = et_new_password.getText().toString().trim();
        if (TextUtils.isEmpty(st_new_password)) {
            Toast.makeText(this, "新密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (st_new_password.length() < 6 || st_new_password.length() > 15) {
            Toast.makeText(this, "请输入6~15位密码", Toast.LENGTH_SHORT).show();
            return;
        }
        String st_determine_password = et_determine_password.getText().toString().trim();
        if (TextUtils.isEmpty(st_determine_password)) {
            Toast.makeText(this, "请确认新密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (st_new_password.equals(st_determine_password)) {
            dataBaseUpdate();
            showDialog();
        } else {
            Toast.makeText(this, "两次输入的密码不相同", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    private void dataBaseUpdate() {
        MyHelper helper = new MyHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();

        String password = et_determine_password.getText().toString().trim();
        String phone = bundle.getString("your_phone");

        ContentValues values = new ContentValues();
        values.put("password", password);
        database.update("user", values, "phone=?", new String[]{phone});
        database.close();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("亲爱的用户:");
        builder.setMessage("   本次修改密码成功!");
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(RetrievePasswordActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        DrawerLayoutActivity.a.finish();
                        LoginActivity.e.finish();
                        ForgetPasswordActivity.f.finish();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}


