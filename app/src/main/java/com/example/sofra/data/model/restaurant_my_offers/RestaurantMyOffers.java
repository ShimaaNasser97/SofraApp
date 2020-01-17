
package com.example.sofra.data.model.restaurant_my_offers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantMyOffers {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private RestaurantMyOffersPagenation data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public RestaurantMyOffersPagenation getData() {
        return data;
    }

    public void setData(RestaurantMyOffersPagenation data) {
        this.data = data;
    }

}
