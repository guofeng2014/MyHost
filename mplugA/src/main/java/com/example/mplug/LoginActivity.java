package com.example.mplug;

import android.os.Bundle;
import android.view.View;

import com.example.pluglibrary.BasePlugActivity;
import com.example.pluglibrary.DLIntent;

/**
 * create by guofeng
 * date on 2019-09-24
 */
public class LoginActivity extends BasePlugActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.btn_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DLIntent intent = new DLIntent();
                intent.setPackageName("com.example.mplug");
                intent.setmPlugnClass("com.example.mplug.MainActivity");
                startActivity(intent);
            }
        });


        findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

}
