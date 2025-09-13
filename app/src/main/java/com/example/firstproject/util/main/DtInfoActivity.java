package com.example.firstproject.util.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.firstproject.Dao.DoctorDao;
import com.example.firstproject.bean.Doctor;
import com.example.firstproject.databinding.ActivityDtInfoBinding;

public class DtInfoActivity extends AppCompatActivity {
    private ActivityDtInfoBinding binding;
    private DoctorDao doctorDao;
    private String doctorName;
    private String doctorCode;
    private int patientCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivityDtInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //获取工号和姓名
        doctorName = getIntent().getStringExtra("doctorName");
        doctorCode = getIntent().getStringExtra("doctorCode");
        //获取总患者数
        patientCount = getIntent().getIntExtra("toTalValue",0);
        Log.d("DtInfoActivity", "toTalValue: " + patientCount);

        Log.d("DtInfoActivity", "doctorName: " + doctorName);
        Log.d("DtInfoActivity", "doctorCode: " + doctorCode);
        // 初始化 DAO
        doctorDao = new DoctorDao(this);

        // 加载医生信息
        loadDoctorInfo();

        binding.btnSave.setOnClickListener(view -> {
            saveDoctorInfo();
        });

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void loadDoctorInfo() {
        Doctor doctor = doctorDao.getDoctorInfo(doctorCode);
        if (doctor != null) {
            binding.tvCode.setText("工号："+doctor.getId());
            binding.etName.setText(doctor.getName());
            binding.etAge.setText(String.valueOf(doctor.getAge()));
            binding.etPtNum.setText(String.valueOf(patientCount));
            binding.etGender.setText(doctor.getGender());
            binding.etDepartment.setText(doctor.getDepartment());
        } else {
            // 新医生，显示工号
            binding.tvCode.setText("工号："+doctorCode);
        }
    }

    private void saveDoctorInfo() {
        String name = binding.etName.getText().toString().trim();
        String ageStr = binding.etAge.getText().toString().trim();
        String ptNumStr = binding.etPtNum.getText().toString().trim();
        String gender = binding.etGender.getText().toString().trim();
        String department = binding.etDepartment.getText().toString().trim();

        if (name.isEmpty() || ageStr.isEmpty() || ptNumStr.isEmpty()|| gender.isEmpty() || department.isEmpty()) {
            Toast.makeText(this, "请填写完整信息", Toast.LENGTH_SHORT).show();
            return;
        }

        int age, ptNum;
        try {
            age = Integer.parseInt(ageStr);
            ptNum = Integer.parseInt(ptNumStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "年龄和患者数必须是数字", Toast.LENGTH_SHORT).show();
            return;
        }



        Doctor doctor = new Doctor(doctorCode, name, gender, age, department, ptNum);

        boolean success;
        Doctor exist = doctorDao.getDoctorInfo(doctorCode);
        if (exist == null) {
            success = doctorDao.addDoctorInfo(doctor); // 新增
        } else {
            success = doctorDao.updateDoctorInfo(doctor); // 更新
        }

        if (success) {
            Toast.makeText(this, "信息保存成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DtInfoActivity.this, DtMainActivity.class);
            intent.putExtra("doctorCode", doctorCode);  // 只传工号就够了
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(this, "保存失败，请重试", Toast.LENGTH_SHORT).show();
        }
    }
}