package com.zq.springcloud.application.beans;

/**
 * @author 01367477
 */
public class ResultBody {
    private int code;
    private String message;
    private Object data;

    public ResultBody() {
    }
    public ResultBody(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

