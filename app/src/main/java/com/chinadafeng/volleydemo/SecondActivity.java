package com.chinadafeng.volleydemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by liangzr on 16-6-13.
 */
public class SecondActivity extends AppCompatActivity {

    public static final String TAG = "SecondActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView textView = (TextView) this.findViewById(R.id.tv_sendMsg);
        TextView textView1 = (TextView) this.findViewById(R.id.tv_sendMsg2);
        TextView textView2 = (TextView) this.findViewById(R.id.tv_sendMsg3)
                ;
        assert textView != null;
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new TestEvent("就是我"));
            }
        });

        assert textView1 != null;
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post("字符串也可以的咯");
            }
        });

        assert textView2 != null;
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(0);
            }
        });
    }



}
