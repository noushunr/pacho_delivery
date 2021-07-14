package com.palana.paachoodelivery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderItemsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<Orderproduct> ordersList;
    public OrderItemsListAdapter(Context mContext, List<Orderproduct> ordersList) {
        this.mContext = mContext;
        this.ordersList = ordersList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.products_item, viewGroup, false);

        viewHolder = new ViewHolder(v);
        return viewHolder;

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

        ((ViewHolder) viewHolder).tvPrice.setText(mContext.getString(R.string.rupees_symbol) +  (ordersList.get(i).getQuantity() * ordersList.get(i).getOfferPrice()));
        ((ViewHolder) viewHolder).tvItemName.setText(ordersList.get(i).getProductName());
        if (ordersList.get(i).getWishes()!=null) {
            ((ViewHolder) viewHolder).tvOrderWishes.setVisibility(View.VISIBLE);
            ((ViewHolder) viewHolder).tvOrderWishes.setText("Wishes :  " + ordersList.get(i).getWishes());
        }else {
            ((ViewHolder) viewHolder).tvOrderWishes.setVisibility(View.GONE);
        }

    }


    @Override
    public int getItemCount() {
        return ordersList.size();
    }


    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDay;
        private TextView tvMonth;
        private TextView tvItemName;
        private TextView tvOrderWishes;
        private TextView tvPrice;
        private LinearLayout llContent;
        public ViewHolder(View view) {
            super(view);
            llContent = view.findViewById(R.id.ll_content);
            tvDay = view.findViewById(R.id.tv_day);
            tvMonth = view.findViewById(R.id.tv_month);
            tvItemName = view.findViewById(R.id.tv_item_name);
            tvOrderWishes = view.findViewById(R.id.tv_wishes);
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
