package com.flagquiz;

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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.flagquiz.database.DatabaseHelper;
import com.flagquiz.database.UserDatabase;
import com.flagquiz.model.Flag;
import com.flagquiz.model.User;

import java.sql.SQLException;
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
    private Handler handler = new Handler();
    private Runnable runnable;
    private User user;
    private boolean responseError = false;
    private String selectedRegion;

    public static MapFragment newInstance(String region) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString("region", region);
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
        }

        flagImageView = view.findViewById(R.id.flag_game);
        optionButtons[0] = view.findViewById(R.id.btt_opc1);
        optionButtons[1] = view.findViewById(R.id.btt_opc2);
        optionButtons[2] = view.findViewById(R.id.btt_opc3);
        optionButtons[3] = view.findViewById(R.id.btt_opc4);

        hits = view.findViewById(R.id.txt_hits);
        record = view.findViewById(R.id.txt_record);
        databaseHelper = new DatabaseHelper(requireContext());

        user = databaseHelper.getUser();
        for (Button button : optionButtons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleOptionClick(button.getText().toString());
                }
            });
        }
        hits.setText((String.valueOf(score)));
        record.setText(String.valueOf(user.getHardcoreGlobal()));

        // PROGRES BAR
        progressBar = view.findViewById(R.id.progress_bar);

        // Configurar el Runnable para actualizar el progreso cada 100 milisegundos
        runnable = new Runnable() {
            @Override
            public void run() {
                if(!responseError) {
                    progressBar.setProgress(progressBar.getProgress() - 10); // Restar 10 ms
                    if (progressBar.getProgress() <= 0) {
                        closeFragment();
                    } else {
                        changeColorBar();
                        handler.postDelayed(this, 10); // Ejecutar nuevamente en 10 ms
                    }
                }
            }
        };
        loadRandomFlagAndOptions();


        return view;
    }

    // SETEA EL COLOR DE LA BARRA SEGUN EL TIEMPO RESTANTE
    private void changeColorBar() {
        if(progressBar.getProgress() > 2500) {
            progressBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        } else if (progressBar.getProgress() < 2500 && progressBar.getProgress() > 1200) {
            progressBar.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
        } else {
            progressBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
        }
    }

    private void loadRandomFlagAndOptions() {
        Flag randomFlag = databaseHelper.getRandomFlag(selectedRegion);
        if (randomFlag != null) {
            // Cargar los valores de la bandera aleatoria en la interfaz
            int resourceId = getResources().getIdentifier(randomFlag.getImage(), "drawable", requireContext().getPackageName());
            flagImageView.setImageResource(resourceId);

            // Obtener nombres aleatorios de países para las opciones
            String[] randomCountryNames = databaseHelper.getRandomCountryNames(randomFlag.getName(), 3,selectedRegion);

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
            progressBar.setProgress(5000); // Reiniciar el progreso a 5 segundos
        }
    }

    private String getStringResource(String resourceName) {
        int resourceId = getResources().getIdentifier(resourceName, "string", requireContext().getPackageName());
        return getResources().getString(resourceId);
    }

    @Override
    public void onDestroyView() {
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
                    loadRandomFlagAndOptions();
                    // Habilitar todos los botones nuevamente
                    for (Button button : optionButtons) {
                        button.setEnabled(true);

                        button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.bg_buttons));
                       // button.setBackgroundColor(Color.WHITE);
                    }
                }
            }, 500);
            // SUMA PUNTOS
            score++;
            hits.setText((String.valueOf(score)));
        } else {
            responseError = true;

            // Si la respuesta es incorrecta, para el tiempo,guarda (si es necesario) en record y cierra fragment
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    /*
                    // Habilitar todos los botones nuevamente
                    for (Button button : optionButtons) {
                        button.setEnabled(true);
                    }
                                         */
                    if(score > user.getHardcoreGlobal()) {
                        databaseHelper.updateUserPoints(user.getId(),"region",score);
                    }

                    closeFragment();
                }
            }, 500);
        }
    }

    private void closeFragment() {
        requireActivity().getSupportFragmentManager().popBackStack();
    }
}