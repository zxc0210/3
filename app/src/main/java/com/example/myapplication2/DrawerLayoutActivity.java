package com.example.myapplication2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication2.util.SharedPreferencesUtil;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrawerLayoutActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private RecyclerView recyclerView;
    private ImageView image;
    private SharedPreferencesUtil sp;
    private TextView header_name;
    private String name1;
    private String phone;
    private String shop_name1;
    private String shop_introduce1;
    private String user_name1;

    public static DrawerLayoutActivity a = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout);

        initUI();
        a = this;
        //传递phone
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(DrawerLayoutActivity.this);
        phone = sharedPreferencesUtil.getAccountId();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        //添加list
        List<Map<String, Object>> list = new ArrayList<>();
        MyHelper helper = new MyHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();//打开数据库
        Cursor cursor = database.query("user_shop", new String[]{"shop_name", "shop_introduce", "user_name", "phone"}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            String shop_name2 = cursor.getString(cursor.getColumnIndex("shop_name"));
            String shop_introduce2 = cursor.getString(cursor.getColumnIndex("shop_introduce"));
            String user_name2 = cursor.getString(cursor.getColumnIndex("user_name"));
            String phone2 = cursor.getString(cursor.getColumnIndex("phone"));
            Map<String, Object> map2 = new HashMap<>();
            map2.put("shop_name", shop_name2);
            map2.put("shop_introduce", shop_introduce2);
            map2.put("user_name", user_name2);
            map2.put("phone", phone2);
            list.add(map2);
            while (cursor.moveToNext()) {
                shop_name1 = cursor.getString(cursor.getColumnIndex("shop_name"));
                shop_introduce1 = cursor.getString(cursor.getColumnIndex("shop_introduce"));
                user_name1 = cursor.getString(cursor.getColumnIndex("user_name"));
                String phone1 = cursor.getString(cursor.getColumnIndex("phone"));
                Map<String, Object> map = new HashMap<>();
                map.put("shop_name", shop_name1);
                map.put("shop_introduce", shop_introduce1);
                map.put("user_name", user_name1);
                map.put("phone", phone1);
                list.add(map);
            }
        }
        cursor.close();

//        Cursor cursor2 = database.query("user", new String[]{"phone","name"}, "phone=?", new String[]{phone}, null, null, null);
//        if (cursor2.moveToFirst()) {
//            String phone1 = cursor2.getString(cursor2.getColumnIndex("phone"));
//            Log.d("zxc",phone1);
//            Map<String, Object> map1 = new HashMap<>();
//            map1.put("phone111", phone1);
//            list.add(map1);
//        }
//        cursor2.close();
        database.close();

        recyclerView.setLayoutManager(new LinearLayoutManager(DrawerLayoutActivity.this));//垂直排列 , Ctrl+P
        recyclerView.setAdapter(new MyAdapter(DrawerLayoutActivity.this, list));//绑定适配器


        //设置昵称
        MyHelper helper1 = new MyHelper(this);
        SQLiteDatabase database1 = helper1.getReadableDatabase();//打开数据库
        Cursor cursor1 = database1.query("user", new String[]{"phone", "name"}, "phone=?", new String[]{phone}, null, null, null);
        if (cursor1.moveToFirst()) {
            name1 = cursor1.getString(cursor1.getColumnIndex("name"));
            header_name.setText(name1);
        }
        cursor1.close();
        database1.close();

        sp = SharedPreferencesUtil.getInstance(getApplicationContext());

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        image.setOnClickListener(this);

    }


    private void initUI() {
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);
        drawer = findViewById(R.id.drawer_layout);
        //设置头像部分的监听
        View view = navigationView.inflateHeaderView(R.layout.header);
        image = view.findViewById(R.id.header_image);
        header_name = view.findViewById(R.id.header_name);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_image:
                Toast.makeText(DrawerLayoutActivity.this, "你点击了头像", Toast.LENGTH_LONG).show();
                break;

        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.personal_information:
                Intent intent = new Intent(DrawerLayoutActivity.this, PersonalInformationActivity.class);
                intent.putExtra("phone", phone);
                startActivity(intent);
                break;
            case R.id.change_information:
                Intent intent1 = new Intent(DrawerLayoutActivity.this, ChangePersonalDataActivity.class);
                intent1.putExtra("phone", phone);
                startActivity(intent1);
                break;
            case R.id.my_shop:
                Intent intent2 = new Intent(DrawerLayoutActivity.this, MyShopActivity.class);
                intent2.putExtra("phone", phone);
                startActivity(intent2);
                break;
            case R.id.add_shop:
                Intent intent3 = new Intent(DrawerLayoutActivity.this, AddShopActivity.class);
                intent3.putExtra("phone", phone);
                startActivity(intent3);
                break;
            case R.id.history:
                Intent intent4 = new Intent(DrawerLayoutActivity.this, HistoryActivity.class);
                startActivity(intent4);

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //切换账号
    public void onLogoutClick(View view) {
        sp.setLogin(false);
        Intent intent = new Intent(DrawerLayoutActivity.this, LoginActivity.class);
        startActivity(intent);
    }


}
