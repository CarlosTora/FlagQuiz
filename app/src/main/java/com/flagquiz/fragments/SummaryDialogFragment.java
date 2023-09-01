package com.flagquiz.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.flagquiz.MainActivity;
import com.flagquiz.R;

public class SummaryDialogFragment extends DialogFragment {

    private int score;
    private String record;
    private int level;
    private String mode;
    private TextView txt_score;
    private TextView txt_record;

    private FrameLayout frameLayout;

    public static SummaryDialogFragment newInstance(int score, String record,int level, String mode) {
        SummaryDialogFragment fragment = new SummaryDialogFragment();
        Bundle args = new Bundle();
        args.putInt("score", score);
        args.putString("record", record);
        args.putInt("level", level);
        args.putString("mode", mode);
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


        getDialog().getWindow().setLayout(width, height);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().getAttributes().dimAmount = 0.5f; // Opacidad de fondo
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.summary_fragment, container, false);

        Bundle args = getArguments();
        if (args != null) {
            score = args.getInt("score", 0);
            record = args.getString("record", "x");
            level = args.getInt("level", 1);
            mode = args.getString("mode", "x");

            frameLayout = rootView.findViewById(R.id.dialog_background);
            txt_score = rootView.findViewById(R.id.txt_summary_score);
            txt_record = rootView.findViewById(R.id.txt_summary_record);

            frameLayout.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            txt_score.setText(String.valueOf(score));
            txt_record.setText(record);
        }

        // Configura el click listener para el botón de cerrar
        Button closeButton = rootView.findViewById(R.id.btt_closeSummary);
        Button retryButton = rootView.findViewById(R.id.btt_retrySummary);
        Button continueButton = rootView.findViewById(R.id.btt_continueSummary);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                requireFragmentManager().popBackStack();
            }
        });

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                requireFragmentManager().popBackStack();
                MapFragment fragment = MapFragment.newInstance("Global", MainActivity.listFlagMain,mode,level);
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return rootView;
    }
}
