package com.example.firstproject.util.main;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.firstproject.R;
import com.example.firstproject.bean.Patient;
import com.example.firstproject.databinding.ActivityDtLogBinding;
import com.example.firstproject.databinding.ActivityDtMainBinding;
import com.example.firstproject.db.PatientAdapter;

import java.util.ArrayList;
import java.util.List;

public class DtMainActivity extends AppCompatActivity {
private ActivityDtMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDtMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // RecyclerView 初始化
        binding.rvKeyPatients.setLayoutManager(new LinearLayoutManager(this));

        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient(1,"张三", 34,"man","赵医生","脑瘫","痉挛期", 60, "肌力↑", false,"9.8"));
        patients.add(new Patient(2, "李妹", 45, "woman", "赵医生","脑卒中", "恢复期", 75, "平衡能力改善", true, "9.5"));
        patients.add(new Patient(3, "王五", 28, "man","赵医生", "脊髓损伤", "康复期", 85, "关节活动度改善", false, "8.22"));

        PatientAdapter adapter = new PatientAdapter(patients);
        binding.rvKeyPatients.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}