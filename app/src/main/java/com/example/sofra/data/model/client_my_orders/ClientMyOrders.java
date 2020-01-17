
package com.example.sofra.data.model.client_my_orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientMyOrders {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private ClientMyOrdersPagenation data;

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

    public ClientMyOrdersPagenation getData() {
        return data;
    }

    public void setData(ClientMyOrdersPagenation data) {
        this.data = data;
    }

}
