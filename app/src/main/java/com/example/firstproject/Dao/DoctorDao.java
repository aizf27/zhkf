package com.example.firstproject.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.firstproject.bean.Doctor;
import com.example.firstproject.db.DoctorDbHelper;
import com.example.firstproject.db.PatientDbHelper;

public class DoctorDao {
    private DoctorDbHelper dbHelper;

    public DoctorDao(Context context) {
        dbHelper = new DoctorDbHelper(context);
    }

    // ==============================
    // 账号相关操作（doctor_account）
    // ==============================
    public int checkDoctor(String id, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // 查询 doctor_account 表
        Cursor cursor = db.query(
                DoctorDbHelper.TABLE_DOCTOR_ACCOUNT,
                new String[]{DoctorDbHelper.COL_ACCOUNT_PASSWORD},
                DoctorDbHelper.COL_ACCOUNT_ID + "=?",
                new String[]{id},
                null, null, null
        );

        if (!cursor.moveToFirst()) {
            cursor.close();
            return 0; // 用户不存在
        }

        // 获取数据库中密码
        String dbPassword = cursor.getString(cursor.getColumnIndexOrThrow(DoctorDbHelper.COL_ACCOUNT_PASSWORD));
        cursor.close();

        if (dbPassword.equals(password)) {
            return 1; // 存在且密码正确
        } else {
            return 2; // 存在但密码错误
        }
    }

    // 注册医生账号
    public boolean registerDoctorAccount(String id, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 检查账号是否存在
        Cursor cursor = db.query(DoctorDbHelper.TABLE_DOCTOR_ACCOUNT,
                null,
                DoctorDbHelper.COL_ACCOUNT_ID + "=?",
                new String[]{id},
                null, null, null);

        if (cursor.moveToFirst()) {
            cursor.close();
            return false; // 已存在
        }
        cursor.close();

        // 插入新账号
        ContentValues values = new ContentValues();
        values.put(DoctorDbHelper.COL_ACCOUNT_ID, id);
        values.put(DoctorDbHelper.COL_ACCOUNT_PASSWORD, password);
        return db.insert(DoctorDbHelper.TABLE_DOCTOR_ACCOUNT, null, values) != -1;
    }

    // 登录验证
    // 返回值：0=不存在，1=密码正确，2=密码错误
    public int loginDoctor(String id, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DoctorDbHelper.TABLE_DOCTOR_ACCOUNT,
                new String[]{DoctorDbHelper.COL_ACCOUNT_PASSWORD},
                DoctorDbHelper.COL_ACCOUNT_ID + "=?",
                new String[]{id},
                null, null, null);

        if (!cursor.moveToFirst()) {
            cursor.close();
            return 0; // 用户不存在
        }

        String dbPassword = cursor.getString(cursor.getColumnIndexOrThrow(DoctorDbHelper.COL_ACCOUNT_PASSWORD));
        cursor.close();

        return dbPassword.equals(password) ? 1 : 2;
    }

    // ==============================
    // 医生信息相关操作（doctor_info）
    // ==============================
    public boolean isInfoComplete(String account) {
        if (account == null || account.trim().isEmpty()) return false;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DoctorDbHelper.TABLE_DOCTOR_INFO,
                new String[]{
                        DoctorDbHelper.COL_INFO_NAME,
                        DoctorDbHelper.COL_INFO_AGE,
                        DoctorDbHelper.COL_INFO_PATIENT_COUNT
                },
                DoctorDbHelper.COL_INFO_ID + "=?",
                new String[]{account},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DoctorDbHelper.COL_INFO_NAME));
            int age = cursor.getInt(cursor.getColumnIndexOrThrow(DoctorDbHelper.COL_INFO_AGE));
            int patientCount = cursor.getInt(cursor.getColumnIndexOrThrow(DoctorDbHelper.COL_INFO_PATIENT_COUNT));
            cursor.close();

            return name != null && !name.isEmpty()
                    && age > 0
                    && patientCount >= 0;
        }

        if (cursor != null) cursor.close();
        return false;
    }


    // 添加医生信息
    public boolean addDoctorInfo(Doctor doctor) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 检查是否已存在
        Cursor cursor = db.query(DoctorDbHelper.TABLE_DOCTOR_INFO,
                null,
                DoctorDbHelper.COL_INFO_ID + "=?",
                new String[]{doctor.getId()},
                null, null, null);

        if (cursor.moveToFirst()) {
            cursor.close();
            return false; // 已存在
        }
        cursor.close();

        ContentValues values = new ContentValues();
        values.put(DoctorDbHelper.COL_INFO_ID, doctor.getId());
        values.put(DoctorDbHelper.COL_INFO_NAME, doctor.getName());
        values.put(DoctorDbHelper.COL_INFO_GENDER, doctor.getGender());
        values.put(DoctorDbHelper.COL_INFO_AGE, doctor.getAge());
        values.put(DoctorDbHelper.COL_INFO_DEPARTMENT, doctor.getDepartment());
        values.put(DoctorDbHelper.COL_INFO_PATIENT_COUNT, doctor.getPatientCount());
        return db.insert(DoctorDbHelper.TABLE_DOCTOR_INFO, null, values) != -1;
    }

    // 查询医生信息
    public Doctor getDoctorInfo(String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DoctorDbHelper.TABLE_DOCTOR_INFO,
                null,
                DoctorDbHelper.COL_INFO_ID + "=?",
                new String[]{id},
                null, null, null);

        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DoctorDbHelper.COL_INFO_NAME));
            String gender=cursor.getString(cursor.getColumnIndexOrThrow(DoctorDbHelper.COL_INFO_GENDER));
            int age = cursor.getInt(cursor.getColumnIndexOrThrow(DoctorDbHelper.COL_INFO_AGE));
            String department=cursor.getString(cursor.getColumnIndexOrThrow(DoctorDbHelper.COL_INFO_DEPARTMENT));
            int patientCount = cursor.getInt(cursor.getColumnIndexOrThrow(DoctorDbHelper.COL_INFO_PATIENT_COUNT));
            cursor.close();
            return new Doctor(id, name,gender, age,department, patientCount);
        }
        cursor.close();
        return null;
    }

    // 更新医生信息（姓名、年龄）
    public boolean updateDoctorInfo(Doctor doctor) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DoctorDbHelper.COL_INFO_NAME, doctor.getName());
        values.put(DoctorDbHelper.COL_INFO_GENDER, doctor.getGender());
        values.put(DoctorDbHelper.COL_INFO_AGE, doctor.getAge());
        values.put(DoctorDbHelper.COL_INFO_DEPARTMENT, doctor.getDepartment());
        values.put(DoctorDbHelper.COL_INFO_PATIENT_COUNT, doctor.getPatientCount());

        int rows = db.update(DoctorDbHelper.TABLE_DOCTOR_INFO,
                values,
                DoctorDbHelper.COL_INFO_ID + "=?",
                new String[]{doctor.getId()});
        return rows > 0;
    }

    // 更新医生患者数量
    public void updatePatientCount(String id, int newCount) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DoctorDbHelper.COL_INFO_PATIENT_COUNT, newCount);
        db.update(DoctorDbHelper.TABLE_DOCTOR_INFO,
                values,
                DoctorDbHelper.COL_INFO_ID + "=?",
                new String[]{id});
    }
}
