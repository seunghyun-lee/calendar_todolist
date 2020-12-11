package com.kerberos.todoit.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kerberos.todoit.ui.home.HomeFragment;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "todoit.db";
    public static final String TABLE_NAME_TODOITEM = "TODOITEM";


    private static final String CREATE_TABLE_TODOITEM =
            "CREATE TABLE " + TABLE_NAME_TODOITEM +
                    "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "item TEXT, " +
                    "item_category TEXT, " +
                    "start_at TEXT," +
                    "end_at TEXT," +
                    "dayortime TEXT," +
                    "location TEXT," +
                    "alarm TEXT," +
                    "alarm_loop TEXT," +
                    "email TEXT," +
                    "memo TEXT," +
                    "create_at TEXT);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TODOITEM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL();

    }
}
