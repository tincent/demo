package com.tincent.demo;

import android.widget.TextView;

/**
 * Created by hui on 2016/12/5.
 */

public class LeakSingle {
    private static LeakSingle sInstance;
    private TextView mTextView;

    public static LeakSingle getInstance() {
        if (sInstance == null) {
            sInstance = new LeakSingle();
        }
        return sInstance;
    }

    // 内存泄露
    public void setRetainedTextView(TextView tv) {
        mTextView = tv;
    }
}
