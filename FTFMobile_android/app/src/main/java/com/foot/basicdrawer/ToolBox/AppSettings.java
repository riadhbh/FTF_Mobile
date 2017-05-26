package com.foot.basicdrawer.ToolBox;

import android.content.Context;

import static com.foot.basicdrawer.SQLite.DB_Queries.update_DB_Lang;

/**
 * Created by riadh on 4/18/2017.
 */

public class AppSettings {
private static String lang="fr";
private static int ListType=0;

    public static String getLang() {
        return lang;
    }

    public static void setLang(String lang) {
        AppSettings.lang = lang;

    }

    public static int getListType() {
        return ListType;
    }

    public static void setListType(int listType) {
        ListType = listType;
    }
}
