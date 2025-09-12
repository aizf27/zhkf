package com.example.firstproject.db;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstproject.R;
import com.example.firstproject.bean.Patient;

import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder> {
    private List<Patient> patientList;


    public PatientAdapter(List<Patient> patientList) {
        this.patientList = patientList;
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_key_patient, parent, false);     //绑定item_key_patient.xml
        return new PatientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {
        Patient patient = patientList.get(position);
        holder.tvName.setText(patient.getName());
        holder.tvStage.setText("康复阶段：" + patient.getStage());
        holder.progressTraining.setProgress(patient.getProgress());
        holder.tvProgressValue.setText(patient.getProgress() + "%");
        holder.tvAiResult.setText(patient.getAiResult());
        holder.imgAvatar.setImageResource(R.mipmap.mrtx); // 你自己的图片
        holder.imgAiEval.setImageResource(R.mipmap.ai); // 你自己的图标
        if (patient.isHasAlert()) {
            holder.imgAlert.setVisibility(View.VISIBLE);
        } else {
            holder.imgAlert.setVisibility(View.GONE);
        }
        // 在这里直接设置点击事件
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), com.example.firstproject.util.main.PtInfoActivity.class);
            intent.putExtra("account", patient.getAccount());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    static class PatientViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvStage, tvProgressValue, tvAiResult;
        ProgressBar progressTraining;
        ImageView imgAlert;
        ImageView imgAvatar;
        ImageView imgAiEval;

        public PatientViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvStage = itemView.findViewById(R.id.tv_stage);
            tvProgressValue = itemView.findViewById(R.id.tv_progress_value);
            tvAiResult = itemView.findViewById(R.id.tv_ai_result);
            progressTraining = itemView.findViewById(R.id.progress_training);
            imgAlert = itemView.findViewById(R.id.img_alert);
            imgAvatar = itemView.findViewById(R.id.img_avatar);
            imgAiEval = itemView.findViewById(R.id.img_ai_eval);
        }
    }
}
