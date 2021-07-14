package com.palana.paachoodelivery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class OrdersListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<Data> ordersList;
    String orderStatus;
    public OrdersListAdapter(Context mContext, List<Data> ordersList, String orderStatus) {
        this.mContext = mContext;
        this.ordersList = ordersList;
        this.orderStatus = orderStatus;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.item_orders, viewGroup, false);

        viewHolder = new ViewHolder(v);
        return viewHolder;

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

        ((ViewHolder) viewHolder).tvPrice.setText(mContext.getString(R.string.rupees_symbol) +  ordersList.get(i).getAmount());
        ((ViewHolder) viewHolder).tvItemName.setText(ordersList.get(i).getName());
        ((ViewHolder) viewHolder).tvOrderId.setText("Order Id : " + ordersList.get(i).getOrderId());
        ((ViewHolder) viewHolder).tvDay.setText(getDay(ordersList.get(i).getCreatedDate()));
        ((ViewHolder) viewHolder).tvMonth.setText(getMonth(ordersList.get(i).getCreatedDate()));

        ((ViewHolder)viewHolder).llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,OrderDetailsActivity.class);
                Bundle args = new Bundle();
                args.putString("order_id",ordersList.get(i).getOrderId());
                intent.putExtras(args);
                mContext.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return ordersList.size();
    }


    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDay;
        private TextView tvMonth;
        private TextView tvItemName;
        private TextView tvOrderId;
        private TextView tvPrice;
        private LinearLayout llContent;
        public ViewHolder(View view) {
            super(view);
            llContent = view.findViewById(R.id.ll_content);
            tvDay = view.findViewById(R.id.tv_day);
            tvMonth = view.findViewById(R.id.tv_month);
            tvItemName = view.findViewById(R.id.tv_item_name);
            tvOrderId = view.findViewById(R.id.tv_item_order_id);
            tvPrice = view.findViewById(R.id.tv_price);

        }
    }

    private String getDay(String AppoinmentDate) {
        String finalDate = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        SimpleDateFormat mDateFormat = new SimpleDateFormat("dd", Locale.US);
        Date date;
        try {
            date = format.parse(AppoinmentDate);
            finalDate = mDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return finalDate;
    }
    private String getMonth(String AppoinmentDate) {
        String finalDate = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM", Locale.US);
        Date date;
        try {
            date = format.parse(AppoinmentDate);
            finalDate = mDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return finalDate;
    }
}
