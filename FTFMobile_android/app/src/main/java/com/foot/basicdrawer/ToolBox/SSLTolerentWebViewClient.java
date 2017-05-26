package com.foot.basicdrawer.ToolBox;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.opengl.Visibility;
import android.os.Build;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by riadh on 4/10/2017.
 */

public class SSLTolerentWebViewClient extends WebViewClient {

    boolean timeout;

    public SSLTolerentWebViewClient() {
        timeout = true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(timeout) {
                    // do what you want
                }
            }
        }).start();
    }

    @Override
    public void onPageFinished(WebView view, String url) {

        timeout = false;
    }

//    @Override
//    public boolean shouldOverrideUrlLoading(WebView view, String url) {
//        //your handling...
//        return super.shouldOverrideUrlLoading(view, url);
//    }

    public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {

        String message = "SSL Certificate error.";
        switch (error.getPrimaryError()) {
            case SslError.SSL_UNTRUSTED:
                message = "The certificate authority is not trusted.";
                break;
            case SslError.SSL_EXPIRED:
                message = "The certificate has expired.";
                break;
            case SslError.SSL_IDMISMATCH:
                message = "The certificate Hostname mismatch.";
                break;
            case SslError.SSL_NOTYETVALID:
                message = "The certificate is not yet valid.";
                break;
        }

                // Ignore SSL certificate errors
                handler.proceed();


    }
}
