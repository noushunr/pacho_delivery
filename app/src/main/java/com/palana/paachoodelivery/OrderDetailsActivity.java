package com.palana.paachoodelivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.palana.paachoodelivery.SharedPrefs.Keys.TOKEN;

public class OrderDetailsActivity extends AppCompatActivity implements StateDialog.StateDialogListener {

    private String orderId;
    private TextView tvOrderId;
    private TextView tvOrderDate;
    private TextView tvOrderStatus;
    private TextView tvOrderWish;
    private TextView tvCustomerName;
    private TextView tvContactNo;
    private TextView tvAddress;
    private TextView tvAmount;
    private LinearLayout llUpdate;
    private LinearLayout llAssign;
    private TableLayout tableLayout;
    private RecyclerView rvItems;
    private ProgressDialogFragment progressDialogFragment;
    private int orderStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        if (getIntent().getExtras()!=null){
            Bundle bundle = getIntent().getExtras();
            orderId = bundle.getString("order_id");

        }
        tvOrderId = findViewById(R.id.order_id);
        tvOrderDate = findViewById(R.id.order_date_time);
        tvOrderStatus = findViewById(R.id.order_status);
        tvOrderWish = findViewById(R.id.order_wish);
        tvCustomerName = findViewById(R.id.tv_customer_name);
        tvContactNo = findViewById(R.id.tv_contact_no);
        tvAddress = findViewById(R.id.tv_address);
        tvAmount = findViewById(R.id.tv_amount);
        llUpdate = findViewById(R.id.btn_update);
        llAssign = findViewById(R.id.btn_assign);
        tableLayout = findViewById(R.id.tableLayout);
        rvItems = findViewById(R.id.rv_items);
        llAssign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StateDialog stateDialog = new StateDialog();
                stateDialog.show(getSupportFragmentManager(), "State");
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvItems.setLayoutManager(layoutManager);
        rvItems.setHasFixedSize(true);
        llUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialogFragment = ProgressDialogFragment.newInstance();
                progressDialogFragment.show(getSupportFragmentManager(), "progress_dialog");
                ApiClient.getApiInterface().updateOrderStatus("Bearer "+ SharedPrefs.getString(TOKEN,""),orderId,String.valueOf(orderStatus+1)).enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        try {
                            if (progressDialogFragment != null && !isFinishing())
                                progressDialogFragment.dismiss();
                            Result jsonObject = response.body();
                            if (response.isSuccessful()) {
                                if (jsonObject!=null && jsonObject.getData()!=null) {
                                    Toast toast = Toast.makeText(OrderDetailsActivity.this, jsonObject.getMessage(), Toast.LENGTH_SHORT);
                                    toast.show();
                                    finish();
                                } else {
                                    Toast toast = Toast.makeText(OrderDetailsActivity.this, jsonObject.getMessage(), Toast.LENGTH_SHORT);
                                    toast.show();
                                }

                            } else {


                            }
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        try {
                            if (progressDialogFragment != null && !isFinishing())
                                progressDialogFragment.dismiss();
                            Toast toast = Toast.makeText(OrderDetailsActivity.this, "Something went wrong", Toast.LENGTH_SHORT);
                            toast.show();
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        progressDialogFragment = ProgressDialogFragment.newInstance();
        progressDialogFragment.show(getSupportFragmentManager(), "progress_dialog");
        ApiClient.getApiInterface().orderDetails("Bearer "+ SharedPrefs.getString(TOKEN,""),orderId).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                try {
                    if (progressDialogFragment != null && !isFinishing())
                        progressDialogFragment.dismiss();
                    Result jsonObject = response.body();
                    if (response.isSuccessful()) {
                        if (jsonObject!=null && jsonObject.getData()!=null) {
                            showDetails(jsonObject.getData());
                        } else {
                            Toast toast = Toast.makeText(OrderDetailsActivity.this, jsonObject.getMessage(), Toast.LENGTH_SHORT);
                            toast.show();
                        }

                    } else {


                    }
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                try {
                    if (progressDialogFragment != null && !isFinishing())
                        progressDialogFragment.dismiss();
                    Toast toast = Toast.makeText(OrderDetailsActivity.this, "Something went wrong", Toast.LENGTH_SHORT);
                    toast.show();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    private void showDetails(Data orderDetails){
        if (orderDetails!=null){
            if (orderDetails.getOrderdetails()!=null){
                tvOrderId.setText(String.valueOf(orderDetails.getOrderdetails().getOrderId()));
                tvOrderDate.setText(formattedDate(orderDetails.getOrderdetails().getCreatedDate()));
                orderStatus = Integer.parseInt(orderDetails.getOrderdetails().getOrderStatus());
                tvAmount.setText(getString(R.string.rupees_symbol)+ orderDetails.getOrderdetails().getAmount());
                switch (orderDetails.getOrderdetails().getOrderStatus()){
                    case "0":
                        tvOrderStatus.setText("Cancelled");
                        llUpdate.setVisibility(View.GONE);
                        llAssign.setVisibility(View.GONE);
                        break;
                    case "1":
                        tvOrderStatus.setText("Order Placed");
                        llUpdate.setVisibility(View.VISIBLE);
                        llAssign.setVisibility(View.GONE);
                        break;
                    case "2":
                        tvOrderStatus.setText("Item Prepared");
                        llUpdate.setVisibility(View.VISIBLE);
                        llAssign.setVisibility(View.GONE);
                        break;
                    case "3":
                        tvOrderStatus.setText("Packed");
                        llUpdate.setVisibility(View.GONE);
                        llAssign.setVisibility(View.VISIBLE);
                        break;
                    case "4":
                        tvOrderStatus.setText("Picked");
                        llUpdate.setVisibility(View.VISIBLE);
                        llAssign.setVisibility(View.GONE);
                        break;
                    case "5":
                        tvOrderStatus.setText("Delivered");
                        llUpdate.setVisibility(View.GONE);
                        llAssign.setVisibility(View.GONE);
                        break;
                    case "6":
                        tvOrderStatus.setText("Returned");
                        llUpdate.setVisibility(View.GONE);
                        break;
                }
            } if (orderDetails.getDeliverydetails()!=null){
                tvCustomerName.setText(orderDetails.getDeliverydetails().getName());
                tvContactNo.setText(orderDetails.getDeliverydetails().getContactNo());
                tvAddress.setText(orderDetails.getDeliverydetails().getAddress());
            }if (orderDetails.getOrderproducts()!=null && orderDetails.getOrderproducts().size()>0){
//                for (int i=0;i<orderDetails.getOrderproducts().size();i++){
//                    /* Create a new row to be added. */
//                    TableRow tr = new TableRow(this);
//                    tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
//                    /* Create a Button to be the row-content. */
//                    tr.setPadding(0,5,0,5);
//                    TextView b = new TextView(this);
//                    b.setText("Black");
//                    b.setTextSize(16);
//                    TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT);
//                    params.weight = 0.50f;
//                    params.gravity = Gravity.CENTER;
//                    b.setLayoutParams(params);
//                    b.setGravity(Gravity.CENTER);
//                    /* Add Button to row. */
//                    TextView b1 = new TextView(this);
//                    int price = orderDetails.getOrderproducts().get(i).getOfferPrice();
//                    int quantity = orderDetails.getOrderproducts().get(i).getQuantity();
//                    int totalPrice = price*quantity;
//                    b1.setText(getString(R.string.rupees_symbol )+ totalPrice);
//                    b1.setTextSize(16);
//                    b1.setGravity(Gravity.CENTER);
//                    TableRow.LayoutParams params1 = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT);
//                    params1.weight = 0.50f;
//                    params1.gravity = Gravity.CENTER;
//                    b1.setLayoutParams(params1);
//
//                    tr.addView(b);
//                    tr.addView(b1);
//                    /* Add row to TableLayout. */
////tr.setBackgroundResource(R.drawable.sf_gradient_03);
//                    tableLayout.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
//                }

                rvItems.setAdapter(new OrderItemsListAdapter(OrderDetailsActivity.this,orderDetails.getOrderproducts()));
            }
        }
    }

    private String formattedDate(String createdDate) {
        String finalDate = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        SimpleDateFormat mDateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.US);
        Date date;
        try {
            date = format.parse(createdDate);
            finalDate = mDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return finalDate;
    }

    @Override
    public void applyState(int state) {
        progressDialogFragment = ProgressDialogFragment.newInstance();
        progressDialogFragment.show(getSupportFragmentManager(), "progress_dialog");
        ApiClient.getApiInterface().updateOrderBoy("Bearer "+ SharedPrefs.getString(TOKEN,""),orderId,String.valueOf(state)).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                try {
                    if (progressDialogFragment != null && !isFinishing())
                        progressDialogFragment.dismiss();
                    Result jsonObject = response.body();
                    if (response.isSuccessful()) {
                        if (jsonObject!=null && jsonObject.getData()!=null) {
                            Toast toast = Toast.makeText(OrderDetailsActivity.this, jsonObject.getMessage(), Toast.LENGTH_SHORT);
                            toast.show();
                            finish();
                        } else {
                            Toast toast = Toast.makeText(OrderDetailsActivity.this, jsonObject.getMessage(), Toast.LENGTH_SHORT);
                            toast.show();
                        }

                    } else {


                    }
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                try {
                    if (progressDialogFragment != null && !isFinishing())
                        progressDialogFragment.dismiss();
                    Toast toast = Toast.makeText(OrderDetailsActivity.this, "Something went wrong", Toast.LENGTH_SHORT);
                    toast.show();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
