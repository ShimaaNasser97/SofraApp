
package com.example.sofra.data.model.offers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Offers {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private OffersPagenation data;

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

    public OffersPagenation getData() {
        return data;
    }

    public void setData(OffersPagenation data) {
        this.data = data;
    }

}
