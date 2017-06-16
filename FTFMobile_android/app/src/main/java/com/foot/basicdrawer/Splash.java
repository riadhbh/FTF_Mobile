package com.foot.basicdrawer;

import android.app.*;
import android.app.AlertDialog;
import android.content.*;

import android.net.*;
import android.os.*;

import android.provider.Settings;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.foot.basicdrawer.Navigation.*;
import com.foot.basicdrawer.Network.MySingleton;
import com.foot.basicdrawer.ToolBox.LanguageChanger;
import com.wang.avi.AVLoadingIndicatorView;


import static com.foot.basicdrawer.Network.Server_Host_Constant.*;
import static com.foot.basicdrawer.SQLite.DB_Queries.*;
import static com.foot.basicdrawer.ToolBox.AppSettings.*;
import static com.foot.basicdrawer.ToolBox.FrequentlyUsed.*;

/**
 * Created by riadh on 2/12/2017.
 */

@SuppressWarnings("deprecation")
public class Splash extends Activity {

    private AlertDialog NetDiag,WebsiteDiag;
    private static AVLoadingIndicatorView avi ;
    private static final Animation loaderAnim=NewRotationAnim(1000);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash);
        avi=(AVLoadingIndicatorView)findViewById(R.id.avi);
        //load_DB_Lang(getBaseContext());
        //load_DB_ListType(getBaseContext());

        getLang_controller(getBaseContext());
        getListType_controller(getBaseContext());





//        final Handler start = new Handler();
//
//        start.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ShowLoading();
//                StartupVerification();
//            }
//        }, 1000);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }




    private void NetworkDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.caution);
        builder.setCancelable(false);
        builder.setTitle(R.string.netpb_title);
        builder.setMessage(R.string.netpb_msg);

        builder.setPositiveButton(R.string.verify, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog

                dialog.dismiss();
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
            }
        });

        builder.setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                KillWholeApp(Splash.this);

            }
        });

        NetDiag=builder.create();

        HideLoading();
        NetDiag.show();

    }



    private void NonDispoWebsiteDiag(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.pbconn_website);
        builder.setIcon(R.drawable.caution);
        builder.setCancelable(false);
        builder.setMessage(R.string.nondispo_website);

        builder.setNeutralButton(R.string.exit, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                KillWholeApp(Splash.this);
            }
        }).create();

        WebsiteDiag=builder.create();

        HideLoading();
        WebsiteDiag.show();
    }



    private void StartupVerification(){

Thread th = new Thread(new Runnable() {
    @Override
    public void run() {
        while(!isNetworkAvailable());
        NetDiag.dismiss();
        avi.show();
        avi.setAnimation(loaderAnim);
        StartupVerification();

    }
    });
        if(isNetworkAvailable()){
            th.interrupt();
            //Toast.makeText(getBaseContext(),"Network is available !", Toast.LENGTH_LONG).show();
           /* if(WebsiteAvailable(APIServHost)){
                //Toast.makeText(getBaseContext(),"Website is available !", Toast.LENGTH_LONG).show();

                    //getDocument();
                        //getNewsDoc();
//                if(NewsDoc!=null){
                CloseAllDiags();
                HideLoading();
                GotoAct(Splash.this,Act_News.class);
                finish();
//                }else NonDispoWebsiteDiag();
            }else{
                NonDispoWebsiteDiag();
            }*/
            String url =APISite+"/web/v1/handshake";
            final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,

                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.contains("welcome_ftfmobile")){
                                CloseAllDiags();
                                HideLoading();
                                GotoAct(Splash.this,Act_News.class);
                                Splash.this.overridePendingTransition(R.anim.fade_in_from_bottom,R.anim.fade_out_from_bottom);
                                finish();
                            }else
                                NonDispoWebsiteDiag();
                            //Toast.makeText(getActivity().getBaseContext(),response,Toast.LENGTH_LONG).show();
                        }


                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    NonDispoWebsiteDiag();
//            res.setText("News loading is failed !");
                    //Toast.makeText(getActivity().getBaseContext(),error.toString(),Toast.LENGTH_LONG).show();
                }
            });
            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    15000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));


        }
        else{
            //Toast.makeText(getBaseContext(),"Network is not available !", Toast.LENGTH_LONG).show();
            NetworkDialog();
        th.start();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        avi.setAnimation(loaderAnim);
        ShowLoading();

/*        final Handler start = new Handler();

        start.postDelayed(new Runnable() {
            @Override
            public void run() {*/
                CloseAllDiags();
        StartupVerification();
/*                final Handler start = new Handler();

                start.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        StartupVerification();
                    }
                }, 1000);

            }
        }, 1000);*/
    }



    private void CloseAllDiags(){
        try{
            if(NetDiag!=null)
                if (NetDiag.isShowing())
                    NetDiag.dismiss();

            if(WebsiteDiag!=null)
                if(WebsiteDiag.isShowing())
                    WebsiteDiag.dismiss();

        }catch (RuntimeException e ){        }
    }



    private void ShowLoading()
    {
                avi.smoothToShow();
                avi.setAnimation(loaderAnim);
    }

    private void HideLoading()
    {                avi.smoothToHide();

    }


   private static Animation NewRotationAnim(int speed){
        Animation rotation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, .5f,
        Animation.RELATIVE_TO_SELF, .5f);
        rotation.setInterpolator(new LinearInterpolator());
        rotation.setRepeatCount(Animation.INFINITE);
        rotation.setDuration(speed);

      return  rotation;
    }

}
