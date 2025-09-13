package com.example.firstproject.util.main.dt_or_pt;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.firstproject.databinding.ActivityPtMainBinding;
import com.example.firstproject.util.main.Psychlogy.PsychologyActivity;
import com.example.firstproject.util.main.TrainVideo.TrainingVideoActivity;

public class PtMainActivity extends AppCompatActivity {
private ActivityPtMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityPtMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnViewInfo.setOnClickListener(view -> {
            Intent intent = new Intent(this, PtInfoActivity.class);
            // 把 account 传过去
            String account = getIntent().getStringExtra("account");
            intent.putExtra("mode","patient");
            intent.putExtra("account", account);
            startActivity(intent);
        });
        //跳转到训练视频界面
        binding.cardTraining.setOnClickListener(view -> {
            Intent intent = new Intent(this, TrainingVideoActivity.class);

            startActivity(intent);
        });
//        跳转到心理助手界面
        binding.cardPsychology.setOnClickListener(view -> {
            Intent intent = new Intent(this, PsychologyActivity.class);
            String account = getIntent().getStringExtra("account");
            intent.putExtra("account", account);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}