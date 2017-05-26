package com.foot.basicdrawer.Adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.foot.basicdrawer.Navigation.Fragments.Frag_league_gen_calendar;
import com.foot.basicdrawer.Navigation.Fragments.Frag_league_calres;
import com.foot.basicdrawer.Navigation.Fragments.Frag_league_playoff_calres;
import com.foot.basicdrawer.Navigation.Fragments.Frag_league_playoff_ranking;
import com.foot.basicdrawer.Navigation.Fragments.Frag_league_playout_calres;
import com.foot.basicdrawer.Navigation.Fragments.Frag_league_playout_ranking;
import com.foot.basicdrawer.Navigation.Fragments.Frag_league_ranking;

/**
 * Created by riadh on 2/11/2017.
 */
public class Leagues_frags_Adapter extends FragmentPagerAdapter {
    private String league;

    public void setLeague(String league) {
        this.league = league;
    }



    /*    public String getLCA() {

        return LCA;
    }*/

    public Leagues_frags_Adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position ==0) {

            Frag_league_ranking frag =  new Frag_league_ranking();
            frag.setLeague(league);
            return frag;

        } else if (position == 1){
            Frag_league_calres frag = new Frag_league_calres();
            frag.setLeague(league);
            return frag;
            }else if(position == 2) {
            Frag_league_gen_calendar frag = new Frag_league_gen_calendar();
            frag.setLeague(league);
            return frag;
        } else if(position == 3) {
            Frag_league_playoff_ranking fpoff = new Frag_league_playoff_ranking();
            fpoff.setLeague(league);
            return fpoff;
        } else if(position == 4) {
            Frag_league_playoff_calres fpoff = new Frag_league_playoff_calres();
            fpoff.setLeague(league);
            return fpoff;
        } else if(position == 5) {
            Frag_league_playout_ranking fpout = new Frag_league_playout_ranking();
            fpout.setLeague(league);
            return fpout;
        } else{
            Frag_league_playout_calres fpout = new Frag_league_playout_calres();
            fpout.setLeague(league);
            return fpout;
        }
        //return null;
     }

    @Override
    public int getCount() {
        if(league=="l1")
            return 7;
        else
            return 3;
    }
}
