package com.palana.paachoodelivery;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Orderdetails {

    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("amount")
    @Expose
    private Integer amount;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
