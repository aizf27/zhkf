package com.example.firstproject.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.firstproject.db.DoctorDbHelper;

public class DoctorDao {
    private DoctorDbHelper dbHelper;

    public DoctorDao(Context context) {
        dbHelper = new DoctorDbHelper(context);
    }

    // 注册医生账号
    public boolean registerDoctor(String id, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 检查是否已存在
        Cursor cursor = db.query(DoctorDbHelper.TABLE_DOCTOR,
                null,
                DoctorDbHelper.COL_ID + "=?",
                new String[]{id},
                null, null, null);

        if (cursor.moveToFirst()) {
            cursor.close();
            return false; // 已存在
        }
        cursor.close();

        // 插入
        ContentValues values = new ContentValues();
        values.put(DoctorDbHelper.COL_ID, id);
        values.put(DoctorDbHelper.COL_PASSWORD, password);
        long result = db.insert(DoctorDbHelper.TABLE_DOCTOR, null, values);
        return result != -1;
    }

    // 验证登录
    // 验证登录：返回 0=不存在，1=密码正确，2=密码错误
    public int loginDoctor(String id, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(DoctorDbHelper.TABLE_DOCTOR,
                new String[]{DoctorDbHelper.COL_PASSWORD},
                DoctorDbHelper.COL_ID + "=?",
                new String[]{id},
                null, null, null);

        if (!cursor.moveToFirst()) {
            cursor.close();
            return 0; // 用户不存在
        }

        String dbPassword = cursor.getString(cursor.getColumnIndexOrThrow(DoctorDbHelper.COL_PASSWORD));
        cursor.close();

        if (dbPassword.equals(password)) {
            return 1; // 密码正确
        } else {
            return 2; // 密码错误
        }
    }

    // 检查账号情况
// 返回值：0=不存在，1=存在且密码正确，2=存在但密码错误
    public int checkDoctor(String id, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // 查询账号是否存在
        Cursor cursor = db.query(DoctorDbHelper.TABLE_DOCTOR,
                null,
                DoctorDbHelper.COL_ID + "=?",
                new String[]{id},
                null, null, null);

        if (!cursor.moveToFirst()) {
            cursor.close();
            return 0; // 不存在
        }

        String dbPassword = cursor.getString(cursor.getColumnIndexOrThrow(DoctorDbHelper.COL_PASSWORD));
        cursor.close();

        if (dbPassword.equals(password)) {
            return 1; // 存在且密码正确
        } else {
            return 2; // 存在但密码错误
        }
    }

}
