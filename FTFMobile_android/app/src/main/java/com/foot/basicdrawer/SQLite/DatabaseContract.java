package com.foot.basicdrawer.SQLite;

import android.provider.BaseColumns;

/**
 * Created by riadh on 12/13/2016.
 */

public class DatabaseContract {
    public interface AppSettingsDB extends BaseColumns{
        String tablename = "Settings";

        String col_lang="Lang";
        String col_lang_Type="TEXT";

        String col_ListType="ListType";
        String col_ListType_Type="INT";
    }
}
