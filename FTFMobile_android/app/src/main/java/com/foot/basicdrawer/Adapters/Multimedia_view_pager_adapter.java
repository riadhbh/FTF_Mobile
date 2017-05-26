package com.foot.basicdrawer.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.foot.basicdrawer.Navigation.Fragments.Frag_news_List;

/**
 * Created by riadh on 4/18/2017.
 */

public class Multimedia_view_pager_adapter extends FragmentPagerAdapter {


    public Multimedia_view_pager_adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position ==0) {
            Frag_news_List frag =new Frag_news_List();

            Bundle args = new Bundle();
            args.putString("cat", "photos");
            frag.setArguments(args);

//            frag.setCategory("all");
            return frag;
        } else{
            Frag_news_List frag =new Frag_news_List();

            Bundle args = new Bundle();
            args.putString("cat", "videos");
            frag.setArguments(args);

            return frag;
        }     }

    @Override
    public int getCount() {
        return 2;
    }
}
