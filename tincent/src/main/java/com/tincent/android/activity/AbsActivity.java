package com.tincent.android.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;

import com.orhanobut.logger.Logger;
import com.tincent.android.AbsApplication;
import com.tincent.android.R;
import com.tincent.android.event.TXNativeEvent;
import com.tincent.android.event.TXNetworkEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by hui on 2016/12/5.
 */

public abstract class AbsActivity extends AppCompatActivity implements View.OnClickListener, Handler.Callback {
    /**
     * 应用程序引用
     **/
    public AbsApplication mApplication;
    /**
     * 主线程消息处理器
     **/
    public Handler mMainHandler;
    /**
     * 后台线程消息处理器
     **/
    public Handler mWorkHandler;

    /**
     * 根布局
     */
    public FrameLayout mLayoutContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取宿主应用句柄
        mApplication = (AbsApplication) getApplication();

        // 添加Activity
        mApplication.addActivity(this);

        //订阅事件
        EventBus.getDefault().register(this);


        // 创建主线程消息处理器
        mMainHandler = new Handler(this);

        Looper looper = mApplication.getWorkLooper();
        // 创建后台线程消息处理器
        mWorkHandler = new Handler(looper, new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                return handleAsynMsg(msg);
            }
        });

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消订阅
        EventBus.getDefault().unregister(this);
        // 删除Activity
        mApplication.removeActivity(this);
    }


    /**
     * 初始化
     */
    public void init() {
        setContentView(R.layout.layout_container);

        //获取根容器
        mLayoutContainer = (FrameLayout) findViewById(R.id.layoutContainer);

        //构造数据为空或网络错误占位提示
        inflateStubView();

        // 子类设置布局
        View contentView = inflateContentView();
        mLayoutContainer.addView(contentView);

        // 子类初始化界面
        initView();

        // 子类初始化数据
        initData();
    }

    /**
     * 构造占位布局（可选）：数据为空；网络错误
     */
    protected void inflateStubView() {
        Logger.d("add none or network stub view to here");
    }

    /**
     * 构造布局界面
     */
    protected abstract View inflateContentView();

    /**
     * 初始化界面
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 更新界面(可选)
     */
    protected void updateView() {
        Logger.d("refresh ui if needed");
    }


    /**
     * 处理后台线程中的消息(可选)
     *
     * @param msg
     * @return
     */
    public boolean handleAsynMsg(Message msg) {
        return false;
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

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public int getScreenWidth() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        if (metrics.widthPixels > 900) {
            return 1080;
        } else if (metrics.widthPixels > 700 && metrics.widthPixels <= 900) {
            return 720;
        } else if (metrics.widthPixels >= 500 && metrics.widthPixels <= 700) {
            return 640;
        } else {
            return 490;
        }
    }
}
