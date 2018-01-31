package com.example.nicholash.practicedemo;

import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import java.util.Arrays;

/**
 * @author Nicholas.Huang
 * @Declaration: shortcuts练习
 * @Email: kurode@sina.cn
 * <p>
 * 2018/1/15 10:36
 **/
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1) {
//            Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/"));
            Intent mIntent = new Intent(Intent.ACTION_VIEW, null, this, Main2Activity.class);
            ShortcutManager shortcutMgr = getSystemService(ShortcutManager.class);
            ShortcutInfo shortcutInfo = new ShortcutInfo.Builder(this, "iiddkk")
                    .setShortLabel("web site")
                    .setLongLabel("长按")
                    .setIcon(Icon.createWithResource(this, R.mipmap.ic_launcher))
                    .setIntent(mIntent)
                    .build();
            shortcutMgr.setDynamicShortcuts(Arrays.asList(shortcutInfo));
        }
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
            }
        });


    }
}
