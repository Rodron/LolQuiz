package com.example.lolquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private  int questionCounter = 0;
    private  int correctCounter = 0;
    List<Button> options = new ArrayList<>();
    List<ImageButton> imageOptions = new ArrayList<>();
    List<Integer> alreadyUsed = new ArrayList<>();
    TextView category;
    View categoryBackground;
    List<List<String>> questions = new ArrayList<>();
    TextView questionBox;
    ImageView questionImg;
    FragmentManager fragmentController;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        InputStream is = getResources().openRawResource(R.raw.preguntas);

        //questionBox = (TextView) findViewById(R.id.question);
        questions = new ArrayList<>();
        alreadyUsed = new ArrayList<>();

        category = (TextView) findViewById(R.id.category);
        categoryBackground = findViewById(R.id.categoryBackground);

        //fragmentController = getSupportFragmentManager();
        //transaction = fragmentController.beginTransaction();

        ImageButton back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goToMenu();
            }
        });


        /*
        Button option1 =  (Button)  findViewById(R.id.option1);
        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAnswer(v)){
                    alreadyUsed.add( questionGenerator(alreadyUsed,questions,options,questionBox,category,categoryBackground));
                }
            }
        });
        Button option2 =  (Button)  findViewById(R.id.option2);
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAnswer(v)){
                    alreadyUsed.add( questionGenerator(alreadyUsed,questions,options,questionBox,category,categoryBackground));
                }
            }
        });
        Button option3 =  (Button)  findViewById(R.id.option3);
        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAnswer(v)){
                    alreadyUsed.add( questionGenerator(alreadyUsed,questions,options,questionBox,category,categoryBackground));
                }
            }
        });
        Button option4 =  (Button)  findViewById(R.id.option4);
        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAnswer(v)){
                    alreadyUsed.add( questionGenerator(alreadyUsed,questions,options,questionBox,category,categoryBackground));
                }
            }
        });
        options.add(option1);
        options.add(option2);
        options.add(option3);
        options.add(option4);
        */

        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            List<String> aux = new ArrayList<String>();

            for (int i = 0; (line = br.readLine()) != null; i++) {
                if(i%7 == 0 && i/7>0){
                    questions.add(new ArrayList<>(aux));
                    aux.clear();
                }
                aux.add(line);
            }

            questions.add(new ArrayList<>(aux));
            aux.clear();

            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
            Toast.makeText(this, "Error al leer el archivo de preguntas", Toast.LENGTH_SHORT).show();

        }


        questionGenerator();

    }

    @SuppressLint("SetTextI18n")
    public void questionGenerator() {
        Random random = new Random();
        int questionTitle = random.nextInt(10);
        while (alreadyUsed.contains(questionTitle)) {
            questionTitle = random.nextInt(10);
        }

        alreadyUsed.add(questionTitle);

        fragmentController = getSupportFragmentManager();
        transaction = fragmentController.beginTransaction();

        switch (Integer.parseInt(questions.get(questionTitle).get(1).substring(0,1))) {
            case 0:
                StandardQuestionFragment fragment0 = new StandardQuestionFragment();
                transaction.replace(R.id.frameLayout, fragment0);
                transaction.commit();
                break;
            case 1:
                ImageAnswersQuestionsFragment fragment1 = new ImageAnswersQuestionsFragment();
                transaction.replace(R.id.frameLayout, fragment1);
                transaction.commit();
                break;
            case 2:
                ImageQuestionFragment fragment2 = new ImageQuestionFragment();
                transaction.replace(R.id.frameLayout, fragment2);
                transaction.commit();
                break;

        }
    }

    public void questionWriter(){
        Random random = new Random();
        questionBox.setText(questions.get(alreadyUsed.get(alreadyUsed.size()-1)).get(2));

        int buttonIndex = random.nextInt(4);
        ArrayList<Integer> usedButtons = new ArrayList<Integer>();

        usedButtons.add(buttonIndex);
        String questionType = questions.get(alreadyUsed.get(alreadyUsed.size()-1)).get(1);
        switch (Integer.parseInt(""+questionType.substring(0,1))){
            case 2:
                questionImg.setImageResource(getImageId(this, questionType.substring(1)));
            case 0:
                options.get(buttonIndex).setText(questions.get(alreadyUsed.get(alreadyUsed.size()-1)).get(3));
                options.get(buttonIndex).setTag(0);

                while(usedButtons.size() != 4) {
                    while (usedButtons.contains(buttonIndex)) {
                        buttonIndex = random.nextInt(4);
                    }
                    usedButtons.add(buttonIndex);

                    options.get(buttonIndex).setText(questions.get(alreadyUsed.get(alreadyUsed.size()-1)).get(2 + usedButtons.size()));
                    options.get(buttonIndex).setTag(1);
                }
                break;
            case 1:
                imageOptions.get(buttonIndex).setImageResource(getImageId(this, questions.get(alreadyUsed.get(alreadyUsed.size()-1)).get(3)));
                imageOptions.get(buttonIndex).setTag(0);

                while(usedButtons.size() != 4) {
                    while (usedButtons.contains(buttonIndex)) {
                        buttonIndex = random.nextInt(4);
                    }
                    usedButtons.add(buttonIndex);

                    imageOptions.get(buttonIndex).setImageResource(getImageId(this, questions.get(alreadyUsed.get(alreadyUsed.size()-1)).get(2 + usedButtons.size())));
                    imageOptions.get(buttonIndex).setTag(1);
                }
                break;
        }

        switch(questions.get(alreadyUsed.get(alreadyUsed.size()-1)).get(0)){
            case "C":
                category.setText("Campeones y habilidades");
                categoryBackground.setBackgroundColor(Color.parseColor("#272B71"));
                break;
            case "L" :
                category.setText("Lore del juego");
                categoryBackground.setBackgroundColor(Color.parseColor("#1C9E71"));
                break;
        }
    }

    public boolean checkAnswer(View v){
        boolean end = true;

        questionCounter ++;
        if (v.getTag().equals(0)){
            correctCounter ++;
        }

        if(questionCounter == questions.size()){
            end = false;
        }

        return end;
    }

    public void goToMenu(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToResults(){
        Intent intent = new Intent(this, RankingActivity.class);
        intent.putExtra("questionCounter",questionCounter);
        intent.putExtra("correctCounter",correctCounter);
        startActivity(intent);
    }

    public void receiveQuestion (TextView question){        questionBox = question;    }
    public void receiveQuestionImage (ImageView questionImage){        questionImg = questionImage;    }

    public void receiveButtons (List<Button> receivedOptions){
        options = receivedOptions;
    }

    public void receiveImageButtons (List<ImageButton> receivedOptions){
        imageOptions = receivedOptions;
    }

    public void clickButton(){
        questionGenerator();
    }

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }

}