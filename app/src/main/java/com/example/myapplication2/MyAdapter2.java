package com.example.myapplication2;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.ViewHolder2> {
    private List<Map<String, Object>> list;
    private Context context;

    public MyAdapter2(Context context, List<Map<String, Object>> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdapter2.ViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item2, parent, false);
        return new MyAdapter2.ViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyAdapter2.ViewHolder2 holder, final int position) {
        holder.text_shop.setText("店铺:"+list.get(position).get("shop_name").toString());
        final String shop_name=list.get(position).get("shop_name").toString();
        final String phone=list.get(position).get("phone").toString();

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyHelper helper = new MyHelper(context);
                SQLiteDatabase database = helper.getReadableDatabase();//打开数据库
                database.delete("user_shop", "shop_name=?", new String[]{shop_name});

                Toast.makeText(context,"你删除了商品"+shop_name,Toast.LENGTH_LONG).show();
                Intent intent=new Intent(context,DrawerLayoutActivity.class);
                context.startActivity(intent);
                if (MyShopActivity.class.isInstance(context)) {
                    // 转化为activity，然后finish就行了
                    MyShopActivity activity = (MyShopActivity) context;
                    activity.finish();
                    DrawerLayoutActivity.a.finish();
                }
            }
        });
        holder.manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(context,GoodsManagerActivity.class);
                intent1.putExtra("shop_name",shop_name);
                intent1.putExtra("phone",phone);
                context.startActivity(intent1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder2 extends RecyclerView.ViewHolder{

        private final TextView text_shop;
        private final Button delete;
        private final Button manager;

        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            text_shop = itemView.findViewById(R.id.text_shop);
            delete = itemView.findViewById(R.id.delete);
            manager = itemView.findViewById(R.id.manager);
        }
    }
}
