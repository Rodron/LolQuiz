package com.example.lolquiz;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RankingActivity extends AppCompatActivity {
    private static final String TAG = "RankingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        Log.d(TAG, "onCreate, Started.");

        ListView mListView = (ListView) findViewById(R.id.scoreList);
        TextView noScoreStored = (TextView) findViewById(R.id.noScoreStored);

        boolean scoreFound = false;

        InputStream is = getResources().openRawResource(R.raw.ranking);
        ArrayList<Userscore> userscoreList = new ArrayList<>();


        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            String [] scores = new String[3];

            while ((line = br.readLine()) != null) {
                if(!scoreFound)
                    scoreFound = true;
                scores = line.split("-\"-");
                userscoreList.add(new Userscore(scores[0], scores[1], scores[2]));
            }

            br.close();
        }
        catch (IOException e) {
            Toast.makeText(this, "Error al leer el archivo de puntuaciones", Toast.LENGTH_SHORT).show();
        }
        if(scoreFound) {
            UserscoreListAdapter adapter = new UserscoreListAdapter(this, R.layout.adapter_view_layout, userscoreList);
            mListView.setAdapter(adapter);
            noScoreStored.setVisibility(View.INVISIBLE);
        }
        else{
            noScoreStored.setVisibility(View.VISIBLE);
            Toast.makeText(this, "No se han encontrado puntuaciones almacenadas.", Toast.LENGTH_SHORT).show();
        }

        ImageButton back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goToMenu();
            }
        });
    }

    public void goToMenu(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}