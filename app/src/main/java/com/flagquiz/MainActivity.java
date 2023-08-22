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
        RegionsFragment fragment = RegionsFragment.newInstance(modeGame);
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
    private void update(Context context) throws SQLException {
        FlagDatabase flagDatabase = new FlagDatabase(context);
        UserDatabase userDatabase = new UserDatabase(context);

        // BORRA DATABSE

        DatabaseHelper dbHelper = new DatabaseHelper(context);
        dbHelper.deleteAllFlags();
        dbHelper.deleteAllUsers();
        dbHelper.close();


        flagDatabase.open();
        flagDatabase.insertFlag("flag_albania","FLAG_ALBANIA",2,"europe");
        flagDatabase.insertFlag("flag_alemania","FLAG_ALEMANIA",1,"europe");
        flagDatabase.insertFlag("flag_andorra","FLAG_ANDORRA",2,"europe");
        flagDatabase.insertFlag("flag_armenia","FLAG_ARMENIA",1,"europe");
        flagDatabase.insertFlag("flag_austria","FLAG_AUSTRIA",1,"europe");
        flagDatabase.insertFlag("flag_azerbaijan","FLAG_AZERBAIJAN",2,"europe");
        flagDatabase.insertFlag("flag_belgica","FLAG_BELGICA",1,"europe");
        flagDatabase.insertFlag("flag_bielorusia","FLAG_BIELORUSIA",2,"europe");
        flagDatabase.insertFlag("flag_bosnia_herzegovina","FLAG_BOSNIA",3,"europe");
        flagDatabase.insertFlag("flag_bulgaria","FLAG_BULGARIA",2,"europe");
        flagDatabase.insertFlag("flag_chipre","FLAG_CHIPRE",2,"europe");
        flagDatabase.insertFlag("flag_ciudad_vaticano","FLAG_VATICANO",2,"europe");
        flagDatabase.insertFlag("flag_croacia","FLAG_CROACIA",1,"europe");
        flagDatabase.insertFlag("flag_dinamarca","FLAG_DINAMARCA",1,"europe");
        flagDatabase.insertFlag("flag_eslovaquia","FLAG_ESLOVAQUIA",3,"europe");
        flagDatabase.insertFlag("flag_eslovenia","FLAG_ESLOVENIA",2,"europe");
        flagDatabase.insertFlag("flag_espanya","FLAG_ESPAÑA",1,"europe");
        flagDatabase.insertFlag("flag_estonia","FLAG_ESTONIA",2,"europe");
        flagDatabase.insertFlag("flag_finlandia","FLAG_FINLANDIA",2,"europe");
        flagDatabase.insertFlag("flag_francia","FLAG_FRANCIA",1,"europe");
        flagDatabase.insertFlag("flag_georgia","FLAG_GEORGIA",3,"europe");
        flagDatabase.insertFlag("flag_grecia","FLAG_GRECIA",1,"europe");
        flagDatabase.insertFlag("flag_hungria","FLAG_HUNGRIA",1,"europe");
        flagDatabase.insertFlag("flag_irlanda","FLAG_IRLANDA",2,"europe");
        flagDatabase.insertFlag("flag_islandia","FLAG_ISLANDIA",3,"europe");
        flagDatabase.insertFlag("flag_italia","FLAG_ITALIA",1,"europe");
        flagDatabase.insertFlag("flag_kazajistan","FLAG_KAZAJISTAN",3,"europe");
        flagDatabase.insertFlag("flag_letonia","FLAG_LETONIA",2,"europe");
        flagDatabase.insertFlag("flag_liechtenstein","FLAG_LIECHTENSTEIN",3,"europe");
        flagDatabase.insertFlag("flag_lituania","FLAG_LITUANIA",2,"europe");
        flagDatabase.insertFlag("flag_luxemburgo","FLAG_LUXEMBURGO",2,"europe");
        flagDatabase.insertFlag("flag_malta","FLAG_MALTA",3,"europe");
        flagDatabase.insertFlag("flag_moldavia","FLAG_MOLDAVIA",3,"europe");
        flagDatabase.insertFlag("flag_monaco","FLAG_MONACO",3,"europe");
        flagDatabase.insertFlag("flag_montenegro","FLAG_MONTENEGRO",3,"europe");
        flagDatabase.insertFlag("flag_noruega","FLAG_NORUEGA",1,"europe");
        flagDatabase.insertFlag("flag_paises_bajos","FLAG_PAISES_BAJOS",1,"europe");
        flagDatabase.insertFlag("flag_polonia","FLAG_POLONIA",2,"europe");
        flagDatabase.insertFlag("flag_portugal","FLAG_PORTUGAL",1,"europe");
        flagDatabase.insertFlag("flag_reino_unido","FLAG_REINO_UNIDO",1,"europe");
        flagDatabase.insertFlag("flag_republica_checa","FLAG_REP_CHECA",2,"europe");
        flagDatabase.insertFlag("flag_republica_macedonia","FLAG_MACEDONIA",3,"europe");
        flagDatabase.insertFlag("flag_rumania","FLAG_RUMANIA",2,"europe");
        flagDatabase.insertFlag("flag_rusia","FLAG_RUSIA",1,"europe");
        flagDatabase.insertFlag("flag_san_marino","FLAG_SAN_MARINO",3,"europe");
        flagDatabase.insertFlag("flag_serbia","FLAG_SERBIA",2,"europe");
        flagDatabase.insertFlag("flag_suecia","FLAG_SUECIA",1,"europe");
        flagDatabase.insertFlag("flag_suiza","FLAG_SUIZA",1,"europe");
        flagDatabase.insertFlag("flag_turquia","FLAG_TURQUIA",1,"europe");
        flagDatabase.insertFlag("flag_ucrania","FLAG_UCRANIA",1,"europe");

        /** AMERICA  */
        flagDatabase.insertFlag("flag_antigua_barbuda","FLAG_ANTIGUA_BARBUDA",3,"america");
        flagDatabase.insertFlag("flag_argentina","FLAG_ARGENTINA",1,"america");
        flagDatabase.insertFlag("flag_bahamas","FLAG_BAHAMAS",2,"america");
        flagDatabase.insertFlag("flag_barbados","FLAG_BARBADOS",2,"america");
        flagDatabase.insertFlag("flag_belice","FLAG_BELICE",3,"america");
        flagDatabase.insertFlag("flag_bolivia","FLAG_BOLIVIA",1,"america");
        flagDatabase.insertFlag("flag_brasil","FLAG_BRASIL",1,"america");
        flagDatabase.insertFlag("flag_canada","FLAG_CANADA",1,"america");
        flagDatabase.insertFlag("flag_chile","FLAG_CHILE",1,"america");
        flagDatabase.insertFlag("flag_colombia","FLAG_COLOMBIA",1,"america");
        flagDatabase.insertFlag("flag_costarica","FLAG_COSTARICA",1,"america");
        flagDatabase.insertFlag("flag_cuba","FLAG_CUBA",1,"america");
        flagDatabase.insertFlag("flag_dominica","FLAG_DOMINICA",3,"america");
        flagDatabase.insertFlag("flag_ecuador","FLAG_ECUADOR",1,"america");
        flagDatabase.insertFlag("flag_eeuu","FLAG_EEUU",1,"america");
        flagDatabase.insertFlag("flag_el_salvador","FLAG_EL_SALVADOR",1,"america");
        flagDatabase.insertFlag("flag_granada","FLAG_GRANADA",3,"america");
        flagDatabase.insertFlag("flag_guatemala","FLAG_GUATEMALA",2,"america");



        flagDatabase.insertFlag("afganis","FLAG_AF1",2,"asia");
        flagDatabase.insertFlag("flag_afganistan","FLAG_AF2",2,"asia");
        flagDatabase.insertFlag("afganis","FLAG_AF3",2,"asia");
        flagDatabase.insertFlag("flag_afganistan","FLAG_AF4",2,"asia");

        flagDatabase.close();
        userDatabase.open();
        userDatabase.insertUser(new User(1,0,0,0,0,0,0,100));
        userDatabase.close();
    }

}