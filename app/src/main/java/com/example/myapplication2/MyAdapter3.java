package com.example.myapplication2;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class MyAdapter3 extends RecyclerView.Adapter<MyAdapter3.ViewHolder3> {

    private List<Map<String, Object>> list;
    private Context context;

    public MyAdapter3(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdapter3.ViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item3, parent, false);
        return new MyAdapter3.ViewHolder3(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter3.ViewHolder3 holder, final int position) {
        final String goods_name = list.get(position).get("goods_name").toString();
        final String goods_price = list.get(position).get("goods_price").toString();
        final String goods_number = list.get(position).get("goods_number").toString();
        if (!TextUtils.isEmpty(goods_name) && !TextUtils.isEmpty(goods_price) && !TextUtils.isEmpty(goods_number)) {
            holder.tv_goods_name.setText("商品名:" + list.get(position).get("goods_name").toString());
            holder.tv_goods_price.setText("¥ " + list.get(position).get("goods_price").toString());
            holder.tv_goods_number.setText("待售:" + list.get(position).get("goods_number").toString());
        }

        holder.delete_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyHelper helper = new MyHelper(context);
                SQLiteDatabase database = helper.getReadableDatabase();//打开数据库
                database.delete("goods", "name=?", new String[]{goods_name});

                Toast.makeText(context, "你删除了" +goods_name, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context,DrawerLayoutActivity.class);
                context.startActivity(intent);
                if (GoodsManagerActivity.class.isInstance(context)) {
                    // 转化为activity，然后finish就行了
                    GoodsManagerActivity activity = (GoodsManagerActivity) context;
                    activity.finish();
                    DrawerLayoutActivity.a.finish();
                    MyShopActivity.b.finish();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder3 extends RecyclerView.ViewHolder {
        private TextView tv_goods_name;
        private TextView tv_goods_price;
        private TextView tv_goods_number;
        private Button delete_goods;

        ViewHolder3(@NonNull View itemView) {
            super(itemView);

            tv_goods_name = itemView.findViewById(R.id.tv_goods_name);
            tv_goods_price = itemView.findViewById(R.id.tv_goods_price);
            tv_goods_number = itemView.findViewById(R.id.tv_goods_number);
            delete_goods = itemView.findViewById(R.id.delete_goods);
        }
    }
}