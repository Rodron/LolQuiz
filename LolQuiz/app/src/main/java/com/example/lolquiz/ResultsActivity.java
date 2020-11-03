package com.example.lolquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Mostramos el resultado de la partida con un mensaje y una imagen.
        int questionCounter = getIntent().getIntExtra("questionCounter",0);
        int correctCounter = getIntent().getIntExtra("correctCounter",0);
        TextView message = (TextView) findViewById(R.id.message);
        message.setText("Has acertado " + correctCounter  + " de " + questionCounter  + " preguntas");

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