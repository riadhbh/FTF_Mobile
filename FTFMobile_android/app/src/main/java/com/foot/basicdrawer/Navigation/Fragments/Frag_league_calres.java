package com.foot.basicdrawer.Navigation.Fragments;


import android.os.*;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.*;
import android.webkit.WebView;
import android.widget.*;


import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.foot.basicdrawer.Network.MySingleton;
import com.foot.basicdrawer.R;

import java.util.ArrayList;

import static com.foot.basicdrawer.ToolBox.AppSettings.*;
import static com.foot.basicdrawer.ToolBox.FrequentlyUsed.ConfigBrowser;
import static com.foot.basicdrawer.ToolBox.LanguageChanger.*;
import static com.foot.basicdrawer.Network.Server_Host_Constant.APISite;
import static com.foot.basicdrawer.ToolBox.FrequentlyUsed.loadwebPage;

/*
*
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Frag_league_calres.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Frag_league_calres#newInstance} factory method to
 * create an instance of this fragment.
*/
public class Frag_league_calres extends Fragment {

    private static String league;
    private SwipeRefreshLayout SwipeRefresh;
    private static Spinner round_sp,group_sp;
    private static WebView content;

    private static String path;
    private static String file;



    public void setLeague(String league) {
        this.league = league;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_league_calres, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

/*
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }
*/
        SwipeRefresh=(SwipeRefreshLayout)view.findViewById(R.id.CalResRefresh);
        round_sp=(Spinner)view.findViewById(R.id.l_round_sp);
        group_sp=(Spinner)view.findViewById(R.id.l_grp_sp);
        content=(WebView)view.findViewById(R.id.l_calres_content);
//        ConfigBrowser(content,false);
        //getCurrweek();

        round_sp.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        Toast.makeText(getActivity().getBaseContext(),String.valueOf(parent.getItemAtPosition(position)),Toast.LENGTH_SHORT).show();
                        getCalresContent(String.valueOf(parent.getItemAtPosition(position)));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );

        group_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //round_sp.setAdapter(null);

                getCurrweek();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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


private  void getCurrweek(){

  String url =APISite+"/web/v1/getlastweek/"+getLang()+"/"+league+"/"+get_spgroup(group_sp);
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


private  String get_spgroup(Spinner sp){
    if(sp.getSelectedItemPosition()==0)
        return "grpa";
    else
        return "grpb";
}


private  void getCalresContent(final String week){
    // Request a string response from the provided URL.
//    content.setVisibility(View.GONE);
//    new Handler().postDelayed(new Runnable() {
//        @Override
//        public void run() {
    String group =get_spgroup(group_sp);
    path=APISite+"/data/v1/leagues/"+league+"/calres/"+group+"/"+getLang()+"/";
    file=getLang()+"_"+league+"_calres_"+group+"_"+week+".html";
    final String url=path+file;
    /*content.setWebChromeClient(new WebChromeClient() {
        public void onProgressChanged(WebView view, int progress) {



            if(progress == 100) {
                content.setVisibility(View.VISIBLE);

            }
        }
    });*/

    String html = null;
    //        html = getHtml(url);
//    html=getDoc(url).html();

//        content.loadData(html,"text/html","UTF-8");

//content.loadUrl(url);

                    loadwebPage(getActivity().getApplicationContext(),content,url);


    //String html= d.toString();
            //Toast.makeText(getActivity().getBaseContext(),html, Toast.LENGTH_LONG).show();

//        }
//        },300);


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

// TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
 /*   private static static final String ARG_PARAM1 = "param1";
    private static static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private static String mParam1;
    private static String mParam2;

    private static OnFragmentInteractionListener mListener;

    public Frag_league_calres() {
        // Required empty public constructor
    }

    *//**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Frag_league_calres.
     *//*
    // TODO: Rename and change types and number of parameters
    public static Frag_league_calres newInstance(String param1, String param2) {
        Frag_league_calres fragment = new Frag_league_calres();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_league_calres, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
/*    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
