package com.foot.basicdrawer.Navigation.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.foot.basicdrawer.R;

import static com.foot.basicdrawer.ToolBox.LanguageChanger.*;
import static com.foot.basicdrawer.Network.Server_Host_Constant.APISite;
import static com.foot.basicdrawer.ToolBox.FrequentlyUsed.*;
import static com.foot.basicdrawer.ToolBox.AppSettings.*;


public class Frag_league_playout_ranking extends Fragment {
    private static String league;
    public void setLeague(String league) {
        this.league = league;
    }

    private static String path;
    private static String file;


    private SwipeRefreshLayout SwipeRefresh;
    private static WebView rankingwv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_league_playout_ranking, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SwipeRefresh=(SwipeRefreshLayout)view.findViewById(R.id.PlayoutRefresh);
        rankingwv= (WebView)view.findViewById(R.id.rankpoutwv);
//        rankingtitle=(TextView)view.findViewById(R.id.l_rank_title_pout);


//        rankingtitle.setText(R.string.l1_rk_pout);
        ConfigBrowser(rankingwv,false);


//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
                path=APISite+"/data/v1/leagues/"+league+"/ranking/pout/"+getLang()+"/";
                file=getLang()+"_"+league+"_rk_pout.html";
//                rankingwv.loadUrl(path+file);

        SwipeRefresh.setRefreshing(true);
                 loadwebPage(getActivity().getApplicationContext(),rankingwv,path+file);
        SwipeRefresh.setRefreshing(false);

        SwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SwipeRefresh.setRefreshing(true);
                loadwebPage(getActivity().getApplicationContext(),rankingwv,path+file);
                SwipeRefresh.setRefreshing(false);
            }
        });


//                        }
//            },300);


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
