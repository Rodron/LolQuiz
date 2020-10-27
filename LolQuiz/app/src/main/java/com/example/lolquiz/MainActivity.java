package com.example.lolquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*Button optionsButton =  (Button)  findViewById(R.id.optionsButton);
    Button newGameButton  = (Button)  findViewById(R.id.newGameButton);
    Button rankingButton = (Button)  findViewById(R.id.rankingButton);


    public void onClickNewGameButton(View v){

        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);

    }

    public void onClickOptionsActivity(View v){

        Intent intent = new Intent(this, OptionsActivity.class);
        startActivity(intent);

    }

    public void onClickRankingButton(View v){

        Intent intent = new Intent(this, RankingActivity.class);
        startActivity(intent);

    }*/
}