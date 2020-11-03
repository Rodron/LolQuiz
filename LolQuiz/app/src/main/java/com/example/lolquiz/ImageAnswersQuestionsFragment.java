package com.example.lolquiz;

import android.animation.Animator;
import android.animation.ValueAnimator;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ImageAnswersQuestionsFragment extends Fragment {
    List<ImageButton> options = new ArrayList<ImageButton>();
    TextView questionBox;
    boolean first = true;

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
            int finalI = i;
            options.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (View button : options){
                        button.setOnClickListener(null);
                    }
                    ColorMatrix matrix = new ColorMatrix();


                    ValueAnimator valueanimator = ValueAnimator.ofFloat(0f,.75f);

                    if(v.getTag().equals(0)){
                        valueanimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float factor = 0.7f*animation.getAnimatedFraction();
                                matrix.setScale(1 * (1 - factor),1 + 4*(factor) * (1 - factor),1 * (1 - factor),1);
                                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                                options.get(finalI).setColorFilter(filter);
                            }
                        });
                    }else{
                        valueanimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float factor = 0.4f*animation.getAnimatedFraction();
                                matrix.setScale((1 + 3*(factor))*(1-factor),1*(1-factor),1*(1-factor),1);
                                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                                options.get(finalI).setColorFilter(filter);
                            }
                        });
                    }
                    valueanimator.setDuration(1000);

                    valueanimator.start();

                    if (((GameActivity) Objects.requireNonNull(getActivity())).checkAnswer(v)) {
                        valueanimator.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                ((GameActivity) getActivity()).clickButton();
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });
                    }else{
                        valueanimator.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                ((GameActivity) getActivity()).goToResults();
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });
                    }
                }
            });
        }
        ((GameActivity) Objects.requireNonNull(getActivity())).receiveImageButtons(options);
        ((GameActivity) getActivity()).receiveQuestion(questionBox);
        ((GameActivity) getActivity()).questionWriter();
    }
}