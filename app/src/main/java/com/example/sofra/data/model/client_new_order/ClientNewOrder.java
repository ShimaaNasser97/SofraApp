
package com.example.sofra.data.model.client_new_order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientNewOrder {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private ClintNewOrderData data;

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

    public ClintNewOrderData getData() {
        return data;
    }

    public void setData(ClintNewOrderData data) {
        this.data = data;
    }

}
