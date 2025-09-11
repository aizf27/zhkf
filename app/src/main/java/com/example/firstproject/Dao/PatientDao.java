package com.example.firstproject.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.firstproject.bean.Patient;
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
        Cursor cursor = db.query(PatientDbHelper.TABLE_ACCOUNT,
                null,
                PatientDbHelper.COL_ACCOUNT_ID + "=?",
                new String[]{id},
                null, null, null);

        if (cursor.moveToFirst()) {
            cursor.close();
            return false; // 已存在
        }
        cursor.close();

        // 插入
        ContentValues values = new ContentValues();
        values.put(PatientDbHelper.COL_ACCOUNT_ID, id);
        values.put(PatientDbHelper.COL_ACCOUNT_PASSWORD, password);
        long result = db.insert(PatientDbHelper.TABLE_ACCOUNT, null, values);
        return result != -1;
    }

    // 登录检查：0=不存在，1=密码正确，2=密码错误
    public int loginPatient(String id, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(PatientDbHelper.TABLE_ACCOUNT,
                new String[]{PatientDbHelper.COL_ACCOUNT_PASSWORD},
                PatientDbHelper.COL_ACCOUNT_ID + "=?",
                new String[]{id},
                null, null, null);

        if (!cursor.moveToFirst()) {
            cursor.close();
            return 0; // 不存在
        }

        String dbPassword = cursor.getString(cursor.getColumnIndexOrThrow(PatientDbHelper.COL_ACCOUNT_PASSWORD));
        cursor.close();

        if (dbPassword.equals(password)) {
            return 1; // 密码正确
        } else {
            return 2; // 密码错误
        }
    }

    // 插入患者详细信息
    public long insertPatientInfo(Patient patient) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PatientDbHelper.COL_NAME, patient.getName());
        values.put(PatientDbHelper.COL_AGE, patient.getAge());
        values.put(PatientDbHelper.COL_GENDER, patient.getGender());
        values.put(PatientDbHelper.COL_PHYSICIAN, patient.getMyPhysician());
        values.put(PatientDbHelper.COL_DIAGNOSIS, patient.getDiagnosis());
        values.put(PatientDbHelper.COL_STAGE, patient.getStage());
        values.put(PatientDbHelper.COL_PROGRESS, patient.getProgress());
        values.put(PatientDbHelper.COL_AI_RESULT, patient.getAiResult());
        values.put(PatientDbHelper.COL_HAS_ALERT, patient.isHasAlert() ? 1 : 0);
        values.put(PatientDbHelper.COL_LAST_TRAINING_DATE, patient.getLastTrainingDate());

        return db.insert(PatientDbHelper.TABLE_INFO, null, values);
    }

    // 根据患者 id 查询信息
    public Patient getPatientInfoById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(PatientDbHelper.TABLE_INFO,
                null,
                PatientDbHelper.COL_INFO_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Patient patient = new Patient(
                    cursor.getInt(cursor.getColumnIndexOrThrow(PatientDbHelper.COL_INFO_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(PatientDbHelper.COL_NAME)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(PatientDbHelper.COL_AGE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(PatientDbHelper.COL_GENDER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(PatientDbHelper.COL_PHYSICIAN)),
                    cursor.getString(cursor.getColumnIndexOrThrow(PatientDbHelper.COL_DIAGNOSIS)),
                    cursor.getString(cursor.getColumnIndexOrThrow(PatientDbHelper.COL_STAGE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(PatientDbHelper.COL_PROGRESS)),
                    cursor.getString(cursor.getColumnIndexOrThrow(PatientDbHelper.COL_AI_RESULT)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(PatientDbHelper.COL_HAS_ALERT)) == 1,
                    cursor.getString(cursor.getColumnIndexOrThrow(PatientDbHelper.COL_LAST_TRAINING_DATE))
            );
            cursor.close();
            return patient;
        }
        if (cursor != null) cursor.close();
        return null;
    }
}
