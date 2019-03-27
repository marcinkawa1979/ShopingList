package com.example.android.shopinglist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyOrderViewHolder> {

    private List<Order> orderList;

    private View.OnClickListener onItemClickListener;

    public void setOnItemClickListener(View.OnClickListener clickListener) {
        onItemClickListener = clickListener;
    }

    public class MyOrderViewHolder extends RecyclerView.ViewHolder {

        TextView mOrderNameTv;
        TextView mOrderDateTv;

        public MyOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            mOrderNameTv = itemView.findViewById(R.id.order_name_tv);
            mOrderDateTv = itemView.findViewById(R.id.order_date_tv);
            itemView.setTag(this);
            itemView.setOnClickListener(onItemClickListener);
        }
    }

    public MyOrderAdapter(List<Order> orderList){this.orderList = orderList;}

    @NonNull
    @Override
    public MyOrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_list_item, viewGroup, false);

        return new MyOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderViewHolder holder, int i) {
        Order order = orderList.get(i);
        holder.mOrderNameTv.setText(order.getShopName());
        holder.mOrderDateTv.setText(HelperMethods.dateToString(order.getReceptionData()));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
