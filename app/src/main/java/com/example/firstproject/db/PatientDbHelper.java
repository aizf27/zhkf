package com.example.firstproject.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PatientDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "ptUser.db"; // 患者数据库
    private static final int DB_VERSION = 2; // 升级数据库版本

    //  表名
    public static final String TABLE_ACCOUNT = "patient_account";
    public static final String TABLE_INFO = "patient_info";

    //  patient_account 表字段
    public static final String COL_ACCOUNT_ID = "id";       // 手机号
    public static final String COL_ACCOUNT_PASSWORD = "password"; // 密码

    // ---------- patient_info 表字段 ----------
    public static final String COL_INFO_ID = "id";             // 主键（自增）
    public static final String COL_NAME = "name";              // 姓名
    public static final String COL_AGE = "age";                // 年龄
    public static final String COL_GENDER = "gender";          // 性别
    public static final String COL_PHYSICIAN = "myPhysician";  // 主治医师
    public static final String COL_DIAGNOSIS = "diagnosis";    // 诊断/病情描述
    public static final String COL_STAGE = "stage";            // 康复阶段
    public static final String COL_PROGRESS = "progress";      // 康复进度
    public static final String COL_AI_RESULT = "aiResult";     // AI 分析结果
    public static final String COL_HAS_ALERT = "hasAlert";     // 异常预警（0/1）
    public static final String COL_LAST_TRAINING_DATE = "lastTrainingDate"; // 最近训练日期

    public PatientDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // ---------- 创建 patient_account ----------
        String createAccountTable = "CREATE TABLE " + TABLE_ACCOUNT + " ("
                + COL_ACCOUNT_ID + " TEXT PRIMARY KEY, "
                + COL_ACCOUNT_PASSWORD + " TEXT)";
        db.execSQL(createAccountTable);

        // ---------- 创建 patient_info ----------
        String createInfoTable = "CREATE TABLE " + TABLE_INFO + " ("
                + COL_INFO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "account TEXT, "       // 新增账号列
                + COL_NAME + " TEXT, "
                + COL_AGE + " INTEGER, "
                + COL_GENDER + " TEXT, "
                + COL_PHYSICIAN + " TEXT, "
                + COL_DIAGNOSIS + " TEXT, "
                + COL_STAGE + " TEXT, "
                + COL_PROGRESS + " INTEGER, "
                + COL_AI_RESULT + " TEXT, "
                + COL_HAS_ALERT + " INTEGER DEFAULT 0, "
                + COL_LAST_TRAINING_DATE + " TEXT)";
        db.execSQL(createInfoTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 升级时删除旧表（如果有），重新创建
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INFO);
        onCreate(db);
    }
}
