package com.flagquiz.fragments;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
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

    TextView lvl1;
    TextView lvl2;
    TextView lvl3;
    TextView lvl4;
    TextView lvl5;
    TextView lvl6;
    TextView lvl7;
    TextView lvl8;
    TextView lvl9;
    TextView lvl10;

    Button bttLVL1;
    Button bttLVL2;
    Button bttLVL3;
    Button bttLVL4;
    Button bttLVL5;
    Button bttLVL6;
    Button bttLVL7;
    Button bttLVL8;
    Button bttLVL9;
    Button bttLVL10;

    ConstraintLayout cons1;
    ConstraintLayout cons2;
    ConstraintLayout cons3;
    ConstraintLayout cons4;
    ConstraintLayout cons5;
    ConstraintLayout cons6;
    ConstraintLayout cons7;
    ConstraintLayout cons8;
    ConstraintLayout cons9;
    ConstraintLayout cons10;
    
    List<ImageView> listStarsLevels;


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

        listStarsLevels = new ArrayList<>();
        tittleText = view.findViewById(R.id.txt_tittle_level);

        bttLVL1 = view.findViewById(R.id.btt_lvl1);
        bttLVL2 = view.findViewById(R.id.btt_lvl2);
        bttLVL3 = view.findViewById(R.id.btt_lvl3);
        bttLVL4 = view.findViewById(R.id.btt_lvl4);
        bttLVL5 = view.findViewById(R.id.btt_lvl5);
        bttLVL6 = view.findViewById(R.id.btt_lvl6);
        bttLVL7 = view.findViewById(R.id.btt_lvl7);
        bttLVL8 = view.findViewById(R.id.btt_lvl8);
        bttLVL9 = view.findViewById(R.id.btt_lvl9);
        bttLVL10 = view.findViewById(R.id.btt_lvl10);

        lvl1 = view.findViewById(R.id.txt_record_lvl1);
        lvl2 = view.findViewById(R.id.txt_record_lvl2);
        lvl3 = view.findViewById(R.id.txt_record_lvl3);
        lvl4 = view.findViewById(R.id.txt_record_lvl4);
        lvl5 = view.findViewById(R.id.txt_record_lvl5);
        lvl6 = view.findViewById(R.id.txt_record_lvl6);
        lvl7 = view.findViewById(R.id.txt_record_lvl7);
        lvl8 = view.findViewById(R.id.txt_record_lvl8);
        lvl9 = view.findViewById(R.id.txt_record_lvl9);
        lvl10 = view.findViewById(R.id.txt_record_lvl10);

        cons1 = view.findViewById(R.id.lvl1);
        cons2 = view.findViewById(R.id.lvl2);
        cons3 = view.findViewById(R.id.lvl3);
        cons4 = view.findViewById(R.id.lvl4);
        cons5 = view.findViewById(R.id.lvl5);
        cons6 = view.findViewById(R.id.lvl6);
        cons7 = view.findViewById(R.id.lvl7);
        cons8 = view.findViewById(R.id.lvl8);
        cons9 = view.findViewById(R.id.lvl9);
        cons10 = view.findViewById(R.id.lvl10);

        listStarsLevels.add(view.findViewById(R.id.img_star_1));
        listStarsLevels.add(view.findViewById(R.id.img_star_2));
        listStarsLevels.add(view.findViewById(R.id.img_star_3));
        listStarsLevels.add(view.findViewById(R.id.img_star_4));
        listStarsLevels.add(view.findViewById(R.id.img_star_5));
        listStarsLevels.add(view.findViewById(R.id.img_star_6));
        listStarsLevels.add(view.findViewById(R.id.img_star_7));
        listStarsLevels.add(view.findViewById(R.id.img_star_8));
        listStarsLevels.add(view.findViewById(R.id.img_star_9));
        listStarsLevels.add(view.findViewById(R.id.img_star_10));

        setStarsLevels();
        setRecords();
        setStyleButtons(bttLVL2, MainActivity.user.getHardcore_1(),cons2);
        setStyleButtons(bttLVL3, MainActivity.user.getHardcore_2(),cons3);
        setStyleButtons(bttLVL4, MainActivity.user.getHardcore_3(),cons4);
        setStyleButtons(bttLVL5, MainActivity.user.getHardcore_4(),cons5);
        setStyleButtons(bttLVL6, MainActivity.user.getHardcore_5(),cons6);
        setStyleButtons(bttLVL7, MainActivity.user.getHardcore_6(),cons7);
        setStyleButtons(bttLVL8, MainActivity.user.getHardcore_7(),cons8);
        setStyleButtons(bttLVL9, MainActivity.user.getHardcore_8(),cons9);
        setStyleButtons(bttLVL10, MainActivity.user.getHardcore_9(),cons10);

        bttLVL1.setOnClickListener(v -> chargeLVL("",1));
        bttLVL2.setOnClickListener(v -> chargeLVL("",2));
        bttLVL3.setOnClickListener(v -> chargeLVL("",3));
        bttLVL4.setOnClickListener(v -> chargeLVL("",4));
        bttLVL5.setOnClickListener(v -> chargeLVL("",5));
        bttLVL6.setOnClickListener(v -> chargeLVL("",6));
        bttLVL7.setOnClickListener(v -> chargeLVL("",7));
        bttLVL8.setOnClickListener(v -> chargeLVL("",8));
        bttLVL9.setOnClickListener(v -> chargeLVL("",9));
        bttLVL10.setOnClickListener(v -> chargeLVL("",10));
    }

    /**
     * Funcion para poner la imagen de la estrella segun corresponda a cada nivel
     */
    private void setStarsLevels() {

        for(int i = 0 ; i< listStarsLevels.size() ; i++) {
            if(i==0) {
                if(MainActivity.user.getHardcore_1() > 24) {
                    listStarsLevels.get(i).setImageResource(android.R.drawable.btn_star_big_on);
                }
                else {
                    listStarsLevels.get(i).setImageResource(android.R.drawable.btn_star_big_off);
                }
            }
            if(i==1) {
                if(MainActivity.user.getHardcore_2() > 24) {
                    listStarsLevels.get(i).setImageResource(android.R.drawable.btn_star_big_on);
                }
                else {
                    listStarsLevels.get(i).setImageResource(android.R.drawable.btn_star_big_off);
                }
            }
            if(i==2) {
                if(MainActivity.user.getHardcore_3() > 24) {
                    listStarsLevels.get(i).setImageResource(android.R.drawable.btn_star_big_on);
                }
                else {
                    listStarsLevels.get(i).setImageResource(android.R.drawable.btn_star_big_off);
                }
            }
            if(i==3) {
                if(MainActivity.user.getHardcore_4() > 24) {
                    listStarsLevels.get(i).setImageResource(android.R.drawable.btn_star_big_on);
                }
                else {
                    listStarsLevels.get(i).setImageResource(android.R.drawable.btn_star_big_off);
                }
            }
            if(i==4) {
                if(MainActivity.user.getHardcore_5() > 24) {
                    listStarsLevels.get(i).setImageResource(android.R.drawable.btn_star_big_on);
                }
                else {
                    listStarsLevels.get(i).setImageResource(android.R.drawable.btn_star_big_off);
                }
            }
            if(i==5) {
                if(MainActivity.user.getHardcore_6() > 24) {
                    listStarsLevels.get(i).setImageResource(android.R.drawable.btn_star_big_on);
                }
                else {
                    listStarsLevels.get(i).setImageResource(android.R.drawable.btn_star_big_off);
                }
            }
            if(i==6) {
                if(MainActivity.user.getHardcore_7() > 24) {
                    listStarsLevels.get(i).setImageResource(android.R.drawable.btn_star_big_on);
                }
                else {
                    listStarsLevels.get(i).setImageResource(android.R.drawable.btn_star_big_off);
                }
            }
            if(i==7) {
                if(MainActivity.user.getHardcore_8() > 24) {
                    listStarsLevels.get(i).setImageResource(android.R.drawable.btn_star_big_on);
                }
                else {
                    listStarsLevels.get(i).setImageResource(android.R.drawable.btn_star_big_off);
                }
            }
            if(i==8) {
                if(MainActivity.user.getHardcore_9() > 24) {
                    listStarsLevels.get(i).setImageResource(android.R.drawable.btn_star_big_on);
                }
                else {
                    listStarsLevels.get(i).setImageResource(android.R.drawable.btn_star_big_off);
                }
            }
            if(i==9) {
                if(MainActivity.user.getHardcore_10() > 24) {
                    listStarsLevels.get(i).setImageResource(android.R.drawable.btn_star_big_on);
                }
                else {
                    listStarsLevels.get(i).setImageResource(android.R.drawable.btn_star_big_off);
                }
            }
        }
    }

    /**
     * Funcion para setear el estilo del boton (habilitado o deshabilitado)
     * @param button boton
     * @param hardcoreLevel nivel del boton
     * @param constraint constraint del boton
     */
    private void setStyleButtons(Button button, int hardcoreLevel, ConstraintLayout constraint) {

        if (hardcoreLevel < 25) {
            button.setBackgroundTintList(ColorStateList.valueOf(R.drawable.button_hardcore));
            button.setEnabled(false);
            constraint.setAlpha(0.5f);
        }
    }

    /**
     * Funcion para poner los records
     */
    private void setRecords() {
        lvl1.setText(String.valueOf(MainActivity.user.getHardcore_1()));
        lvl2.setText(String.valueOf(MainActivity.user.getHardcore_2()));
        lvl3.setText(String.valueOf(MainActivity.user.getHardcore_3()));
        lvl4.setText(String.valueOf(MainActivity.user.getHardcore_4()));
        lvl5.setText(String.valueOf(MainActivity.user.getHardcore_5()));
        lvl6.setText(String.valueOf(MainActivity.user.getHardcore_6()));
        lvl7.setText(String.valueOf(MainActivity.user.getHardcore_7()));
        lvl8.setText(String.valueOf(MainActivity.user.getHardcore_8()));
        lvl9.setText(String.valueOf(MainActivity.user.getHardcore_9()));
        lvl10.setText(String.valueOf(MainActivity.user.getHardcore_10()));
    }

    public void chargeLVL(String difficulty,int level) {
        MapFragment fragment = MapFragment.newInstance(difficulty,MainActivity.listFlagMain,modeGame,level);
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}