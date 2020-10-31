package com.example.lolquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        InputStream is = getResources().openRawResource(R.raw.preguntas);

        TextView questionBox = (TextView) findViewById(R.id.question);
         List<List<String>> questions = new ArrayList<>();
         List<Integer> alreadyUsed = new ArrayList<>();
        List<Button> options = new ArrayList<>();

        TextView category = (TextView) findViewById(R.id.category);
        View categoryBackground = findViewById(R.id.categoryBackground);

        ImageButton back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });
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


        alreadyUsed.add( questionGenerator(alreadyUsed,questions,options,questionBox,category,categoryBackground));

    }

    @SuppressLint("SetTextI18n")
    public int questionGenerator(List<Integer> alreadyUsed, List<List<String>> questions, List<Button> options, TextView questionBox, TextView category, View categoryBackground){
        Random random = new Random();
        int questionTitle = random.nextInt(10);
        while(alreadyUsed.contains(questionTitle)){
            questionTitle = random.nextInt(10);
        }

        questionBox.setText(questions.get(questionTitle).get(2));

        int buttonIndex = random.nextInt(4);
        ArrayList<Integer> usedButtons = new ArrayList<Integer>();

        usedButtons.add(buttonIndex);

        options.get(buttonIndex).setText(questions.get(questionTitle).get(3));
        options.get(buttonIndex).setTag(0);

        while(usedButtons.size() != 4) {
            while (usedButtons.contains(buttonIndex)) {
                buttonIndex = random.nextInt(4);
            }
            usedButtons.add(buttonIndex);

            options.get(buttonIndex).setText(questions.get(questionTitle).get(2 + usedButtons.size()));
            options.get(buttonIndex).setTag(1);
        }

        switch(questions.get(questionTitle).get(0)){
            case "C":
                category.setText("Campeones y habilidades");
                categoryBackground.setBackgroundColor(Color.parseColor("#C62B38"));
                break;
            case "L" :
                category.setText("Lore del juego");
                categoryBackground.setBackgroundColor(Color.parseColor("#60BA1D"));
                break;
        }



        return questionTitle;


    }

    public boolean checkAnswer(View v){
        questionCounter ++;
        if (v.getTag().equals(0)){
            correctCounter ++;
        }
        if(questionCounter == 10){
            Intent intent = new Intent(this, RankingActivity.class);
            intent.putExtra("questionCounter",questionCounter);
            intent.putExtra("correctCounter",correctCounter);
            startActivity(intent);
            return false;
        }

        return true;
    }

}