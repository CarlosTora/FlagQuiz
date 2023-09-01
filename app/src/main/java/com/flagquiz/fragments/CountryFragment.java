package com.flagquiz.fragments;

import android.annotation.SuppressLint;
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

import com.flagquiz.MainActivity;
import com.flagquiz.R;
import com.flagquiz.database.DatabaseHelper;
import com.flagquiz.model.Flag;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CountryFragment extends Fragment {

    private ImageView[] optionButtons = new ImageView[4];
    private int score = 0;
    private String correctOption;
    private DatabaseHelper databaseHelper;
    private TextView hits;
    private TextView record;
    private TextView time;
    private TextView txt_scoreGame;
    private TextView label_info;
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
    private boolean inDetails;
    private Button btt_starGame;
    private ImageView img_life1;
    private ImageView img_life2;
    private ImageView img_life3;
    private int countLife;

    public static CountryFragment newInstance(String region, List<Flag> listFlags, String modeGame, int level) {
        CountryFragment fragment = new CountryFragment();
        Bundle args = new Bundle();
        args.putString("region", region);
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
        View view = inflater.inflate(R.layout.country_fragment, container, false);
        Bundle args = getArguments();
        if (args != null) {
            selectedRegion = args.getString("region");
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
        /**
         *  SI ES HARDCORE DEBEMOS DE RECOGER EL NIVEL ASI COMO SU RECORD
         */
        if(modeGame.equals("hardcoreMode")){
            time.setText("NIVEL "+levelGame);
            txt_scoreGame.setText("ACIERTOS");
        }

        for (ImageView btt : optionButtons) {
            btt.setVisibility(View.GONE);
        }
        btt_starGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starGame(view);
            }
        });
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
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleOptionClick(img.getTag().toString());
                }
            });
        }
        loadRandomCountryAndOptions();

        // ESTO ES PARA BLOQUEAR LAS PULSACIONES FAKE
        ConstraintLayout mapsConstraint = view.findViewById(R.id.const_zoneCountry);
        ConstraintLayout pointsConstraint = view.findViewById(R.id.const_pointsCountry);
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

    private void loadRandomCountryAndOptions() {
        if(positionList >= listFlagGame.size()) {
            positionList = 0;
            Collections.shuffle(listFlagGame);
        }
        Flag randomFlag = listFlagGame.get(positionList);
        if (randomFlag != null) {
            label_info.setVisibility(View.VISIBLE);
            label_info.setText(getStringResource(randomFlag.getName()));

            // Obtener nombres aleatorios de países para las opciones
            String[] randomCountryNames = databaseHelper.getRandomFlagsOptions(randomFlag.getName(), 3,randomFlag.getRegion());

            correctOption = randomFlag.getName();
            // Asignar opciones de respuesta a los botones
            int correctOptionIndex = new Random().nextInt(optionButtons.length);
            for (int i = 0; i < optionButtons.length; i++) {
                if (i == correctOptionIndex) {
                    int resourceId = getResources().getIdentifier(randomFlag.getImage(), "drawable", requireContext().getPackageName());
                    optionButtons[i].setImageResource(resourceId);
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
                    restLife();
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
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    resolveError();
                }
            }, 500);
        }
    }

    private void restLife() {
        countLife--;
        int resourceId = getResources().getIdentifier("icon_life_false", "drawable", requireContext().getPackageName());

        if(countLife == 2) {
            img_life3.setImageResource(resourceId);
        }else if(countLife == 1){
            img_life2.setImageResource(resourceId);
        }else {
            img_life1.setImageResource(resourceId);
            showSummaryDialog();
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


/*



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
*/
    private void closeFragment() {
        requireActivity().getSupportFragmentManager().popBackStack();
    }

    public void  showSummaryDialog() {
        if(!inDetails) {
            inDetails = true;
            SummaryDialogFragment dialogFragment = SummaryDialogFragment.newInstance(score, record.getText().toString(), levelGame, modeGame);
            dialogFragment.show(getFragmentManager(), "summary_dialog");
        }
    }

}