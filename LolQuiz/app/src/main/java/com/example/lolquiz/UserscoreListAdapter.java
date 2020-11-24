package com.example.lolquiz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class UserscoreListAdapter extends ArrayAdapter<Userscore> {
    private static final String TAG = "UserscoreListAdapter";

    private Context mContext;
    int mResource;

    public UserscoreListAdapter(Context context, int resource, ArrayList<Userscore> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }


    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String username = getItem(position).getUsername();
        String score = getItem(position).getScore();
        String questions = getItem(position).getQuestions();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView userView = (TextView) convertView.findViewById(R.id.textView1);
        TextView scoreView = (TextView) convertView.findViewById(R.id.textView2);
        TextView questionsView = (TextView) convertView.findViewById(R.id.textView3);

        userView.setText(username);
        scoreView.setText(score);
        questionsView.setText(questions);

        return convertView;
    }
}
