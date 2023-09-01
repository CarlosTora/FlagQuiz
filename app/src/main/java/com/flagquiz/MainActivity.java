package com.flagquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.flagquiz.database.DatabaseHelper;
import com.flagquiz.database.FlagDatabase;
import com.flagquiz.database.UserDatabase;
import com.flagquiz.fragments.LevelFragment;
import com.flagquiz.fragments.PoblationFragment;
import com.flagquiz.fragments.RegionsFragment;
import com.flagquiz.model.Flag;
import com.flagquiz.model.User;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    public static User user;
    public static List<Flag> listFlagMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView points = this.findViewById(R.id.txt_pointsUser);
        databaseHelper = new DatabaseHelper(this);
/*
        try {
            update(this);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
*/

        user = databaseHelper.getUser();
        listFlagMain = databaseHelper.getAllFlags();
        points.setText(String.valueOf(user.getPoints()));
    }

    public void hardcoreMode(View view) {
        String modeGame = "hardcoreMode"; // Aquí debes obtener la región seleccionada
        LevelFragment fragment = LevelFragment.newInstance(modeGame);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void minuteMode(View view) {
        String modeGame = "minuteMode"; // Aquí debes obtener la región seleccionada
        RegionsFragment fragment = RegionsFragment.newInstance(modeGame);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void flagMode(View view) {
        String modeGame = "flagMode"; // Aquí debes obtener la región seleccionada
        RegionsFragment fragment = RegionsFragment.newInstance(modeGame);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void countryMode(View view) {
        String modeGame = "countryMode"; // Aquí debes obtener la región seleccionada
        RegionsFragment fragment = RegionsFragment.newInstance(modeGame);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void poblacionMode(View view) {
        String modeGame = "poblacionMode"; // Aquí debes obtener la región seleccionada
        PoblationFragment fragment = PoblationFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void update(Context context) throws SQLException {
        FlagDatabase flagDatabase = new FlagDatabase(context);
        UserDatabase userDatabase = new UserDatabase(context);

        // BORRA DATABSE

        DatabaseHelper dbHelper = new DatabaseHelper(context);
        dbHelper.deleteAllFlags();
        dbHelper.deleteAllUsers();
        dbHelper.close();


        flagDatabase.open();
        flagDatabase.insertFlag("flag_albania","FLAG_ALBANIA",2,2818000,"europe");
        flagDatabase.insertFlag("flag_alemania","FLAG_ALEMANIA",1,83279000,"europe");
        flagDatabase.insertFlag("flag_andorra","FLAG_ANDORRA",2,79000,"europe");
        flagDatabase.insertFlag("flag_armenia","FLAG_ARMENIA",1,2960000,"europe");
        flagDatabase.insertFlag("flag_austria","FLAG_AUSTRIA",1,8992000,"europe");
        flagDatabase.insertFlag("flag_azerbaiyan","FLAG_AZERBAIJAN",2,10171000,"europe");
        flagDatabase.insertFlag("flag_belgica","FLAG_BELGICA",1,11621000,"europe");
        flagDatabase.insertFlag("flag_bielorrusia","FLAG_BIELORUSIA",2,9336000,"europe");
        flagDatabase.insertFlag("flag_bosnia_herzegovina","FLAG_BOSNIA",3,3247000,"europe");
        flagDatabase.insertFlag("flag_bulgaria","FLAG_BULGARIA",2,6851000,"europe");
        flagDatabase.insertFlag("flag_chipre","FLAG_CHIPRE",2,913000,"europe");
        flagDatabase.insertFlag("flag_ciudad_vaticano","FLAG_VATICANO",2,825,"europe");
        flagDatabase.insertFlag("flag_croacia","FLAG_CROACIA",1,3899000,"europe");
        flagDatabase.insertFlag("flag_dinamarca","FLAG_DINAMARCA",1,5875000,"europe");
        flagDatabase.insertFlag("flag_eslovaquia","FLAG_ESLOVAQUIA",3,5453000,"europe");
        flagDatabase.insertFlag("flag_eslovenia","FLAG_ESLOVENIA",2,2110000,"europe");
        flagDatabase.insertFlag("flag_espanya","FLAG_ESPAÑA",1,47408000,"europe");
        flagDatabase.insertFlag("flag_estonia","FLAG_ESTONIA",2,1333000,"europe");
        flagDatabase.insertFlag("flag_finlandia","FLAG_FINLANDIA",2,5552000,"europe");
        flagDatabase.insertFlag("flag_francia","FLAG_FRANCIA",1,67413000,"europe");
        flagDatabase.insertFlag("flag_georgia","FLAG_GEORGIA",3,3729000,"europe");
        flagDatabase.insertFlag("flag_grecia","FLAG_GRECIA",1,	10665000,"europe");
        flagDatabase.insertFlag("flag_hungria","FLAG_HUNGRIA",1,9730000,"europe");
        flagDatabase.insertFlag("flag_irlanda","FLAG_IRLANDA",2,6572728,"europe");
        flagDatabase.insertFlag("flag_islandia","FLAG_ISLANDIA",3,375000,"europe");
        flagDatabase.insertFlag("flag_italia","FLAG_ITALIA",1,59096000,"europe");
        flagDatabase.insertFlag("flag_kazajistan","FLAG_KAZAJISTAN",3,	19141000,"europe");
        flagDatabase.insertFlag("flag_letonia","FLAG_LETONIA",2,1875000,"europe");
        flagDatabase.insertFlag("flag_liechtenstein","FLAG_LIECHTENSTEIN",3,39000,"europe");
        flagDatabase.insertFlag("flag_lituania","FLAG_LITUANIA",2,2774000,"europe");
        flagDatabase.insertFlag("flag_luxemburgo","FLAG_LUXEMBURGO",2,645000,"europe");
        /*
        flagDatabase.insertFlag("flag_malta","FLAG_MALTA",3,"europe");
        flagDatabase.insertFlag("flag_moldavia","FLAG_MOLDAVIA",3,"europe");
        flagDatabase.insertFlag("flag_monaco","FLAG_MONACO",3,"europe");
        flagDatabase.insertFlag("flag_montenegro","FLAG_MONTENEGRO",3,"europe");
        flagDatabase.insertFlag("flag_noruega","FLAG_NORUEGA",1,"europe");
        flagDatabase.insertFlag("flag_paises_bajos","FLAG_PAISES_BAJOS",1,"europe");
        flagDatabase.insertFlag("flag_polonia","FLAG_POLONIA",2,"europe");
        flagDatabase.insertFlag("flag_portugal","FLAG_PORTUGAL",1,"europe");
        flagDatabase.insertFlag("flag_reino_unido","FLAG_REINO_UNIDO",1,"europe");
         */
        flagDatabase.insertFlag("flag_republica_checa","FLAG_REP_CHECA",2,10685000,"europe");
        flagDatabase.insertFlag("flag_republica_macedonia","FLAG_MACEDONIA",3,2061000,"europe");
        /*
        flagDatabase.insertFlag("flag_rumania","FLAG_RUMANIA",2,"europe");
        flagDatabase.insertFlag("flag_rusia","FLAG_RUSIA",1,"europe");
        flagDatabase.insertFlag("flag_san_marino","FLAG_SAN_MARINO",3,"europe");
        flagDatabase.insertFlag("flag_serbia","FLAG_SERBIA",2,"europe");
        flagDatabase.insertFlag("flag_suecia","FLAG_SUECIA",1,"europe");
        flagDatabase.insertFlag("flag_suiza","FLAG_SUIZA",1,"europe");
        flagDatabase.insertFlag("flag_turquia","FLAG_TURQUIA",1,"europe");
        flagDatabase.insertFlag("flag_ucrania","FLAG_UCRANIA",1,"europe");

         */

        /** AMERICA  */
        flagDatabase.insertFlag("flag_antigua_barbuda","FLAG_ANTIGUA_BARBUDA",3,100000,"america");
        flagDatabase.insertFlag("flag_argentina","FLAG_ARGENTINA",1,46028000,"america");
        flagDatabase.insertFlag("flag_bahamas","FLAG_BAHAMAS",2,395000,"america");
        flagDatabase.insertFlag("flag_barbados","FLAG_BARBADOS",2,288000,"america");
        flagDatabase.insertFlag("flag_belice","FLAG_BELICE",3,436000,"america");
        flagDatabase.insertFlag("flag_bolivia","FLAG_BOLIVIA",1,11881000,"america");
        flagDatabase.insertFlag("flag_brasil","FLAG_BRASIL",1,214421000,"america");
        flagDatabase.insertFlag("flag_canada","FLAG_CANADA",1,38541000,"america");
        flagDatabase.insertFlag("flag_chile","FLAG_CHILE",1,19818000,"america");
        flagDatabase.insertFlag("flag_colombia","FLAG_COLOMBIA",1,51474000,"america");
        flagDatabase.insertFlag("flag_costa_rica","FLAG_COSTARICA",1,5189000,"america");
        flagDatabase.insertFlag("flag_cuba","FLAG_CUBA",1,11170000,"america");
        flagDatabase.insertFlag("flag_dominica","FLAG_DOMINICA",3,72000,"america");
        flagDatabase.insertFlag("flag_ecuador","FLAG_ECUADOR",1,17927000,"america");
        flagDatabase.insertFlag("flag_eeuu","FLAG_EEUU",1,331893745,"america");
        flagDatabase.insertFlag("flag_el_salvador","FLAG_EL_SALVADOR",1,6328000,"america");
        flagDatabase.insertFlag("flag_granada","FLAG_GRANADA",3,113000,"america");
        flagDatabase.insertFlag("flag_guatemala","FLAG_GUATEMALA",2,17238000,"america");
        flagDatabase.insertFlag("flag_guyana","FLAG_GUYANA",3,791000,"america");
        flagDatabase.insertFlag("flag_haiti","FLAG_HAITI",2,11439646,"america");
        flagDatabase.insertFlag("flag_honduras","FLAG_HONDURAS",2,9587522,"america");
        flagDatabase.insertFlag("flag_jamaica","FLAG_JAMAICA",2,2726667,"america");
        flagDatabase.insertFlag("flag_rep_dominicana","FLAG_REP_DOMINICANA",2,11120000,"america");



        flagDatabase.close();
        userDatabase.open();
        userDatabase.insertUser(new User(1,0,0,0,0,
                0,0,0,0,0,0,0,0,100));
        userDatabase.close();
    }

}