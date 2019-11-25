package com.example.myapplication2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.util.RegexUtil;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_your_phone;
    private Button bt_determine;
    public static ForgetPasswordActivity f = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        f = this;
        et_your_phone = findViewById(R.id.et_your_phone);
        bt_determine = findViewById(R.id.bt_determine);

        bt_determine.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(ForgetPasswordActivity.this, RetrievePasswordActivity.class);
        Bundle bundle = new Bundle();
        String your_phone = et_your_phone.getText().toString().trim();
        if (TextUtils.isEmpty(your_phone)) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!RegexUtil.isPhone(your_phone)) {
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        MyHelper helper = new MyHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();//打开数据库
        Cursor cursor1 = database.query("user", new String[]{"phone"}, null, null, null, null, null);
        if (cursor1.moveToFirst()) {
            String phone1 = cursor1.getString(cursor1.getColumnIndex("phone"));
            if (!phone1.equals(your_phone)) {
                Toast.makeText(this, "请先注册!", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        cursor1.close();
        database.close();

        bundle.putString("your_phone", your_phone);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
