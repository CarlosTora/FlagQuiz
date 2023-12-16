package com.flagquiz.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.flagquiz.R;

public class PreparationFragment extends Fragment {

    ProgressBar progressBar;
    TextView countdownTextView;

    private int countdownValue = 5; // Inicializar con el valor deseado
    private final Handler handler = new Handler();
    private Runnable countdownRunnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.poblation_fragment, container, false);



        startCountdown();

        return rootView;
    }

    private void startCountdown() {
        countdownRunnable = new Runnable() {
            @Override
            public void run() {
                if (countdownValue >= 0) {
                    progressBar.setProgress(countdownValue);
                    countdownTextView.setText(String.valueOf(countdownValue));
                    countdownValue--;
                    handler.postDelayed(this, 1000); // Ejecutar nuevamente en 1 segundo
                } else {
                    // Reemplazar el fragmento actual por MapFragment
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container, new MapFragment());
                    fragmentTransaction.commit();
                }
            }
        };

        handler.post(countdownRunnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(countdownRunnable); // Detener el Runnable al salir del fragmento
    }
}