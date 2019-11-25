package com.example.myapplication2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PersonalInformationActivity extends AppCompatActivity {

    private TextView tv_gender;
    private TextView tv_birthday;
    private TextView tv_career;
    private TextView tv_address;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);

        intUI();

        phone = getIntent().getStringExtra("phone");
        Query();
    }


    private void intUI() {
        tv_gender = findViewById(R.id.tv_gender);
        tv_birthday = findViewById(R.id.tv_birthday);
        tv_career = findViewById(R.id.tv_career);
        tv_address = findViewById(R.id.tv_address);
    }

    private void Query() {
        MyHelper helper = new MyHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();//打开数据库

        Cursor cursor = database.query("user", new String[]{"phone", "gender", "birthday", "career", "address"}, "phone=?", new String[]{phone}, null, null, null);
        if (cursor.moveToFirst()) {
            String gender1 = cursor.getString(cursor.getColumnIndex("gender"));
            String birthday1 = cursor.getString(cursor.getColumnIndex("birthday"));
            String career1 = cursor.getString(cursor.getColumnIndex("career"));
            String address1 = cursor.getString(cursor.getColumnIndex("address"));
            tv_gender.setText(gender1);
            tv_birthday.setText(birthday1);
            tv_career.setText(career1);
            tv_address.setText(address1);
        }
        cursor.close();
        database.close();
    }
}