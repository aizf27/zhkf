package com.example.firstproject.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DoctorDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "rehab.db"; // 数据库名
    private static final int DB_VERSION = 1;

    public static final String TABLE_DOCTOR = "doctor";
    public static final String COL_ID = "id";   // 工号
    public static final String COL_PASSWORD = "password"; // 密码

    public DoctorDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_DOCTOR + " ("
                + COL_ID + " TEXT PRIMARY KEY, "
                + COL_PASSWORD + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCTOR);
        onCreate(db);
    }
}
