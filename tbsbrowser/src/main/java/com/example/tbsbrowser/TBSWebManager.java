package com.example.tbsbrowser;

import android.content.Context;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;

/**
 * author: DragonForest
 * time: 2019/12/6
 */
public class TBSWebManager {
    // webView类型
    /**
     * 正在初始化 未检测出使用的webView类型
     */
    public final static int WEB_TYPE_NONE = 0;
    /**
     * 使用的webView类型为 腾讯x5
     */
    public final static int WEB_TYPE_X5 = 1;
    /**
     * 使用的webView类型为 原生WebView
     */
    public final static int WEB_TYPE_LOCAL = 2;

    // 当前的webView类型
    private volatile static int webViewType = WEB_TYPE_NONE;

    public static void init(Context appContext){
        //非wifi情况下，主动下载x5内核
        QbSdk.setDownloadWithoutWifi(true);

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.e("app", " onViewInitFinished is " + arg0);
                if(arg0){
                    webViewType=WEB_TYPE_X5;
                }else{
                    webViewType=WEB_TYPE_LOCAL;
                }
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
                Log.e("app", " onCoreInitFinished ");
            }
        };
        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                Log.e("app", " onDownloadFinish " + i);
            }

            @Override
            public void onInstallFinish(int i) {
                Log.e("app", " onInstallFinish " + i);
            }

            @Override
            public void onDownloadProgress(int i) {
                Log.e("app", " onDownloadProgress " + i);
            }
        });
        //x5内核初始化接口
        QbSdk.initX5Environment(appContext, cb);
    }

    public static int getWebViewType() {
        return webViewType;
    }
}
