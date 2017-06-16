package com.foot.basicdrawer.ToolBox;

import android.content.Context;

import static com.foot.basicdrawer.SQLite.DB_Queries.*;

/**
 * Created by riadh on 4/18/2017.
 */

public class AppSettings {
private static String lang="fr";
private static int ListType=0;

    public static String getLang() {
        return lang;
    }

    public static void getLang_controller(Context ctxt) {
        load_DB_Lang(ctxt);
        LanguageChanger.setLocale(ctxt,getLang());
    }


    public static void setLang(String lang) {
        AppSettings.lang = lang;
    }

    public static void setLang_controller(Context ctxt,String lang) {
        AppSettings.lang = lang;
        update_DB_Lang(ctxt);
        LanguageChanger.setLocale(ctxt,lang);
    }

    public static int getListType() {
        return ListType;
    }
    public static void getListType_controller(Context ctxt) {
        load_DB_ListType(ctxt);
    }


    public static void setListType(int listType) {
        AppSettings.ListType = listType;
    }
    public static void setListType_controller(Context ctxt,int listType) {
        AppSettings.ListType = listType;
        update_DB_ListType(ctxt);
    }
}
