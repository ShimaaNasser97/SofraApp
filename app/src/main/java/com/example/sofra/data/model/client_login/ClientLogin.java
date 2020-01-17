
package com.example.sofra.data.model.client_login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientLogin {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private ClientLoginData data;

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

    public ClientLoginData getData() {
        return data;
    }

    public void setData(ClientLoginData data) {
        this.data = data;
    }

}
