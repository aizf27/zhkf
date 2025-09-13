package com.example.firstproject.db;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.firstproject.R;
import com.example.firstproject.bean.ChatMessage;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_USER = 0;
    private static final int TYPE_AI = 1;

    private List<ChatMessage> chatList;



    public ChatAdapter(List<ChatMessage> chatList) {

        this.chatList = chatList;
    }

    @Override
    public int getItemViewType(int position) {
        return chatList.get(position).getType() == ChatMessage.Type.USER ? TYPE_USER : TYPE_AI;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_USER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_user, parent, false);
            return new UserViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_ai, parent, false);
            return new AIViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String content = chatList.get(position).getContent();
        if(holder instanceof UserViewHolder) {
            ((UserViewHolder) holder).tvMessage.setText(content);
        } else {
            ((AIViewHolder) holder).tvMessage.setText(content);
        }
    }

    @Override
    public int getItemCount() { return chatList.size(); }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvMessage;
        UserViewHolder(View itemView) { super(itemView); tvMessage = itemView.findViewById(R.id.tvMessage); }
    }

    static class AIViewHolder extends RecyclerView.ViewHolder {
        TextView tvMessage;
        AIViewHolder(View itemView) { super(itemView); tvMessage = itemView.findViewById(R.id.tvMessage); }
    }
}
