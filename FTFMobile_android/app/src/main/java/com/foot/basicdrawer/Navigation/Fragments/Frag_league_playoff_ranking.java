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

import static com.foot.basicdrawer.ToolBox.AppSettings.*;
import static com.foot.basicdrawer.ToolBox.LanguageChanger.*;
import static com.foot.basicdrawer.Network.Server_Host_Constant.APISite;
import static com.foot.basicdrawer.ToolBox.FrequentlyUsed.ConfigBrowser;
import static com.foot.basicdrawer.ToolBox.FrequentlyUsed.loadwebPage;


public class Frag_league_playoff_ranking extends Fragment {
    private static String league;
    private SwipeRefreshLayout SwipeRefresh;
    public void setLeague(String league) {
        this.league = league;
    }

    private static String path;
    private static String file;



    private static WebView rankingwv;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_league_playoff_ranking, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SwipeRefresh=(SwipeRefreshLayout)view.findViewById(R.id.PlayoffRefresh);
        rankingwv= (WebView)view.findViewById(R.id.rankpoffwv);

//        rankingtitle=(TextView)view.findViewById(R.id.l_rank_title_poff);


//        rankingtitle.setText(R.string.l1_rk_poff);
        ConfigBrowser(rankingwv,false);


//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {

                path=APISite+"/data/v1/leagues/"+league+"/ranking/poff/"+getLang()+"/";
                file=getLang()+"_"+league+"_rk_poff.html";
//                Toast.makeText(getActivity().getBaseContext(),path,Toast.LENGTH_LONG).show();
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


//            }
//        },300);
//

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
