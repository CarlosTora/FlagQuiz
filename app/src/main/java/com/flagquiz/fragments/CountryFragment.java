package com.flagquiz.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
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

public class CountryFragment extends Fragment {

    ImageView[] optionButtons = new ImageView[4];
    private int score = 0;
    private String correctOption;
    private DatabaseHelper databaseHelper;
    private TextView hits;
    private TextView record;
    TextView time;
    TextView txt_scoreGame;
    private TextView label_info;
    private TextView indicationPoints;
    Handler handler = new Handler();
    Runnable runnable;
    boolean responseError = false;
    private String modeGame;
    private int levelGame;
    private String difficulty;
    private List<Flag> listFlagGame;
    private int positionList;
    private String correctFlag;
    boolean starGame;
    private boolean inDetails;
    private Button btt_starGame;
    private ImageView img_life1;
    private ImageView img_life2;
    private ImageView img_life3;
    private int countLife;

    public static CountryFragment newInstance(String difficulty, List<Flag> listFlags, String modeGame, int level) {
        CountryFragment fragment = new CountryFragment();
        Bundle args = new Bundle();
        args.putString("difficulty", difficulty);
        args.putString("modeGame", modeGame);
        args.putSerializable("list", (Serializable)  listFlags);
        args.putInt("level", level);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.country_fragment, container, false);
        Bundle args = getArguments();
        if (args != null) {
            difficulty = args.getString("difficulty");
            modeGame = args.getString("modeGame");
            levelGame = args.getInt("level");
            listFlagGame = (List<Flag>) getArguments().getSerializable("list");
        }

        countLife = 3;
        Collections.shuffle(listFlagGame);
        optionButtons[0] = view.findViewById(R.id.img_flag_1);
        optionButtons[1] = view.findViewById(R.id.img_flag_2);
        optionButtons[2] = view.findViewById(R.id.img_flag_3);
        optionButtons[3] = view.findViewById(R.id.img_flag_4);

        btt_starGame = view.findViewById(R.id.btt_star_hardcore);
        img_life1 = view.findViewById(R.id.img_life1);
        img_life2 = view.findViewById(R.id.img_life2);
        img_life3 = view.findViewById(R.id.img_life3);

        hits = view.findViewById(R.id.txt_hits);
        txt_scoreGame = view.findViewById(R.id.txt_scoreGame);
        record = view.findViewById(R.id.txt_record);
        time = view.findViewById(R.id.txt_temp_crono);
        label_info = view.findViewById(R.id.txt_infoHardMode);
        indicationPoints = view.findViewById(R.id.txt_indicatorPoints);
        indicationPoints.setVisibility(View.GONE);  // OCULTAR ADD POINTS SCORE

        databaseHelper = new DatabaseHelper(requireContext());
        positionList = 0;
        starGame = false;

        hits.setText((String.valueOf(score)));
        setRecordText();

        if(modeGame.equals(Constants.modeCountry)) {
            label_info.setText(getString(R.string.LABEL_INFO_PAISES));
        } else {
            label_info.setText(getString(R.string.LABEL_INFO_CAPITAL));
        }

        for (ImageView btt : optionButtons) {
            btt.setVisibility(View.GONE);
        }
        btt_starGame.setOnClickListener(v -> starGame(view));
        return view;
    }

    private void starGame(View view) {
        starGame = true;
        btt_starGame.setVisibility(View.GONE);
        label_info.setVisibility(View.GONE);

        //flagImageView.setVisibility(View.VISIBLE);
        for (ImageView img : optionButtons) {
            img.setVisibility(View.VISIBLE);
        }

        for (ImageView img : optionButtons) {
            img.setOnClickListener(v -> handleOptionClick(img.getTag().toString()));
        }
        loadRandomCountryAndOptions();

        // ESTO ES PARA BLOQUEAR LAS PULSACIONES FAKE
        ConstraintLayout mapsConstraint = view.findViewById(R.id.const_zoneCountry);
        ConstraintLayout pointsConstraint = view.findViewById(R.id.const_pointsCountry);
        mapsConstraint.setOnClickListener(v -> {    });
        pointsConstraint.setOnClickListener(v -> {  });
    }

    private void setRecordText() {
        switch (difficulty) {
            case Constants.EASY:
                if(modeGame.equals(Constants.modeCountry))
                    record.setText(String.valueOf(MainActivity.user.getCountryEasy()));
                else if(modeGame.equals(Constants.modeCapital))
                    record.setText(String.valueOf(MainActivity.user.getCapitalEasy()));
                break;
            case Constants.MEDIUM:
                if(modeGame.equals(Constants.modeCountry))
                    record.setText(String.valueOf(MainActivity.user.getCountryMedium()));
                else if(modeGame.equals(Constants.modeCapital))
                    record.setText(String.valueOf(MainActivity.user.getCapitalMedium()));
                break;
            case Constants.HARD:
                if(modeGame.equals(Constants.modeCountry))
                    record.setText(String.valueOf(MainActivity.user.getCountryHard()));
                else if(modeGame.equals(Constants.modeCapital))
                    record.setText(String.valueOf(MainActivity.user.getCapitalHard()));
                break;
            case Constants.EXTREME:
                if(modeGame.equals(Constants.modeCountry))
                    record.setText(String.valueOf(MainActivity.user.getCountryExtreme()));
                else if(modeGame.equals(Constants.modeCapital))
                    record.setText(String.valueOf(MainActivity.user.getCapitalExtreme()));
                break;
            case Constants.INSANE:
                if(modeGame.equals(Constants.modeCountry))
                    record.setText(String.valueOf(MainActivity.user.getCountryInsane()));
                else if(modeGame.equals(Constants.modeCapital))
                    record.setText(String.valueOf(MainActivity.user.getCapitalInsane()));
                break;
        }
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
    private void loadRandomCountryAndOptions() {
        if(positionList >= listFlagGame.size()) {
            positionList = 0;
        }

        Flag randomFlag = getFlagByLevel();

        if (randomFlag != null) {
            label_info.setVisibility(View.VISIBLE);
            if(modeGame.equals(Constants.modeCountry)) {
                label_info.setText(getStringResource(randomFlag.getName()));
            }
            else {
                label_info.setText(getStringResource(randomFlag.getCapital()));
            }

            // Obtener nombres aleatorios de países para las opciones
            String[] randomCountryNames = databaseHelper.getRandomFlagsOptions(randomFlag.getName(), 3,randomFlag.getRegion());

            correctOption = randomFlag.getName();
            // Asignar opciones de respuesta a los botones
            int correctOptionIndex = new Random().nextInt(optionButtons.length);
            for (int i = 0; i < optionButtons.length; i++) {
                if (i == correctOptionIndex) {
                    int resourceId = getResources().getIdentifier(randomFlag.getImage(), "drawable", requireContext().getPackageName());
                    optionButtons[i].setImageResource(resourceId);
                    // Aqui se guarda la respuesta correcta ya sea la imagen de la bandera o el nombre
                    if(modeGame.equals(Constants.modeCountry)) {
                        correctFlag = String.valueOf(resourceId);
                    } else {
                        correctFlag =  getStringResource(correctOption);
                    }
                    correctOption = optionButtons[i].getTag().toString();// Opción correcta
                } else {
                    int incorrectIndex = i < correctOptionIndex ? i : i - 1;
                    int resourceId = getResources().getIdentifier(randomCountryNames[incorrectIndex], "drawable", requireContext().getPackageName());
                    optionButtons[i].setImageResource(resourceId); // Opciones incorrectas
                }
            }
            handler.postDelayed(runnable, 100); // Iniciar el progreso
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
        for (ImageView button : optionButtons) {
            button.setEnabled(false);
        }

        boolean isCorrect = correctOption.equals(selectedOption);

        // Cambiar el color del botón seleccionado según si la respuesta es correcta o incorrecta
        for (ImageView button : optionButtons) {
            if (button.getTag().equals(selectedOption)) {
                if (isCorrect) {
                    for (ImageView response : optionButtons) {
                        if(!response.getTag().equals(selectedOption)) {
                            response.setAlpha(0.3f);
                        }
                    }
                    handler.removeCallbacks(runnable);
                } else {
                    restLife(correctFlag);
                    button.setAlpha(0.3f);
                }
            }
        }

        // Si la respuesta es correcta, espera 0.5 segundo y luego cargar una nueva bandera con opciones
        if (isCorrect) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    positionList++;
                    loadRandomCountryAndOptions();
                    // Habilitar todos los botones nuevamente
                    for (ImageView button : optionButtons) {
                        button.setEnabled(true);
                        button.setAlpha(1f);
                    }
                    indicationPoints.setVisibility(View.GONE);
                }
            }, 500);
            // SUMA PUNTOS
            showPoints();
            score++;
            hits.setText((String.valueOf(score)));
        } else {
            // Si la respuesta es incorrecta, para el tiempo,guarda (si es necesario) en record y cierra fragment
            new Handler().postDelayed(this::resolveError, 500);
        }
    }

    private void restLife(String correctFlag) {
        countLife--;
        int resourceId = getResources().getIdentifier("icon_life_false", "drawable", requireContext().getPackageName());

        if(countLife == 2) {
            img_life3.setImageResource(resourceId);
        }else if(countLife == 1){
            img_life2.setImageResource(resourceId);
        }else {
            img_life1.setImageResource(resourceId);
            updateRecord();
            showSummaryDialog(correctFlag);
        }
    }

    private void resolveError() {
        for (ImageView button : optionButtons) {
            button.setEnabled(true);
        }
    }

    private void showPoints() {
        indicationPoints.setVisibility(View.VISIBLE);
        indicationPoints.setTextColor(Color.GREEN);
        indicationPoints.setText("+1");
    }

    /**
     * Funcion para actualizar el record
     */
    private void updateRecord() {
        switch (difficulty) {
            case Constants.EASY:
                if (modeGame.equals(Constants.modeCountry) && score > MainActivity.user.getCountryEasy()) {
                    databaseHelper.updateUserPoints(MainActivity.user.getId(), DatabaseHelper.COLUMN_COUNTRY_EASY, score);
                    MainActivity.user.setCountryEasy(score);

                } else if (modeGame.equals(Constants.modeCapital) && score > MainActivity.user.getCapitalEasy()) {
                    databaseHelper.updateUserPoints(MainActivity.user.getId(), DatabaseHelper.COLUMN_CAP_EASY, score);
                    MainActivity.user.setCapitalEasy(score);
                }
                break;

            case Constants.MEDIUM:
                if (modeGame.equals(Constants.modeCountry) && score > MainActivity.user.getCountryMedium()) {
                    databaseHelper.updateUserPoints(MainActivity.user.getId(), DatabaseHelper.COLUMN_COUNTRY_MEDIUM, score);
                    MainActivity.user.setCountryMedium(score);
                } else if (modeGame.equals(Constants.modeCapital) && score > MainActivity.user.getCapitalMedium()) {
                    databaseHelper.updateUserPoints(MainActivity.user.getId(), DatabaseHelper.COLUMN_CAP_MEDIUM, score);
                    MainActivity.user.setCapitalMedium(score);
                }
                break;

            case Constants.HARD:
                if (modeGame.equals(Constants.modeCountry) && score > MainActivity.user.getCountryHard()) {
                    databaseHelper.updateUserPoints(MainActivity.user.getId(), DatabaseHelper.COLUMN_COUNTRY_HARD, score);
                    MainActivity.user.setCountryHard(score);
                } else if (modeGame.equals(Constants.modeCapital) && score > MainActivity.user.getCapitalHard()) {
                    databaseHelper.updateUserPoints(MainActivity.user.getId(), DatabaseHelper.COLUMN_CAP_HARD, score);
                    MainActivity.user.setCapitalHard(score);
                }
                break;

            case Constants.EXTREME:
                if (modeGame.equals(Constants.modeCountry) && score > MainActivity.user.getCountryExtreme()) {
                    databaseHelper.updateUserPoints(MainActivity.user.getId(), DatabaseHelper.COLUMN_COUNTRY_EXTREME, score);
                    MainActivity.user.setCountryExtreme(score);
                } else if (modeGame.equals(Constants.modeCapital) && score > MainActivity.user.getCapitalExtreme()) {
                    databaseHelper.updateUserPoints(MainActivity.user.getId(), DatabaseHelper.COLUMN_CAP_EXTREME, score);
                    MainActivity.user.setCapitalExtreme(score);
                }
                break;

            case Constants.INSANE:
                if (modeGame.equals(Constants.modeCountry) && score > MainActivity.user.getCountryInsane()) {
                    databaseHelper.updateUserPoints(MainActivity.user.getId(), DatabaseHelper.COLUMN_COUNTRY_INSANE, score);
                    MainActivity.user.setCountryInsane(score);
                } else if (modeGame.equals(Constants.modeCapital) && score > MainActivity.user.getCapitalInsane()) {
                    databaseHelper.updateUserPoints(MainActivity.user.getId(), DatabaseHelper.COLUMN_CAP_INSANE, score);
                    MainActivity.user.setCapitalInsane(score);
                }
                break;
        }
    }

    public void  showSummaryDialog(String correctFlag) {
        if(!inDetails) {
            inDetails = true;
            SummaryDialogFragment dialogFragment = SummaryDialogFragment.newInstance(score,
                    record.getText().toString(), levelGame, modeGame,correctFlag,difficulty);
            assert getFragmentManager() != null;
            dialogFragment.show(getFragmentManager(), "summary_dialog");
        }
    }

}