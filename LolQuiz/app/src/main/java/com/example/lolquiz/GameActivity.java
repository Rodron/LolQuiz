package com.example.lolquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        InputStream is = getResources().openRawResource(R.raw.preguntas);
        List<List<String>> questions = new ArrayList<>();
        List<Integer> alreadyUsed = new ArrayList<>();

        TextView questionBox = (TextView) findViewById(R.id.question);

        List<Button> options = new ArrayList<>();

        Button option1 =  (Button)  findViewById(R.id.option1);
        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(v);
                alreadyUsed.add( questionGenerator(alreadyUsed,questions,options,questionBox));
            }
        });

        Button option2 =  (Button)  findViewById(R.id.option2);
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(v);
                alreadyUsed.add( questionGenerator(alreadyUsed,questions,options,questionBox));
            }
        });

        Button option3 =  (Button)  findViewById(R.id.option3);
        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(v);
                alreadyUsed.add( questionGenerator(alreadyUsed,questions,options,questionBox));
            }
        });

        Button option4 =  (Button)  findViewById(R.id.option4);
        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(v);
                alreadyUsed.add( questionGenerator(alreadyUsed,questions,options,questionBox));
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


        alreadyUsed.add( questionGenerator(alreadyUsed,questions,options,questionBox));
    }

    public int questionGenerator(List<Integer> alreadyUsed, List<List<String>> questions, List<Button> options, TextView questionBox){
        Random random = new Random();
        int questionTitle = random.nextInt(10);
        while(alreadyUsed.contains(questionTitle)){
            questionTitle = random.nextInt(10);
        }
        questionBox.setText(questions.get(questionTitle).get(2));
        options.get(0).setText(questions.get(questionTitle).get(3));
        options.get(0).setTag(0);
        options.get(1).setText(questions.get(questionTitle).get(4));
        options.get(1).setTag(1);
        options.get(2).setText(questions.get(questionTitle).get(5));
        options.get(2).setTag(1);
        options.get(3).setText(questions.get(questionTitle).get(6));
        options.get(3).setTag(1);
        return questionTitle;


    }

    public void checkAnswer(View v){
        if (v.getTag().equals(0)){
            Toast.makeText(this, "Es correcta",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Es incorrecta",Toast.LENGTH_SHORT).show();
        }
    }

}