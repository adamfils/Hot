package com.adamsapp.rateme;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ChatAdapter extends RecyclerView.Adapter<ChatHolder> {

    Context c;

    public ChatAdapter(Context c) {
        this.c = c;
    }


    private String[] mData = {"Adam", "John", "Fils", "Gates", "Queen", "Adam", "John", "Fils", "Gates", "Queen"};

    @Override
    public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_chat_list, parent, false);
        return new ChatHolder(v);
    }

    @Override
    public void onBindViewHolder(ChatHolder holder, int position) {
        holder.chat_username.setText(mData[position]);
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }
}

class ChatHolder extends RecyclerView.ViewHolder {
    TextView chat_username;

    public ChatHolder(View itemView) {
        super(itemView);
        chat_username = (TextView) itemView.findViewById(R.id.chat_username);
    }
}
