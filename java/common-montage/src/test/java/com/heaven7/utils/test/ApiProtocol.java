package com.heaven7.utils.test;

import com.google.gson.annotations.SerializedName;

public class ApiProtocol<T> {

    private int code;
    @SerializedName("message")
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
}
