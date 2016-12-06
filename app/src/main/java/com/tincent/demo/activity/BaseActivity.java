package com.tincent.demo.activity;

import android.os.Bundle;
import android.view.MotionEvent;

import com.bugtags.library.Bugtags;
import com.igexin.sdk.PushManager;
import com.tincent.android.activity.AbsActivity;
import com.tincent.demo.service.DemoIntentService;
import com.tincent.demo.service.DemoPushService;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by hui on 2016/12/6.
 */

public abstract class BaseActivity extends AbsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushManager.getInstance().initialize(this.getApplicationContext(), DemoPushService.class);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), DemoIntentService.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注：回调 1
        Bugtags.onResume(this);
        MobclickAgent.onResume(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        //注：回调 2
        Bugtags.onPause(this);
        MobclickAgent.onPause(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //注：回调 3
        Bugtags.onDispatchTouchEvent(this, event);
        return super.dispatchTouchEvent(event);
    }
}
