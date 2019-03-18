package com.example.android.shopinglist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class MyProductAdapter extends RecyclerView.Adapter<MyProductAdapter.MyViewHolder> {

    List<Product> productList;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView nameTv;
        public ImageButton removeIb;

        public MyViewHolder(View view){
            super(view);
            nameTv = view.findViewById(R.id.product_item_name);
            removeIb = view.findViewById(R.id.remove_product_item);
        }
    }

    public MyProductAdapter(List<Product> list){
        this.productList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.produtcts_list_item, viewGroup , false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int i) {
        Product product = productList.get(i);
        holder.nameTv.setText(product.getProductName());
        holder.removeIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productList.size()>0) {
                    productList.remove(i);
                    notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
