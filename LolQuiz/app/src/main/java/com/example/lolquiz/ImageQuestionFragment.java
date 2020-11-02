package com.example.lolquiz;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ImageQuestionFragment extends Fragment {
    List<Button> options = new ArrayList<Button>();
    TextView questionBox;
    public ImageQuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_question, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        questionBox = (TextView) getView().findViewById(R.id.question);
        options.add((Button) getView().findViewById(R.id.option1));
        options.add((Button) getView().findViewById(R.id.option2));
        options.add((Button) getView().findViewById(R.id.option3));
        options.add((Button) getView().findViewById(R.id.option4));
        for (int i = 0; i<4;i++) {
            options.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((GameActivity) getActivity()).checkAnswer(v)) {
                        ((GameActivity) getActivity()).clickButton();
                    }
                }
            });
        }
        ((GameActivity) getActivity()).receiveButtons(options);
        ((GameActivity) getActivity()).receiveQuestion(questionBox);
        ((GameActivity) getActivity()).questionWriter();
    }
}