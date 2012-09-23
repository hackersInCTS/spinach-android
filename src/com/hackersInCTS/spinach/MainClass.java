package com.hackersInCTS.spinach;

import android.os.Bundle;
import android.view.Menu;

import org.apache.cordova.*;

import com.xtify.cordova.*;

public class MainClass extends DroidGap {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.loadUrl("file:///android_asset/www/xpsrc/index.html");
        XtifyCordovaPlugin.processActivityExtras(getIntent().getExtras(), this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_class, menu);
        return true;
    }
}
