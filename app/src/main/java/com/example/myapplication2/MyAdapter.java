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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private List<Map<String, Object>> list;
    private  Context context;

    public MyAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override

    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, final int position) {

        final String phone=list.get(position).get("phone").toString();
        final String shop_name=list.get(position).get("shop_name").toString();
        final String shop_introduce=list.get(position).get("shop_introduce").toString();
        final String user_name=list.get(position).get("user_name").toString();
        if (!TextUtils.isEmpty(shop_name) && !TextUtils.isEmpty(shop_introduce) && !TextUtils.isEmpty(user_name)) {
        holder.textView.setText(list.get(position).get("shop_name").toString());
        holder.text_introduce.setText(" 店铺简介:"+list.get(position).get("shop_introduce").toString());
        holder.text_name.setText(" 店主:"+list.get(position).get("user_name").toString());}

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,ShopActivity.class);
                intent.putExtra("shop_name",shop_name);
                intent.putExtra("phone",phone);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() { return list.size(); }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private TextView text_introduce;
        private TextView text_name;
        private Button button;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.text);
            text_introduce = itemView.findViewById(R.id.text_introduce);
            text_name = itemView.findViewById(R.id.text_name);
            button = itemView.findViewById(R.id.btn);
        }
    }
}
