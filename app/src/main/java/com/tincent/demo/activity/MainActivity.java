package com.tincent.demo.activity;

import android.content.Intent;
import android.os.Message;
import android.view.View;

import com.tincent.demo.R;

public class MainActivity extends BaseActivity {

    /**
     * 浪费内存
     */
    private void gotoLeakMomory() {
        Intent intent = new Intent(this, LeakActivity.class);
        startActivity(intent);
    }

    @Override
    protected View inflateContentView() {
        return getLayoutInflater().inflate(R.layout.activity_main, null);
    }

    @Override
    protected void initView() {
        findViewById(R.id.btnLeak).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLeak:
                gotoLeakMomory();
                break;
        }
    }
}
