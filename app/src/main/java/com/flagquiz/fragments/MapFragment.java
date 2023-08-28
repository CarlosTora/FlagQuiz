package com.flagquiz.fragments;

import android.annotation.SuppressLint;
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
import androidx.fragment.app.FragmentTransaction;

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
    private Handler handler = new Handler();
    private Runnable runnable;
    private boolean responseError = false;
    private String selectedRegion;
    private String modeGame;
    private int levelGame;
    private List<Flag> listFlagGame;
    private int positionList;
    private boolean starGame;
    private Button btt_starGame;

    public static MapFragment newInstance(String region, List<Flag> listFlags, String modeGame, int level) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString("region", region);
        args.putString("modeGame", modeGame);
        args.putSerializable("list", (Serializable)  listFlags);
        args.putInt("level", level);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.maps_fragment, container, false);
        Bundle args = getArguments();
        if (args != null) {
            selectedRegion = args.getString("region");
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
        record = view.findViewById(R.id.txt_record);
        time = view.findViewById(R.id.txt_temp_crono);
        indicationPoints = view.findViewById(R.id.txt_indicatorPoints);
        indicationPoints.setVisibility(View.GONE);  // OCULTAR ADD POINTS SCORE

        databaseHelper = new DatabaseHelper(requireContext());
        positionList = 0;

        /**
         *  SI ES HARDCORE DEBEMOS DE RECOGER EL NIVEL ASI COMO SU RECORD
         */
        if(modeGame.equals("hardcoreMode")){
            time.setText("NIVEL "+levelGame);
            txt_scoreGame.setText("ACIERTOS");
        }

        for (Button button : optionButtons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleOptionClick(button.getText().toString());
                }
            });
        }
        hits.setText((String.valueOf(score)));
        setRecordText();

        // PROGRES BAR
        progressBar = view.findViewById(R.id.progress_bar);

        // Configurar el Runnable para actualizar el progreso cada 100 milisegundos
        runnable = new Runnable() {
            @Override
            public void run() {
                if(!responseError) {
                    progressBar.setProgress(progressBar.getProgress() - 50); // Restar 10 ms
                    if (progressBar.getProgress() <= 0) {
                        closeFragment();
                    } else {
                        changeColorBar();
                        handler.postDelayed(this, 50); // Ejecutar nuevamente en 10 ms
                    }
                }
            }
        };
        updateTimeBarByScore();
        loadRandomFlagAndOptions();

        // ESTO ES PARA BLOQUEAR LAS PULSACIONES FAKE
            ConstraintLayout mapsConstraint = view.findViewById(R.id.const_zoneFlags);
            ConstraintLayout pointsConstraint = view.findViewById(R.id.const_points);
            mapsConstraint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            pointsConstraint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //

        return view;
    }

    private void setRecordText() {
        switch (selectedRegion) {
        case "Europe":
            record.setText(String.valueOf(MainActivity.user.getHardcoreEurope()));
            break;
        case "America":
            record.setText(String.valueOf(MainActivity.user.getHardcoreAmerica()));
            break;
        case "Asia":
            record.setText(String.valueOf(MainActivity.user.getHardcoreAsia()));
            break;
        case "Oceania":
            record.setText(String.valueOf(MainActivity.user.getHardcoreOceania()));
            break;
        case "Africa":
            record.setText(String.valueOf(MainActivity.user.getHardcoreAfrica()));
            break;
        default:
            record.setText(String.valueOf(MainActivity.user.getHardcoreGlobal()));
            break;
    }


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

    private void loadRandomFlagAndOptions() {
        if(positionList >= listFlagGame.size()) {
            positionList = 0;
            Collections.shuffle(listFlagGame);
        }
        Flag randomFlag = listFlagGame.get(positionList);
        if (randomFlag != null) {
            // Cargar los valores de la bandera aleatoria en la interfaz
            int resourceId = getResources().getIdentifier(randomFlag.getImage(), "drawable", requireContext().getPackageName());
            flagImageView.setImageResource(resourceId);

            // Obtener nombres aleatorios de países para las opciones
            String[] randomCountryNames = databaseHelper.getRandomCountryNames(randomFlag.getName(), 3,randomFlag.getRegion());

            correctOption = randomFlag.getName();
            // Asignar opciones de respuesta a los botones
            int correctOptionIndex = new Random().nextInt(optionButtons.length);
            for (int i = 0; i < optionButtons.length; i++) {
                if (i == correctOptionIndex) {
                    optionButtons[i].setText(getStringResource(randomFlag.getName())); // Opción correcta
                } else {
                    int incorrectIndex = i < correctOptionIndex ? i : i - 1;
                    String randomCountryName = randomCountryNames[incorrectIndex];
                    optionButtons[i].setText(getStringResource(randomCountryName)); // Opciones incorrectas
                }
            }
            handler.postDelayed(runnable, 100); // Iniciar el progreso

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
        // Desactivar todos los botones para evitar clics repetidos mientras se procesa la respuesta
        for (Button button : optionButtons) {
            button.setEnabled(false);
        }

        boolean isCorrect = getStringResource(correctOption).equals(selectedOption);

        // Cambiar el color del botón seleccionado según si la respuesta es correcta o incorrecta
        for (Button button : optionButtons) {
            if (button.getText().toString().equals(selectedOption)) {
                if (isCorrect) {
                    handler.removeCallbacks(runnable);
                    button.setBackgroundColor(Color.GREEN);
                } else {
                    button.setBackgroundColor(Color.RED);
                }
            }
        }

        // Si la respuesta es correcta, espera 0.5 segundo y luego cargar una nueva bandera con opciones
        if (isCorrect) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    positionList++;
                    loadRandomFlagAndOptions();
                    // Habilitar todos los botones nuevamente
                    for (Button button : optionButtons) {
                        button.setEnabled(true);
                        button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.bg_buttons));
                    }
                    indicationPoints.setVisibility(View.GONE);
                }
            }, 500);
            // SUMA PUNTOS
            showPoints(true);
            score++;
            hits.setText((String.valueOf(score)));
        } else {
            // Si la respuesta es incorrecta, para el tiempo,guarda (si es necesario) en record y cierra fragment

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    resolveError();
                    indicationPoints.setVisibility(View.GONE);
                }
            }, 500);
            showPoints(false);
        }
    }

    private void showPoints(boolean success) {
        if(success) {
            indicationPoints.setVisibility(View.VISIBLE);
            indicationPoints.setTextColor(Color.GREEN);
            indicationPoints.setText("+1");
        }
        else {
            indicationPoints.setVisibility(View.VISIBLE);
            indicationPoints.setTextColor(Color.RED);
            indicationPoints.setText("-1");
        }
    }

    private void resolveError() {
        switch (modeGame) {
            case "hardcoreMode":
                responseError = true;
                updateRecord();
                showSummaryDialog();
                //closeFragment();
                break;
            case "minuteMode":
                for (Button button : optionButtons) {
                    button.setEnabled(true);
                }
                score--;
                hits.setText((String.valueOf(score)));
        }
    }

    private void updateTimeBarByScore() {
        switch (modeGame){
            case "hardcoreMode":
                setTimeAccordingLevel();
                break;

            case "minuteMode":
                progressBar.setMax(60000);
                progressBar.setProgress(60000);
                break;
        }
    }

    private void setTimeAccordingLevel() {

        if (levelGame == 1 ) {
            progressBar.setMax(4000);
            progressBar.setProgress(4000);
        } else if (levelGame == 2 ) {
            progressBar.setMax(3700);
            progressBar.setProgress(3700);
        } else if (levelGame == 3 ) {
            progressBar.setMax(3400);
            progressBar.setProgress(3400);
        } else if (levelGame == 4 ) {
            progressBar.setMax(3100);
            progressBar.setProgress(3100);
        } else if (levelGame == 5 ) {
            progressBar.setMax(2800);
            progressBar.setProgress(2800);
        } else if (levelGame == 6 ) {
            progressBar.setMax(2400);
            progressBar.setProgress(2400);
        } else if (levelGame == 7 ) {
            progressBar.setMax(2200);
            progressBar.setProgress(2200);
        } else if (levelGame == 8 ) {
            progressBar.setMax(2000);
            progressBar.setProgress(2000);
        } else if (levelGame == 9 ) {
            progressBar.setMax(1800);
            progressBar.setProgress(1800);
        } else  {
            progressBar.setMax(1500);
            progressBar.setProgress(1500);
        }
    }

    private void updateRecord() {

        switch (modeGame+selectedRegion) {
            case "minuteModeEurope":
                if(score > MainActivity.user.getTimeEurope()) {
                    databaseHelper.updateUserPoints(MainActivity.user.getId(),modeGame+selectedRegion,score);
                    MainActivity.user.setTimeEurope(score);
                }
                break;
            case "hardcoreModeEurope":
                if(score > MainActivity.user.getHardcoreEurope()) {
                    databaseHelper.updateUserPoints(MainActivity.user.getId(),modeGame+selectedRegion,score);
                    MainActivity.user.setHardcoreEurope(score);
                }
                break;

            case "minuteModeAmerica":
                if(score > MainActivity.user.getTimeAmerica()) {
                    databaseHelper.updateUserPoints(MainActivity.user.getId(),modeGame+selectedRegion,score);
                    MainActivity.user.setTimeAmerica(score);
                }
                break;
            case "hardcoreModeAmerica":
                if(score > MainActivity.user.getHardcoreAmerica()) {
                    databaseHelper.updateUserPoints(MainActivity.user.getId(),modeGame+selectedRegion,score);
                    MainActivity.user.setHardcoreAmerica(score);
                }
                break;

            case "minuteModeAsia":
                if(score > MainActivity.user.getTimeAsia()) {
                    databaseHelper.updateUserPoints(MainActivity.user.getId(),modeGame+selectedRegion,score);
                    MainActivity.user.setTimeAsia(score);
                }
                break;
            case "hardcoreModeAsia":
                if(score > MainActivity.user.getHardcoreAsia()) {
                    databaseHelper.updateUserPoints(MainActivity.user.getId(),modeGame+selectedRegion,score);
                    MainActivity.user.setHardcoreAsia(score);
                }
                break;

            case "minuteModeOceania":
                if(score > MainActivity.user.getTimeOceania()) {
                    databaseHelper.updateUserPoints(MainActivity.user.getId(),modeGame+selectedRegion,score);
                    MainActivity.user.setTimeOceania(score);
                }
                break;
            case "hardcoreModeOceania":
                if(score > MainActivity.user.getHardcoreOceania()) {
                    databaseHelper.updateUserPoints(MainActivity.user.getId(),modeGame+selectedRegion,score);
                    MainActivity.user.setHardcoreOceania(score);
                }
                break;

            case "minuteModeAfrica":
                if(score > MainActivity.user.getTimeAfrica()) {
                    databaseHelper.updateUserPoints(MainActivity.user.getId(),modeGame+selectedRegion,score);
                    MainActivity.user.setTimeAfrica(score);
                }
                break;
            case "hardcoreModeAfrica":
                if(score > MainActivity.user.getHardcoreAfrica()) {
                    databaseHelper.updateUserPoints(MainActivity.user.getId(),modeGame+selectedRegion,score);
                    MainActivity.user.setHardcoreAfrica(score);
                }
                break;

            case "minuteModeGlobal":
                if(score > MainActivity.user.getTimeGlobal()) {
                    databaseHelper.updateUserPoints(MainActivity.user.getId(),modeGame+selectedRegion,score);
                    MainActivity.user.setTimeGlobal(score);
                }
                break;
            case "hardcoreModeGlobal":
                if(score > MainActivity.user.getHardcoreGlobal()) {
                    databaseHelper.updateUserPoints(MainActivity.user.getId(),modeGame+selectedRegion,score);
                    MainActivity.user.setHardcoreGlobal(score);
                }
                break;
        }

    }

    private void closeFragment() {
        requireActivity().getSupportFragmentManager().popBackStack();
    }

    public void  showSummaryDialog() {
        SummaryDialogFragment dialogFragment = SummaryDialogFragment.newInstance(score, record.getText().toString());
        dialogFragment.show(getFragmentManager(), "summary_dialog");
    }
}