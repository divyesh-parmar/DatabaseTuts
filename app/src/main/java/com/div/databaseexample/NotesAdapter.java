package com.div.databaseexample;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyHolder> {

    Context context;
    ArrayList<String> titleList = new ArrayList<>();
    ArrayList<String> contentList = new ArrayList<>();

    NotesAdapter(Context con, ArrayList<String> titList, ArrayList<String> contList) {
        context = con;
        titleList = titList;
        contentList = contList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_notes, parent, false);

        MyHolder myHolder = new MyHolder(view);

        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.tv_title.setText(titleList.get(position));
        holder.tv_content.setText(contentList.get(position));

        holder.main_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.selected_title = titleList.get(position);
                Util.selected_content = contentList.get(position);
                context.startActivity(new Intent(context, PreviewNoteActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView tv_title, tv_content;
        LinearLayout main_item;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_content);
            main_item = itemView.findViewById(R.id.main_item);
        }
    }
}
