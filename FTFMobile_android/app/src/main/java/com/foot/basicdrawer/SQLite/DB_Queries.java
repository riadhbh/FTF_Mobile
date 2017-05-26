package com.foot.basicdrawer.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;

import static com.foot.basicdrawer.ToolBox.AppSettings.*;
import static com.foot.basicdrawer.ToolBox.LanguageChanger.*;
import static com.foot.basicdrawer.SQLite.DatabaseContract.*;
/**
 * Created by riadh on 3/19/2017.
 */

public class DB_Queries {

    public static void load_DB_Lang(Context ctxt){
        DataBaseOpenHelper dbhlp =new DataBaseOpenHelper(ctxt);

        SQLiteDatabase database = dbhlp.getReadableDatabase();
        Cursor cursor= database.query(AppSettingsDB.tablename, null,null,
                new String[]{},null,null,null);

        if (cursor.moveToFirst()){
            //do{
            setLang(cursor.getString(cursor.getColumnIndex(AppSettingsDB.col_lang)));
            // do what ever you want here
            //}while(cursor.moveToNext());
        }
        cursor.close();
    }


    public static void load_DB_ListType(Context ctxt){
        DataBaseOpenHelper dbhlp =new DataBaseOpenHelper(ctxt);

        SQLiteDatabase database = dbhlp.getReadableDatabase();
        Cursor cursor= database.query(AppSettingsDB.tablename, null,null,
                new String[]{},null,null,null);

        if (cursor.moveToFirst()){
            //do{
            setListType(Integer.parseInt(cursor.getString(cursor.getColumnIndex(AppSettingsDB.col_ListType))));
            // do what ever you want here
            //}while(cursor.moveToNext());
        }
        cursor.close();
    }


    public static void update_DB_Lang(Context ctxt){


        DataBaseOpenHelper dbhlp =new DataBaseOpenHelper(ctxt);
        SQLiteDatabase database=dbhlp.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AppSettingsDB.col_lang,getLang());
        database.update(AppSettingsDB.tablename,values,null,null);
    }


    public static void update_DB_ListType(Context ctxt){


        DataBaseOpenHelper dbhlp =new DataBaseOpenHelper(ctxt);
        SQLiteDatabase database=dbhlp.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AppSettingsDB.col_ListType,getListType());
        database.update(AppSettingsDB.tablename,values,null,null);
    }
}
