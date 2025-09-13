package com.example.firstproject.util.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.firstproject.Dao.DoctorDao;
import com.example.firstproject.Dao.PatientDao;
import com.example.firstproject.R;
import com.example.firstproject.bean.Doctor;
import com.example.firstproject.bean.Patient;

import com.example.firstproject.databinding.ActivityDtMainBinding;
import com.example.firstproject.db.PatientAdapter;

import java.util.ArrayList;
import java.util.List;

public class DtMainActivity extends AppCompatActivity {
private ActivityDtMainBinding binding;
    private String doctorCode;
    private String doctorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDtMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // 获取医生信息（工号和姓名）
        doctorCode = getIntent().getStringExtra("doctorCode");
        Doctor doctor = new DoctorDao(this).getDoctorInfo(doctorCode);
        if (doctor != null) {
            doctorName = doctor.getName();
        } else {
            doctorName = "";
        }


        //从数据库获取患者列表
        PatientDao patientDao = new PatientDao(this);
        List<Patient> patients = patientDao.getPatientsByDoctor(doctorName, doctorCode);
        //
        // 初始化 Adapter
        PatientAdapter adapter = new PatientAdapter(patients,doctorCode);
        binding.rvKeyPatients.setLayoutManager(new LinearLayoutManager(this));
        binding.rvKeyPatients.setAdapter(adapter);

        //显示患者总数
      binding.tvTotalValue.setText(String.valueOf(patients.size()));



        binding.btnInfoCenter.setOnClickListener(v -> {
            Intent intent = new Intent(this, DtInfoActivity.class);

            intent.putExtra("doctorCode", doctorCode); // 医生工号

            startActivity(intent);});

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
