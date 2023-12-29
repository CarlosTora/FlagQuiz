package com.flagquiz.fragments;

import static com.flagquiz.MainActivity.languageSelected;
import static com.flagquiz.MainActivity.sharedPreferences;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.flagquiz.MainActivity;
import com.flagquiz.R;

import java.util.Objects;

public class LanguageFragment extends DialogFragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Bloquear el fragmento principal cuando el diálogo esté abierto
        setCancelable(false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Ajusta las dimensiones para que el diálogo sea un círculo
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().widthPixels; // Mismo valor para hacer un círculo


        Objects.requireNonNull(getDialog()).getWindow().setLayout(width, height);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().getAttributes().dimAmount = 0.5f; // Opacidad de fondo
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.language_fragment, null);

        ImageView ic_es = view.findViewById(R.id.ic_language_es);
        ImageView ic_en = view.findViewById(R.id.ic_language_en);
        ImageView ic_fr = view.findViewById(R.id.ic_language_fr);
        ImageView ic_de = view.findViewById(R.id.ic_language_de);

        ic_es.setOnClickListener(v -> {
            if(getActivity() != null && !languageSelected.equals("es")) {
                sharedPreferences.edit().putString("language",  "es").apply();
                getActivity().recreate();
            }
            dismiss(); // Cerrar el diálogo
        });

        ic_en.setOnClickListener(v -> {
            if(getActivity() != null && !languageSelected.equals("en")) {
                sharedPreferences.edit().putString("language",  "en").apply();
                getActivity().recreate();
            }
            dismiss();
        });

        ic_fr.setOnClickListener(v -> {
            if(getActivity() != null  && !languageSelected.equals("fr")) {
                sharedPreferences.edit().putString("language",  "fr").apply();
                getActivity().recreate();
            }
            dismiss();
        });
        ic_de.setOnClickListener(v -> {
            if(getActivity() != null && !languageSelected.equals("de")) {
                sharedPreferences.edit().putString("language",  "de").apply();
                getActivity().recreate();
            }
            dismiss();
        });

        builder.setView(view);

        return builder.create();
    }
}
