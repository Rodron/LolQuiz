package com.example.lolquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class OptionsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    int questNumber;
    int categCount = 0;
    Context context;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        CheckBox [] categs = new CheckBox[4];
        TextView questions = (TextView) findViewById(R.id.questions);
        EditText username = (EditText) findViewById(R.id.insertUsername);

        context = this;
        sharedPref = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);

        categs[0] = (CheckBox) findViewById(R.id.category1);
        categs[1] = (CheckBox) findViewById(R.id.category2);
        categs[2] = (CheckBox) findViewById(R.id.category3);
        categs[3] = (CheckBox) findViewById(R.id.category4);

        String categCode = sharedPref.getString("categories", "1111");
        for(int i = 0; i < 4; i++) {
            categs[i].setChecked(categCode.charAt(i)-'0' == 1);

            if (categs[i].isChecked()){
                categCount++;
            }

            categs[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        checkAdd(1);
                    }
                    else {
                        checkAdd(-1);
                        if(questNumber>categCount*5){
                            questNumber = categCount*5;
                            assignQuestions(questions);
                        }
                    }
                    categsCheck(categs);
                }
            });
        }

        categsCheck(categs);

        questNumber = Integer.parseInt(sharedPref.getString("questions", "20"));

        username.setText(sharedPref.getString("user","Anónimo"));
        username.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                sharedPref.edit().putString("user", s.toString()).apply();
            }
        });

        ImageButton back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goToMenu();
            }
        });

        assignQuestions(questions);

        ImageButton addQuestion = (ImageButton) findViewById(R.id.addQuestion);
        addQuestion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (questNumber<categCount*5){
                    questNumber++;
                    assignQuestions(questions);
                }
                else if (categCount != 4)
                    Toast.makeText(context, "Se ha alcanzado el número máximo de preguntas (seleccione más categorías si desea agregar más preguntas).", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(context, "Se ha alcanzado el número máximo de preguntas.", Toast.LENGTH_SHORT).show();
            }
        });

        ImageButton subsQuestion = (ImageButton) findViewById(R.id.subsQuestion);
        subsQuestion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (questNumber>4) {
                    questNumber--;
                    assignQuestions(questions);
                }
                else
                    Toast.makeText(context, "Se ha alcanzado el número mínimo de preguntas.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void categsCheck(CheckBox[] categs){
        String s = "";
        for(CheckBox check : categs){
            if (check.isChecked()){
                s += 1;
                check.setEnabled(true);
                if(categCount == 1) {
                    check.setEnabled(false);
                }
            }else {
                s+= 0;
            }
        }

        sharedPref.edit().putString("categories", s).apply();
    }

    public void checkAdd(int n){
        categCount += n;
    }


    public void goToMenu(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void assignQuestions(TextView questions){
        questions.setText("" + questNumber);
        sharedPref.edit().putString("questions", ""+questNumber).apply();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}