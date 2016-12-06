package com.tincent.demo.fragment;

import com.squareup.leakcanary.RefWatcher;
import com.tincent.android.fragment.AbsFragment;
import com.tincent.demo.DemoApplication;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by hui on 2016/12/6.
 */

public abstract class BaseFragment extends AbsFragment {

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getSimpleName());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = DemoApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
