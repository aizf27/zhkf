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

import com.example.firstproject.databinding.ActivityDtLogBinding;
import com.example.firstproject.Dao.DoctorDao;
import com.example.firstproject.util.main.DtInfoActivity;
import com.example.firstproject.util.main.DtMainActivity;
import com.example.firstproject.util.main.PtInfoActivity;
import com.example.firstproject.util.main.PtMainActivity;

public class DtLog extends AppCompatActivity {
    private ActivityDtLogBinding binding;
    private DoctorDao doctorDao;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDtLogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        doctorDao = new DoctorDao(this);
        preferences = getSharedPreferences("doctor_prefs", MODE_PRIVATE);

        //给“医生登录”加下划线
        String titleText = "<u>医生登录</u>";
        binding.tvDoctorLoginTitle.setText(Html.fromHtml(titleText, Html.FROM_HTML_MODE_LEGACY));

        //恢复记住的账号密码
        String savedId = preferences.getString("doctor_id", "");
        String savedPassword = preferences.getString("doctor_password", "");
        boolean remember = preferences.getBoolean("remember", false);

        binding.etDoctorId.setText(savedId);
        if (remember) {
            binding.etDoctorPassword.setText(savedPassword);
            binding.cbRememberPassword.setChecked(true);
        }

        // 登录逻辑
        binding.btnDoctorLogin.setOnClickListener(v -> {
            String id = binding.etDoctorId.getText().toString().trim();
            String password = binding.etDoctorPassword.getText().toString().trim();

            if (id.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "请输入工号和密码", Toast.LENGTH_SHORT).show();
                return;
            }

            int result = doctorDao.loginDoctor(id, password);
            switch (result) {
                case 0:
                    Toast.makeText(this, "账号不存在，请先注册", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                    saveLoginInfo(id, password, binding.cbRememberPassword.isChecked());
                    goToNext(id);
                    break;
                case 2:
                    Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
                    break;
            }

        });

        //注册逻辑
        binding.btnDoctorRegister.setOnClickListener(v -> {
            String id = binding.etDoctorId.getText().toString().trim();
            String password = binding.etDoctorPassword.getText().toString().trim();

            if (id.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "请输入工号和密码", Toast.LENGTH_SHORT).show();
                return;
            }

            //查询账号是否存在
            int checkResult = doctorDao.checkDoctor(id, password);
            //checkDoctor 自定义：0=不存在，1=存在+密码正确，2=存在+密码错误

            switch (checkResult) {
                case 0: // 不存在 -> 注册
                    if (doctorDao.registerDoctorAccount(id, password)) {
                        Toast.makeText(this, "注册成功，请登录", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "注册失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 1: // 存在且密码正确
                    Toast.makeText(this, "该账号已存在，自动登录", Toast.LENGTH_SHORT).show();
                    saveLoginInfo(id, password, binding.cbRememberPassword.isChecked());
                    goToNext(id);
                    break;
                case 2: // 存在但密码错误
                    Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
                    break;
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // 保存账号密码
    private void saveLoginInfo(String id, String password, boolean remember) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("doctor_id", id);
        editor.putBoolean("remember", remember);
        if (remember) {
            editor.putString("doctor_password", password);
        } else {
            editor.remove("doctor_password");
        }
        editor.apply();
    }

    private void goToNext(String id) {



        // 查询患者信息是否完整
        if (!doctorDao.isInfoComplete(id)) {
            // 信息未完善 → 跳转到 DttientInfoActivity
            Intent intent = new Intent(DtLog.this, DtInfoActivity.class);

            intent.putExtra("doctorCode", id); // 工号
            startActivity(intent);
        } else {
            // 信息已完善 → 跳转 DtMainActivity
            Intent intent = new Intent(DtLog.this, DtMainActivity.class);

            intent.putExtra("doctorCode", id); // 工号
            startActivity(intent);
        }

        finish();
    }
}
