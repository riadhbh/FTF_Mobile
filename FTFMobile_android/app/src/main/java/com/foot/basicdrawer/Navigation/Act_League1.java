package com.foot.basicdrawer.Navigation;

import android.app.*;
import android.os.*;
import android.support.design.widget.*;
import android.support.v4.view.*;
import android.support.v4.widget.*;
import android.support.v7.app.*;
import android.view.*;
import android.widget.*;

import com.foot.basicdrawer.Adapters.Leagues_frags_Adapter;
import com.foot.basicdrawer.R;

import static com.foot.basicdrawer.ToolBox.FrequentlyUsed.GotoAct;

public class Act_League1 extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mynavview;

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private Dialog LangDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league1);

        mDrawerLayout=(DrawerLayout)findViewById(R.id.league1_drawerlayout);
        viewPager = (ViewPager)findViewById(R.id.league1_view_pager);

        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mynavview=(NavigationView)findViewById(R.id.league1_nav_view);

        assert mynavview != null;

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();


        tabLayout = (TabLayout) findViewById(R.id.league1_tab_layout);


        tabLayout.addTab(tabLayout.newTab().setText(R.string.ranking));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.calres));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.gen_calendar));

        tabLayout.addTab(tabLayout.newTab().setText(R.string.poff_ranking));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.poff_calres));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.pout_ranking));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.pout_calres));



        //set gravity for tab bar
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        Leagues_frags_Adapter pagerAdapter = new Leagues_frags_Adapter(getSupportFragmentManager());
//        pagerAdapter.setLCA(APISite+"/data/v1/leagues/l1/rk/"+lang+"_l1_rk_ca.html");
//        pagerAdapter.setLCB(APISite+"/data/v1/leagues/l1/rk/"+lang+"_l1_rk_cb.html");
        pagerAdapter.setLeague("l1");
        viewPager.setAdapter(pagerAdapter);


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //change ViewPager page when tab selected
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
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
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setTitle(R.string.league1);

        mynavview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            @SuppressWarnings("StatementWithEmptyBody")
            public boolean onNavigationItemSelected(MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.mNews) {
//                    Toast.makeText(getBaseContext(),"Act_News",Toast.LENGTH_SHORT).show();
                    GotoAct(Act_League1.this,Act_News.class);
                    finish();
                } else if (id == R.id.mleague1) {
                    Toast.makeText(getBaseContext(),R.string.league1,Toast.LENGTH_SHORT).show();
                    //GotoAct(Act_League1.this,Act_League1.class);
                    //finish();
                } else if (id == R.id.mleague2) {
                    //Toast.makeText(getBaseContext(),"Leauge2",Toast.LENGTH_SHORT).show();
                    GotoAct(Act_League1.this,Act_League2.class);
                    finish();
                }else if (id == R.id.m_multimedia){
                    GotoAct(Act_League1.this,Act_Multimedia.class);
                }else if (id == R.id.mnatteam) {
                    //Toast.makeText(getBaseContext(),"Leauge3",Toast.LENGTH_SHORT).show();
                    GotoAct(Act_League1.this,Act_Natteam.class);
                    finish();
                }else if (id == R.id.app_settings){
                    GotoAct(Act_League1.this,Act_Settings.class);
                }
                /*
                else if (id == R.id.mleague_reg) {
//                    Toast.makeText(getBaseContext(),"LeaugeReg",Toast.LENGTH_SHORT).show();
                    GotoAct(Act_League1.this,Act_Settings.class);
                    finish();
                } else if (id == R.id.mWomensFoot) {
//                    Toast.makeText(getBaseContext(),"WomensFoot",Toast.LENGTH_SHORT).show();
                    GotoAct(Act_League1.this,Act_Multimedia.class);
                    finish();
                }*/

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.league1_drawerlayout);
                drawer.closeDrawer(GravityCompat.START);

                return  true;
            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //change ViewPager page when tab selected
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }



        switch (item.getItemId()) {
            /*case R.id.langchanger:
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

//                        Toast.makeText(getBaseContext(),"Hola "+lang,Toast.LENGTH_SHORT).show();




                        //this.setContentView(R.layout.main);

                        LangDialog.dismiss();

                        Reload(Act_League1.this);
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
    }
*/
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
/*
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mynavview;

    private Dialog LangDialog;
    private String lang="fr";

    */
/**
     * The {@link ViewPager} that will host the section contents.
     *//*

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        mDrawerLayout=(DrawerLayout)findViewById(R.id.league1_drawerlayout);
        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mynavview=(NavigationView)findViewById(R.id.league1_nav_view);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.changelang_menu, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

*/
/**
 * A placeholder fragment containing a simple view.
 *//*

public static class PlaceholderFragment extends Fragment {
    */
/**
     * The fragment argument representing the section number for this
     * fragment.
     *//*

    private static final String ARG_SECTION_NUMBER = "section_number";

    public PlaceholderFragment() {
    }

    */
/**
     * Returns a new instance of this fragment for the given section
     * number.
     *//*

    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_league, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        return rootView;
    }
}

*/
/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 *//*

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "SECTION 1";
            case 1:
                return "SECTION 2";
//                case 2:
//                    return "SECTION 3";
        }
        return null;
    }
}*/
