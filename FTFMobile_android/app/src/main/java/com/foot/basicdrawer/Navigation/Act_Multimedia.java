package com.foot.basicdrawer.Navigation;

import android.app.*;
import android.content.Intent;
import android.os.*;
import android.support.design.widget.*;
import android.support.v4.view.*;
import android.support.v4.widget.*;
import android.support.v7.app.*;
import android.view.*;

import com.foot.basicdrawer.Adapters.Multimedia_view_pager_adapter;
import com.foot.basicdrawer.R;
import com.foot.basicdrawer.Adapters.Leagues_frags_Adapter;

import static com.foot.basicdrawer.ToolBox.FrequentlyUsed.GotoAct;

/**
 * Created by riadh on 2/12/2017.
 */

public class Act_Multimedia extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mynavview;

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private Dialog LangDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multimedia);

        mDrawerLayout=(DrawerLayout)findViewById(R.id.multimedia_drawerlayout);
        viewPager = (ViewPager)findViewById(R.id.multimedia_view_pager);

        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mynavview=(NavigationView)findViewById(R.id.multimedia_nav_view);

        assert mynavview != null;

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();


        tabLayout = (TabLayout) findViewById(R.id.multimedia_tab_layout);


        tabLayout.addTab(tabLayout.newTab().setText(R.string.a_photos));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.a_videos));



        //set gravity for tab bar
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        Multimedia_view_pager_adapter pagerAdapter = new Multimedia_view_pager_adapter(getSupportFragmentManager());
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
        getSupportActionBar().setTitle(R.string.multimedia);
//        getSupportActionBar().setTitle(R.string.womensfoot);

        mynavview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            @SuppressWarnings("StatementWithEmptyBody")
            public boolean onNavigationItemSelected(MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.mNews) {
                    //Toast.makeText(getBaseContext(),"Act_News",Toast.LENGTH_SHORT).show();
                    GotoAct(Act_Multimedia.this,Act_News.class);
                    finish();
                } else if (id == R.id.mleague1) {
                    //Toast.makeText(getBaseContext(),"Leauge1",Toast.LENGTH_SHORT).show();
                    GotoAct(Act_Multimedia.this,Act_League1.class);
                    finish();
                } else if (id == R.id.mleague2) {
                    //Toast.makeText(getBaseContext(),"Leauge2",Toast.LENGTH_SHORT).show();
                    GotoAct(Act_Multimedia.this,Act_League2.class);
                    finish();
                } else if (id == R.id.mnatteam) {
                    //Toast.makeText(getBaseContext(),"Leauge3",Toast.LENGTH_SHORT).show();
                    GotoAct(Act_Multimedia.this,Act_Natteam.class);
                    finish();
                }else if (id == R.id.app_settings){
                    Intent i = new Intent(Act_Multimedia.this, Act_Settings.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.putExtra("class",Act_Multimedia.class);
                    startActivity(i);
                }
                /*
                else if (id == R.id.mleague_reg) {
                    //Toast.makeText(getBaseContext(),"LeaugeReg",Toast.LENGTH_SHORT).show();
                    GotoAct(Act_Multimedia.this,Act_Settings.class);
                    finish();
                } else if (id == R.id.mWomensFoot) {
                    Toast.makeText(getBaseContext(),R.string.womensfoot,Toast.LENGTH_SHORT).show();
                    //finish();
                }
                */
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.multimedia_drawerlayout);
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

                nobtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LangDialog.dismiss();
                    }
                });

                yesbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        if(ar_rBtn.isChecked())
                            lang="ar";
                        else if(fr_rBtn.isChecked())
                            lang="fr";
                        LanguageChanger.setLocale(getBaseContext(),lang);

//                        Toast.makeText(getBaseContext(),"Hola "+lang,Toast.LENGTH_SHORT).show();




                        //this.setContentView(R.layout.main);

                        LangDialog.dismiss();

                        Reload(Act_Multimedia.this);
                    }
                });

                LangDialog.show();
                break;
*/
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.changelang_menu, menu);
        return super.onPrepareOptionsMenu(menu);
    }
}
