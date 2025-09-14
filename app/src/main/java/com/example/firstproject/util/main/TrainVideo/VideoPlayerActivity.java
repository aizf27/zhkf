package com.example.firstproject.util.main.TrainVideo;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstproject.R;

public class VideoPlayerActivity extends AppCompatActivity {

    private VideoView videoView;
    private TextView tvTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        videoView = findViewById(R.id.videoView);
        tvTitle = findViewById(R.id.tvTitle);

        //接收参数
        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");

        Log.d("VideoPlayerActivity", "接收到的参数：url=" + url + ", title=" + title);
        if (title != null){

            tvTitle.setText(title);
        }
        if (url != null) {
            Uri uri = Uri.parse(url);
            videoView.setVideoURI(uri);
            //添加播放控制条（暂停/播放/快进）
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);

            videoView.start();
        }
    }
}