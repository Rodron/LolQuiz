package com.example.lolquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class OptionsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    int questNumber = 20;
    int categCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        CheckBox [] categs = new CheckBox[4];



        categs[0] = (CheckBox) findViewById(R.id.category1);
        categs[1] = (CheckBox) findViewById(R.id.category2);
        categs[2] = (CheckBox) findViewById(R.id.category2);
        categs[3] = (CheckBox) findViewById(R.id.category3);

        for(int i = 0; i < 4; i++) {

            categs[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        checkAdd(1);
                    }
                    else {
                        checkAdd(-1);
                    }
                }
            });

            categs[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (categCount == 0){
                        ((CheckBox)v).setChecked(true);
                    }
                    //Toast.makeText(((CheckBox)v).getContext(), "" + categCount, Toast.LENGTH_SHORT).show();
                }
            });
        }

        ImageButton back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goToMenu();
            }
        });

        TextView questions = (TextView) findViewById(R.id.questions);
        questions.setText("" + questNumber);

        ImageButton addQuestion = (ImageButton) findViewById(R.id.addQuestion);
        addQuestion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                categs[0].setChecked(true);
                if (questNumber<20){
                    questNumber++;
                    questions.setText("" + questNumber);
                }
            }
        });

        ImageButton subsQuestion = (ImageButton) findViewById(R.id.subsQuestion);
        subsQuestion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (questNumber>5) {
                    questNumber--;
                    questions.setText("" + questNumber);
                }
            }
        });
    }

    public int getCategsCheck(CheckBox[] categs){
        int count = 0;
        for(CheckBox check : categs){
            if (check.isChecked()){
                count++;
            }
        }
        return count;
    }

    public void checkAdd(int n){
        categCount += n;
    }


    public void goToMenu(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}