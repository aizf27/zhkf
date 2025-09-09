package com.example.firstproject.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.firstproject.db.PatientDbHelper;

public class PatientDao {
    private PatientDbHelper dbHelper;

    public PatientDao(Context context) {
        dbHelper = new PatientDbHelper(context);
    }

    // 注册患者账号
    public boolean registerPatient(String id, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 检查是否已存在
        Cursor cursor = db.query(PatientDbHelper.TABLE_PATIENT,
                null,
                PatientDbHelper.COL_ID + "=?",
                new String[]{id},
                null, null, null);

        if (cursor.moveToFirst()) {
            cursor.close();
            return false; // 已存在
        }
        cursor.close();

        // 插入
        ContentValues values = new ContentValues();
        values.put(PatientDbHelper.COL_ID, id);
        values.put(PatientDbHelper.COL_PASSWORD, password);
        long result = db.insert(PatientDbHelper.TABLE_PATIENT, null, values);
        return result != -1;
    }

    // 登录检查：0=不存在，1=密码正确，2=密码错误
    public int loginPatient(String id, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(PatientDbHelper.TABLE_PATIENT,
                new String[]{PatientDbHelper.COL_PASSWORD},
                PatientDbHelper.COL_ID + "=?",
                new String[]{id},
                null, null, null);

        if (!cursor.moveToFirst()) {
            cursor.close();
            return 0; // 不存在
        }

        String dbPassword = cursor.getString(cursor.getColumnIndexOrThrow(PatientDbHelper.COL_PASSWORD));
        cursor.close();

        if (dbPassword.equals(password)) {
            return 1; // 密码正确
        } else {
            return 2; // 密码错误
        }
    }
}
