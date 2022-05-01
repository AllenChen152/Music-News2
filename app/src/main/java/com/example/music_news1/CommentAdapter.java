package com.example.music_news1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CommentAdapter extends ArrayAdapter<Comment> {

    private final Comment[] comments;
    private final int resource;

    public CommentAdapter(@NonNull Context context, int resource, Comment[] comments) {
        super(context, resource, comments);
        this.resource = resource;
        this.comments = comments;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        } else {
            view = convertView;
        }

        ((TextView)view.findViewById(R.id.user_id)).setText(comments[position].getUserId());
        ((TextView)view.findViewById(R.id.time)).setText(comments[position].getTime());
        ((TextView)view.findViewById(R.id.message)).setText(comments[position].getMessage());
        ((TextView)view.findViewById(R.id.zan)).setText(String.valueOf(comments[position].getZan()));
        ((ImageView)view.findViewById(R.id.imageView)).setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_zan));
        return view;
    }
}
