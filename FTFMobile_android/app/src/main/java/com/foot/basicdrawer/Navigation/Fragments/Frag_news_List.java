package com.foot.basicdrawer.Navigation.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.foot.basicdrawer.Adapters.NewsListAdapter;
import com.foot.basicdrawer.Navigation.Act_Read_New;
import com.foot.basicdrawer.Network.MySingleton;
import com.foot.basicdrawer.R;
import com.foot.basicdrawer.ToolBox.RecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.foot.basicdrawer.ToolBox.LanguageChanger.*;
import static com.foot.basicdrawer.Network.Server_Host_Constant.APISite;
import static com.foot.basicdrawer.ToolBox.AppSettings.*;
/**
 * A simple {@link Fragment} subclass.
 */
public class Frag_news_List extends Fragment implements NewsListAdapter.OnLoadMoreListener
        ,SwipeRefreshLayout.OnRefreshListener{

    private String Category=null;


    private static int begin =1,end=3;
    private  ArrayList<String> NewsIDs ;
    private  ArrayList<String> NewsTitles ;
    private  ArrayList<String> NewsDates ;
    private  ArrayList<String> NewsImgsUrls;
    private  ArrayList<String> NewsUrls;

    private NewsListAdapter adapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private LinearLayoutManager llm;

    public Frag_news_List() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=(RecyclerView)view.findViewById(R.id.news_rv);
        swipeRefresh=(SwipeRefreshLayout)view.findViewById(R.id.swipeRefresh);
        Category=getArguments().getString("cat");

        NewsIDs= new ArrayList<>();
        NewsTitles=new ArrayList<>();
        NewsDates=new ArrayList<>();
        NewsImgsUrls=new ArrayList<>();
        NewsUrls=new ArrayList<>();


        llm = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(llm);
//        recyclerView.setItemAnimator(itemAnimator);

        adapter = new NewsListAdapter(this);
        adapter.setLinearLayoutManager(llm);
        adapter.setRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);

        swipeRefresh.setOnRefreshListener(this);

//        final Handler start = new Handler();
//        start.postDelayed(new Runnable() {
//            @Override
//            public void run() {
                clearalltabs();
                swipeRefresh.setRefreshing(true);
                begin=1;end=3;
                loadNews(begin,end);

//            }
//        }, 1000);







        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity().getBaseContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {
                        if(!adapter.getMoreLoading()){
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    Intent go = new Intent(getActivity(), Act_Read_New.class);
                                    go.putExtra("newid", NewsIDs.get(position));
                                    go.putExtra("newtitle", NewsTitles.get(position));
                                    startActivity(go);
                                }
                            },300);
                        }
                    }
                }) {
                    @Override
                    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

                    }
                }
        );


    }


    @Override
    public void onRefresh() {
        clearalltabs();
        begin = 1;
        end = 3;

        loadNews(begin,end);
    }

    @Override
    public void onLoadMore() {
//        swipeRefresh.setRefreshing(true);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
        begin += 3;//adapter.getVisibleItemCount();
        end += 3;//adapter.getVisibleItemCount();
        loadNews(begin,end);
//
//            }
//        },2000);

    }




    private void loadNews(final int startup, final int endup ){
        adapter.setMoreLoading(true);
        //Toast.makeText(getBaseContext(),"startup = "+startup+ "endup = "+endup, Toast.LENGTH_SHORT).show();
        String url =APISite+"/web/v1/getnews/"+getLang()+"/"+Category+"/"+String.valueOf(startup)+"/"+String.valueOf(endup);

// Request a string response from the provided URL.
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //res.setText(response);
                        if(response!=null){

                        JSONObject jsnobject = null;
                        try {

                            jsnobject = new JSONObject(response);
                            JSONArray jsonArray = jsnobject.getJSONArray("news");
                            if(jsnobject!=null){

                            int size=jsonArray.length();
//                        res.setText(String.valueOf(size));

                            for(int i=0; i<size; i++){
                                JSONObject explrObject = jsonArray.getJSONObject(i);
                                String n_id = explrObject.getString("id");
                                String n_title = explrObject.getString("title");
                                String n_date = explrObject.getString("date");
                                String n_url = APISite+explrObject.getString("imgurl");
                                if(n_id!=null&&n_title!=null&&n_date!=null&&n_url!=null)
                                    if(n_id.length()!=0&&n_title.length()!=0&&n_date.length()!=0&&n_url.length()!=0){
                                        NewsIDs.add(n_id);
                                        NewsTitles.add(n_title);
                                        NewsDates.add(n_date);
                                        NewsImgsUrls.add(n_url);
                                    }
                            }
                            if((NewsIDs.size()==NewsTitles.size())&&
                                    (NewsTitles.size()==NewsDates.size())&&
                                    (NewsDates.size()==NewsImgsUrls.size())){
                                adapter.UpdateRecyclerViewer(NewsTitles,NewsDates,NewsImgsUrls);


//                            recyclerView.scrollToPosition(begin-1);
                            }
                            }
                            adapter.setMoreLoading(false);
                            swipeRefresh.setRefreshing(false);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            adapter.setMoreLoading(false);
//                        swipeRefresh.setRefreshing(false);
                        }

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//            res.setText("News loading is failed !");
                swipeRefresh.setRefreshing(false);
                adapter.setMoreLoading(false);
            }
        });
        MySingleton.getInstance(getActivity().getApplicationContext()).
                addToRequestQueue(stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        15000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));
    }

    private void clearalltabs(){
        NewsIDs.clear();
        NewsTitles.clear();
        NewsDates.clear();
        NewsImgsUrls.clear();
        NewsUrls.clear();
    }

}
