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

public class DeliveryBoysListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<Data> ordersList;
    ItemClick itemClick;
    public DeliveryBoysListAdapter(Context mContext, List<Data> ordersList, ItemClick itemClick) {
        this.mContext = mContext;
        this.ordersList = ordersList;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.item_delivery_boys, viewGroup, false);

        viewHolder = new ViewHolder(v);
        return viewHolder;

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

        ((ViewHolder) viewHolder).tvName.setText(ordersList.get(i).getName());
        ((ViewHolder) viewHolder).tvNumber.setText(ordersList.get(i).getContactNo());

        ((ViewHolder)viewHolder).llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClick(i);
            }
        });

    }


    @Override
    public int getItemCount() {
        return ordersList.size();
    }


    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvNumber;
        private LinearLayout llContent;
        public ViewHolder(View view) {
            super(view);
            llContent = view.findViewById(R.id.ll_content);
            tvName = view.findViewById(R.id.tv_name);
            tvNumber = view.findViewById(R.id.tv_phone_no);

        }
    }

}
