package com.tincent.android;

import android.app.Activity;
import android.app.Application;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.tincent.android.event.TXNativeEvent;
import com.tincent.android.event.TXNetworkEvent;
import com.tincent.android.utils.TXCache;
import com.tincent.android.utils.TXShareFileUtil;
import com.tincent.android.utils.TXToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hui on 2016/12/3.
 */

/**
 * 应用抽象基类
 */
public abstract class AbsApplication extends Application implements Runnable {
    // 当前打开的Activity列表
    private List<Activity> activities;
    // 后台线程消息处理器
    private Handler workHandler;
    // 线程的循环体
    private Looper looper;
    // 具有循环对列的后台线程
    private HandlerThread handlerThread;

    @Override
    public void onCreate() {
        super.onCreate();
        // 创建列表容器
        activities = new ArrayList<Activity>();
        // 注册事件理处回调
        EventBus.getDefault().register(this);
        // 初始化ShareFile文件实例
        TXShareFileUtil.getInstance().init(this);
        // 初始化提示工具实例
        TXToastUtil.getInstatnce().init(this);
        // 创建具有循环对列的后台线程
        handlerThread = new HandlerThread(this.getPackageName());
        // 开始后台线程体
        handlerThread.start();
        // 获取后台线程的循环体
        looper = handlerThread.getLooper();
        // 创建后台线程相关的Handler外理器
        workHandler = new Handler(looper);
        // 投递给后台线程一个runnable运行体，用来做一些初使化较耗时的动作
        workHandler.post(this);
        // 初始化日志工具
        Logger.init(Constants.TAG).logLevel(Constants.DEBUG ? LogLevel.FULL : LogLevel.NONE);
        Logger.d("app process start !");
        // 初始化缓存
        TXCache.init(this);
    }

    /**
     * 添加己打开的Activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * 删除关闭的 Activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     * 退出APP
     */
    public void exitApp() {
        for (Activity act : activities) {
            act.finish();
        }
    }

    /**
     * 结束除过当前Activity以外的其它Activity
     *
     * @param activity
     */
    public void finishOthers(Activity activity) {
        for (Activity act : activities) {
            if (!act.equals(activity)) {
                act.finish();
            }
        }
    }

    /**
     * 结束当前Activity
     *
     * @param activity
     */
    public void finishSelf(Activity activity) {
        activity.finish();
    }

    /**
     * 返回后台线程的Handler处理器
     *
     * @return
     */
    public Handler getWorkHandler() {
        return workHandler;
    }

    /**
     * 返回后台线程的循环体
     *
     * @return
     */
    public Looper getWorkLooper() {
        return looper;
    }

    /**
     * 接收网络请求的结果
     *
     * @param evt
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRecvNetworkResponse(TXNetworkEvent evt) {
        Logger.d(evt);
    }

    /**
     * 接收本地消息的通知
     *
     * @param evt
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRecvNativeMessage(TXNativeEvent evt) {
        Logger.d(evt);
    }

}
