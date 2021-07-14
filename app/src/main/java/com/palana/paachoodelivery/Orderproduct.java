package com.palana.paachoodelivery;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Orderproduct {

    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("selling_price")
    @Expose
    private Integer sellingPrice;
    @SerializedName("offer_price")
    @Expose
    private Integer offerPrice;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;

    @SerializedName("product_name")
    @Expose
    private String productName;

    @SerializedName("wishes")
    @Expose
    private String wishes;


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Integer sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Integer getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(Integer offerPrice) {
        this.offerPrice = offerPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getWishes() {
        return wishes;
    }

    public void setWishes(String wishes) {
        this.wishes = wishes;
    }
}
