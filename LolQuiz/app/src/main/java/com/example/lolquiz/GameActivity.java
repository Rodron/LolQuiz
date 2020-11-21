package com.example.lolquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private  int questionCounter = 0;
    private  int correctCounter = 0;
    private int questionNumber;
    List<Button> options = new ArrayList<>();
    List<ImageButton> imageOptions = new ArrayList<>();
    List<Integer> alreadyUsed = new ArrayList<>();
    TextView category;
    TextView questionCount;
    TextView correctCount;
    View categoryBackground;
    List<List<String>> questions = new ArrayList<>();
    TextView questionBox;
    ImageView questionImg;
    FragmentManager fragmentController;
    FragmentTransaction transaction;
    Context context;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        context = this;
        sharedPref = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);

        questionNumber = Integer.parseInt(sharedPref.getString("questions", "5"));

        // Inicializamos los parámetros de la clase que usaremos durante la actividad.
        InputStream is = getResources().openRawResource(R.raw.preguntas);

        questions = new ArrayList<>();
        alreadyUsed = new ArrayList<>();

        category = (TextView) findViewById(R.id.category);
        categoryBackground = findViewById(R.id.categoryBackground);

        questionCount = (TextView) findViewById(R.id.questionCount);
        correctCount = (TextView) findViewById(R.id.correctCount);

        ImageButton back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goToMenu();
            }
        });

        // Procedemos a leer el fichero txt que contiene las preguntas y guardarlas en el ArrayList
        // questions.
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
            Toast.makeText(this, "Error al leer el archivo de preguntas", Toast.LENGTH_SHORT).show();
        }

        // Se llama por primera vez al método questiongenerator que iniciará el bucle del juego
        // seleccionando la primera pregunta
        questionGenerator();
    }

    @SuppressLint("SetTextI18n")
    public void questionGenerator() {
        // Esta función genera un indice aleatorio que selecciona la próxima pregunta a mostrar de
        // las que están contenidas en el ArrayList questions.
        Random random = new Random();
        int questionTitle = random.nextInt(questionNumber);

        // Además el índice seleccionado se añade a un array de preguntas usadas para que no pueda
        // volver a salir durante la partida y se comprueba dicho array para que asegurarnos de que
        // la pregunta no ha salido.
        while (alreadyUsed.contains(questionTitle)) {
            questionTitle = random.nextInt(questionNumber);
        }

        alreadyUsed.add(questionTitle);

        fragmentController = getSupportFragmentManager();
        transaction = fragmentController.beginTransaction();

        // Una vez seleccionada la pregunta se llama al fragmento correspondiente a su tipo mediante
        // un switch.
        switch (Integer.parseInt(questions.get(questionTitle).get(1).substring(0,1))) {
            case 0:
                StandardQuestionFragment fragment0 = new StandardQuestionFragment();
                transaction.replace(R.id.frameLayout, fragment0);
                transaction.commit();
                break;
            case 1:
                ImageAnswersQuestionsFragment fragment1 = new ImageAnswersQuestionsFragment();
                transaction.replace(R.id.frameLayout, fragment1);
                transaction.commit();
                break;
            case 2:
                ImageQuestionFragment fragment2 = new ImageQuestionFragment();
                transaction.replace(R.id.frameLayout, fragment2);
                transaction.commit();
                break;
        }
    }

    public void questionWriter(){
        // Este método será siempre llamado desde algún fragmento. Se encargará de tomar los botones
        // y los elementos de la pantalla y rellenarlos con los contenidos de la pregunta (enunciado,
        // respuestas, imágenes, color de la categoría, nombre de la categoría...).
        // Los botones en los que se coloca cada respuesta se seleccionan también de forma aleatoria.
        updateCounts();

        Random random = new Random();

        // Aquí se asigna el enunciado de la pregunta.
        questionBox.setText(questions.get(alreadyUsed.get(alreadyUsed.size()-1)).get(2));

        // De forma similar al questionGenerator, se asignab las respuestas a cada botópn de forma
        // aleatoria de tal forma que no siempre le aparezcan en el mismo orden al usuario
        int buttonIndex = random.nextInt(4);
        ArrayList<Integer> usedButtons = new ArrayList<Integer>();

        usedButtons.add(buttonIndex);

        String questionType = questions.get(alreadyUsed.get(alreadyUsed.size()-1)).get(1);
        switch (Integer.parseInt(""+questionType.substring(0,1))){
            case 2:
                // Las preguntas de tipo 2 cambian los mismos elementos que las de tipo 0 con la
                // diferencia de que las de tipo 2 necesitan además una imagen para el enunciado.
                questionImg.setImageResource(getImageId(this, questionType.substring(1)));
            case 0:
                // Las preguntas de tipo 0 son las que no incluyen imágenes ni en los botones ni en
                // las respuestas. Solo cambian el contenido de texto de los enuncaidos y los botones.
                options.get(buttonIndex).setText(questions.get(alreadyUsed.get(alreadyUsed.size()-1)).get(3));
                options.get(buttonIndex).setTag(0);

                while(usedButtons.size() != 4) {
                    while (usedButtons.contains(buttonIndex)) {
                        buttonIndex = random.nextInt(4);
                    }
                    usedButtons.add(buttonIndex);

                    options.get(buttonIndex).setText(questions.get(alreadyUsed.get(alreadyUsed.size()-1)).get(2 + usedButtons.size()));
                    options.get(buttonIndex).setTag(1);
                }
                break;
            case 1:
                // Las preuntas del tipo 1 son las que tienen imágenes en las respuestas.
                imageOptions.get(buttonIndex).setImageResource(getImageId(this, questions.get(alreadyUsed.get(alreadyUsed.size()-1)).get(3)));
                imageOptions.get(buttonIndex).setTag(0);

                while(usedButtons.size() != 4) {
                    while (usedButtons.contains(buttonIndex)) {
                        buttonIndex = random.nextInt(4);
                    }
                    usedButtons.add(buttonIndex);

                    imageOptions.get(buttonIndex).setImageResource(getImageId(this, questions.get(alreadyUsed.get(alreadyUsed.size()-1)).get(2 + usedButtons.size())));
                    imageOptions.get(buttonIndex).setTag(1);
                }
                break;
        }

        // Aquí se cambia el texto y el color de la categoría, esto funciona igual independientemente
        // del fragmento.
        switch(questions.get(alreadyUsed.get(alreadyUsed.size()-1)).get(0)){
            case "C":
                category.setText("Campeones y habilidades");
                categoryBackground.setBackgroundColor(Color.parseColor("#272B71"));
                break;
            case "L" :
                category.setText("Lore del juego");
                categoryBackground.setBackgroundColor(Color.parseColor("#1C9E71"));
                break;
        }
    }

    public boolean checkAnswer(View v){
        // Este método va contando las respuestas correctas que selecciona el usuario (las cuales
        // tienen un tag 0 asignado). El método devolverá el boolean next el cual definirá si debemos
        // pasar a una nueva pregunta o no, esto se hace para que al llegar a la última pregunta el
        // booleano pasará a ser false y esto hará un trigger en el fragmento para ejecutar el método
        // que nos lleva a la actividad de resultados.
        boolean next = true;

        questionCounter ++;
        if (v.getTag().equals(0)){
            correctCounter ++;
        }

        if(questionCounter == questionNumber){
            next = false;
        }

        return next;
    }

    // ----------------------------------------------------------------------------------------------
    // Métodos auxiliares para cambiar de actividad, ejecutar funcione, recibir y asignar referencias
    // a los elementos pertenecientes a los fragmentos...
    public void goToMenu(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToResults(){
        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtra("questionCounter",questionCounter);
        intent.putExtra("correctCounter",correctCounter);
        startActivity(intent);
    }

    public void updateCounts(){
        questionCount.setText(questionCounter+"/"+questionNumber);
        correctCount.setText(""+correctCounter);
    }

    public void receiveQuestion (TextView question){        questionBox = question;    }

    public void receiveQuestionImage (ImageView questionImage){        questionImg = questionImage;    }

    public void receiveButtons (List<Button> receivedOptions){
        options = receivedOptions;
    }

    public void receiveImageButtons (List<ImageButton> receivedOptions){
        imageOptions = receivedOptions;
    }

    public void clickButton(){
        questionGenerator();
    }

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }
    // ----------------------------------------------------------------------------------------------
}