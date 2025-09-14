package com.example.firstproject.util.main.Psychlogy;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.firstproject.bean.ChatMessage;
import com.example.firstproject.databinding.ActivityPsychologyBinding;
import com.example.firstproject.db.ChatAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PsychologyActivity extends AppCompatActivity {

    private ActivityPsychologyBinding binding;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> chatList = new ArrayList<>();
    // 在类里
    private List<JSONObject> conversationHistory = new ArrayList<>();


    private final boolean USE_SIMULATION = false; // true=模拟 AI, false=DeepSeek API
    private final String API_KEY = "sk-d6e708da4ac740d89600089d911f9a65";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPsychologyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chatAdapter = new ChatAdapter(chatList);
        binding.rvChat.setLayoutManager(new LinearLayoutManager(this));
        binding.rvChat.setAdapter(chatAdapter);

        binding.btnSend.setOnClickListener(view -> {
            Log.d("PsychologyActivity", "发送按钮点击");
            //Toast.makeText(this, "发送按钮点击", Toast.LENGTH_SHORT).show();
            sendMessage();
        });
    }

    private void sendMessage() {
        String msg = binding.etMessage.getText().toString().trim();
        if(msg.isEmpty()) {
            Log.d("PsychologyActivity", "输入为空，取消发送");
            return;
        }
        // 保存历史用户消息
        JSONObject userMessage = new JSONObject();
        try {
            userMessage.put("role", "user");
            userMessage.put("content", msg);
            conversationHistory.add(userMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("PsychologyActivity", "用户消息: " + msg);

        // 添加用户消息
        chatList.add(new ChatMessage(msg, ChatMessage.Type.USER));
        chatAdapter.notifyItemInserted(chatList.size() - 1);
        binding.rvChat.scrollToPosition(chatList.size() - 1);
        binding.etMessage.setText("");

        // 调用 AI
        if(USE_SIMULATION) simulateAI(msg);
        else callDeepSeekAPI(msg);
    }

    private void simulateAI(String userMsg){
        Log.d("PsychologyActivity", "模拟 AI 开始处理");
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            String reply = "保持积极心态，今天也要加油!";
            Log.d("PsychologyActivity", "模拟 AI 回复: " + reply);

            chatList.add(new ChatMessage(reply, ChatMessage.Type.AI));
            chatAdapter.notifyItemInserted(chatList.size() - 1);
            binding.rvChat.scrollToPosition(chatList.size() - 1);
        }, 1000);
    }

    private void callDeepSeekAPI(String userMsg){
        Log.d("PsychologyActivity", "调用 DeepSeek API");
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                //请求 Body
//                {
//                    "model": "deepseek-chat",
//                        "messages": [
//                    {"role": "system", "content": "You are a helpful assistant."},
//                    {"role": "user", "content": "Hello!"}
//        ],
//                    "stream": false
//                }'

                //构建messages JSON
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("model", "deepseek-chat");


                // 把历史消息转换成 JSONArray
                JSONArray messages = new JSONArray(conversationHistory);
                jsonBody.put("messages", messages);

                jsonBody.put("max_tokens", 100);

                //输出请求
                String bodyStr = jsonBody.toString();
                Log.d("PsychologyActivity", "请求 Body: " + bodyStr);

                RequestBody body = RequestBody.create(bodyStr, MediaType.parse("application/json"));

                Request request = new Request.Builder()
                        .url("https://api.deepseek.com/chat/completions")
                        .addHeader("Authorization", "Bearer " + API_KEY)
                        .post(body)
                        .build();

                Response response = client.newCall(request).execute();

                int code = response.code();
                String resStr = response.body() != null ? response.body().string() : "null";
                Log.d("PsychologyActivity", "HTTP code=" + code + ", body=" + resStr);

                //响应Body
//                {
//                    "choices": [
//                    {
//                        "message": {
//                        "role": "assistant",
//                                "content": "Hello! How can I help you today? 😊"
//                    }
//                    }
//  ]
////                }
                if(response.isSuccessful() && resStr != null){

                    JSONObject resJson = new JSONObject(resStr);
                    JSONArray choices = resJson.getJSONArray("choices");
                    JSONObject firstChoice = choices.getJSONObject(0);
                    JSONObject message = firstChoice.getJSONObject("message");
                    String aiReply = message.getString("content");
                    Log.d("PsychologyActivity", "AI 回复: " + aiReply);


                    JSONObject aiMessage = new JSONObject();
                    aiMessage.put("role", "assistant");
                    aiMessage.put("content", aiReply);
                    conversationHistory.add(aiMessage);


                    runOnUiThread(() -> {
                        chatList.add(new ChatMessage(aiReply, ChatMessage.Type.AI));
                        chatAdapter.notifyItemInserted(chatList.size() - 1);
                        binding.rvChat.scrollToPosition(chatList.size() - 1);
                    });
                } else {
                    showAIError("AI 回复失败, code=" + code);
                }

            } catch (Exception e){
                e.printStackTrace();
                Log.d("PsychologyActivity", "DeepSeek 异常: " + e.getMessage());
                showAIError("AI 回复异常");
            }
        }).start();
    }

    private void showAIError(String msg){
        runOnUiThread(() -> {
            chatList.add(new ChatMessage(msg, ChatMessage.Type.AI));
            chatAdapter.notifyItemInserted(chatList.size() - 1);
            binding.rvChat.scrollToPosition(chatList.size() - 1);
        });
    }
}
