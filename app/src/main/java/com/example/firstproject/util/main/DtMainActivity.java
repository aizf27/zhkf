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
        patients.add(new Patient("张三", "痉挛期", 60, "肌力↑", false));
        patients.add(new Patient("李四", "恢复期", 80, "关节灵活", true));
        patients.add(new Patient("王五", "训练期", 40, "平衡提升", false));

        PatientAdapter adapter = new PatientAdapter(patients);
        binding.rvKeyPatients.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}