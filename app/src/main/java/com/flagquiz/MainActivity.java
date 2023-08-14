package com.flagquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.flagquiz.database.DatabaseHelper;
import com.flagquiz.database.FlagDatabase;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        try {
            update(this);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

         */


    }

    public void hardcoreMode(View view) {
        RegionsFragment fragment = new RegionsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void update(Context context) throws SQLException {
        FlagDatabase flagDatabase = new FlagDatabase(context);

        // BORRA DATABSE
        /*
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        dbHelper.deleteAllFlags();
        dbHelper.close();

         */

        // UPDATE
        flagDatabase.open();






        flagDatabase.close();
    }

}