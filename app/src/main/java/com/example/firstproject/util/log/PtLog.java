package com.example.firstproject.util.log;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.firstproject.databinding.ActivityPtLogBinding;
import com.example.firstproject.Dao.PatientDao;
import com.example.firstproject.util.main.PtInfoActivity;
import com.example.firstproject.util.main.PtMainActivity;

public class PtLog extends AppCompatActivity {
    private ActivityPtLogBinding binding;
    private PatientDao patientDao;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityPtLogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        patientDao = new PatientDao(this);
        preferences = getSharedPreferences("patient_prefs", MODE_PRIVATE);

        // 给“患者登录”加下划线
        String titleText = "<u>患者登录</u>";
        binding.tvPtLoginTitle.setText(Html.fromHtml(titleText, Html.FROM_HTML_MODE_LEGACY));

        // 恢复记住密码
        String savedId = preferences.getString("pt_id", "");
        String savedPassword = preferences.getString("pt_password", "");
        boolean remember = preferences.getBoolean("remember", false);

        binding.etPtId.setText(savedId);
        if (remember) {
            binding.etPtPassword.setText(savedPassword);

            binding.cbRememberPassword.setChecked(true);
        }

        // 登录按钮
        binding.btnPtLogin.setOnClickListener(v -> {
            String id = binding.etPtId.getText().toString().trim();
            String password = binding.etPtPassword.getText().toString().trim();

            if (id.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "请输入手机号和密码", Toast.LENGTH_SHORT).show();
                return;
            }

            int result = patientDao.loginPatient(id, password);
            switch (result) {
                case 0:
                    Toast.makeText(this, "账号不存在，请先注册", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                    saveLoginInfo(id, password, binding.cbRememberPassword.isChecked());
                    goNext(id);
                    break;
                case 2:
                    Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
                    break;
            }
        });

        // 注册按钮
        binding.btnPtRegister.setOnClickListener(v -> {
            String id = binding.etPtId.getText().toString().trim();
            String password = binding.etPtPassword.getText().toString().trim();

            if (id.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "请输入手机号和密码", Toast.LENGTH_SHORT).show();
                return;
            }

            int result = patientDao.loginPatient(id, password);
            switch (result) {
                case 0:
                    if (patientDao.registerPatient(id, password)) {
                        Toast.makeText(this, "注册成功，请登录", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "注册失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 1:
                    Toast.makeText(this, "账号已存在，自动登录", Toast.LENGTH_SHORT).show();
                    saveLoginInfo(id, password, binding.cbRememberPassword.isChecked());
                    goNext(id);
                    break;
                case 2:
                    Toast.makeText(this, "账号存在但密码错误", Toast.LENGTH_SHORT).show();
                    break;
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void saveLoginInfo(String id, String password, boolean remember) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("pt_id", id);
        editor.putBoolean("remember", remember);
        if (remember) {
            editor.putString("pt_password", password);
        } else {
            editor.remove("pt_password");
        }
        editor.apply();
    }

    private void goNext(String account) {
        // 查询患者信息是否完整
        if (!patientDao.isInfoComplete(account)) {
            // 信息未完善 → 跳转到 PatientInfoActivity
            Intent intent = new Intent(PtLog.this, PtInfoActivity.class);
            intent.putExtra("account", account);
            intent.putExtra("mode", "patient");
            startActivity(intent);
        } else {
            // 信息已完善 → 跳转 PtMainActivity
            Intent intent = new Intent(PtLog.this, PtMainActivity.class);
            intent.putExtra("account", account);
            intent.putExtra("mode", "patient");
            startActivity(intent);
        }
        finish();
    }


}
