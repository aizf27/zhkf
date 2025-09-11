package com.example.firstproject.util.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstproject.Dao.PatientDao;
import com.example.firstproject.bean.Patient;
import com.example.firstproject.databinding.ActivityPtInfoBinding;


public class PtInfoActivity extends AppCompatActivity {

    private ActivityPtInfoBinding binding;
    private PatientDao patientDao;
    private String account; // 从登录页传过来的账号（手机号）


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPtInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        patientDao = new PatientDao(this);

        // 获取登录页传过来的账号
        account = getIntent().getStringExtra("account");
        // 加载已有信息（如果有的话）
        loadPatientInfo();
        // 保存按钮
        binding.btnSave.setOnClickListener(v -> savePatientInfo());
    }
    private void loadPatientInfo() {
        Patient patient = patientDao.getPatientInfoByAccount(account);
        if (patient != null) {
            binding.etName.setText(patient.getName());
            binding.etGender.setText(patient.getGender());
            binding.etAge.setText(String.valueOf(patient.getAge()));
            binding.etDoctor.setText(patient.getMyPhysician());
        }
    }
    private void savePatientInfo() {
        String name = binding.etName.getText().toString().trim();
        String gender = binding.etGender.getText().toString().trim();
        String ageStr = binding.etAge.getText().toString().trim();
        String doctor = binding.etDoctor.getText().toString().trim();

        if (name.isEmpty() || gender.isEmpty() || ageStr.isEmpty() || doctor.isEmpty()) {
            Toast.makeText(this, "请填写完整信息", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = Integer.parseInt(ageStr);

        Patient patient = new Patient(
                0,
                name,
                age,
                gender,
                doctor,
                "无",
                "未设置",
                0,
                "未分析",
                false,
                ""
        );

        boolean success;

        // 🔑 先查数据库里是否已有该账号
        Patient exist = patientDao.getPatientInfoByAccount(account);
        if (exist == null) {
            success = patientDao.insertPatientInfo(account, patient); // 首次 → 插入
        } else {
            success = patientDao.updatePatientInfo(account, patient); // 已存在 → 更新
        }

        if (success) {
            Toast.makeText(this, "信息保存成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(PtInfoActivity.this, PtMainActivity.class);
            intent.putExtra("account", account);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "保存失败，请重试", Toast.LENGTH_SHORT).show();
        }
    }

}
