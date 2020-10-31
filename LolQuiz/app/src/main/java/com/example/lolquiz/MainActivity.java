package com.example.lolquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button newGameButton =  (Button)  findViewById(R.id.newGameButton);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame();
            }
        });
        //Button rankingButton = (Button)  findViewById(R.id.rankingButton);
    }

    public void newGame(){

        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);

    }

    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
    /*
    public void onClickRankingButton(View v){
        Intent intent = new Intent(this, RankingActivity.class);
        startActivity(intent);

    }*/
}