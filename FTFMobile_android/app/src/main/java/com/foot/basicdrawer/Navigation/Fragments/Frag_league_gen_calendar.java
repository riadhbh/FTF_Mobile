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
import android.widget.Spinner;

import com.foot.basicdrawer.R;

import static com.foot.basicdrawer.ToolBox.AppSettings.*;
import static com.foot.basicdrawer.ToolBox.LanguageChanger.*;
import static com.foot.basicdrawer.Network.Server_Host_Constant.APISite;
import static com.foot.basicdrawer.ToolBox.FrequentlyUsed.ConfigBrowser;
import static com.foot.basicdrawer.ToolBox.FrequentlyUsed.loadwebPage;

/**
 * A simple {@link Fragment} subclass.
 */
public class Frag_league_gen_calendar extends Fragment {
    private static String league;
    public  void setLeague(String league) {
        this.league = league;
    }
    private SwipeRefreshLayout SwipeRefresh;
    private Spinner group_sp;
    private WebView content;
    private String path,file;
    public Frag_league_gen_calendar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_league_gen_calendar, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SwipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.GenCalRefresh);
        group_sp=(Spinner)view.findViewById(R.id.l_gen_cal_grp_sp);
        content= (WebView)view.findViewById(R.id.l_gen_cal_wv);
//        ConfigBrowser(content,false);


        group_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //round_sp.setAdapter(null);
                SwipeRefresh.setRefreshing(true);
                loadContent(get_spgroup());
                SwipeRefresh.setRefreshing(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SwipeRefresh.setRefreshing(true);
                loadContent(get_spgroup());
                SwipeRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    private void loadContent(String group){
        path=APISite+"/data/v1/leagues/"+league+"/gencal/"+getLang()+"/";
        file=getLang()+"_"+league+"_"+group+".html";
//        Toast.makeText(getActivity().getBaseContext(),path,Toast.LENGTH_LONG).show();
//    new Thread(new Runnable() {
//    @Override
//    public void run() {

        loadwebPage(getActivity().getApplicationContext(),content,path+file);
//    }
//    }).start();

    }

    private  String get_spgroup(){
        if(group_sp.getSelectedItemPosition()==0)
            return "a";
        else
            return "b";
    }

}
