package com.example.myapplication2;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class MyAdapter5 extends RecyclerView.Adapter<MyAdapter5.ViewHolder5> {

    private List<Map<String, Object>> list;
    private Context context;

    public MyAdapter5(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdapter5.ViewHolder5 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item5, parent, false);
        return new MyAdapter5.ViewHolder5(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter5.ViewHolder5 holder, final int position) {

        holder.goods_name.setText("商品:" + list.get(position).get("goods_name").toString());
        holder.shop_name.setText("店铺:" + list.get(position).get("shop_name").toString());
        holder.goods_price.setText("¥ " + list.get(position).get("goods_price").toString());
        holder.number.setText("购买数:" + list.get(position).get("number").toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder5 extends RecyclerView.ViewHolder {


        private final TextView goods_name;
        private final TextView goods_price;
        private final TextView shop_name;
        private final TextView number;

        ViewHolder5(@NonNull View itemView) {
            super(itemView);

            goods_name = itemView.findViewById(R.id.goods_name);
            goods_price = itemView.findViewById(R.id.goods_price);
            shop_name = itemView.findViewById(R.id.shop_name);
            number = itemView.findViewById(R.id.number);
        }
    }
}