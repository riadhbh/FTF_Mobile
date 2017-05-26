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

import static com.foot.basicdrawer.ToolBox.FrequentlyUsed.ConfigBrowser;
import static com.foot.basicdrawer.ToolBox.LanguageChanger.*;
import static com.foot.basicdrawer.Network.Server_Host_Constant.APISite;
import static com.foot.basicdrawer.ToolBox.FrequentlyUsed.loadwebPage;
import static com.foot.basicdrawer.ToolBox.AppSettings.*;
/**
 * A simple {@link Fragment} subclass.
 */
public class Frag_natteam_events extends Fragment {
private SwipeRefreshLayout SwipeRefresh;

//    public Frag_natteam_events() {
//        // Required empty public constructor
//    }

private static WebView content;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_natteam_events, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SwipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.NatTeamEventRefresh);
        content=(WebView)view.findViewById(R.id.natteam_events_content);
//        ConfigBrowser(content,false);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
                final String url=APISite+"/data/v1/nationalteam/events/"+getLang()+"/"+getLang()+"_events.html";
//                content.loadUrl(url);

        SwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SwipeRefresh.setRefreshing(true);
                loadwebPage(getActivity().getApplicationContext(),content,url);
                SwipeRefresh.setRefreshing(false);
            }
        });

        SwipeRefresh.setRefreshing(true);
        loadwebPage(getActivity().getApplicationContext(),content,url);
        SwipeRefresh.setRefreshing(false);



//            }
//        },300);

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
