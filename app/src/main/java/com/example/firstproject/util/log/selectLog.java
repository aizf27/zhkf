package com.example.firstproject.util.log;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.firstproject.databinding.ActivitySelectLogBinding;

public class selectLog extends AppCompatActivity {
    private ActivitySelectLogBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySelectLogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //医生和患者登陆界面跳转
        binding.imageDtLog.setOnClickListener(v -> { startActivity(new Intent(this, DtLog.class));});
        binding.imagePtLog.setOnClickListener(v -> { startActivity(new Intent(this, PtLog.class));});

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}