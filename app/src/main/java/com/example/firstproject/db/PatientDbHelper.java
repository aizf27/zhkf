package com.example.firstproject.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PatientDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "ptUser.db"; // 患者数据库
    private static final int DB_VERSION = 1;

    public static final String TABLE_PATIENT = "patient";
    public static final String COL_ID = "id";   // 手机号
    public static final String COL_PASSWORD = "password"; // 密码

    public PatientDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_PATIENT + " ("
                + COL_ID + " TEXT PRIMARY KEY, "
                + COL_PASSWORD + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATIENT);
        onCreate(db);
    }
}
