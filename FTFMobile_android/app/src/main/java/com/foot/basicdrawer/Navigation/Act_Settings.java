package com.foot.basicdrawer.Navigation;

import android.app.*;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.*;
import android.support.design.widget.*;
import android.support.v4.view.*;
import android.support.v4.widget.*;
import android.support.v7.app.*;
import android.view.*;
import android.widget.*;

import com.foot.basicdrawer.R;
import com.foot.basicdrawer.Adapters.Leagues_frags_Adapter;
import com.foot.basicdrawer.ToolBox.LanguageChanger;

import java.util.ArrayList;

import static com.foot.basicdrawer.SQLite.DB_Queries.update_DB_Lang;
import static com.foot.basicdrawer.SQLite.DB_Queries.update_DB_ListType;
import static com.foot.basicdrawer.ToolBox.AppSettings.*;
import static com.foot.basicdrawer.ToolBox.FrequentlyUsed.*;


/**
 * Created by riadh on 2/12/2017.
 */

public class Act_Settings extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mynavview;
    private TextView tv_listType;

    private Class previous;
    private LinearLayout s_lang, s_ltype;
    private int CurrLisType=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        previous= (Class) getIntent().getSerializableExtra("class");
        mDrawerLayout=(DrawerLayout)findViewById(R.id.settings_drawerlayout);
        tv_listType=(TextView)findViewById(R.id.s_curr_list_type);

        s_lang=(LinearLayout)findViewById(R.id.s_lang_l);
        s_ltype=(LinearLayout)findViewById(R.id.s_ltype_l);

//        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mynavview=(NavigationView)findViewById(R.id.settings_nav_view);

        assert mynavview != null;

        CurrLisType=getListType();
        if(CurrLisType==0)
            tv_listType.setText(String.format("%1$s", getString(R.string.card_format)));
        else
            tv_listType.setText(String.format("%1$s", getString(R.string.list_format)));

//        mDrawerLayout.addDrawerListener(mToggle);
//        mToggle.syncState();





        //set gravity for tab bar


        Leagues_frags_Adapter pagerAdapter = new Leagues_frags_Adapter(getSupportFragmentManager());


//        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.settings);

//        getSupportActionBar().setTitle(R.string.leaguereg);

        mynavview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            @SuppressWarnings("StatementWithEmptyBody")
            public boolean onNavigationItemSelected(MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.mNews) {
                    //Toast.makeText(getBaseContext(),"Act_News",Toast.LENGTH_SHORT).show();
                    GotoAct(Act_Settings.this,Act_News.class);
//                    finish();
                } else if (id == R.id.mleague1) {
                    //Toast.makeText(getBaseContext(),"Leauge1",Toast.LENGTH_SHORT).show();
                    GotoAct(Act_Settings.this,Act_League1.class);
//                    finish();
                } else if (id == R.id.mleague2) {
                    //Toast.makeText(getBaseContext(),"Leauge2",Toast.LENGTH_SHORT).show();
                    GotoAct(Act_Settings.this,Act_League2.class);
//                    finish();
                }else if (id == R.id.m_multimedia){
                    GotoAct(Act_Settings.this,Act_Multimedia.class);
                }
                else if (id == R.id.mnatteam) {
                    //Toast.makeText(getBaseContext(),"Leauge3",Toast.LENGTH_SHORT).show();
                    GotoAct(Act_Settings.this,Act_Natteam.class);
//                    finish();
                }else if (id == R.id.app_settings){
                    Toast.makeText(getBaseContext(),R.string.settings,Toast.LENGTH_SHORT).show();
                }
                /*
                else if (id == R.id.mleague_reg) {
                    Toast.makeText(getBaseContext(),R.string.leaguereg,Toast.LENGTH_SHORT).show();
                    //finish();
                } else if (id == R.id.mWomensFoot) {
//                    Toast.makeText(getBaseContext(),"WomensFoot",Toast.LENGTH_SHORT).show();
                    GotoAct(Act_Settings.this,Act_Multimedia.class);
                    finish();
                }*/

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.settings_drawerlayout);
                drawer.closeDrawer(GravityCompat.START);

                return  true;
            }
        });

        s_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LangDiag();
            }
        });

        s_ltype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LisTypeDiag();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if(mToggle.onOptionsItemSelected(item)){
//            return true;
//        }
//


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

                        Reload(Act_Settings.this);
                    }
                });

                LangDialog.show();
                break;
*/

            case android.R.id.home:
                onBackPressed();


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

private void LisTypeDiag(){
    CurrLisType=getListType();
    final int Old_ListTypeIndex = CurrLisType;

    final String[] items = {String.format("%1$s", getString(R.string.card_format)), String.format("%1$s", getString(R.string.list_format))};
// arraylist to keep the selected items
    final ArrayList seletedItems=new ArrayList();
    AlertDialog.Builder builder2 = new AlertDialog.Builder(this)
            .setTitle(R.string._list_format)
            .setSingleChoiceItems(items, CurrLisType, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if(which!=Old_ListTypeIndex){
                        setListType_controller(getBaseContext(),which);

                        tv_listType.setText(items[which]);
                        /*
                        String lang="fr";
                        if(which==0)
                            lang="ar";

                        setLang(lang);
                        update_DB_Lang(getBaseContext());
                        LanguageChanger.setLocale(getBaseContext(),lang);
                        Reload(Act_Settings.this);
                        */
                    }
//dismissing the dialog when the user makes a selection.
                    dialog.dismiss();
                }
//            })
// .setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
////                    Toast.makeText(getBaseContext(),"ok",Toast.LENGTH_LONG).show();
//                }
            }).setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
//                    Toast.makeText(getBaseContext(),"cancel",Toast.LENGTH_LONG).show();
                }
            });
builder2.create().show();
}


    private void LangDiag(){
        int CurrLangIndex=1;
        if(getLang().equals("ar"))
            CurrLangIndex=0;

        final int Old_langIndex = CurrLangIndex;

        final CharSequence[] items = {"العربية","Français"};
// arraylist to keep the selected items
        final ArrayList seletedItems=new ArrayList();
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this)
                .setTitle(R.string.lanChDiagTitle)
                .setSingleChoiceItems(items, CurrLangIndex, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(which!=Old_langIndex){
                            String lang="fr";
                            if(which==0)
                                lang="ar";

                            setLang_controller(getBaseContext(),lang);

                            final Handler start = new Handler();

/*                            start.postDelayed(new Runnable() {
                                @Override
                                public void run() {*/
                                    Reload(Act_Settings.this);
  /*                              }
                            }, 500);
*/
                        }
//dismissing the dialog when the user makes a selection.
                        dialog.dismiss();
                    }
//            })
// .setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
////                    Toast.makeText(getBaseContext(),"ok",Toast.LENGTH_LONG).show();
//                }
                }).setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
//                    Toast.makeText(getBaseContext(),"cancel",Toast.LENGTH_LONG).show();
                    }
                });
        builder2.create().show();
    }



    @Override
    public void onBackPressed() {
        GotoAct(Act_Settings.this,previous);
//        super.onBackPressed();
    }
}
