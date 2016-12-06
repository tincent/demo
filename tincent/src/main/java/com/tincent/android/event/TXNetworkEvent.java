package com.tincent.android.event;

/**
 * Created by hui on 2016/12/3.
 */

public class TXNetworkEvent {
    /**
     * 网络请求成功码
     */
    public static final int REQUET_OK_CODE = 200;
    /**
     * 业务请求标签
     */
    public String requestTag;

    /**
     * 业务状态码
     */
    public int statusCode;

    /**
     * 错误信息
     */
    public String message;

    /**
     * 业务数据
     */
    public Object response;

    public TXNetworkEvent(String tag, int code, String msg) {
        requestTag = tag;
        statusCode = code;
        message = msg;
    }

    public TXNetworkEvent(String tag, int code, String msg, Object resp) {
        requestTag = tag;
        statusCode = code;
        message = msg;
        response = resp;
    }

    public String toString() {
        return "tag : " + requestTag + ", code : " + statusCode + ", msg : " + message + ", data : " + response;
    }
}
