package com.foot.basicdrawer.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.foot.basicdrawer.ToolBox.AppSettings;

import static com.foot.basicdrawer.SQLite.DatabaseContract.*;
import static com.foot.basicdrawer.ToolBox.AppSettings.*;

/**
 * Created by riadh on 12/13/2016.
 */

public class DataBaseOpenHelper extends SQLiteOpenHelper {
    public static  final int dbversion = 1;
    public static  final String dbname="FTFMobileSettings.db";

    private static final String SQLCreateTableLogin =
     "CREATE TABLE IF NOT EXISTS "+AppSettingsDB.tablename+
     " ("+AppSettingsDB._ID + " INTEGER PRIMARY KEY,"+
     AppSettingsDB.col_lang+" "+AppSettingsDB.col_lang_Type+", "+
     AppSettingsDB.col_ListType+" "+AppSettingsDB.col_ListType_Type+" "+" )";


    private static final String SQLDelteTableLogin =
  "DROP TABLE IF EXISTS " +  AppSettingsDB.tablename;

    public DataBaseOpenHelper(Context context){
        super(context , dbname, null, dbversion) ;
    }

/*
    public DataBaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
*/
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQLCreateTableLogin);

        String sql = "INSERT INTO "+AppSettingsDB.tablename+" VALUES ("+null+","+ "'"+getLang()+"' , "
                +getListType()+" );";

        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQLDelteTableLogin);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onUpgrade(sqLiteDatabase,i1,i);
    }




}
