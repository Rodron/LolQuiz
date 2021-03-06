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

        // Se recogen los botones del menú y se añade la funcionalidad correspondiente.
        Button newGameButton =  (Button)  findViewById(R.id.newGameButton);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame();
            }
        });

        Button options =  (Button)  findViewById(R.id.optionsButton);
        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                options();
            }
        });

        // Se ha omitido la funcionalidad del rankingButton ya que será implementado en una versión
        // futura pero se ha dejado en la pantalla a modo demostrativo de nuestra idea de diseño de
        // la pantalla
        // Button rankingButton = (Button)  findViewById(R.id.rankingButton);
    }

    public void newGame(){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void options(){
        Intent intent = new Intent(this, OptionsActivity.class);
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