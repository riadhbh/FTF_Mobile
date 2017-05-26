package com.foot.basicdrawer.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.foot.basicdrawer.Navigation.Fragments.Frag_natteam_appointments;
import com.foot.basicdrawer.Navigation.Fragments.Frag_natteam_events;
import com.foot.basicdrawer.Navigation.Fragments.Frag_news_List;

/**
 * Created by riadh on 4/17/2017.
 */

public class Nat_Team_Adapter extends FragmentPagerAdapter {


    public Nat_Team_Adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position ==0)
            return new Frag_natteam_events();
        else
            return new Frag_natteam_appointments();
    }

    @Override
    public int getCount() {
        return 2;
    }
}
