package com.palana.paachoodelivery;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {

    @SerializedName("user_type")
    @Expose
    private String type;

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("session_id")
    @Expose
    private String sessionId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("contact_no")
    @Expose
    private String contactNo;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("razorpay_order_id")
    @Expose
    private String razorpayOrderId;
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRazorpayOrderId() {
        return razorpayOrderId;
    }

    public void setRazorpayOrderId(String razorpayOrderId) {
        this.razorpayOrderId = razorpayOrderId;
    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("created_date")
    @Expose
    private String createdDate;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @SerializedName("orderdetails")
    @Expose
    private Orderdetails orderdetails;
    @SerializedName("deliverydetails")
    @Expose
    private Deliverydetails deliverydetails;
    @SerializedName("orderproducts")
    @Expose
    private List<Orderproduct> orderproducts = null;

    public Orderdetails getOrderdetails() {
        return orderdetails;
    }

    public void setOrderdetails(Orderdetails orderdetails) {
        this.orderdetails = orderdetails;
    }

    public Deliverydetails getDeliverydetails() {
        return deliverydetails;
    }

    public void setDeliverydetails(Deliverydetails deliverydetails) {
        this.deliverydetails = deliverydetails;
    }

    public List<Orderproduct> getOrderproducts() {
        return orderproducts;
    }

    public void setOrderproducts(List<Orderproduct> orderproducts) {
        this.orderproducts = orderproducts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
