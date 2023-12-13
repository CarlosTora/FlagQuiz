package com.flagquiz.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.flagquiz.Constants;
import com.flagquiz.MainActivity;
import com.flagquiz.R;
import com.flagquiz.database.DatabaseHelper;
import com.flagquiz.model.Flag;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MapFragment extends Fragment {

    private ImageView flagImageView;
    private Button[] optionButtons = new Button[4];
    private int score = 0;
    private String correctOption;
    private DatabaseHelper databaseHelper;
    private ProgressBar progressBar;
    private TextView hits;
    private TextView record;
    private TextView time;
    private TextView txt_scoreGame;
    private TextView indicationPoints;
    private TextView infoPreparation;
    private Handler handler = new Handler();
    private Runnable runnable;
    private boolean responseError = false;
    private String difficulty;
    private String modeGame;
    private int levelGame;
    private List<Flag> listFlagGame;
    private int positionList;
    private boolean starGame;
    private boolean inDetails;
    private Button btt_starGame;

    private ImageView[] lifesImages = new ImageView[3];

    private int lifeCount;
    private Flag flagSelectGame;
    private String labelCorrectOption;


    /**
     * Funcion para cuando de cierra el fragment, habilitar el boton de lenguaje
     */
    @Override
    public void onDetach() {
        super.onDetach();

        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).showLanguageButton();
        }
    }
    /**
     * Funcion para cuando de abre el fragment, deshabilitar el boton de lenguaje
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof MainActivity) {
            ((MainActivity) context).hideLanguageButton();
        }
    }

    public static MapFragment newInstance(String difficulty, List<Flag> listFlags, String modeGame, int level) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString("difficulty", difficulty);
        args.putString("modeGame", modeGame);
        args.putSerializable("list", (Serializable)  listFlags);
        args.putInt("level", level);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.maps_fragment, container, false);
        Bundle args = getArguments();
        if (args != null) {
            difficulty = args.getString("difficulty");
            modeGame = args.getString("modeGame");
            levelGame = args.getInt("level");
            listFlagGame = (List<Flag>) getArguments().getSerializable("list");
        }

        Collections.shuffle(listFlagGame);
        flagImageView = view.findViewById(R.id.flag_game);
        optionButtons[0] = view.findViewById(R.id.btt_opc1);
        optionButtons[1] = view.findViewById(R.id.btt_opc2);
        optionButtons[2] = view.findViewById(R.id.btt_opc3);
        optionButtons[3] = view.findViewById(R.id.btt_opc4);
        btt_starGame = view.findViewById(R.id.btt_star_hardcore);

        hits = view.findViewById(R.id.txt_hits);
        txt_scoreGame = view.findViewById(R.id.txt_scoreGame);
        infoPreparation = view.findViewById(R.id.txt_infoPreparation);
        record = view.findViewById(R.id.txt_record);
        time = view.findViewById(R.id.txt_temp_crono);
        indicationPoints = view.findViewById(R.id.txt_indicatorPoints);
        indicationPoints.setVisibility(View.GONE);  // OCULTAR ADD POINTS SCORE
        lifesImages[0] = view.findViewById(R.id.img_life1M);
        lifesImages[1] = view.findViewById(R.id.img_life2M);
        lifesImages[2] = view.findViewById(R.id.img_life3M);

        databaseHelper = new DatabaseHelper(requireContext());
        positionList = 0;
        lifeCount = 3;
        starGame = false;

        hits.setText((String.valueOf(score)));
        setRecordText();
        // PROGRES BAR
        progressBar = view.findViewById(R.id.progress_bar);

        /**
         *  SI ES HARDCORE DEBEMOS DE RECOGER EL NIVEL ASI COMO SU RECORD
         */
        if(modeGame.equals(Constants.modeHardcore)){
            time.setText(getString(R.string.TXT_NIVEL)+" "+levelGame);
            txt_scoreGame.setText(getString(R.string.TXT_ACIERTOS));
        }
        else {
            time.setVisibility(View.GONE);
        }

        setTextInfoPreparation();
        //  OCULTAMOS COSAS DEL JUEGO
        flagImageView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        lifesImages[0].setVisibility(View.GONE);
        lifesImages[1].setVisibility(View.GONE);
        lifesImages[2].setVisibility(View.GONE);

        for (Button btt : optionButtons) {
            btt.setVisibility(View.GONE);
        }
        btt_starGame.setOnClickListener(v -> starGame(view));
        return view;
    }

    private void setTextInfoPreparation() {

        switch (modeGame) {
            case Constants.modeHardcore:
                infoPreparation.setText(getString(R.string.LABEL_PREP_HARD));
                break;
            case Constants.modeMinute:
                infoPreparation.setText(getString(R.string.LABEL_PREP_MINUTE));
                break;
            case Constants.modeFlag:
                infoPreparation.setText(getString(R.string.LABEL_PREP_FLAG));
                break;
        }
    }

    private void starGame(View view) {
        starGame = true;
        btt_starGame.setVisibility(View.GONE);
        infoPreparation.setVisibility(View.GONE);

        flagImageView.setVisibility(View.VISIBLE);
        for (Button btt : optionButtons) {
            btt.setVisibility(View.VISIBLE);
        }

        for (Button button : optionButtons) {
            button.setOnClickListener(v -> handleOptionClick(button.getText().toString()));
        }
        if(modeGame.equals("flagMode")) {
            lifesImages[0].setVisibility(View.VISIBLE);
            lifesImages[1].setVisibility(View.VISIBLE);
            lifesImages[2].setVisibility(View.VISIBLE);
        }
        if(!modeGame.equals("flagMode")) {
            progressBar.setVisibility(View.VISIBLE);
            time.setVisibility(View.VISIBLE);
            // Configurar el Runnable para actualizar el progreso cada 100 milisegundos
            runnable = new Runnable() {
                @Override
                public void run() {
                    if(!responseError) {
                        progressBar.setProgress(progressBar.getProgress() - 50); // Restar 10 ms
                        if (progressBar.getProgress() <= 0 ) {
                            showCorrectOption();
                            updateRecord();
                            showSummaryDialog();
                        } else {
                            changeColorBar();
                            handler.postDelayed(this, 50); // Ejecutar nuevamente en 10 ms
                        }
                    }
                }
            };
            updateTimeBarByScore();
        }
        loadRandomFlagAndOptions();

        // Si es hardcore como se reanuda y para el time, se inicia en "loadRandomFlagAndOptions"
        if(!modeGame.equals(Constants.modeHardcore)){
            handler.postDelayed(runnable, 100); // Esto inicia el tiempo
        }

        // ESTO ES PARA BLOQUEAR LAS PULSACIONES FAKE
        ConstraintLayout mapsConstraint = view.findViewById(R.id.const_zoneFlags);
        ConstraintLayout pointsConstraint = view.findViewById(R.id.const_points);
        mapsConstraint.setOnClickListener(v -> {});
        pointsConstraint.setOnClickListener(v -> {});
    }

    /**
     * Metodo para mostrar tras fallar, cual era la opcion correcta
     */
    private void showCorrectOption() {
        labelCorrectOption = getStringResource(correctOption);
        for (Button button : optionButtons) {
            if (button.getText().toString().equals(labelCorrectOption)) {
                button.setBackgroundColor(Color.parseColor("#FF7EF8AB"));
            }
            else{
                button.setAlpha(0.3f);
            }
        }
    }
    private void getActualRecordHardcore() {
        switch (levelGame) {
            case 1:
                record.setText(String.valueOf(MainActivity.user.getHardcore_1()));
                break;
            case 2:
                record.setText(String.valueOf(MainActivity.user.getHardcore_2()));
                break;
            case 3:
                record.setText(String.valueOf(MainActivity.user.getHardcore_3()));
                break;
            case 4:
                record.setText(String.valueOf(MainActivity.user.getHardcore_4()));
                break;
            case 5:
                record.setText(String.valueOf(MainActivity.user.getHardcore_5()));
                break;
            case 6:
                record.setText(String.valueOf(MainActivity.user.getHardcore_6()));
                break;
            case 7:
                record.setText(String.valueOf(MainActivity.user.getHardcore_7()));
                break;
            case 8:
                record.setText(String.valueOf(MainActivity.user.getHardcore_8()));
                break;
            case 9:
                record.setText(String.valueOf(MainActivity.user.getHardcore_9()));
                break;
            case 10:
                record.setText(String.valueOf(MainActivity.user.getHardcore_10()));
                break;
        }
    }
    private void getActualRecordMinute() {
        switch (difficulty) {
            case Constants.EASY:
                if(modeGame.equals(Constants.modeMinute))
                    record.setText(String.valueOf(MainActivity.user.getTimeEasy()));
                else if(modeGame.equals(Constants.modeFlag))
                    record.setText(String.valueOf(MainActivity.user.getFlagEasy()));
                break;
            case Constants.MEDIUM:
                if(modeGame.equals(Constants.modeMinute))
                    record.setText(String.valueOf(MainActivity.user.getTimeMedium()));
                else if(modeGame.equals(Constants.modeFlag))
                    record.setText(String.valueOf(MainActivity.user.getFlagMedium()));
                break;
            case Constants.HARD:
                if(modeGame.equals(Constants.modeMinute))
                    record.setText(String.valueOf(MainActivity.user.getTimeHard()));
                else if(modeGame.equals(Constants.modeFlag))
                    record.setText(String.valueOf(MainActivity.user.getFlagHard()));
                break;
            case Constants.EXTREME:
                if(modeGame.equals(Constants.modeMinute))
                    record.setText(String.valueOf(MainActivity.user.getTimeExtreme()));
                else if(modeGame.equals(Constants.modeFlag))
                    record.setText(String.valueOf(MainActivity.user.getFlagExtreme()));
                break;
            case Constants.INSANE:
                if(modeGame.equals(Constants.modeMinute))
                    record.setText(String.valueOf(MainActivity.user.getTimeInsane()));
                else if(modeGame.equals(Constants.modeFlag))
                    record.setText(String.valueOf(MainActivity.user.getFlagInsane()));
                break;
        }
    }

    private void setRecordText() {
        switch (modeGame) {
            case Constants.modeHardcore:
                getActualRecordHardcore();
                break;
            case Constants.modeMinute:
            case Constants.modeFlag:
                getActualRecordMinute();
                break;
        }
    }

    private void getActualRecordFlag() {
    }

    /**
     *  SETEA EL COLOR DE LA BARRA SEGUN EL TIEMPO RESTANTE
     */
    private void changeColorBar() {
        int totalProgress = progressBar.getMax();
        int currentProgress = progressBar.getProgress();
        double percentage = (double) currentProgress / totalProgress * 100;

        if(modeGame.equals("minuteMode")) {
            if(progressBar.getProgress() >= 10000) {
                time.setText(String.valueOf(progressBar.getProgress()).substring(0,2));
            }
            else if(progressBar.getProgress() >= 1000) {
                time.setText(String.valueOf(progressBar.getProgress()).substring(0,1));
            }
            else {
                time.setText("0");
            }
        }

        // COLOR PROGRESBAR
        if (percentage >= 50) {
            progressBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        } else if (percentage >= 20) {
            progressBar.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
        } else {
            progressBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
        }
    }

    /**
     * Metodo para filtrar las banderas y solo seleccionar la que coincida con la dificultad
     * @param difficulty dificultad de la bandera segun el nivel de juego
     * @return bandera adecuada al nivel
     */
    private Flag logicGetFlagLevel(int difficulty){
        Flag flagSeleted = listFlagGame.get(positionList);

        while (flagSeleted.getDifficulty() > difficulty) {
            if (positionList < listFlagGame.size()-1) {
                positionList++;
                flagSeleted = listFlagGame.get(positionList);
            } else {
                Collections.shuffle(listFlagGame);
                positionList = -1;
            }
        }
        return flagSeleted;
    }

    /**
     * Metodo para asignar dependiendo el nivel, la dificultad de las banderas
     * @return retorna la bandera adecuada
     */
    private Flag getFlagByLevel(){
        Flag flagSeleted;
        if (levelGame<=2) {
            if (positionList >= listFlagGame.size()) {
                positionList = 0;
            }
            flagSeleted = logicGetFlagLevel(1);
        }
        else if (levelGame<=4) {
            if (positionList >= listFlagGame.size()) {
                Collections.shuffle(listFlagGame);
                positionList = 0;
            }
            flagSeleted = logicGetFlagLevel(2);
        }
        else if (levelGame<=6) {
            if (positionList >= listFlagGame.size()) {
                Collections.shuffle(listFlagGame);
                positionList = 0;
            }
            flagSeleted = logicGetFlagLevel(3);
        }
        else if (levelGame<=8) {
            if (positionList >= listFlagGame.size()) {
                Collections.shuffle(listFlagGame);
                positionList = 0;
            }
            flagSeleted = logicGetFlagLevel(4);
        }
        else {
            flagSeleted = listFlagGame.get(positionList);
        }
        return flagSeleted;
    }

    /**
     * Metodo para cargar las banderas, asi como las 4 opciones
     */
    private void loadRandomFlagAndOptions() {
        if(positionList >= listFlagGame.size()) {
            positionList = 0;
        }

        flagSelectGame = getFlagByLevel();

        if (flagSelectGame != null) {
            // Cargar los valores de la bandera aleatoria en la interfaz
            int resourceId = getResources().getIdentifier(flagSelectGame.getImage(), "drawable", requireContext().getPackageName());
            flagImageView.setImageResource(resourceId);

            // Obtener nombres aleatorios de países para las opciones
            String[] randomCountryNames = databaseHelper.getRandomCountryNames(flagSelectGame.getName(), 3,flagSelectGame.getRegion());

            correctOption = flagSelectGame.getName();
            // Asignar opciones de respuesta a los botones
            int correctOptionIndex = new Random().nextInt(optionButtons.length);
            for (int i = 0; i < optionButtons.length; i++) {
                if (i == correctOptionIndex) {
                    optionButtons[i].setText(getStringResource(flagSelectGame.getName())); // Opción correcta
                } else {
                    int incorrectIndex = i < correctOptionIndex ? i : i - 1;
                    String randomCountryName = randomCountryNames[incorrectIndex];
                    optionButtons[i].setText(getStringResource(randomCountryName)); // Opciones incorrectas
                }
            }
            // Como en hardcore se detiene el tiempo al acertar, aqui debe volver a iniciarlo
            if(modeGame.equals(Constants.modeHardcore)) {
                handler.postDelayed(runnable, 100); // Iniciar el progreso temporizador
            }
            if(!modeGame.equals("minuteMode")){
                updateTimeBarByScore();
            }
        }
    }

    private String getStringResource(String resourceName) {
        int resourceId = getResources().getIdentifier(resourceName, "string", requireContext().getPackageName());
        return getResources().getString(resourceId);
    }

    @Override
    public void onDestroyView() {
        responseError = true;
        super.onDestroyView();
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }


    private void handleOptionClick(String selectedOption) {
        if(modeGame.equals(Constants.modeHardcore)) {
            handler.removeCallbacks(runnable);// Esto detiene el tiempo
        }
        if (progressBar.getProgress() > 0 ) {


            // Desactivar todos los botones para evitar clics repetidos mientras se procesa la respuesta
            for (Button button : optionButtons) {
                button.setEnabled(false);
            }

            boolean isCorrect = getStringResource(correctOption).equals(selectedOption);
            // Cambiar el color del botón seleccionado según si la respuesta es correcta o incorrecta
            for (Button button : optionButtons) {
                if (button.getText().toString().equals(selectedOption)) {
                    if (isCorrect) {
                        button.setBackgroundColor(Color.GREEN);
                    } else {
                        button.setBackgroundColor(Color.RED);
                    }
                }
            }

            // Si la respuesta es correcta, espera 0.5 segundo y luego cargar una nueva bandera con opciones
            if (isCorrect) {
                new Handler().postDelayed(() -> {
                    positionList++;
                    loadRandomFlagAndOptions();
                    // Habilitar todos los botones nuevamente
                    for (Button button : optionButtons) {
                        button.setEnabled(true);
                        button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.bg_buttons));
                    }
                    indicationPoints.setVisibility(View.GONE);
                }, 500);
                // SUMA PUNTOS
                showPoints(true);
                score++;
                hits.setText((String.valueOf(score)));
            } else {

                // Si la respuesta es incorrecta, para el tiempo,guarda (si es necesario) en record y cierra fragment
                new Handler().postDelayed(() -> {
                    resolveError();
                    indicationPoints.setVisibility(View.GONE);
                }, 500);
                showPoints(false);
            }
        }
    }

    private void showPoints(boolean success) {
        if(success) {
            indicationPoints.setVisibility(View.VISIBLE);
            indicationPoints.setTextColor(Color.GREEN);
            indicationPoints.setText("+1");
        }
        else if( !modeGame.equals(Constants.modeHardcore) && !modeGame.equals(Constants.modeFlag)) {
            indicationPoints.setVisibility(View.VISIBLE);
            indicationPoints.setTextColor(Color.RED);
            indicationPoints.setText("-1");
        }
    }

    private void resolveError() {
        switch (modeGame) {
            case Constants.modeHardcore:
                responseError = true;
                showCorrectOption();
                updateRecord();
                showSummaryDialog();
                //closeFragment();
                break;
            case Constants.modeMinute:
                for (Button button : optionButtons) {
                    button.setEnabled(true);
                }
                if(score>0) {
                    score--;
                    hits.setText((String.valueOf(score)));
                }
                break;
            case Constants.modeFlag:
                for (Button button : optionButtons) {
                    button.setEnabled(true);
                }
                if(lifeCount > 0) {
                    lifeCount--;
                    lifesImages[lifeCount].setImageResource(R.drawable.icon_life_false);
                    hits.setText((String.valueOf(score)));
                    if(lifeCount == 0) {
                        showCorrectOption();
                        updateRecord();
                        showSummaryDialog();
                    }
                }
                break;
        }
    }

    private void updateTimeBarByScore() {
        switch (modeGame){
            case Constants.modeHardcore:
                setTimeAccordingLevel();
                break;

            case "minuteMode":
                progressBar.setMax(60000);
                progressBar.setProgress(60000);
                break;
        }
    }

    /**
     * Funcion para poner el tiempo de respuesta segun el nivel en HardcoreMode
     */
    private void setTimeAccordingLevel() {

        if (levelGame == 1 ) {
            progressBar.setMax(4000);
            progressBar.setProgress(4000);
        } else if (levelGame == 2 ) {
            progressBar.setMax(3700);
            progressBar.setProgress(3700);
        } else if (levelGame == 3 ) {
            progressBar.setMax(3700);
            progressBar.setProgress(3700);
        } else if (levelGame == 4 ) {
            progressBar.setMax(3400);
            progressBar.setProgress(3400);
        } else if (levelGame == 5 ) {
            progressBar.setMax(3400);
            progressBar.setProgress(3400);
        } else if (levelGame == 6 ) {
            progressBar.setMax(3000);
            progressBar.setProgress(3000);
        } else if (levelGame == 7 ) {
            progressBar.setMax(3000);
            progressBar.setProgress(3000);
        } else if (levelGame == 8 ) {
            progressBar.setMax(2800);
            progressBar.setProgress(8800);
        } else if (levelGame == 9 ) {
            progressBar.setMax(2800);
            progressBar.setProgress(2800);
        } else  {
            progressBar.setMax(2500);
            progressBar.setProgress(2500);
        }
    }

    /**
     * Funcion para updatear los records del usuario y de base de datos
     */
    private void updateRecord() {

        switch (difficulty) {
            case Constants.EASY:
                if(modeGame.equals(Constants.modeMinute) && score > MainActivity.user.getTimeEasy() ) {
                    databaseHelper.updateUserPoints(MainActivity.user.getId(),DatabaseHelper.COLUMN_TIME_EASY,score);
                    MainActivity.user.setTimeEasy(score);
                }
                else if(modeGame.equals(Constants.modeFlag) && score > MainActivity.user.getFlagEasy() ) {
                    databaseHelper.updateUserPoints(MainActivity.user.getId(),DatabaseHelper.COLUMN_FLAG_EASY,score);
                    MainActivity.user.setFlagEasy(score);
                }
                break;
            case Constants.MEDIUM:
                if(modeGame.equals(Constants.modeMinute) && score > MainActivity.user.getTimeMedium()) {
                    databaseHelper.updateUserPoints(MainActivity.user.getId(),DatabaseHelper.COLUMN_TIME_MEDIUM,score);
                    MainActivity.user.setTimeMedium(score);
                }
                else if(modeGame.equals(Constants.modeFlag) && score > MainActivity.user.getFlagMedium() ) {
                    databaseHelper.updateUserPoints(MainActivity.user.getId(),DatabaseHelper.COLUMN_FLAG_MEDIUM,score);
                    MainActivity.user.setFlagMedium(score);
                }
                break;
            case Constants.HARD:
                if(modeGame.equals(Constants.modeMinute) && score > MainActivity.user.getTimeHard()) {
                    databaseHelper.updateUserPoints(MainActivity.user.getId(),DatabaseHelper.COLUMN_TIME_HARD,score);
                    MainActivity.user.setTimeHard(score);
                }
                else if(modeGame.equals(Constants.modeFlag) && score > MainActivity.user.getFlagHard() ) {
                    databaseHelper.updateUserPoints(MainActivity.user.getId(),DatabaseHelper.COLUMN_FLAG_HARD,score);
                    MainActivity.user.setFlagHard(score);
                }
                break;
            case Constants.EXTREME:
                if(modeGame.equals(Constants.modeMinute) && score > MainActivity.user.getTimeExtreme()) {
                    databaseHelper.updateUserPoints(MainActivity.user.getId(),DatabaseHelper.COLUMN_TIME_EXTREME,score);
                    MainActivity.user.setTimeExtreme(score);
                }
                else if(modeGame.equals(Constants.modeFlag) && score > MainActivity.user.getFlagExtreme() ) {
                    databaseHelper.updateUserPoints(MainActivity.user.getId(),DatabaseHelper.COLUMN_FLAG_EXTREME,score);
                    MainActivity.user.setFlagExtreme(score);
                }
                break;
            case Constants.INSANE:
                if(modeGame.equals(Constants.modeMinute) && score > MainActivity.user.getTimeInsane()) {
                    databaseHelper.updateUserPoints(MainActivity.user.getId(),DatabaseHelper.COLUMN_TIME_INSANE,score);
                    MainActivity.user.setTimeInsane(score);
                }
                else if(modeGame.equals(Constants.modeFlag) && score > MainActivity.user.getFlagInsane() ) {
                    databaseHelper.updateUserPoints(MainActivity.user.getId(),DatabaseHelper.COLUMN_FLAG_INSANE,score);
                    MainActivity.user.setFlagInsane(score);
                }
                break;

            case "": // hardcoreMode
                switch (levelGame){
                    case 1:
                        if(score > MainActivity.user.getHardcore_1() ) {
                            databaseHelper.updateUserPoints(MainActivity.user.getId(), DatabaseHelper.COLUMN_HARD_1,score);
                            MainActivity.user.setHardcore_1(score);
                        }
                        break;
                    case 2:
                        if(score > MainActivity.user.getHardcore_2() ) {
                            databaseHelper.updateUserPoints(MainActivity.user.getId(),DatabaseHelper.COLUMN_HARD_2,score);
                            MainActivity.user.setHardcore_2(score);
                        }
                        break;
                    case 3:
                        if(score > MainActivity.user.getHardcore_3() ) {
                            databaseHelper.updateUserPoints(MainActivity.user.getId(),DatabaseHelper.COLUMN_HARD_3,score);
                            MainActivity.user.setHardcore_3(score);
                        }
                        break;
                    case 4:
                        if(score > MainActivity.user.getHardcore_4() ) {
                            databaseHelper.updateUserPoints(MainActivity.user.getId(),DatabaseHelper.COLUMN_HARD_4,score);
                            MainActivity.user.setHardcore_4(score);
                        }
                        break;
                    case 5:
                        if(score > MainActivity.user.getHardcore_5() ) {
                            databaseHelper.updateUserPoints(MainActivity.user.getId(),DatabaseHelper.COLUMN_HARD_5,score);
                            MainActivity.user.setHardcore_5(score);
                        }
                        break;
                    case 6:
                        if(score > MainActivity.user.getHardcore_6() ) {
                            databaseHelper.updateUserPoints(MainActivity.user.getId(),DatabaseHelper.COLUMN_HARD_6,score);
                            MainActivity.user.setHardcore_6(score);
                        }
                        break;
                    case 7:
                        if(score > MainActivity.user.getHardcore_7() ) {
                            databaseHelper.updateUserPoints(MainActivity.user.getId(),DatabaseHelper.COLUMN_HARD_7,score);
                            MainActivity.user.setHardcore_7(score);
                        }
                        break;
                    case 8:
                        if(score > MainActivity.user.getHardcore_8() ) {
                            databaseHelper.updateUserPoints(MainActivity.user.getId(),DatabaseHelper.COLUMN_HARD_8,score);
                            MainActivity.user.setHardcore_8(score);
                        }
                        break;
                    case 9:
                        if(score > MainActivity.user.getHardcore_9() ) {
                            databaseHelper.updateUserPoints(MainActivity.user.getId(),DatabaseHelper.COLUMN_HARD_9,score);
                            MainActivity.user.setHardcore_9(score);
                        }
                        break;
                    case 10:
                        if(score > MainActivity.user.getHardcore_10() ) {
                            databaseHelper.updateUserPoints(MainActivity.user.getId(),DatabaseHelper.COLUMN_HARD_10,score);
                            MainActivity.user.setHardcore_10(score);
                        }
                        break;
                }
                break;
        }
    }

    private void closeFragment() {
        requireActivity().getSupportFragmentManager().popBackStack();
    }

    /**
     * Dialogo emergente con el resultado el terminar la pantalla
     */
    public void  showSummaryDialog() {
        if(!inDetails) {
            inDetails = true;
            SummaryDialogFragment dialogFragment = SummaryDialogFragment.newInstance(score,
                    record.getText().toString(), levelGame, modeGame, labelCorrectOption,difficulty );
            dialogFragment.show(getFragmentManager(), "summary_dialog");
        }
    }
}