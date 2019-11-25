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

public class MyAdapter4 extends RecyclerView.Adapter<MyAdapter4.ViewHolder4> {

    private List<Map<String, Object>> list;
    private Context context;

    public MyAdapter4(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdapter4.ViewHolder4 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item4, parent, false);
        return new MyAdapter4.ViewHolder4(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter4.ViewHolder4 holder, final int position) {
        final String goods_name = list.get(position).get("goods_name").toString();
        final String goods_price = list.get(position).get("goods_price").toString();
        final String goods_number = list.get(position).get("goods_number").toString();
        final String shop_name = list.get(position).get("shop_name").toString();
        final String phone = list.get(position).get("phone").toString();
        if (!TextUtils.isEmpty(goods_name) && !TextUtils.isEmpty(goods_price) && !TextUtils.isEmpty(goods_number)) {
            holder.tv_goods_name.setText("商品名:" + list.get(position).get("goods_name").toString());
            holder.tv_goods_price.setText("¥ " + list.get(position).get("goods_price").toString());
            holder.tv_goods_number.setText("库存:" + list.get(position).get("goods_number").toString());
        }

        holder.buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,BuyActivity.class);
                intent.putExtra("goods_name",goods_name);
                intent.putExtra("goods_price",goods_price);
                intent.putExtra("goods_number",goods_number);
                intent.putExtra("shop_name",shop_name);
                intent.putExtra("phone",phone);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder4 extends RecyclerView.ViewHolder {
        private TextView tv_goods_name;
        private TextView tv_goods_price;
        private TextView tv_goods_number;
        private Button buy;

        ViewHolder4(@NonNull View itemView) {
            super(itemView);

            tv_goods_name = itemView.findViewById(R.id.tv_goods_name);
            tv_goods_price = itemView.findViewById(R.id.tv_goods_price);
            tv_goods_number = itemView.findViewById(R.id.tv_goods_number);
            buy = itemView.findViewById(R.id.buy);
        }
    }
}
