package com.example.tbsbrowser;

import android.app.Application;

/**
 * author: DragonForest
 * time: 2019/12/6
 */
public class TBSApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化x5
        TBSWebManager.init(getApplicationContext());
    }
}
