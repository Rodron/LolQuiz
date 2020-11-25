package com.example.lolquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.database.Cursor;
import android.content.SharedPreferences;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private DbManager dbManager;
    private  int questionCounter = 0;
    private  int correctCounter = 0;
    private int questionNumber;
    private int questionPoolLimit;
    private String categCode;
    List<Button> options = new ArrayList<>();
    List<ImageButton> imageOptions = new ArrayList<>();
    List<Integer> alreadyUsed = new ArrayList<>();
    TextView category;
    TextView questionCount;
    TextView correctCount;
    TextView videoMessage;
    View categoryBackground;
    List<List<String>> questions = new ArrayList<>();
    List<String> categs;
    TextView questionBox;
    ImageView questionImg;
    VideoView questionVid;
    FragmentManager fragmentController;
    FragmentTransaction transaction;
    Context context;
    SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        dbManager = new DbManager(this);
        dbManager.deleteAll();

        dbManager.insertEntry("C","0","¿Cuál de estas habilidades no pertenece a Vel'Koz?",
                "Rayo de la muerte",
                "Grieta del vacío",
                "Fisión de plasma",
                "Perturbación tectónica");
        dbManager.insertEntry("C", "1","¿Cuál de estos campeones es más antiguo?",
                "veigar",
                "kassadin",
                "mundo",
                "lux");
        dbManager.insertEntry("C","0","A fecha de 24/10/2020 ¿cuál de estos campeones tiene un mayor cooldown en su ultimate al nivel 6 (sin aplicar items o runas de CDR)?",
                "Ryze",
                "Karthus",
                "Twisted Fate",
                "Shen");
        dbManager.insertEntry("C","0","A fecha de 24/10/2020 ¿cuál de estos campeones cuenta con el mayor número de skins?",
                "Ezreal",
                "Ornn",
                "Miss Fortune",
                "Lux");
        dbManager.insertEntry("C","1","¿Cuál de estas habilidades inmoviliza al campeón enemigo durante más tiempo (todas las habilidades están a nivel 1)?",
                "burbujador",
                "hechizoos",
                "llamaradasol",
                "cadenaset");
        dbManager.insertEntry("L","2zaun","¿Qué personaje no proviene de esta región de Runaterra?",
                "Ziggs",
                "Dr. Mundo",
                "Singed",
                "Viktor");
        dbManager.insertEntry("L","1","¿Cuál de estos personajes no es un darkin?",
                "diana",
                "aatrox",
                "varus",
                "rhaast");
        dbManager.insertEntry("L","0","¿Cuál de estos personajes no pertenece a la Orden Kinkou?",
                "Yasuo",
                "Akali",
                "Kennen",
                "Shen");
        dbManager.insertEntry("L","0","¿Cuál de estos pares de personajes son hermanos?",
                "Lux y Garen",
                "Xayah y Rakan",
                "Sylas y Ezreal",
                "Teemo y Corki");
        dbManager.insertEntry("L","0","¿Quién es el mentor/la mentora de Wukong?",
                "Maestro Yi",
                "Lee Sin",
                "Akali",
                "Yasuo");
        dbManager.insertEntry("O","3fakerwhat","¿En qué año ocurrió esta mítica jugada?", "2013", "2011", "2009", "2015");
        dbManager.insertEntry("O","3zhonyas","¿Que objeto se ha activado en este video?", "Reloj de Arena de Zhonya", "Quimiotanque Turbo", "Chupasangre", "Presagio de Randuin");
        dbManager.insertEntry("O","3velo","¿A que objeto le pertenece este escudo?", "Velo del Hada de la Muerte", "Velo de la Noche", "Armadura de Warmog", "Fuerza de la Naturaleza");
        dbManager.insertEntry("O","3randuin","El objeto mostrado en el video permite...", "Reducir daño y ralentizar", "Aumentar daño", "Aturdir", "Curar");
        dbManager.insertEntry("O","3flash","¿Cómo se llama el siguiente hechizo de invocador?", "Destello", "Extenuación", "Aplastar", "Prender");
        dbManager.insertEntry("E","3heraldo","¿En qué minuto ocurre esto?", "19 : 45", "17 : 20", "16 : 50", "18 : 30");
        dbManager.insertEntry("E","3peke","¿A qué temporada pertenece esta partida?", "1", "5", "8", "2");
        dbManager.insertEntry("E","3robots","¿A qué fase del mundial pertenece este clip?", "Grupos", "Cuartos", "Semifinal", "Final");
        dbManager.insertEntry("E","3insec","¿Con el nombre de qué jugador fue bautizada la siguiente mecánica?", "InSec", "Bjergsen", "Faker", "Madllife");
        dbManager.insertEntry("E","3drake","¿Qué equipos jugaban en esta final de Worlds?", "SKT y SSG", "FNC y SKT", "TSM y C9", "RNG y FLW");


        // Inicializamos los parámetros de la clase que usaremos durante la actividad.
        context = this;
        InputStream is = getResources().openRawResource(R.raw.preguntas);

        sharedPref = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        questionNumber = Integer.parseInt(sharedPref.getString("questions", "5"));
        categCode = sharedPref.getString("categories", "1111");

        categs = new ArrayList<>();

        for(int i = 0; i < 4; i++){
            if(categCode.charAt(i)-'0' == 1){
                switch (i){
                    case 0:
                        categs.add("C");
                        break;
                    case 1:
                        categs.add("L");
                        break;
                    case 2:
                        categs.add("O");
                        break;
                    case 3:
                        categs.add("E");
                        break;
                }
            }
        }

        questionPoolLimit = categs.size()*5;

        questions = new ArrayList<>();
        alreadyUsed = new ArrayList<>();

        category = (TextView) findViewById(R.id.ranking);
        categoryBackground = findViewById(R.id.rankingBackground);

        questionCount = (TextView) findViewById(R.id.questionCount);
        correctCount = (TextView) findViewById(R.id.correctCount);

        ImageButton back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goToMenu();
            }
        });

        setQuestions(dbManager.getEntries());
        questionGenerator();
    }

    @SuppressLint("SetTextI18n")
    public void questionGenerator() {
        // Esta función genera un indice aleatorio que selecciona la próxima pregunta a mostrar de
        // las que están contenidas en el ArrayList questions.
        Random random = new Random();

        int questionTitle = random.nextInt(questionPoolLimit);

        // Además el índice seleccionado se añade a un array de preguntas usadas para que no pueda
        // volver a salir durante la partida y se comprueba dicho array para que asegurarnos de que
        // la pregunta no ha salido.
        //|| !categs.contains(questions.get(questionTitle).get(0))
        while (alreadyUsed.contains(questionTitle)) {
            questionTitle = random.nextInt(questionPoolLimit);
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
            case 3:
                VideoQuestionFragment fragment3 = new VideoQuestionFragment();
                transaction.replace(R.id.frameLayout,fragment3);
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
            case 3:
                // Las preguntas de tipo 3 cambian los mismos elementos que las de tipo 0 con la
                // diferencia de que las de tipo 3 necesitan además un video para el enunciado.
                String videoPath = "android.resource://" + getPackageName() + "/" + getVideoId(this, questionType.substring(1));
                Uri uri = Uri.parse(videoPath);
                questionVid.setVideoURI(uri);
                questionVid.setOnTouchListener(new View.OnTouchListener(){
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        videoMessage.setVisibility(View.INVISIBLE);
                        return false;
                    }
                });
                MediaController mediaController = new MediaController(this);
                questionVid.setMediaController(new MediaController(this));
            case 2:
            case 0:
                // Las preguntas de tipo 2 cambian los mismos elementos que las de tipo 0 con la
                // diferencia de que las de tipo 2 necesitan además una imagen para el enunciado.
                if(Integer.parseInt(""+questionType.substring(0,1))==2) {
                    questionImg.setImageResource(getImageId(this, questionType.substring(1)));
                }
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
            case "O":
                category.setText("Objetos y mecánicas");
                categoryBackground.setBackgroundColor(Color.parseColor("#CC7D37"));
                break;
            case "E":
                category.setText("Esports");
                categoryBackground.setBackgroundColor(Color.parseColor("#AA3A3A"));
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

    public void receiveQuestionVideo (VideoView questionVideo, TextView videoMsg){
        questionVid = questionVideo;
        videoMessage = videoMsg;
    }

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

    public static int getVideoId(Context context, String videoName){
        return context.getResources().getIdentifier("raw/" + videoName, null, context.getPackageName());
    }

    // ----------------------------------------------------------------------------------------------
    // Mediante el cursor que se creara con el get entries , se introducirá en el arrayList questions.
    // De esa manera la aplicación seguirá funcionando de forma idéntica a la primera versión.
    public void setQuestions(Cursor entries) {

        List<String> aux = new ArrayList<String>();
        String saux;
        entries.moveToFirst();
        while (!entries.isAfterLast()) {
            aux.clear();

            saux = entries.getString(entries.getColumnIndex(DbContract.DbEntry.COLUMN_NAME_CATEGORY));
            if(categs.contains(saux)){
                aux.add(saux);
                saux = entries.getString(entries.getColumnIndex(DbContract.DbEntry.COLUMN_NAME_FRAGMENT));
                aux.add(saux);
                saux = entries.getString(entries.getColumnIndex(DbContract.DbEntry.COLUMN_NAME_TITLE));
                aux.add(saux);
                saux = entries.getString(entries.getColumnIndex(DbContract.DbEntry.COLUMN_NAME_ANSWER1));
                aux.add(saux);
                saux = entries.getString(entries.getColumnIndex(DbContract.DbEntry.COLUMN_NAME_ANSWER2));
                aux.add(saux);
                saux = entries.getString(entries.getColumnIndex(DbContract.DbEntry.COLUMN_NAME_ANSWER3));
                aux.add(saux);
                saux = entries.getString(entries.getColumnIndex(DbContract.DbEntry.COLUMN_NAME_ANSWER4));
                aux.add(saux);
                questions.add(new ArrayList<>(aux)); //add the item
            }
            entries.moveToNext();
        }
    }


    @Override
    public void onBackPressed() {
        goToMenu();
    }
}
