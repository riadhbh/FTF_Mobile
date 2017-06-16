package com.foot.basicdrawer.Navigation;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.*;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.*;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.*;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.foot.basicdrawer.Network.MySingleton;
import com.foot.basicdrawer.R;
import com.foot.basicdrawer.ToolBox.InputStreamVolleyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import static com.foot.basicdrawer.Network.Server_Host_Constant.APISite;
import static com.foot.basicdrawer.ToolBox.FrequentlyUsed.ConfigBrowser;
import static com.foot.basicdrawer.ToolBox.FrequentlyUsed.loadwebPage;


/**
 * Created by riadh on 2/22/2017.
 */

public class Act_Read_New extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefresh;
    private WebView webview;//,placeholder;
    private static String content=null;
    private String new_id,new_title,websitenew_url,mobilenew_url;
    private android.app.AlertDialog BardUrlDiag;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.activity_read_new);
        new_id =getIntent().getStringExtra("newid");
        new_title =getIntent().getStringExtra("newtitle");

//        getSupportActionBar().setDisplayShowTitleEnabled(false);

        webview=(WebView)findViewById(R.id.new_reader_view);
        swipeRefresh=(SwipeRefreshLayout)findViewById(R.id.ReadNewRefresh);



//        placeholder=(WebView)findViewById(R.id.wv_reader_placeholder);

        ConfigBrowser(webview,true);
//        ConfigBrowser(placeholder);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(new_title);

//        placeholder.setVisibility(View.GONE);
//        webview .setVisibility(View.VISIBLE);

//        placeholder.loadUrl("file:///android_asset/new_preload.html");

        if(new_id!=null)

            if(new_id.length()>0){
        /*
        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(final WebView view, int progress) {
                if(progress==100){
                    webview.setVisibility(View.VISIBLE);
                    placeholder.setVisibility(View.GONE);
                }
                }
        });*/

                swipeRefresh.setRefreshing(true);
                getNew(new_id);
                swipeRefresh.setRefreshing(false);

        }else{
                swipeRefresh.setRefreshing(false);
                BadUrlDiag();
            }else{
            swipeRefresh.setRefreshing(false);
            BadUrlDiag();
        }



        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if(new_id!=null)
                    if(new_id.length()>0){
                        if(URLUtil.isValidUrl(webview.getUrl())){
                            swipeRefresh.setRefreshing(true);
                            String url = webview.getUrl();
                            webview.loadUrl(url);
                            swipeRefresh.setRefreshing(false);
                        }else{
                        swipeRefresh.setRefreshing(true);
                        getNew(new_id);
                        swipeRefresh.setRefreshing(false);
                        }
                        }else{
                        swipeRefresh.setRefreshing(false);
                        BadUrlDiag();
                    }else{
                    swipeRefresh.setRefreshing(false);
                    BadUrlDiag();
                }
            }
        });


    }


    private void BadUrlDiag(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(R.string.badurl_title);
        builder.setIcon(R.drawable.caution);
        builder.setCancelable(false);
        builder.setMessage(R.string.badurl_msg);

        builder.setNeutralButton(R.string.exit, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                finish();
            }
        }).create();

        BardUrlDiag=builder.create();


        BardUrlDiag.show();
    }


    private void getNew(String new_id){

        String url =APISite+"/web/v1/getnew/"+new_id;

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //res.setText(response);
                        if(response!=null){

                        JSONObject jsnobject = null;
                        try {


                            jsnobject = new JSONObject(response);
                            JSONArray jsonArray = jsnobject.getJSONArray("anew");

                            if(jsnobject!=null){
                                JSONObject explrObject = jsonArray.getJSONObject(0);

                                websitenew_url=explrObject.getString("websitenewurl");
                                mobilenew_url=explrObject.getString("mobilenewurl");

                            final String mUrl= APISite+mobilenew_url;

//                                            loadwebPage(getApplicationContext(),webview,mUrl);

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

                                                    content = new String(response);
//                                                    String textOfHtmlString = Jsoup.parse(content).text();


//                                                    if(!textOfHtmlString.equals(content)){
                                                        webview.loadData(content, "text/html; charset=UTF-8", null);
//                                                    }
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

                            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request.setRetryPolicy(new DefaultRetryPolicy(
                                    15000,
                                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));


//                            firstopnedurl=webview.getUrl();
//                            Toast.makeText(getBaseContext(),webview.getUrl(),Toast.LENGTH_LONG).show();
//                            webview.loadUrl("http://"+APIServHost+"/ftfmobile"+mobilenew_url);

                        }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(getApplicationContext()).
                addToRequestQueue(stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            if(item.getItemId()==R.id.rn_share_opt){
//                Toast.makeText(getBaseContext(),"share option",Toast.LENGTH_LONG).show();
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "FTF Mobile");
                    String sAux = "\n"+new_title+"\n\n";
                    sAux = sAux + websitenew_url+" \n\n";
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, ""));
                } catch(Exception e) {
                    //e.toString();
                }
            }else if(item.getItemId()==R.id.rn_copyurl_opt){
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", websitenew_url);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getBaseContext(),R.string.link_copied,Toast.LENGTH_LONG).show();
            }else if(item.getItemId()==android.R.id.home){
                    this.onBackPressed();
            }


         return  super.onOptionsItemSelected(item);
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.share_menu, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if(URLUtil.isValidUrl(webview.getUrl())){
            webview.goBack();
//            webview.loadUrl("about:blank");
//            webview.loadData(content, "text/html; charset=UTF-8", null);

//                    Toast.makeText(getBaseContext(),webview.getUrl()+" Go Back",Toast.LENGTH_LONG).show();
        }else{
            webview.loadUrl("about:blank");
            super.onBackPressed();
//            onBackPressed();

    }
    }
}
