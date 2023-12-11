package com.flagquiz.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.flagquiz.Constants;
import com.flagquiz.MainActivity;
import com.flagquiz.R;
import com.flagquiz.model.Flag;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RegionsFragment extends Fragment {

    private TextView recordEasy;
    private TextView recordMedium;
    private TextView recordHard;
    private TextView recordExtreme;
    private TextView recordInsane;
    private TextView tittle;
    private String modeGame;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.regions_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            modeGame = getArguments().getString("mode");
        }

        recordEasy = view.findViewById(R.id.txt_record_easy);
        recordMedium = view.findViewById(R.id.txt_record_medium);
        recordHard = view.findViewById(R.id.txt_record_hard);
        recordExtreme = view.findViewById(R.id.txt_record_extreme);
        recordInsane = view.findViewById(R.id.txt_record_insane);

        tittle = view.findViewById(R.id.txt_tittle_level);
        setTittle();
        setRecords();

        Button bttEasy = view.findViewById(R.id.btt_easy);
        Button bttMedium = view.findViewById(R.id.btt_medium);
        Button bttHard = view.findViewById(R.id.btt_hard);
        Button bttExtreme = view.findViewById(R.id.btt_extreme);
        Button bttInsane = view.findViewById(R.id.btt_insane);

        bttEasy.setOnClickListener(v -> loadGame(Constants.EASY,MainActivity.listFlagMain,modeGame,2));
        bttMedium.setOnClickListener(v -> loadGame(Constants.MEDIUM, MainActivity.listFlagMain,modeGame,4));
        bttHard.setOnClickListener(v -> loadGame(Constants.HARD,MainActivity.listFlagMain,modeGame,6));
        bttExtreme.setOnClickListener(v -> loadGame(Constants.EXTREME,MainActivity.listFlagMain,modeGame,8));
        bttInsane.setOnClickListener(v -> loadGame(Constants.INSANE,MainActivity.listFlagMain,modeGame,10));
    }

    private void setTittle() {
        if( modeGame.equals(Constants.modeMinute)) {
            tittle.setText(getResources().getString(R.string.MODE_TIME));
        } else if( modeGame.equals(Constants.modeFlag)) {
            tittle.setText(getResources().getString(R.string.MODE_FLAG));
        }else if( modeGame.equals(Constants.modeCapital)) {
            tittle.setText("Capitales");
        }
        else  {
            tittle.setText(getResources().getString(R.string.MODE_COUNTRY));
        }
    }

    private void setRecords() {
        switch (modeGame){
            case Constants.modeMinute:
                recordEasy.setText( String.valueOf( MainActivity.user.getTimeEasy()) );
                recordMedium.setText( String.valueOf( MainActivity.user.getTimeMedium()) );
                recordHard.setText( String.valueOf( MainActivity.user.getTimeHard()) );
                recordExtreme.setText( String.valueOf( MainActivity.user.getTimeExtreme()) );
                recordInsane.setText( String.valueOf( MainActivity.user.getTimeInsane()) );
                break;

            case Constants.modeFlag:
                recordEasy.setText( String.valueOf( MainActivity.user.getFlagEasy()) );
                recordMedium.setText( String.valueOf( MainActivity.user.getFlagMedium()) );
                recordHard.setText( String.valueOf( MainActivity.user.getFlagHard()) );
                recordExtreme.setText( String.valueOf( MainActivity.user.getFlagExtreme()) );
                recordInsane.setText( String.valueOf( MainActivity.user.getFlagInsane()) );
                break;

            case Constants.modeCountry:
                recordEasy.setText( String.valueOf( MainActivity.user.getCountryEasy()) );
                recordMedium.setText( String.valueOf( MainActivity.user.getCountryMedium()) );
                recordHard.setText( String.valueOf( MainActivity.user.getCountryHard()) );
                recordExtreme.setText( String.valueOf( MainActivity.user.getCountryExtreme()) );
                recordInsane.setText( String.valueOf( MainActivity.user.getCountryInsane()) );
                break;

            case Constants.modeCapital:
                recordEasy.setText( String.valueOf( MainActivity.user.getCountryEasy()) );
                recordMedium.setText( String.valueOf( MainActivity.user.getCountryMedium()) );
                recordHard.setText( String.valueOf( MainActivity.user.getCountryHard()) );
                recordExtreme.setText( String.valueOf( MainActivity.user.getCountryExtreme()) );
                recordInsane.setText( String.valueOf( MainActivity.user.getCountryInsane()) );
                break;
        }
    }

    public void loadGame(String difficulty, List<Flag> listFlags, String modeGame, int level) {
        if(!modeGame.equals(Constants.modeCountry)) { // 1 BANDERA - 4 TEXTOS
            MapFragment fragment = MapFragment.newInstance(difficulty,listFlags,modeGame,level);
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else { // 1 TEXTO - 4 BANDERAS
            CountryFragment fragment = CountryFragment.newInstance(difficulty,listFlags,modeGame,level);
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    public static RegionsFragment newInstance(String modeGame) {
        RegionsFragment fragment = new RegionsFragment();
        Bundle args = new Bundle();
        args.putString("mode", modeGame);
        fragment.setArguments(args);
        return fragment;
    }

    private void loadFlags() {
        //listGlobal = new ArrayList<>(MainActivity.listFlagMain);
        //listEurope = MainActivity.listFlagMain.stream().filter(flag -> flag.getRegion().equals("europe")).collect(Collectors.toList());
        //listAmerica = MainActivity.listFlagMain.stream().filter(flag -> flag.getRegion().equals("america")).collect(Collectors.toList());
        //listAsia = MainActivity.listFlagMain.stream().filter(flag -> flag.getRegion().equals("asia")).collect(Collectors.toList());
    }
}