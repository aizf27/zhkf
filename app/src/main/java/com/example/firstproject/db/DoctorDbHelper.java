package com.example.firstproject.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DoctorDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "rehab.db"; // 数据库名
    private static final int DB_VERSION = 2;

    // 表名
    public static final String TABLE_DOCTOR_ACCOUNT = "doctor_account";
    public static final String TABLE_DOCTOR_INFO = "doctor_info";

    // doctor_account 列
    public static final String COL_ACCOUNT_ID = "id";       // 工号
    public static final String COL_ACCOUNT_PASSWORD = "password"; // 密码

    // doctor_info 列
    public static final String COL_INFO_ID = "id";         // 工号（主键，对应账号表）
    public static final String COL_INFO_NAME = "name";     // 姓名
    public static final String COL_INFO_GENDER = "gender";
    public static final String COL_INFO_AGE = "age";       // 年龄
    public static final String COL_INFO_DEPARTMENT = "department";
    public static final String COL_INFO_PATIENT_COUNT = "patient_count"; // 当前患者数

    public DoctorDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建医生账号表
        String createAccountTable = "CREATE TABLE " + TABLE_DOCTOR_ACCOUNT + " ("
                + COL_ACCOUNT_ID + " TEXT PRIMARY KEY, "
                + COL_ACCOUNT_PASSWORD + " TEXT NOT NULL)";
        db.execSQL(createAccountTable);

        // 创建医生信息表
        String createInfoTable = "CREATE TABLE " + TABLE_DOCTOR_INFO + " ("
                + COL_INFO_ID + " TEXT PRIMARY KEY, "
                + COL_INFO_NAME + " TEXT NOT NULL, "
                + COL_INFO_GENDER + " TEXT, "
                + COL_INFO_AGE + " INTEGER, "
                + COL_INFO_DEPARTMENT + " TEXT,"
                + COL_INFO_PATIENT_COUNT + " INTEGER DEFAULT 0)";
        db.execSQL(createInfoTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // 增加 gender 和 department 两列
            db.execSQL("ALTER TABLE " + TABLE_DOCTOR_INFO + " ADD COLUMN gender TEXT");
            db.execSQL("ALTER TABLE " + TABLE_DOCTOR_INFO + " ADD COLUMN department TEXT");
        }
        onCreate(db);
    }
}
