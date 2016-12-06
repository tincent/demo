package com.tincent.android.event;

/**
 * Created by hui on 2016/12/3.
 */

/**
 * 消息事件
 */
public class TXNativeEvent {

    public String eventType;

    public TXNativeEvent(String evtType) {
        eventType = evtType;
    }

    public String toString() {
        return "eventType : " + eventType;
    }
}
