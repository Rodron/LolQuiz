package com.example.lolquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        InputStream is = getResources().openRawResource(R.raw.preguntas);
        ArrayList<String[]> questions = new ArrayList<String[]>();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            String aux[] = new String[6];
            for (int i = 0; (line = br.readLine()) != null; i++) {
                aux[i%6] = line;
                if(i%6 == 0 && i/6>0){
                    questions.add(aux);
                }
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }

    }


}