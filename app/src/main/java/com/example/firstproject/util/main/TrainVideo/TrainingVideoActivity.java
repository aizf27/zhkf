package com.example.firstproject.util.main.TrainVideo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstproject.R;
import com.example.firstproject.bean.Video;
import com.example.firstproject.db.VideoAdapter;

import java.util.ArrayList;
import java.util.List;

public class TrainingVideoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VideoAdapter adapter;
    private List<Video> allVideos;
    private List<Video> filteredVideos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_video);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //1. 准备所有视频（带分类）
        allVideos = new ArrayList<>();
        allVideos.add(new Video("肩关节拉伸", "适合术后康复",
                "https://1378739563.vod-qcloud.com/71ccb256vodcq1378739563/3e4bbde05145403698182508222/d10ku2LEr1QA.mp4", "肩部"));
        allVideos.add(new Video("手臂伸展", "增强上肢力量",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4", "肩部"));
        allVideos.add(new Video("腿部拉伸", "缓解下肢紧张",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4", "腿部"));
        allVideos.add(new Video("全身热身操", "运动前准备",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4", "全身"));

        //默认先显示全部
        filteredVideos = new ArrayList<>(allVideos);

        adapter = new VideoAdapter(filteredVideos, video -> {
            Intent intent = new Intent(this, VideoPlayerActivity.class);
            intent.putExtra("url", video.getUrl());
            intent.putExtra("title", video.getTitle());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        //2.设置分类下拉框
        Spinner spinner = findViewById(R.id.spinnerCategory);
        String[] categories = {"全部", "肩部", "腿部", "全身"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        //3.监听选择分类
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = categories[position];
                filterVideos(selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    // 分类过滤
    private void filterVideos(String category) {
        filteredVideos.clear();
        if (category.equals("全部")) {
            filteredVideos.addAll(allVideos);
        } else {
            for (Video v : allVideos) {
                if (v.getCategory().equals(category)) {
                    filteredVideos.add(v);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}
