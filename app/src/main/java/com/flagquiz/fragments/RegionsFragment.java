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
import com.flagquiz.database.DatabaseHelper;
import com.flagquiz.model.User;

public class RegionsFragment extends Fragment {

    private TextView recordGlobal;
    private TextView recordEurope;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.regions_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        recordGlobal = view.findViewById(R.id.txt_globalRecord);
        recordEurope = view.findViewById(R.id.txt_europeRecord);
        recordGlobal.setText(String.valueOf(MainActivity.user.getHardcoreGlobal()));
        recordEurope.setText(String.valueOf(MainActivity.user.getHardcoreEurope()));

        Button bttWorld = view.findViewById(R.id.btt_world);
        Button bttEurope = view.findViewById(R.id.btt_europe);
        bttWorld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hardcoreGame("Global");
            }
        });
        bttEurope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hardcoreGame("Europe");
            }
        });
    }

    public void hardcoreGame(String region) {
        MapFragment fragment = MapFragment.newInstance(region);
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}