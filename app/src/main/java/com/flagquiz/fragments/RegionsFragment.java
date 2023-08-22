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

import com.flagquiz.MainActivity;
import com.flagquiz.R;
import com.flagquiz.model.Flag;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RegionsFragment extends Fragment {

    private TextView recordGlobal;
    private TextView recordEurope;
    private TextView recordAmeria;
    private TextView recordAsia;
    private String modeGame;
    private List<Flag> listGlobal;
    private List<Flag> listEurope;
    private List<Flag> listAmerica;
    private List<Flag> listAsia;


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

        loadFlags();
        recordGlobal = view.findViewById(R.id.txt_globalRecord);
        recordEurope = view.findViewById(R.id.txt_europeRecord);
        recordAmeria = view.findViewById(R.id.txt_americaRecord);
        recordAsia = view.findViewById(R.id.txt_asiaRecord);
        setRecords();

        Button bttWorld = view.findViewById(R.id.btt_world);
        Button bttEurope = view.findViewById(R.id.btt_europe);
        Button bttAmerica = view.findViewById(R.id.btt_america);
        Button bttAsia = view.findViewById(R.id.btt_asia);
        bttWorld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hardcoreGame("Global",listGlobal,modeGame);
            }
        });
        bttEurope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hardcoreGame("Europe", listEurope,modeGame);
            }
        });

        bttAmerica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hardcoreGame("America",listAmerica,modeGame);
            }
        });
        bttAsia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hardcoreGame("Asia",listAsia,modeGame);
            }
        });
    }

    private void setRecords() {
        switch (modeGame){

            case "hardcoreMode":
                recordGlobal.setText(String.valueOf(MainActivity.user.getHardcoreGlobal()));
                recordEurope.setText(String.valueOf(MainActivity.user.getHardcoreEurope()));
                recordAmeria.setText(String.valueOf(MainActivity.user.getHardcoreAmerica()));
                recordAsia.setText(String.valueOf(MainActivity.user.getHardcoreAsia()));
                break;
            case "minuteMode":
                recordGlobal.setText("43 / "+listGlobal.size());
                recordEurope.setText("13 / "+listEurope.size());
                recordAmeria.setText("4 / "+listAmerica.size());
                recordAsia.setText("0 / "+listAsia.size());
                break;
        }
    }

    public void hardcoreGame(String region, List<Flag> listFlags, String modeGame) {
        MapFragment fragment = MapFragment.newInstance(region,listFlags,modeGame);
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static RegionsFragment newInstance(String modeGame) {
        RegionsFragment fragment = new RegionsFragment();
        Bundle args = new Bundle();
        args.putString("mode", modeGame);
        fragment.setArguments(args);
        return fragment;
    }

    private void loadFlags() {

        listGlobal = new ArrayList<>(MainActivity.listFlagMain);
        listEurope = MainActivity.listFlagMain.stream().filter(flag -> flag.getRegion().equals("europe")).collect(Collectors.toList());
        listAmerica = MainActivity.listFlagMain.stream().filter(flag -> flag.getRegion().equals("america")).collect(Collectors.toList());
        listAsia = MainActivity.listFlagMain.stream().filter(flag -> flag.getRegion().equals("asia")).collect(Collectors.toList());
    }
}