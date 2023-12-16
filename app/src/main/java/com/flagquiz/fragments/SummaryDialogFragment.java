package com.flagquiz.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.flagquiz.Constants;
import com.flagquiz.MainActivity;
import com.flagquiz.R;

import java.util.Objects;

public class SummaryDialogFragment extends DialogFragment {

    private int score;
    private String record;
    private String difficulty;
    private int level;
    private String mode;
    private String correctOption;
    private TextView txt_title;
    private TextView txt_correctOption;
    private ImageView img_result;
    private ImageView img_correctFlag;

    private Button retryButton;

    public static SummaryDialogFragment newInstance(int score, String record,int level, String mode, String correct, String difficulty) {
        SummaryDialogFragment fragment = new SummaryDialogFragment();
        Bundle args = new Bundle();
        args.putInt("score", score);
        args.putString("record", record);
        args.putInt("level", level);
        args.putString("mode", mode);
        args.putString("correct", correct);
        args.putString("correct", correct);
        args.putString("difficulty", difficulty);
        fragment.setArguments(args);
        return fragment;
    }

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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.summary_fragment, container, false);

        Bundle args = getArguments();
        if (args != null) {
            score = args.getInt("score", 0);
            record = args.getString("record", "x");
            level = args.getInt("level", 1);
            mode = args.getString("mode", "x");
            correctOption = args.getString("correct", "");
            difficulty = args.getString("difficulty", "");

            FrameLayout frameLayout = view.findViewById(R.id.dialog_background);
            TextView txt_score = view.findViewById(R.id.txt_summary_score);
            TextView txt_record = view.findViewById(R.id.txt_summary_record);
            txt_title = view.findViewById(R.id.txt_summary_tittle);
            txt_correctOption = view.findViewById(R.id.txt_correctAnswer);
            img_result = view.findViewById(R.id.img_summary_result);
            img_correctFlag = view.findViewById(R.id.img_correct_flag);

            frameLayout.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            txt_score.setText(String.valueOf(score));
            txt_record.setText(record);
        }

        // Configura el click listener para el botón de cerrar
        Button closeButton = view.findViewById(R.id.btt_closeSummary);
        retryButton = view.findViewById(R.id.btt_retrySummary);

        showTitle();
        setCorrectOption();
        setImagenCup();



        closeButton.setOnClickListener(v -> {
            dismiss();
            requireFragmentManager().popBackStack();
        });

        retryButton.setOnClickListener(v -> {
            dismiss();
            requireFragmentManager().popBackStack();

            if(mode.equals(Constants.modeHardcore)){
                if(score > 24) {
                    level++;
                    MapFragment fragment = MapFragment.newInstance(difficulty, MainActivity.listFlagMain,mode,level);
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                else {
                    MapFragment fragment = MapFragment.newInstance(difficulty, MainActivity.listFlagMain,mode,level);
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }

            }
            else if( mode.equals(Constants.modeMinute) || mode.equals(Constants.modeFlag)  ) {
                MapFragment fragment = MapFragment.newInstance(difficulty, MainActivity.listFlagMain,mode,level);
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
            else {
                CountryFragment fragment = CountryFragment.newInstance(difficulty, MainActivity.listFlagMain,mode,level);
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

    private void setImagenCup() {
        int imageResource = 0;
        String text = "";
        if (mode.equals(Constants.modeHardcore)) {
            text = (score > 24) ? getString(R.string.LABEL_NEXT_LEVEL) : getString(R.string.LABEL_RELOAD_GAME);
            retryButton.setText(text);
            imageResource = (score > 24) ? R.drawable.icon_summary_complete : R.drawable.icon_summary;
        }
        else {
            imageResource = (score > Integer.parseInt(record)) ? R.drawable.icon_summary_complete : R.drawable.icon_summary;
        }

        img_result.setImageResource(imageResource);
    }

    private void setCorrectOption() {
        if(!mode.equals(Constants.modeCountry)) {
            img_correctFlag.setVisibility(View.GONE);
            txt_correctOption.setText(getString(R.string.LABEL_RESPONSE));
            txt_correctOption.append(correctOption);
        }
        else {
            txt_correctOption.setText(getString(R.string.LABEL_RESPONSE));
            img_correctFlag.setImageResource(Integer.parseInt(correctOption));
        }

    }

    /**
     * Metodo para mostrar el titulo correcto segun el modo
     */
    private void showTitle() {
        if (Constants.modeHardcore.equals(mode)) {
            titleHardcore();
        } else {
            titleCountry();
        }
    }

    private void titleCountry() {
        if(score > Integer.parseInt(record)) {
            txt_title.setText(getResources().getString(R.string.LABEL_NEW_RECORD));
        }
        else {
            txt_title.setText(getResources().getString(R.string.LABEL_FAIL_RECORD));
        }
    }

    /**
     * Metodo para obtener el titulo para el modo - Hardcore
     */
    private void titleHardcore() {
        if(score > 24 ){
            txt_title.setText(getResources().getString(R.string.LABEL_HARDCORE_PASS));
        }
        else if(score > 18 ){
            txt_title.setText(getResources().getString(R.string.LABEL_HARDCORE_NO_PASS));
        }
        else {
            txt_title.setText(getResources().getString(R.string.LABEL_HARDCORE_FAIL));
        }
    }
}

