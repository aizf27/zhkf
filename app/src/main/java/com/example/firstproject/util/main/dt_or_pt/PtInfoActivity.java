package com.example.firstproject.util.main.dt_or_pt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstproject.Dao.PatientDao;
import com.example.firstproject.bean.Patient;
import com.example.firstproject.databinding.ActivityPtInfoBinding;


public class PtInfoActivity extends AppCompatActivity {

    private ActivityPtInfoBinding binding;
    private PatientDao patientDao;
    private String account; // 从登录页传过来的患者账号（手机号）
    private String mode;
    private String doctorCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPtInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Log.d("PtInfoActivity", "onCreate");

        patientDao = new PatientDao(this);
        doctorCode = getIntent().getStringExtra("doctorCode");  //医生工号
        Log.d("PtInfoActivity", "doctorCode: " + doctorCode);
        // 获取登录页传过来患者的账号
        account = getIntent().getStringExtra("account");
        Log.d("PtInfoActivity", "account: " + account);
        //获取模式信息
        mode = getIntent().getStringExtra("mode"); // "patient" 或 "doctor"
        Log.d("PtInfoActivity", "mode: " + mode);

        loadPatientInfo();
        setModeUI();
        // 保存按钮
        binding.btnSave.setOnClickListener(v -> savePatientInfo());

    }
    private void loadPatientInfo() {
        Patient patient = patientDao.getPatientInfoByAccount(account);
        if (patient != null) {
            //基础信息
            binding.etName.setText(patient.getName());
            binding.etGender.setText(patient.getGender());
            binding.etAge.setText(String.valueOf(patient.getAge()));
            binding.etDoctorName.setText(patient.getPhysicianName());
            binding.etDoctorCode.setText(patient.getPhysicianCode());
            //康复信息
            binding.etDiagnosis.setText(patient.getDiagnosis());
            binding.etStage.setText(patient.getStage());
            binding.etProgress.setText(String.valueOf(patient.getProgress()));
            binding.etAiResult.setText(patient.getAiResult());
            binding.etLastTraining.setText(patient.getLastTrainingDate());

        }
    }
    private void savePatientInfo() {
        Patient exist = patientDao.getPatientInfoByAccount(account);

        if ("patient".equals(mode)) {
            // 患者保存基础信息
            String name = binding.etName.getText().toString().trim();
            String gender = binding.etGender.getText().toString().trim();
            String ageStr = binding.etAge.getText().toString().trim();
            String doctor = binding.etDoctorName.getText().toString().trim();
            String doctorCode = binding.etDoctorCode.getText().toString().trim();

            if (name.isEmpty() || gender.isEmpty() || ageStr.isEmpty() || doctor.isEmpty()) {
                Toast.makeText(this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                return;
            }
            int age = Integer.parseInt(ageStr);

            if (exist == null) {

                exist = new Patient(0, name, age, gender, doctor, doctorCode,
                        "无", "未设置", 0, "未分析", false, "");
                exist.setAccount(account);
                patientDao.insertPatientInfo(account, exist);
            } else {
                exist.setName(name);
                exist.setGender(gender);
                exist.setAge(age);
                exist.setPhysicianName(doctor);
                exist.setPhysicianCode(doctorCode);
                exist.setAccount(account);  //  别忘
                patientDao.updateBasicInfo( exist);
            }
            Toast.makeText(this, "信息保存成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(PtInfoActivity.this, PtMainActivity.class);
            intent.putExtra("account", account);
            startActivity(intent);
            finish();

        } else if ("doctor".equals(mode)) {
            // 医生保存康复信息
            if (exist == null) {
                Toast.makeText(this, "患者信息不存在，无法保存康复信息", Toast.LENGTH_SHORT).show();
                return;
            }

            exist.setDiagnosis(binding.etDiagnosis.getText().toString().trim());
            exist.setStage(binding.etStage.getText().toString().trim());
            exist.setProgress(Integer.parseInt(binding.etProgress.getText().toString().trim()));
            exist.setAiResult(binding.etAiResult.getText().toString().trim());
            //先暂时置为false
            exist.setHasAlert(false);
            exist.setLastTrainingDate(binding.etLastTraining.getText().toString().trim());
            exist.setAccount(account);
            patientDao.updateRehabInfo( exist);

            Toast.makeText(this, "信息保存成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(PtInfoActivity.this, DtMainActivity.class);
            intent.putExtra("doctorCode", doctorCode); // 回传要传医生工号
            intent.putExtra("account", account);
            startActivity(intent);
            finish();
        }



    }
    private void setModeUI(){
        if ("patient".equals(mode)) {
            // 患者模式：基础信息可编辑，康复信息禁用
            binding.etName.setEnabled(true);
            binding.etGender.setEnabled(true);
            binding.etAge.setEnabled(true);
            binding.etDoctorName.setEnabled(true);
            binding.etDoctorCode.setEnabled(true);

            binding.etDiagnosis.setEnabled(false);
            binding.etStage.setEnabled(false);
            binding.etProgress.setEnabled(false);
            binding.etAiResult.setEnabled(false);
            //binding.etAlert.setEnabled(false);
            binding.etLastTraining.setEnabled(false);

        } else if ("doctor".equals(mode)) {
            // 医生模式：基础信息禁用，康复信息可编辑
            binding.etName.setEnabled(false);
            binding.etGender.setEnabled(false);
            binding.etAge.setEnabled(false);
            binding.etDoctorName.setEnabled(false);
            binding.etDoctorCode.setEnabled(false);

            binding.etDiagnosis.setEnabled(true);
            binding.etStage.setEnabled(true);
            binding.etProgress.setEnabled(true);
            binding.etAiResult.setEnabled(true);
            //  binding.etAlert.setEnabled(true);
            binding.etLastTraining.setEnabled(true);
        }

    }


}
