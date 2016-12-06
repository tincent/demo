package com.tincent.android.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.orhanobut.logger.Logger;
import com.tincent.android.R;
import com.tincent.android.event.TXNativeEvent;
import com.tincent.android.event.TXNetworkEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by hui on 2016/12/3.
 */

/**
 * 碎片基类
 */
public abstract class AbsFragment extends Fragment implements View.OnClickListener, Handler.Callback {

    /**
     * 主线程消息处理器
     */
    public Handler mHandler;
    /**
     * 根布局
     */
    public FrameLayout mLayouContainner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 注册事件处理回调
        EventBus.getDefault().register(this);
        // 创建主线程消息处理器
        mHandler = new Handler(this);

        // 子类初始化数据
        initData();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mLayouContainner = (FrameLayout) inflater.inflate(R.layout.layout_container, null);

        //构造数据为空或网络错误占位提示
        inflateStubView();

        // 子类设置布局
        View contentView = createView(inflater);
        mLayouContainner.addView(contentView);

        // 子类初始化界面
        initView(mLayouContainner);

        return mLayouContainner;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 子类更新界面
        updateView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 解除事件处理回调
        EventBus.getDefault().unregister(this);
    }

    /**
     * 构造占位布局（可选）：数据为空；网络错误
     */
    protected void inflateStubView() {
        Logger.d("add none or network stub view to here");
    }

    /**
     * 创建界面，需要子类实现
     *
     * @param inflater
     * @return
     */
    public abstract View createView(LayoutInflater inflater);

    /**
     * 初始化界面，需要子类实现
     *
     * @param rootView
     */
    public abstract void initView(View rootView);

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 更新界面(可选)
     */
    protected void updateView() {
        Logger.d("refresh ui if needed");
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
