package com.foot.basicdrawer.Navigation;

import android.app.*;
import android.content.Intent;
import android.os.*;
import android.support.design.widget.*;
import android.support.v4.view.*;
import android.support.v4.widget.*;
import android.support.v7.app.*;

import android.view.*;
import android.view.animation.Animation;


import com.foot.basicdrawer.ToolBox.AppSettings.*;
import com.foot.basicdrawer.Adapters.News_list_view_pager_adapter;

import com.foot.basicdrawer.R;


import java.io.Serializable;

import static com.foot.basicdrawer.ToolBox.FrequentlyUsed.GotoAct;

@SuppressWarnings("deprecation")
public class Act_News extends AppCompatActivity{


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mynavview;



    private Dialog LangDialog;

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        mDrawerLayout=(DrawerLayout)findViewById(R.id.news_drawerlayout);
        mynavview=(NavigationView)findViewById(R.id.news_nav_view);
        mynavview=(NavigationView)findViewById(R.id.news_nav_view);


/*        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);*/

        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        viewPager = (ViewPager)findViewById(R.id.news_view_pager);
        tabLayout = (TabLayout) findViewById(R.id.news_tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.allnews));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.league1));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.league2));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.league3));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.ntd));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.referees_choices));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.a_photos));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.a_videos));




        //set gravity for tab bar
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

       News_list_view_pager_adapter pagerAdapter = new News_list_view_pager_adapter(getSupportFragmentManager());

        viewPager.setAdapter(pagerAdapter);


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //change ViewPager page when tab selected
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(final TabLayout.Tab tab) {

                        viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.news);

        mynavview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            @SuppressWarnings("StatementWithEmptyBody")
            public boolean onNavigationItemSelected(MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.mNews) {

                } else if (id == R.id.mleague1) {

                    GotoAct(Act_News.this,Act_League1.class);

//                    finish();

                } else if (id == R.id.mleague2) {
                    GotoAct(Act_News.this,Act_League2.class);

//                    finish();
                }else if (id == R.id.m_multimedia){
                    GotoAct(Act_News.this,Act_Multimedia.class);

                }else if (id == R.id.mnatteam) {
                    GotoAct(Act_News.this,Act_Natteam.class);
//                    finish();
                }else if (id == R.id.app_settings){
                    Intent i = new Intent(Act_News.this, Act_Settings.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.putExtra("class", (Serializable) Act_News.class);
                    startActivity(i);
                    finish();
                }
                /*
                else if (id == R.id.mleague_reg) {
                    GotoAct(Act_News.this,Act_Settings.class);
                    finish();
                } else if (id == R.id.mWomensFoot) {

                    GotoAct(Act_News.this,Act_Multimedia.class);
                    finish();
                }*/

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.news_drawerlayout);
                drawer.closeDrawer(GravityCompat.START);

                return  true;
            }
        });





            }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       if(mToggle.onOptionsItemSelected(item)){
           return true;
       }



        switch (item.getItemId()) {
/*            case R.id.langchanger:
                LangDialog = new Dialog(this);
                LangDialog.setContentView(R.layout.diag_changelang);
                LangDialog.setTitle(R.string.lanChDiagTitle);

                // set the custom dialog components - text, image and button
                final RadioGroup LangsGroup = (RadioGroup) LangDialog.findViewById(R.id.setOfLangs);
                final RadioButton ar_rBtn, fr_rBtn;
                ar_rBtn=(RadioButton)LangsGroup.findViewById(R.id.ar_radio_choice);
                fr_rBtn=(RadioButton)LangsGroup.findViewById(R.id.fr_radio_choice);

                Button yesbtn = (Button) LangDialog.findViewById(R.id.langdiag_btn_yes);
                Button nobtn = (Button) LangDialog.findViewById(R.id.langdiag_btn_no);
                if(lang.equals("fr"))
                fr_rBtn.setChecked(true);
                else
                ar_rBtn.setChecked(true);
                // if button is clicked, close the custom dialog
                nobtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LangDialog.dismiss();
                    }
                });

                yesbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        String currentlang=lang;

                        if(ar_rBtn.isChecked())
                            lang="ar";
                        else if(fr_rBtn.isChecked())
                            lang="fr";

                        if(lang!=currentlang){
                            LanguageChanger.setLocale(getBaseContext(),lang);
                        }

                        LangDialog.dismiss();

                        Reload(Act_News.this);
                    }
                });

                LangDialog.show();
                break;
*/
        }

            return super.onOptionsItemSelected(item);

    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.changelang_menu, menu);
        return super.onPrepareOptionsMenu(menu);
    }*/


//    @Override
//    protected void onResume() {
//        super.onResume();
//    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

}

