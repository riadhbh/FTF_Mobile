package com.foot.basicdrawer.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.foot.basicdrawer.Navigation.Fragments.Frag_news_List;


/**
 * Created by riadh on 4/14/2017.
 */

public class News_list_view_pager_adapter extends FragmentPagerAdapter {


    public News_list_view_pager_adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position ==0) {
            Frag_news_List frag =new Frag_news_List();

            Bundle args = new Bundle();
            args.putString("cat", "all");
            frag.setArguments(args);

//            frag.setCategory("all");
            return frag;
        } else if (position == 1){
            Frag_news_List frag =new Frag_news_List();

            Bundle args = new Bundle();
            args.putString("cat", "l1");
            frag.setArguments(args);

            return frag;
        } else if(position == 2) {
            Frag_news_List frag =new Frag_news_List();

            Bundle args = new Bundle();
            args.putString("cat", "l2");
            frag.setArguments(args);

            return frag;
        } else if(position == 3) {
            Frag_news_List frag =new Frag_news_List();

            Bundle args = new Bundle();
            args.putString("cat", "l3");
            frag.setArguments(args);

            return frag;
        }else if (position == 4){
            Frag_news_List frag =new Frag_news_List();

            Bundle args = new Bundle();
            args.putString("cat", "ntd");
            frag.setArguments(args);

            return frag;
        }else{
            Frag_news_List frag =new Frag_news_List();

            Bundle args = new Bundle();
            args.putString("cat", "ad");
            frag.setArguments(args);

            return frag;
        }
        /*else if(position ==5){
            Frag_news_List frag =new Frag_news_List();

            Bundle args = new Bundle();
            args.putString("cat", "photos");
            frag.setArguments(args);

            return frag;
        }else{
            Frag_news_List frag =new Frag_news_List();

            Bundle args = new Bundle();
            args.putString("cat", "videos");
            frag.setArguments(args);

            return frag;

        }*/
//        return null;
    }

    @Override
    public int getCount() {
         return 6;
    }
}