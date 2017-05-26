package com.foot.basicdrawer.Navigation.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.foot.basicdrawer.Network.MySingleton;
import com.foot.basicdrawer.R;

import java.util.ArrayList;

import static com.foot.basicdrawer.ToolBox.AppSettings.*;
import static com.foot.basicdrawer.ToolBox.FrequentlyUsed.ConfigBrowser;
import static com.foot.basicdrawer.ToolBox.LanguageChanger.*;
import static com.foot.basicdrawer.Network.Server_Host_Constant.APISite;
import static com.foot.basicdrawer.ToolBox.FrequentlyUsed.loadwebPage;

/**
 * A simple {@link Fragment} subclass.
 */
public class Frag_league_playout_calres extends Fragment {


    private static String league;
    private SwipeRefreshLayout SwipeRefresh;
    private static Spinner round_sp;
    private static WebView content;

    private static String path;
    private static String file;



    public void setLeague(String league) {
        this.league = league;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag_league_playout_calres, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SwipeRefresh=(SwipeRefreshLayout)view.findViewById(R.id.PlayoutCalResRefresh);
        round_sp=(Spinner)view.findViewById(R.id.l_pout_round_sp);
        content=(WebView)view.findViewById(R.id.l_pout_calres_content);
//        ConfigBrowser(content,false);
        getCurrweek();

        round_sp.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        Toast.makeText(getActivity().getBaseContext(),String.valueOf(parent.getItemAtPosition(position)),Toast.LENGTH_SHORT).show();
                        getCalresContent(Integer.parseInt(String.valueOf(parent.getItemAtPosition(position))));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );

        SwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SwipeRefresh.setRefreshing(true);
                getCurrweek();
                SwipeRefresh.setRefreshing(false);
            }
        });

    }

    private void fill_roundsp(int current){

        ArrayList<String> spinnerArray = new ArrayList<String>();
        for(int i=current;i>0;i--){
            spinnerArray.add(String.valueOf(i));
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, spinnerArray);
        round_sp.setAdapter(spinnerArrayAdapter);

    }


    private void getCurrweek(){

        String url =APISite+"/web/v1/getlastweek/"+getLang()+"/"+league+"/pout";
// Request a string response from the provided URL.
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        fill_roundsp(Integer.parseInt(response));
                        //Toast.makeText(getActivity().getBaseContext(),response,Toast.LENGTH_LONG).show();
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//            res.setText("News loading is failed !");
                //Toast.makeText(getActivity().getBaseContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        MySingleton.getInstance(getActivity().getApplicationContext()).
                addToRequestQueue(stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));

    }

    private void getCalresContent(final int week){
        // Request a string response from the provided URL.

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
        path=APISite+"/data/v1/leagues/"+league+"/calres/pout/"+getLang()+"/";
        file=getLang()+"_"+league+"_calres_pout_"+String.valueOf(week)+".html";
        final String url=path+file;

                        loadwebPage(getActivity().getApplicationContext(),content,url);



//                content.loadUrl(url);
//                }
//            },300);
        //String url =APISite+"/data/v1/leagues/"+league+"/calres/"+lang+"_"+league+"_calres_"+get_spgroup(group_sp)+"_"+String.valueOf(week)+".html";
        //Toast.makeText(getActivity().getBaseContext(),url,Toast.LENGTH_LONG).show();
    /*
    final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,

            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    content.loadData(response,"text/html", "UTF-8");
                    //Toast.makeText(getActivity().getBaseContext(),response,Toast.LENGTH_LONG).show();
                }


            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
//            res.setText("News loading is failed !");
            Toast.makeText(getActivity().getBaseContext(),error.toString(),Toast.LENGTH_LONG).show();
        }
    });
    MySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(stringRequest);
*/

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

}
