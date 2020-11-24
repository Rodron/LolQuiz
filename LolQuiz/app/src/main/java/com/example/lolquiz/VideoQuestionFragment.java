package com.example.lolquiz;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

public class VideoQuestionFragment extends Fragment {
    List<Button> options = new ArrayList<Button>();
    TextView questionBox;
    VideoView questionVideo;

    public VideoQuestionFragment() {
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
        return inflater.inflate(R.layout.fragment_video_question, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Se buscan los elementos del fragmento y se pasan a la actividad para poder ejecutar el
        // método questionwriter.
        questionBox = (TextView) getView().findViewById(R.id.question);
        questionVideo = (VideoView) getView().findViewById(R.id.video);
        options.add((Button) getView().findViewById(R.id.option1));
        options.add((Button) getView().findViewById(R.id.option2));
        options.add((Button) getView().findViewById(R.id.option3));
        options.add((Button) getView().findViewById(R.id.option4));

        for (int i = 0; i < 4; i++) {
            options.get(i).setOnClickListener(new View.OnClickListener() {
                // Aquí se ha implementado una animación para darle feedback visual al usuario
                // dependiendo de si su respuesta es correcta o no.
                @Override
                public void onClick(View v) {
                    for (View button : options) {
                        button.setOnClickListener(null);
                    }
                    ValueAnimator valueanimator;
                    if (v.getTag().equals(0)) {
                        valueanimator = ObjectAnimator.ofInt(v, "backgroundColor", Color.parseColor("#FAE634"), Color.parseColor("#60BA1D"));
                    } else {
                        valueanimator = ObjectAnimator.ofInt(v, "backgroundColor", Color.parseColor("#FAE634"), Color.parseColor("#C62B38"));
                    }

                    valueanimator.setDuration(1000);
                    valueanimator.setEvaluator(new ArgbEvaluator());

                    valueanimator.start();

                    // Con este if se establece la opción a realizar tras completar la animación,
                    // mostrar una nueva pregunta o ir a la pantalla del resultados. Esto se decide
                    // en base al return proporcionado por el método checkAnswer.
                    if (((GameActivity) requireActivity()).checkAnswer(v)) {
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
                    } else {
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

        // Se pasan a la actividad los elementos del fragmento para posteriormente proceder a ejecutar
        // el método questionwriter.
        ((GameActivity) requireActivity()).receiveButtons(options);
        ((GameActivity) getActivity()).receiveQuestion(questionBox);
        ((GameActivity) getActivity()).receiveQuestionVideo(questionVideo);
        ((GameActivity) getActivity()).questionWriter();
    }
}