package com.flagquiz.fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import com.flagquiz.MainActivity;
import com.flagquiz.R;
import com.flagquiz.database.DatabaseHelper;
import com.flagquiz.model.Flag;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PoblationFragment extends Fragment {

    private Button[] optionButtons = new Button[2];
    private ImageView[] imagesResponse = new ImageView[4];
    private int score = 0;
    private String correctOption;

    private Flag flagUP;
    private Flag flagDOWN;
    private ImageView imageTop;
    private ImageView imageDown;

    private DatabaseHelper databaseHelper;
    private TextView hits;
    private TextView record;
    private TextView poblation;
    private TextView newCountry;
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
    private int countLife;
    private ConstraintLayout constraintLayout;
    private boolean isImageUp = true;

    public static PoblationFragment newInstance() {
        PoblationFragment fragment = new PoblationFragment();

        return fragment;
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.poblation_fragment, container, false);
        Bundle args = getArguments();
        imageTop = view.findViewById(R.id.img_flag_up);
        imageDown = view.findViewById(R.id.img_flag_down);

        newCountry = view.findViewById(R.id.txt_newCountrySelected);


        if (args != null){
            /*
            selectedRegion = args.getString("region");
            modeGame = args.getString("modeGame");
            levelGame = args.getInt("level");
            listFlagGame = (List<Flag>) getArguments().getSerializable("list");
            */
        }


        databaseHelper = new DatabaseHelper(requireContext());
        hits = view.findViewById(R.id.txt_hits);
        txt_scoreGame = view.findViewById(R.id.txt_scoreGame);
        record = view.findViewById(R.id.txt_record);
        btt_starGame = view.findViewById(R.id.btt_star_hardcore);
        label_info = view.findViewById(R.id.txt_infoPoblation);
        poblation = view.findViewById(R.id.txt_countryAndPoblation);
        indicationPoints = view.findViewById(R.id.txt_indicatorPoints);
        constraintLayout = view.findViewById(R.id.container);
        positionList = 0;
        starGame = false;

        //hits.setText((String.valueOf(score)));
        imageTop.setVisibility(View.GONE);
        imageDown.setVisibility(View.GONE);

        poblation.setVisibility(View.GONE);
        hits.setText((String.valueOf(score)));
        record.setText("100"); // TODO --> PONER RECORD DEL USER
        indicationPoints.setVisibility(View.GONE);


        btt_starGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starGame(view,requireContext());
            }
        });
        return view;
    }

    private void starGame(View view, Context context) {
        starGame = true;
        btt_starGame.setVisibility(View.GONE);
        label_info.setVisibility(View.GONE);
        imageTop.setVisibility(View.VISIBLE);
        imageDown.setVisibility(View.VISIBLE);
        //bttMore.setVisibility(View.VISIBLE);
        //bttLess.setVisibility(View.VISIBLE);
        poblation.setVisibility(View.VISIBLE);

        flagUP = loadFlag(context);
        flagDOWN = loadFlag(context);

        int resource = getResources().getIdentifier(flagDOWN.getImage(), "drawable", requireContext().getPackageName());
        imageDown.setImageResource(resource);

        int resourceId = getResources().getIdentifier(flagUP.getImage(), "drawable", requireContext().getPackageName());
        imageTop.setImageResource(resourceId);

        poblation.setText(getStringResource(flagUP.getName())+"\n"+formatPoblation(flagUP.getPoblation()));
        newCountry.setText("¿ Cuanta poblacion tiene "+getStringResource(flagDOWN.getName())+" ?");
        //imageTop.setAlpha(0.7f);

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
/*
        bttMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getResult("top",context);
            }
        });
        bttLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getResult("bot",context);

            }
        });

 */
    }

    private void getResult(String optionSelected, Context context) {

        if(optionSelected.equals("top")) {
            if(flagUP.getPoblation() < flagDOWN.getPoblation()){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNewFlag(context);
                        indicationPoints.setVisibility(View.GONE);
                    }
                }, 500);
                showPoints();
                score++;
                hits.setText((String.valueOf(score)));
            }
            else {
                responseError = true;
                //updateRecord();
                showSummaryDialog();
            }
        }

        else {
            if( flagDOWN.getPoblation() <  flagUP.getPoblation()){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNewFlag(context);
                        indicationPoints.setVisibility(View.GONE);
                    }
                }, 500);
                showPoints();
                score++;
                hits.setText((String.valueOf(score)));
            }
            else {
                responseError = true;
                //updateRecord();
                showSummaryDialog();
            }
        }
    }

    private void showSummaryDialog() {
        if(!inDetails) {
            newCountry.setText(getStringResource(flagDOWN.getName())+" tiene "+formatPoblation(flagDOWN.getPoblation())+" de poblacion");
            newCountry.setTextColor(Color.RED);
            inDetails = true;
            SummaryDialogFragment dialogFragment = SummaryDialogFragment.newInstance(score, record.getText().toString(), levelGame, modeGame,"x","x");
            dialogFragment.show(getFragmentManager(), "summary_dialog");
        }
    }

    private void showPoints() {
        indicationPoints.setVisibility(View.VISIBLE);
        indicationPoints.setTextColor(Color.GREEN);
        indicationPoints.setText("+1");
    }


    private String formatPoblation(int poblation) {
        DecimalFormat formato = new DecimalFormat("#,###");
        return formato.format(poblation);
    }

    private void loadNewFlag(Context context) {

        flagUP = new Flag(flagDOWN);
        int resourceId = getResources().getIdentifier(flagUP.getImage(), "drawable", requireContext().getPackageName());
        imageTop.setImageResource(resourceId);

        poblation.setText(getStringResource(flagUP.getName())+"\n"+formatPoblation(flagUP.getPoblation()));

        flagDOWN = loadFlag(context);
        int resource = getResources().getIdentifier(flagDOWN.getImage(), "drawable", requireContext().getPackageName());
        imageDown.setImageResource(resource);
        newCountry.setText("¿ Cuanta poblacion tiene "+getStringResource(flagDOWN.getName())+" ?");
    }

    private String getStringResource(String resourceName) {
        int resourceId = getResources().getIdentifier(resourceName, "string", requireContext().getPackageName());
        return getResources().getString(resourceId);
    }

    private Flag loadFlag(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);

        Flag randomFlag = dbHelper.getRandomFlag();
        dbHelper.close();
        return randomFlag;
    }

    private void clickDown() {
        label_info.setText("abajo");
    }

    private void clickTop() {
        label_info.setText("arriba");
    }


}