package com.tincent.demo;


import android.content.Context;

import com.bugtags.library.Bugtags;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tincent.android.AbsApplication;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by hui on 2016/12/3.
 */

public class DemoApplication extends AbsApplication {
    private RefWatcher refWatcher = null;

    /**
     * 获取对象引用监视器
     *
     * @param context
     * @return
     */
    public static RefWatcher getRefWatcher(Context context) {
        DemoApplication application = (DemoApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            Logger.d("leak canary analyzer process");
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }

        // Normal app init code...

        //安装内存监听器
        refWatcher = Constants.DEBUG ? LeakCanary.install(this) : RefWatcher.DISABLED;

        //初始化Bugtags
        Bugtags.start(Constants.BUGTAGS_APP_KEY, this, Bugtags.BTGInvocationEventBubble);

        //初始化Umeng统计
        MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(this, Constants.UMENG_APP_KEY, "tincent", MobclickAgent.EScenarioType.E_UM_NORMAL, false));
    }

    @Override
    public void run() {
        // This method is prepared for doing long time work

    }
}
