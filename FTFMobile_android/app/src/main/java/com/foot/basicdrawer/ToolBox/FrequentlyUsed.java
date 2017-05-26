package com.foot.basicdrawer.ToolBox;

import android.app.*;

import android.content.*;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.foot.basicdrawer.*;
import com.foot.basicdrawer.Network.MySingleton;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


/**
 * Created by riadh on 2/9/2017.
 */

public class  FrequentlyUsed {
        public static void GotoAct(final Activity from, Class to){
            Intent i = new Intent(from,to);
           from.startActivity(i);
        }



public static void KillWholeApp(Activity ctxt){

        Intent intent = new Intent(ctxt, ExitActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);

        ctxt.startActivity(intent);
    }


    public static Bitmap drawableToBitmap (Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
    public static void ConfigBrowser(WebView webview, boolean zoom){

        webview.setInitialScale(1);
        webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webview.setScrollbarFadingEnabled(true);
        webview.setHorizontalScrollBarEnabled(false);
        webview.zoomOut();

        WebSettings webSettings = webview.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setMinimumFontSize(0);
        webSettings.setBuiltInZoomControls(zoom);


//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);




        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
//        webSettings.setTextSize(WebSettings.TextSize.NORMAL);

        webSettings.setDefaultTextEncodingName("utf-8");
        webview.getSettings().setAllowFileAccess(true);

        webview.canGoBack();
        webview.canGoForward();


        webview.setWebViewClient(new SSLTolerentWebViewClient());
//        webview.setWebChromeClient(new MyWebViewClient());


    }

public static void loadwebPage(Context ctxt, final WebView webview, String mUrl){
    InputStreamVolleyRequest request = new InputStreamVolleyRequest(Request.Method.GET, mUrl,
            new Response.Listener<byte[]>() {
                @Override
                public void onResponse(byte[] response) {
                    // TODO handle the response
                    try {

                        if (response!=null) {
//                                                    FileOutputStream outputStream;
//                                                    String name="test.html";
//                                                    outputStream = openFileOutput(name, Context.MODE_PRIVATE);
//                                                    outputStream.write(response);

                            String str = new String(response);
                            String textOfHtmlString = Jsoup.parse(str).text();


                           if(!textOfHtmlString.equals(str)){
                            webview.loadUrl("about:blank");
                            webview.loadData(str, "text/html; charset=UTF-8", null);
                            }
//                                                    outputStream.close();
//                                                    Toast.makeText(getBaseContext(), "Download complete.", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        Log.d("KEY_ERROR", "UNABLE TO DOWNLOAD FILE");
                        e.printStackTrace();
                    }
                }
            } ,new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            // TODO handle the error
            error.printStackTrace();
        }
    }, null);
//                            RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext(), new HurlStack());
//                            mRequestQueue.add(request);

    MySingleton.getInstance(ctxt).addToRequestQueue(request.setRetryPolicy(new DefaultRetryPolicy(
            15000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));


}


    public static void Reload(Activity a) {
        Intent intent = a.getIntent();
        a.overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        a.finish();

        a.overridePendingTransition(0, 0);
        a.startActivity(intent);

    }

}

