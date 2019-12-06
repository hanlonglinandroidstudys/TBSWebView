package com.example.tbsbrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class TBSWebActivity extends AppCompatActivity {

    private final static String TAG = "TBSWebActivity";
    private WebView x5WebView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tbs_webview);
        initView();
        debug();
    }

    private void initView() {
        progressBar = findViewById(R.id.progressBar);
        x5WebView = findViewById(R.id.x5WebView);

        progressBar.setMax(100);
        progressBar.setVisibility(View.GONE);
        setX5WebView(x5WebView);
        x5WebView.loadUrl("https://www.baidu.com");
    }

    private void setX5WebView(WebView webView) {
        x5WebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                Log.e(TAG, "正在shouldOverrideUrlLoading" + s);
                return super.shouldOverrideUrlLoading(webView, s);
            }
        });

        x5WebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                // 加载进度更新
                Log.e(TAG, "正在onProgressChanged:" + i);
                if (i < 100) {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(i);
                } else if (i == 100) {
                    progressBar.setProgress(0);
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }


    /**
     * 处理回退事件
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (x5WebView != null) {
            // Check if the key event was the Back button and if there's history
            if (x5WebView.canGoBack()) {
                x5WebView.goBack();
                return false;
            }
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }

    private void debug() {
        int webViewType = TBSWebManager.getWebViewType();
        switch (webViewType) {
            case TBSWebManager.WEB_TYPE_NONE:
                Toast.makeText(this, "内核正在加载中...", Toast.LENGTH_SHORT).show();
                break;
            case TBSWebManager.WEB_TYPE_LOCAL:
                Toast.makeText(this, "正在使用原生内核...", Toast.LENGTH_SHORT).show();
                break;
            case TBSWebManager.WEB_TYPE_X5:
                Toast.makeText(this, "正在使用腾讯x5内核...", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
