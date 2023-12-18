package com.flagquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.flagquiz.database.DatabaseHelper;
import com.flagquiz.database.FlagDatabase;
import com.flagquiz.database.UserDatabase;
import com.flagquiz.fragments.LanguageFragment;
import com.flagquiz.fragments.LevelFragment;
import com.flagquiz.fragments.RegionsFragment;
import com.flagquiz.model.Flag;
import com.flagquiz.model.User;
import com.flagquiz.utils.LocaleHelper;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    public static User user;
    public static List<Flag> listFlagMain;
    private static String languageSelected ;
    public static SharedPreferences sharedPreferences;
    private final String[] codesList = new String[]{"es", "en","fr","de"};

    @Override
    protected void attachBaseContext(Context newBase) {
        SharedPreferences sharedPreferences = newBase.getSharedPreferences("Settings", MODE_PRIVATE);
        int indexLanguage = Arrays.asList(codesList).indexOf(sharedPreferences.getString("language", ""));
        String language = indexLanguage == -1 ? "en" : codesList[indexLanguage];
        languageSelected = language;
        Context context = LocaleHelper.changeLanguage(newBase, language);
        super.attachBaseContext(context);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Configura la orientación a retrato
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        databaseHelper = new DatabaseHelper(this);

        //SELECT LANGUAGE APP
        sharedPreferences = this.getSharedPreferences("Settings", MODE_PRIVATE);

        Locale locale = getResources().getConfiguration().getLocales().get(0);
        if(!locale.getLanguage().equals(languageSelected)){
            LocaleHelper.changeLanguage(this.getApplicationContext(), languageSelected);
            languageSelected = locale.getLanguage();
            MainActivity.this.recreate();
        }


        // ADS BLOCK
        MobileAds.initialize(this, initializationStatus -> {});

        List<String> testDeviceIds = Arrays.asList("C38E9DA7733C9477F664672BF823516E", "23ED7E7400D5D7A0167F916AC22FB41C");
        RequestConfiguration configuration =
                new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        MobileAds.setRequestConfiguration(configuration);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.e("AdMob", "Ad failed to load: " + loadAdError.getMessage());
            }
        });
        // FIN ADS BLOCK

        // Verificar si los datos ya existen en la base de datos
        boolean userExist = checkUserExistsDB();
        boolean flagsExist = checkFlagExistsDB();
        try {
            if (!userExist) {
                insertUser(this);
            }
            if (!flagsExist) {
                insertFlags(this);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        user = databaseHelper.getUser();
        listFlagMain = databaseHelper.getAllFlags();
    }
    private boolean checkUserExistsDB() {
        return databaseHelper.checkUserDataExists();
    }
    private boolean checkFlagExistsDB() {
        return databaseHelper.checkFlagDataExists();
    }

    public void hardcoreMode(View view) {
        String modeGame = Constants.modeHardcore;
        LevelFragment fragment = LevelFragment.newInstance(modeGame);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void minuteMode(View view) {
        String modeGame =  Constants.modeMinute;
        RegionsFragment fragment = RegionsFragment.newInstance(modeGame);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void flagMode(View view) {
        RegionsFragment fragment = RegionsFragment.newInstance(Constants.modeFlag);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void countryMode(View view) {
        RegionsFragment fragment = RegionsFragment.newInstance(Constants.modeCountry);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void capitalMode(View view) {
        RegionsFragment fragment = RegionsFragment.newInstance(Constants.modeCapital);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void poblacionMode(View view) {

    }
    public void worldMode(View view) {

    }

    public void changeLanguage(View view) {
        LanguageFragment dialogFragment = new LanguageFragment();
        dialogFragment.show(getSupportFragmentManager(), "language_dialog");
    }


    /**
     * BORRA e INSERTA los datos de usuario
     */
    private void insertUser(Context context) throws SQLException {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        dbHelper.deleteAllUsers();
        dbHelper.close();
        UserDatabase userDatabase = new UserDatabase(context);
        userDatabase.open();
        userDatabase.insertUser(new User(1,0,0,0,0,
                0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,
                0,0,0,0,0,0,
                0,0,0,0,0,0,100));
        userDatabase.close();
    }

    /**
     * BORRA e INSERTA los datos de las banderas
     */
    private void insertFlags(Context context) throws SQLException {

        // BORRA DATABASE
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        dbHelper.deleteAllFlags();
        dbHelper.close();

        FlagDatabase flagDatabase = new FlagDatabase(context);
        flagDatabase.open();
        /* EUROPE */
        flagDatabase.insertFlag("flag_albania","FLAG_ALBANIA",3,2818000,"CAP_ALBANIA","europe");
        flagDatabase.insertFlag("flag_alemania","FLAG_ALEMANIA",1,83279000,"CAP_ALEMANIA","europe");
        flagDatabase.insertFlag("flag_andorra","FLAG_ANDORRA",2,79000,"CAP_ANDORRA","europe");
        flagDatabase.insertFlag("flag_armenia","FLAG_ARMENIA",4,2960000,"CAP_ARMENIA","europe");
        flagDatabase.insertFlag("flag_austria","FLAG_AUSTRIA",2,8992000,"CAP_AUSTRIA","europe");
        flagDatabase.insertFlag("flag_azerbaiyan","FLAG_AZERBAIJAN",4,10171000,"CAP_AZERBAIJAN","europe");
        flagDatabase.insertFlag("flag_belgica","FLAG_BELGICA",1,11621000,"CAP_BELGICA","europe");
        flagDatabase.insertFlag("flag_bielorrusia","FLAG_BIELORUSIA",3,9336000,"CAP_BIELORUSIA","europe");
        flagDatabase.insertFlag("flag_bosnia_herzegovina","FLAG_BOSNIA",4,3247000,"CAP_BOSNIA","europe");
        flagDatabase.insertFlag("flag_bulgaria","FLAG_BULGARIA",2,6851000,"CAP_BULGARIA","europe");
        flagDatabase.insertFlag("flag_chipre","FLAG_CHIPRE",2,913000,"CAP_CHIPRE","europe");
        flagDatabase.insertFlag("flag_ciudad_vaticano","FLAG_VATICANO",1,825,"CAP_VATICANO","europe");
        flagDatabase.insertFlag("flag_croacia","FLAG_CROACIA",1,3899000,"CAP_CROACIA","europe");
        flagDatabase.insertFlag("flag_dinamarca","FLAG_DINAMARCA",1,5875000,"CAP_DINAMARCA","europe");
        flagDatabase.insertFlag("flag_eslovaquia","FLAG_ESLOVAQUIA",2,5453000,"CAP_ESLOVAQUIA","europe");
        flagDatabase.insertFlag("flag_eslovenia","FLAG_ESLOVENIA",2,2110000,"CAP_ESLOVENIA","europe");
        flagDatabase.insertFlag("flag_espanya","FLAG_ESPAÑA",1,47408000,"CAP_ESPAÑA","europe");
        flagDatabase.insertFlag("flag_estonia","FLAG_ESTONIA",2,1333000,"CAP_ESTONIA","europe");
        flagDatabase.insertFlag("flag_finlandia","FLAG_FINLANDIA",2,5552000,"CAP_FINLANDIA","europe");
        flagDatabase.insertFlag("flag_francia","FLAG_FRANCIA",1,67413000,"CAP_FRANCIA","europe");
        flagDatabase.insertFlag("flag_georgia","FLAG_GEORGIA",5,3729000,"CAP_GEORGIA","europe");
        flagDatabase.insertFlag("flag_grecia","FLAG_GRECIA",1,10665000,"CAP_GRECIA","europe");
        flagDatabase.insertFlag("flag_hungria","FLAG_HUNGRIA",3,9730000,"CAP_HUNGRIA","europe");
        flagDatabase.insertFlag("flag_irlanda","FLAG_IRLANDA",2,6572728,"CAP_IRLANDA","europe");
        flagDatabase.insertFlag("flag_islandia","FLAG_ISLANDIA",4,375000,"CAP_ISLANDIA","europe");
        flagDatabase.insertFlag("flag_italia","FLAG_ITALIA",1,59096000,"CAP_ITALIA","europe");
        flagDatabase.insertFlag("flag_kazajistan","FLAG_KAZAJISTAN",5,	19141000,"CAP_KAZAJISTAN","europe");
        flagDatabase.insertFlag("flag_letonia","FLAG_LETONIA",3,1875000,"CAP_LETONIA","europe");
        flagDatabase.insertFlag("flag_liechtenstein","FLAG_LIECHTENSTEIN",5,39000,"CAP_LIECHTENSTEIN","europe");
        flagDatabase.insertFlag("flag_lituania","FLAG_LITUANIA",4,2774000,"CAP_LITUANIA","europe");
        flagDatabase.insertFlag("flag_luxemburgo","FLAG_LUXEMBURGO",2,645000,"CAP_LUXEMBURGO","europe");
        flagDatabase.insertFlag("flag_malta","FLAG_MALTA",4,0,"CAP_MALTA","europe");
        flagDatabase.insertFlag("flag_moldavia","FLAG_MOLDAVIA",4,0,"CAP_MOLDAVIA","europe");
        flagDatabase.insertFlag("flag_monaco","FLAG_MONACO",2,0,"CAP_MONACO","europe");
        flagDatabase.insertFlag("flag_montenegro","FLAG_MONTENEGRO",4,0,"CAP_MONTENEGRO","europe");
        flagDatabase.insertFlag("flag_noruega","FLAG_NORUEGA",2,0,"CAP_NORUEGA","europe");
        flagDatabase.insertFlag("flag_paises_bajos","FLAG_PAISES_BAJOS",1,0,"CAP_PAISES_BAJOS","europe");
        flagDatabase.insertFlag("flag_polonia","FLAG_POLONIA",2,0,"CAP_POLONIA","europe");
        flagDatabase.insertFlag("flag_portugal","FLAG_PORTUGAL",1,0,"CAP_PORTUGAL","europe");
        flagDatabase.insertFlag("flag_reino_unido","FLAG_REINO_UNIDO",1,0,"CAP_REINO_UNIDO","europe");
        flagDatabase.insertFlag("flag_republica_checa","FLAG_REP_CHECA",3,10685000,"CAP_REP_CHECA","europe");
        flagDatabase.insertFlag("flag_republica_macedonia","FLAG_MACEDONIA",5,2061000,"CAP_MACEDONIA","europe");
        flagDatabase.insertFlag("flag_rumania","FLAG_RUMANIA",3,0,"CAP_RUMANIA","europe");
        flagDatabase.insertFlag("flag_rusia","FLAG_RUSIA",1,0,"CAP_RUSIA","europe");
        flagDatabase.insertFlag("flag_san_marino","FLAG_SAN_MARINO",4,0,"CAP_SAN_MARINO","europe");
        flagDatabase.insertFlag("flag_serbia","FLAG_SERBIA",2,0,"CAP_SERBIA","europe");
        flagDatabase.insertFlag("flag_suecia","FLAG_SUECIA",2,0,"CAP_SUECIA","europe");
        flagDatabase.insertFlag("flag_suiza","FLAG_SUIZA",1,0,"CAP_SUIZA","europe");
        flagDatabase.insertFlag("flag_turquia","FLAG_TURQUIA",1,0,"CAP_TURQUIA","europe");
        flagDatabase.insertFlag("flag_ucrania","FLAG_UCRANIA",1,0,"CAP_UCRANIA","europe");

        /* ASIA */
        flagDatabase.insertFlag("flag_afganistan","FLAG_AFGANISTAN",3,0,"CAP_AFGANISTAN","asia");
        flagDatabase.insertFlag("flag_arabia_saudi","FLAG_ARABIA_SAUDI",1,0,"CAP_ARABIA_SAUDI","asia");
        flagDatabase.insertFlag("flag_bahrein","FLAG_BAHREIN",4,0,"CAP_BAHREIN","asia");
        flagDatabase.insertFlag("flag_banglades","FLAG_BANGLADES",5,0,"CAP_BANGLADES","asia");
        flagDatabase.insertFlag("flag_bhutan","FLAG_BHUTAN",5,0,"CAP_BHUTAN","asia");
        flagDatabase.insertFlag("flag_brunei","FLAG_BRUNEI",5,0,"CAP_BRUNEI","asia");
        flagDatabase.insertFlag("flag_camboya","FLAG_CAMBOYA",4,0,"CAP_CAMBOYA","asia");
        flagDatabase.insertFlag("flag_catar","FLAG_CATAR",1,0,"CAP_CATAR","asia");
        flagDatabase.insertFlag("flag_china","FLAG_CHINA",1,0,"CAP_CHINA","asia");
        flagDatabase.insertFlag("flag_corea_norte","FLAG_COREA_NORTE",1,0,"CAP_COREA_NORTE","asia");
        flagDatabase.insertFlag("flag_corea_sur","FLAG_COREA_SUR",1,0,"CAP_COREA_SUR","asia");
        flagDatabase.insertFlag("flag_emiratos_arabes","FLAG_EMIRATOS_ARABES",2,0,"CAP_EMIRATOS_ARABES","asia");
        flagDatabase.insertFlag("flag_filipinas","FLAG_FILIPINAS",2,0,"CAP_FILIPINAS","asia");
        flagDatabase.insertFlag("flag_india","FLAG_INDIA",1,0,"CAP_INDIA","asia");
        flagDatabase.insertFlag("flag_indonesia","FLAG_INDONESIA",2,0,"CAP_INDONESIA","asia");
        flagDatabase.insertFlag("flag_irak","FLAG_IRAK",2,0,"CAP_IRAK","asia");
        flagDatabase.insertFlag("flag_iran","FLAG_IRAN",3,0,"CAP_IRAN","asia");
        flagDatabase.insertFlag("flag_israel","FLAG_ISRAEL",1,0,"CAP_ISRAEL","asia");
        flagDatabase.insertFlag("flag_japon","FLAG_JAPON",1,0,"CAP_JAPON","asia");
        flagDatabase.insertFlag("flag_jordania","FLAG_JORDANIA",2,0,"CAP_JORDANIA","asia");
        flagDatabase.insertFlag("flag_kirguistan","FLAG_KIRGUISTAN",5,0,"CAP_KIRGUISTAN","asia");
        flagDatabase.insertFlag("flag_kuwait","FLAG_KUWAIT",5,0,"CAP_KUWAIT","asia");
        flagDatabase.insertFlag("flag_laos","FLAG_LAOS",4,0,"CAP_LAOS","asia");
        flagDatabase.insertFlag("flag_libano","FLAG_LIBANO",3,0,"CAP_LIBANO","asia");
        flagDatabase.insertFlag("flag_maldivas","FLAG_MALDIVA",3,0,"CAP_MALDIVA","asia");
        flagDatabase.insertFlag("flag_mongolia","FLAG_MONGOLIA",3,0,"CAP_MONGOLIA","asia");
        flagDatabase.insertFlag("flag_nepal","FLAG_NEPAL",4,0,"CAP_NEPAL","asia");
        flagDatabase.insertFlag("flag_nyanmar","FLAG_NYANMAR",4,0,"CAP_NYANMAR","asia");
        flagDatabase.insertFlag("flag_oman","FLAG_OMAN",3,0,"CAP_OMAN","asia");
        flagDatabase.insertFlag("flag_pakistan","FLAG_PAKISTAN",2,0,"CAP_PAKISTAN","asia");
        flagDatabase.insertFlag("flag_palestina","FLAG_PALESTINA",1,0,"CAP_PALESTINA","asia");
        flagDatabase.insertFlag("flag_singapur","FLAG_SINGAPUR",2,0,"CAP_SINGAPUR","asia");
        flagDatabase.insertFlag("flag_siria","FLAG_SIRIA",2,0,"CAP_SIRIA","asia");
        flagDatabase.insertFlag("flag_sri_lanka","FLAG_SRI_LANKA",5,0,"CAP_SRI_LANKA","asia");
        flagDatabase.insertFlag("flag_tailandia","FLAG_TAILANDIA",2,0,"CAP_TAILANDIA","asia");
        flagDatabase.insertFlag("flag_tayikistan","FLAG_TAYIKISTAN",4,0,"CAP_TAYIKISTAN","asia");
        flagDatabase.insertFlag("flag_timor_oriental","FLAG_TIMOR_ORIENTAL",4,0,"CAP_TIMOR_ORIENTAL","asia");
        flagDatabase.insertFlag("flag_turkmenistan","FLAG_TURKMENISTAN",5,0,"CAP_TURKMENISTAN","asia");
        flagDatabase.insertFlag("flag_uzbekistan","FLAG_UZBEKISTAN",3,0,"CAP_UZBEKISTAN","asia");
        flagDatabase.insertFlag("flag_yemen","FLAG_YEMEN",2,0,"CAP_YEMEN","asia");


        /* AMERICA  */
        flagDatabase.insertFlag("flag_antigua_barbuda","FLAG_ANTIGUA_BARBUDA",4,100000,"CAP_ANTIGUA_BARBUDA","america");
        flagDatabase.insertFlag("flag_argentina","FLAG_ARGENTINA",1,46028000,"CAP_ARGENTINA","america");
        flagDatabase.insertFlag("flag_bahamas","FLAG_BAHAMAS",4,395000,"CAP_BAHAMAS","america");
        flagDatabase.insertFlag("flag_barbados","FLAG_BARBADOS",3,288000,"CAP_BARBADOS","america");
        flagDatabase.insertFlag("flag_belice","FLAG_BELICE",3,436000,"CAP_BELICE","america");
        flagDatabase.insertFlag("flag_bolivia","FLAG_BOLIVIA",2,11881000,"CAP_BOLIVIA","america");
        flagDatabase.insertFlag("flag_brasil","FLAG_BRASIL",1,214421000,"CAP_BRASIL","america");
        flagDatabase.insertFlag("flag_canada","FLAG_CANADA",1,38541000,"CAP_CANADA","america");
        flagDatabase.insertFlag("flag_chile","FLAG_CHILE",1,19818000,"CAP_CHILE","america");
        flagDatabase.insertFlag("flag_colombia","FLAG_COLOMBIA",1,51474000,"CAP_COLOMBIA","america");
        flagDatabase.insertFlag("flag_costa_rica","FLAG_COSTARICA",2,5189000,"CAP_COSTARICA","america");
        flagDatabase.insertFlag("flag_cristobal_nieves","FLAG_CRISTOBAL_NIEVES",5,0,"CAP_CRISTOBAL_NIEVES","america");
        flagDatabase.insertFlag("flag_cuba","FLAG_CUBA",1,11170000,"CAP_CUBA","america");
        flagDatabase.insertFlag("flag_dominica","FLAG_DOMINICA",3,72000,"CAP_DOMINICA","america");
        flagDatabase.insertFlag("flag_ecuador","FLAG_ECUADOR",2,17927000,"CAP_ECUADOR","america");
        flagDatabase.insertFlag("flag_eeuu","FLAG_EEUU",1,331893745,"CAP_EEUU","america");
        flagDatabase.insertFlag("flag_el_salvador","FLAG_EL_SALVADOR",2,6328000,"CAP_EL_SALVADOR","america");
        flagDatabase.insertFlag("flag_granada","FLAG_GRANADA",5,124000,"CAP_GRANADA","america");
        flagDatabase.insertFlag("flag_guatemala","FLAG_GUATEMALA",3,17238000,"CAP_GUATEMALA","america");
        flagDatabase.insertFlag("flag_guyana","FLAG_GUYANA",5,791000,"CAP_GUYANA","america");
        flagDatabase.insertFlag("flag_haiti","FLAG_HAITI",4,11439646,"CAP_HAITI","america");
        flagDatabase.insertFlag("flag_honduras","FLAG_HONDURAS",3,9587522,"CAP_HONDURAS","america");
        flagDatabase.insertFlag("flag_jamaica","FLAG_JAMAICA",2,2726667,"CAP_JAMAICA","america");
        flagDatabase.insertFlag("flag_mexico","FLAG_MEXICO",1,0,"CAP_MEXICO","america");
        flagDatabase.insertFlag("flag_nicaragua","FLAG_NICARAGUA",2,0,"CAP_NICARAGUA","america");
        flagDatabase.insertFlag("flag_panama","FLAG_PANAMA",2,0,"CAP_PANAMA","america");
        flagDatabase.insertFlag("flag_paraguay","FLAG_PARAGUAY",2,0,"CAP_PARAGUAY", "america");
        flagDatabase.insertFlag("flag_peru","FLAG_PERU",2,0,"CAP_PERU","america");
        flagDatabase.insertFlag("flag_rep_dominicana","FLAG_REP_DOMINICANA",3,11120000,"CAP_REP_DOMINICANA","america");
        flagDatabase.insertFlag("flag_san_vicente_granadinas","FLAG_SAN_VICENTE",4,0,"CAP_SAN_VICENTE","america");
        flagDatabase.insertFlag("flag_santa_lucia","FLAG_SANTA_LUCIA",5,0,"CAP_SANTA_LUCIA","america");
        flagDatabase.insertFlag("flag_surinam","FLAG_SURINAM",5,0,"CAP_SURINAM","america");
        flagDatabase.insertFlag("flag_trinida_tobago","FLAG_TRINIDAD_TOBAGO",4,0,"CAP_TRINIDAD_TOBAGO","america");
        flagDatabase.insertFlag("flag_uruguay","FLAG_URUGUAY",1,0,"CAP_URUGUAY","america");
        flagDatabase.insertFlag("flag_venezuela","FLAG_VENEZUELA",2,0,"CAP_VENEZUELA","america");


        /* AFRICA  */
        flagDatabase.insertFlag("flag_angola","FLAG_ANGOLA",2,0,"CAP_ANGOLA","africa");
        flagDatabase.insertFlag("flag_argelia","FLAG_ARGELIA",2,0,"CAP_ARGELIA","africa");
        flagDatabase.insertFlag("flag_benin","FLAG_BENIN",4,0,"CAP_BENIN","africa");
        flagDatabase.insertFlag("flag_botsuana","FLAG_BOTSUANA",3,0,"CAP_BOTSUANA","africa");
        flagDatabase.insertFlag("flag_burkina","FLAG_BURKINA",3,0,"CAP_BURKINA","africa");
        flagDatabase.insertFlag("flag_burundi","FLAG_BURUNDI",4,0,"CAP_BURUNDI","africa");
        flagDatabase.insertFlag("flag_cabo_verde","FLAG_CABO_VERDE",5,0,"CAP_CABO_VERDE","africa");
        flagDatabase.insertFlag("flag_camerun","FLAG_CAMERUN",1,0,"CAP_CAMERUN","africa");
        flagDatabase.insertFlag("flag_centroaf","FLAG_CENTRO_AFR",4,5457000,"CAP_CENTRO_AFR","africa");
        flagDatabase.insertFlag("flag_chad","FLAG_CHAD",5,17180000,"CAP_CHAD","africa");
        flagDatabase.insertFlag("flag_comoras","FLAG_COMORAS",5,0,"CAP_COMORAS","africa");
        flagDatabase.insertFlag("flag_congo","FLAG_CONGO",4,0,"CAP_CONGO","africa");
        flagDatabase.insertFlag("flag_congo_demo","FLAG_CONGO_DEMO",4,0,"CAP_CONGO_DEMO","africa");
        flagDatabase.insertFlag("flag_costa_marfil","FLAG_COSTA_MARFIL",2,0,"CAP_COSTA_MARFIL","africa");
        flagDatabase.insertFlag("flag_egipto","FLAG_EGIPTO",1,0,"CAP_EGIPTO","africa");
        flagDatabase.insertFlag("flag_eritrea","FLAG_ERITREA",5,6147000,"CAP_ERITREA","africa");
        flagDatabase.insertFlag("flag_esuatini","FLAG_ESUATINI",5,0,"CAP_ESUATINI","africa");
        flagDatabase.insertFlag("flag_etiopia","FLAG_ETIOPIA",4,0,"CAP_ETIOPIA","africa");
        flagDatabase.insertFlag("flag_gabon","FLAG_GABON",3,0,"CAP_GABON","africa");
        flagDatabase.insertFlag("flag_gambia","FLAG_GAMBIA",3,0,"CAP_GAMBIA","africa");
        flagDatabase.insertFlag("flag_ghana","FLAG_GHANA",2,0,"CAP_GHANA","africa");
        flagDatabase.insertFlag("flag_guinea","FLAG_GUINEA",3,13530000,"CAP_GUINEA","africa");
        flagDatabase.insertFlag("flag_guinea_bisau","FLAG_GUINEA_BISAU",4,2061000,"CAP_GUINEA_BISAU","africa");
        flagDatabase.insertFlag("flag_guinea_ecuatorial","FLAG_GUINEA_ECUA",5,0,"CAP_GUINEA_ECUA","africa");
        flagDatabase.insertFlag("flag_kenia","FLAG_KENIA",2,0,"CAP_KENIA","africa");
        flagDatabase.insertFlag("flag_lesoto","FLAG_LESOTO",4,2281000,"CAP_LESOTO","africa");
        flagDatabase.insertFlag("flag_libia","FLAG_LIBIA",2,0,"CAP_LIBIA","africa");
        flagDatabase.insertFlag("flag_madagascar","FLAG_MADAGASCAR",2,0,"CAP_MADAGASCAR","africa");
        flagDatabase.insertFlag("flag_malaui","FLAG_MALAUI",5,0,"CAP_MALAUI","africa");
        flagDatabase.insertFlag("flag_mali","FLAG_MALI",2,0,"CAP_MALI","africa");
        flagDatabase.insertFlag("flag_marruecos","FLAG_MARRUECOS",1,0,"CAP_MARRUECOS","africa");
        flagDatabase.insertFlag("flag_mauricio","FLAG_MAURICIO",4,0,"CAP_MAURICIO","africa");
        flagDatabase.insertFlag("flag_mauritania","FLAG_MAURITANIA",4,0,"CAP_MAURITANIA","africa");
        flagDatabase.insertFlag("flag_mozambique","FLAG_MOZAMBIQUE",3,0,"CAP_MOZAMBIQUE","africa");
        flagDatabase.insertFlag("flag_namibia","FLAG_NAMIBIA",5,2540000,"CAP_NAMIBIA","africa");
        flagDatabase.insertFlag("flag_niger","FLAG_NIGER",4,0,"CAP_NIGER","africa");
        flagDatabase.insertFlag("flag_nigeria","FLAG_NIGERIA",2,0,"CAP_NIGERIA","africa");
        flagDatabase.insertFlag("flag_ruanda","FLAG_RUANDA",3,0,"CAP_RUANDA","africa");
        flagDatabase.insertFlag("flag_santo_tome_principe","FLAG_SANTO_TOME",5,223107,"CAP_SANTO_TOME","africa");
        flagDatabase.insertFlag("flag_senegal","FLAG_SENEGAL",2,0,"CAP_SENEGAL","africa");
        flagDatabase.insertFlag("flag_seychelles","FLAG_SEYCHELLES",5,0,"CAP_SEYCHELLES","africa");
        flagDatabase.insertFlag("flag_sierra_leona","FLAG_SIERRA_LEONA",5,0,"CAP_SIERRA_LEONA","africa");
        flagDatabase.insertFlag("flag_somalia","FLAG_SOMALIA",4,0,"CAP_SOMALIA","africa");
        flagDatabase.insertFlag("flag_sudafrica","FLAG_SUDAFRICA",1,0,"CAP_SUDAFRICA","africa");
        flagDatabase.insertFlag("flag_sudan","FLAG_SUDAN",3,0,"CAP_SUDAN","africa");
        flagDatabase.insertFlag("flag_sudan_sur","FLAG_SUDAN_SUR",2,0,"CAP_SUDAN_SUR","africa");
        flagDatabase.insertFlag("flag_tanzania","FLAG_TANZANIA",4,63588000,"CAP_TANZANIA","africa");
        flagDatabase.insertFlag("flag_togo","FLAG_TOGO",4,0,"CAP_TOGO","africa");
        flagDatabase.insertFlag("flag_tunez","FLAG_TUNEZ",2,0,"CAP_TUNEZ","africa");
        flagDatabase.insertFlag("flag_uganda","FLAG_UGANDA",5,0,"CAP_UGANDA","africa");
        flagDatabase.insertFlag("flag_yibuti","FLAG_YIBUTI",5,0,"CAP_YIBUTI","africa");
        flagDatabase.insertFlag("flag_zambia","FLAG_ZAMBIA",5,0,"CAP_ZAMBIA","africa");
        flagDatabase.insertFlag("flag_zimbabue","FLAG_ZIMBABUE",4,0,"CAP_ZIMBABUE","africa");

        /* OCEANIA  */
        flagDatabase.insertFlag("flag_australia","FLAG_AUSTRALIA",1,0,"CAP_AUSTRALIA","oceania");
        flagDatabase.insertFlag("flag_fiyi","FLAG_FIYI",3,924610,"CAP_FIYI","oceania");
        flagDatabase.insertFlag("flag_islas_marshall","FLAG_ISLAS_MARSHALL",5,0,"CAP_ISLAS_MARSHALL","oceania");
        flagDatabase.insertFlag("flag_islas_salomon","FLAG_ISLAS_SALOMON",5,0,"CAP_ISLAS_SALOMON","oceania");
        flagDatabase.insertFlag("flag_kiribati","FLAG_KIRIBATI",4,0,"CAP_KIRIBATI","oceania");
        flagDatabase.insertFlag("flag_malasia","FLAG_MALASIA",2,0,"CAP_MALASIA","oceania");
        flagDatabase.insertFlag("flag_micronesia","FLAG_MICRONESIA",3,0,"CAP_MICRONESIA","oceania");
        flagDatabase.insertFlag("flag_nauru","FLAG_NAURU",4,0,"CAP_NAURU","oceania");
        flagDatabase.insertFlag("flag_nueva_guinea","FLAG_NUEVA_GUINEA",5,0,"CAP_NUEVA_GUINEA","oceania");
        flagDatabase.insertFlag("flag_nueva_zelanda","FLAG_NUEVA_ZELANDA",2,0,"CAP_NUEVA_ZELANDA","oceania");
        flagDatabase.insertFlag("flag_palaos","FLAG_PALAOS",5,0,"CAP_PALAOS","oceania");
        flagDatabase.insertFlag("flag_samoa","FLAG_SAMOA",4,0,"CAP_SAMOA","oceania");
        flagDatabase.insertFlag("flag_tonga","FLAG_TONGA",4,0,"CAP_TONGA","oceania");
        flagDatabase.insertFlag("flag_tuvalu","FLAG_TUVALU",4,0,"CAP_TUVALU","oceania");
        flagDatabase.insertFlag("flag_vanuatu","FLAG_VANUATU",5,0,"CAP_VANUATU","oceania");

        flagDatabase.close();
    }

}