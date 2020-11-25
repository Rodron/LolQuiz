package com.example.lolquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Mostramos el resultado de la partida con un mensaje y una imagen.
        int questionCounter = getIntent().getIntExtra("questionCounter",0);
        int correctCounter = getIntent().getIntExtra("correctCounter",0);
        int score = (int)(((float)correctCounter)/((float)questionCounter) * 1000 + correctCounter*10);

        TextView message = (TextView) findViewById(R.id.message);
        message.setText("Has acertado " + correctCounter  + " de " + questionCounter  + " preguntas.\n Has conseguido " + score + " puntos.");

        SharedPreferences sharedPref = this.getSharedPreferences("preferences", Context.MODE_PRIVATE);


        // También se incluyen dos botones, uno que permite jugar de nuevo y otro que nos devuelve
        // al menú.
        Button playAgain =  (Button)  findViewById(R.id.playAgain);
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame();
            }
        });

        Button backToMenu =  (Button)  findViewById(R.id.backToMenu);
        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMenu();
            }
        });

        // La imagen a mostrar varia según el rango en el que se encuentre el resultado.
        ImageView image = (ImageView) findViewById(R.id.image);
        if(correctCounter < questionCounter/2){
            image.setImageResource(R.drawable.amumu);
        }else if( correctCounter == questionCounter){
            image.setImageResource(R.drawable.teemo);
        }else{
            image.setImageResource(R.drawable.ziggs);
        }


        TreeMap<Integer, String> userscoreList = new TreeMap<>();
        try{
            InputStream is = openFileInput("ranking.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            String [] scores = new String[3];

            while ((line = br.readLine()) != null) {
                scores = line.split("---");
                userscoreList.put(Integer.parseInt(scores[2]), line);
            }

            br.close();
            is.close();
        }
        catch (IOException e) {
            //Toast.makeText(this, "Error al leer el archivo de puntuaciones", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        userscoreList.put((int)score, sharedPref.getString("user","Anónimo")+"---"+correctCounter + "/" + questionCounter + "---" + score);

        SortedSet<Integer> keys = new TreeSet<>(userscoreList.keySet());
        ArrayList<Integer> keyList = new ArrayList<Integer>(keys);

        keys.clear();


        try {
            FileOutputStream os = openFileOutput("ranking.txt", MODE_PRIVATE);
            for(int i = keyList.size()-1; i >= 0; i--){
                os.write((userscoreList.get(keyList.get(i))+"\n").getBytes());
            }
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        goToMenu();
    }

    public void newGame(){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void goToMenu(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}