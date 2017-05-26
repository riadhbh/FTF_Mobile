package com.foot.basicdrawer.Navigation.Fragments;
import android.os.*;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.*;
import android.webkit.*;
import android.widget.*;

import com.foot.basicdrawer.R;

import static com.foot.basicdrawer.ToolBox.LanguageChanger.*;
import static com.foot.basicdrawer.Network.Server_Host_Constant.APISite;
import static com.foot.basicdrawer.ToolBox.FrequentlyUsed.ConfigBrowser;
import static com.foot.basicdrawer.ToolBox.FrequentlyUsed.loadwebPage;
import static com.foot.basicdrawer.ToolBox.AppSettings.*;

/*
*
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Frag_league_ranking.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Frag_league_ranking#newInstance} factory method to
 * create an instance of this fragment.
*/

public class Frag_league_ranking extends Fragment {


    private static String league;
    public  void setLeague(String league) {
        this.league = league;
    }

    private static String path;
    private static String file;


    private WebView rankingwv;
    private Spinner groupsp;
    private SwipeRefreshLayout SwipeRefresh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_league_ranking, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SwipeRefresh=(SwipeRefreshLayout)view.findViewById(R.id.RankingRefresh);
        rankingwv= (WebView)view.findViewById(R.id.league_rankingwv);
        groupsp=(Spinner)view.findViewById(R.id.l_rk_grp_sp);
        ConfigBrowser(rankingwv,false);


        groupsp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
    path=APISite+"/data/v1/leagues/"+league+"/ranking/"+league+"/"+getLang()+"/";
    file=getLang()+"_"+league+"_rk_c"+group+".html";

//    new Thread(new Runnable() {
//    @Override
//    public void run() {

        loadwebPage(getActivity().getApplicationContext(),rankingwv,path+file);
//    }
//    }).start();

}

    private  String get_spgroup(){
        if(groupsp.getSelectedItemPosition()==0)
            return "a";
        else
            return "b";
    }



/*    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Frag_league_ranking() {
        // Required empty public constructor
    }

    *//**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Frag_league_ranking.
     *//*
    // TODO: Rename and change types and number of parameters
    public static Frag_league_ranking newInstance(String param1, String param2) {
        Frag_league_ranking fragment = new Frag_league_ranking();
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
        return inflater.inflate(R.layout.fragment_league_ranking, container, false);
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
    }

    *//**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     *//*
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/

}
