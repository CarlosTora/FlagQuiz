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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.flagquiz.MainActivity;
import com.flagquiz.R;
import com.flagquiz.model.Flag;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LevelFragment extends Fragment {

    private TextView tittleText;
    private String modeGame;
    private List<Flag> listGlobal;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.level_fragment, container, false);
    }

    public static LevelFragment newInstance(String modeGame) {
        LevelFragment fragment = new LevelFragment();
        Bundle args = new Bundle();
        args.putString("mode", modeGame);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            modeGame = getArguments().getString("mode");
        }

        tittleText = view.findViewById(R.id.txt_tittle_level);
        setTextTittle();


        Button bttLVL1 = view.findViewById(R.id.btt_lvl1);
        Button bttLVL5 = view.findViewById(R.id.btt_lvl5);
        Button bttLVL9 = view.findViewById(R.id.btt_lvl9);
        bttLVL1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chargeLVL1();
            }
        });
        bttLVL5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chargeLVL(5);
            }
        });
        bttLVL9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chargeLVL(9);
            }
        });
    }

    private void setTextTittle() {
        switch (modeGame) {
            case "hardcoreMode":
                tittleText.setText(R.string.MODE_HARDCORE);
                break;
            case "minuteMode":
                tittleText.setText(R.string.MODE_TIME);
                break;

        }
    }

    private void setRecords() {
        switch (modeGame){

            case "hardcoreMode":
                /*
                recordGlobal.setText(String.valueOf(MainActivity.user.getHardcoreGlobal()));
                recordEurope.setText(String.valueOf(MainActivity.user.getHardcoreEurope()));
                recordAmeria.setText(String.valueOf(MainActivity.user.getHardcoreAmerica()));
                recordAsia.setText(String.valueOf(MainActivity.user.getHardcoreAsia()));

                 */
                break;
            case "minuteMode":

                break;
        }
    }

    public void chargeLVL(int level) {
        MapFragment fragment = MapFragment.newInstance("Global",MainActivity.listFlagMain,modeGame,level);
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void chargeLVL1() {
        PreparationFragment fragment = new PreparationFragment();
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}