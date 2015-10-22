package com.example.MyWeibo.model;

/**
 * Created by wanglu
 */
public class ResponseObject<T> {
    private T result;
    private String state;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
