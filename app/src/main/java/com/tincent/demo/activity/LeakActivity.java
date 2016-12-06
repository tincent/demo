package com.tincent.demo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.tincent.demo.LeakSingle;
import com.tincent.demo.R;

public class LeakActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        TextView txtLeak = (TextView) findViewById(R.id.txtLeak);
        LeakSingle.getInstance().setRetainedTextView(txtLeak);
    }
}
