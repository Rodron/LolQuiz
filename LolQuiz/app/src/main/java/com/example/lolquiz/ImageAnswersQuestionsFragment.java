package com.example.lolquiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ImageAnswersQuestionsFragment extends Fragment {
    List<ImageButton> options = new ArrayList<ImageButton>();
    TextView questionBox;

    public ImageAnswersQuestionsFragment() {
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
        return inflater.inflate(R.layout.fragment_image_answers_questions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        questionBox = (TextView) getView().findViewById(R.id.question);
        options.add((ImageButton) getView().findViewById(R.id.option1));
        options.add((ImageButton) getView().findViewById(R.id.option2));
        options.add((ImageButton) getView().findViewById(R.id.option3));
        options.add((ImageButton) getView().findViewById(R.id.option4));
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
        ((GameActivity) getActivity()).receiveImageButtons(options);
        ((GameActivity) getActivity()).receiveQuestion(questionBox);
        ((GameActivity) getActivity()).questionWriter();
    }
}