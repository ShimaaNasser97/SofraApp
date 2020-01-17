
package com.example.sofra.data.model.categories;

import java.util.List;

import com.example.sofra.data.model.restaurant_my_categories.RestaurantMyCategoriesData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Categories {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<RestaurantMyCategoriesData> data = null;

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

    public List<RestaurantMyCategoriesData> getData() {
        return data;
    }

    public void setData(List<RestaurantMyCategoriesData> data) {
        this.data = data;
    }

}
