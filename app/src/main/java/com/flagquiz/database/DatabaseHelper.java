package com.flagquiz.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.flagquiz.model.Flag;
import com.flagquiz.model.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "flaqquiz.db";
    private static final int DATABASE_VERSION = 2;

    // FLAGS
    public static final String TABLE_NAME_FLAG = "flags";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DIFFICULTY = "difficulty";
    public static final String COLUMN_POBLATION = "poblation";
    public static final String COLUMN_CAPITAL = "capital";
    public static final String COLUMN_REGION = "region";

    // USER
    public static final String COLUMN_ID_USER = "id";
    public static final String TABLE_NAME_USER = "users";
    public static final String COLUMN_HARD_1 = "hardcore_1";
    public static final String COLUMN_HARD_2 = "hardcore_2";
    public static final String COLUMN_HARD_3 = "hardcore_3";
    public static final String COLUMN_HARD_4 = "hardcore_4";
    public static final String COLUMN_HARD_5 = "hardcore_5";
    public static final String COLUMN_HARD_6 = "hardcore_6";
    public static final String COLUMN_HARD_7 = "hardcore_7";
    public static final String COLUMN_HARD_8 = "hardcore_8";
    public static final String COLUMN_HARD_9 = "hardcore_9";
    public static final String COLUMN_HARD_10 = "hardcore_10";
    public static final String COLUMN_TIME_EASY = "time_easy";
    public static final String COLUMN_TIME_MEDIUM = "time_medium";
    public static final String COLUMN_TIME_HARD = "time_hard";
    public static final String COLUMN_TIME_EXTREME = "time_extreme";
    public static final String COLUMN_TIME_INSANE = "time_insane";
    public static final String COLUMN_FLAG_EASY = "flag_easy";
    public static final String COLUMN_FLAG_MEDIUM = "flag_medium";
    public static final String COLUMN_FLAG_HARD = "flag_hard";
    public static final String COLUMN_FLAG_EXTREME = "flag_extreme";
    public static final String COLUMN_FLAG_INSANE = "flag_insane";
    public static final String COLUMN_COUNTRY_EASY = "country_easy";
    public static final String COLUMN_COUNTRY_MEDIUM = "country_medium";
    public static final String COLUMN_COUNTRY_HARD = "country_hard";
    public static final String COLUMN_COUNTRY_EXTREME = "country_extreme";
    public static final String COLUMN_COUNTRY_INSANE = "country_insane";
    public static final String COLUMN_CAP_EASY = "cap_easy";
    public static final String COLUMN_CAP_MEDIUM = "cap_medium";
    public static final String COLUMN_CAP_HARD = "cap_hard";
    public static final String COLUMN_CAP_EXTREME = "cap_extreme";
    public static final String COLUMN_CAP_INSANE = "cap_insane";

    public static final String COLUMN_POINTS = "points";


    // Crear la tabla
    private static final String CREATE_FLAG_TABLE =
            "CREATE TABLE " + TABLE_NAME_FLAG + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_IMAGE + " TEXT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_DIFFICULTY + " INTEGER, " +
                    COLUMN_POBLATION + " INTEGER, " +
                    COLUMN_CAPITAL + " TEXT, " +
                    COLUMN_REGION + " TEXT);";

    private static final String CREATE_USER_TABLE =
            "CREATE TABLE " + TABLE_NAME_USER + " (" +
                    COLUMN_ID_USER + " INTEGER, " +
                    COLUMN_HARD_1 + " INTEGER, " +
                    COLUMN_HARD_2 + " INTEGER, " +
                    COLUMN_HARD_3 + " INTEGER, " +
                    COLUMN_HARD_4 + " INTEGER, " +
                    COLUMN_HARD_5 + " INTEGER, " +
                    COLUMN_HARD_6 + " INTEGER, " +
                    COLUMN_HARD_7 + " INTEGER, " +
                    COLUMN_HARD_8 + " INTEGER, " +
                    COLUMN_HARD_9 + " INTEGER, " +
                    COLUMN_HARD_10 + " INTEGER, " +
                    COLUMN_TIME_EASY + " INTEGER, " +
                    COLUMN_TIME_MEDIUM + " INTEGER, " +
                    COLUMN_TIME_HARD + " INTEGER, " +
                    COLUMN_TIME_EXTREME + " INTEGER, " +
                    COLUMN_TIME_INSANE + " INTEGER, " +
                    COLUMN_FLAG_EASY + " INTEGER, " +
                    COLUMN_FLAG_MEDIUM + " INTEGER, " +
                    COLUMN_FLAG_HARD + " INTEGER, " +
                    COLUMN_FLAG_EXTREME + " INTEGER, " +
                    COLUMN_FLAG_INSANE + " INTEGER, " +
                    COLUMN_COUNTRY_EASY + " INTEGER, " +
                    COLUMN_COUNTRY_MEDIUM + " INTEGER, " +
                    COLUMN_COUNTRY_HARD + " INTEGER, " +
                    COLUMN_COUNTRY_EXTREME + " INTEGER, " +
                    COLUMN_COUNTRY_INSANE + " INTEGER, " +
                    COLUMN_CAP_EASY + " INTEGER, " +
                    COLUMN_CAP_MEDIUM + " INTEGER, " +
                    COLUMN_CAP_HARD + " INTEGER, " +
                    COLUMN_CAP_EXTREME + " INTEGER, " +
                    COLUMN_CAP_INSANE + " INTEGER, " +
                    COLUMN_POINTS + " INTEGER);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FLAG_TABLE);
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < DATABASE_VERSION) {
            // Realiza las actualizaciones necesarias para pasar de la versión 2 a la versión 3.
            // Agrega la nueva columna 'COLUMN_POBLATION' a la tabla existente.


            if(oldVersion < 2) {
                db.execSQL("ALTER TABLE " + TABLE_NAME_USER + " ADD COLUMN " + COLUMN_CAP_EASY + " INTEGER DEFAULT 0;");
                db.execSQL("ALTER TABLE " + TABLE_NAME_USER + " ADD COLUMN " + COLUMN_CAP_MEDIUM + " INTEGER DEFAULT 0;");
                db.execSQL("ALTER TABLE " + TABLE_NAME_USER + " ADD COLUMN " + COLUMN_CAP_HARD + " INTEGER DEFAULT 0;");
                db.execSQL("ALTER TABLE " + TABLE_NAME_USER + " ADD COLUMN " + COLUMN_CAP_EXTREME + " INTEGER DEFAULT 0;");
                db.execSQL("ALTER TABLE " + TABLE_NAME_USER + " ADD COLUMN " + COLUMN_CAP_INSANE + " INTEGER DEFAULT 0;");
            }
            if(oldVersion < 3) {
                // lo que fuera
            }
        }

        // Si hay más actualizaciones de la base de datos, puedes agregar más bloques 'if'.
        // Luego, actualiza la versión de la base de datos.
        // Esto asegura que 'onUpgrade' no se llame nuevamente para la misma versión en el futuro.
        db.setVersion(newVersion);
    }
    public User getUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_USER + " ORDER BY RANDOM() LIMIT 1", null);
        if (cursor.moveToFirst()) {
            int points = cursor.getColumnIndex(COLUMN_POINTS);
            int id = cursor.getColumnIndex(COLUMN_ID_USER);
            int hard_1 = cursor.getColumnIndex(COLUMN_HARD_1);
            int hard_2 = cursor.getColumnIndex(COLUMN_HARD_2);
            int hard_3 = cursor.getColumnIndex(COLUMN_HARD_3);
            int hard_4 = cursor.getColumnIndex(COLUMN_HARD_4);
            int hard_5 = cursor.getColumnIndex(COLUMN_HARD_5);
            int hard_6 = cursor.getColumnIndex(COLUMN_HARD_6);
            int hard_7 = cursor.getColumnIndex(COLUMN_HARD_7);
            int hard_8 = cursor.getColumnIndex(COLUMN_HARD_8);
            int hard_9 = cursor.getColumnIndex(COLUMN_HARD_9);
            int hard_10 = cursor.getColumnIndex(COLUMN_HARD_10);

            int timeEasy = cursor.getColumnIndex(COLUMN_TIME_EASY);
            int timeMedium = cursor.getColumnIndex(COLUMN_TIME_MEDIUM);
            int timeHard = cursor.getColumnIndex(COLUMN_TIME_HARD);
            int timeExtreme = cursor.getColumnIndex(COLUMN_TIME_EXTREME);
            int timeInsane = cursor.getColumnIndex(COLUMN_TIME_INSANE);

            int flagEasy = cursor.getColumnIndex(COLUMN_FLAG_EASY);
            int flagMedium = cursor.getColumnIndex(COLUMN_FLAG_MEDIUM);
            int flagHard = cursor.getColumnIndex(COLUMN_FLAG_HARD);
            int flagExtreme = cursor.getColumnIndex(COLUMN_FLAG_EXTREME);
            int flagInsane = cursor.getColumnIndex(COLUMN_FLAG_INSANE);

            int countryEasy = cursor.getColumnIndex(COLUMN_COUNTRY_EASY);
            int countryMedium = cursor.getColumnIndex(COLUMN_COUNTRY_MEDIUM);
            int countryHard = cursor.getColumnIndex(COLUMN_COUNTRY_HARD);
            int countryExtreme = cursor.getColumnIndex(COLUMN_COUNTRY_EXTREME);
            int countryInsane = cursor.getColumnIndex(COLUMN_COUNTRY_INSANE);

            int capitalEasy = cursor.getColumnIndex(COLUMN_CAP_EASY);
            int capitalMedium = cursor.getColumnIndex(COLUMN_CAP_MEDIUM);
            int capitalHard = cursor.getColumnIndex(COLUMN_CAP_HARD);
            int capitalExtreme = cursor.getColumnIndex(COLUMN_CAP_EXTREME);
            int capitalInsane = cursor.getColumnIndex(COLUMN_CAP_INSANE);

            int userPoint = cursor.getInt(points);
            int userID = cursor.getInt(id);
            int userHard_1 = cursor.getInt(hard_1);
            int userHard_2 = cursor.getInt(hard_2);
            int userHard_3 = cursor.getInt(hard_3);
            int userHard_4 = cursor.getInt(hard_4);
            int userHard_5 = cursor.getInt(hard_5);
            int userHard_6 = cursor.getInt(hard_6);
            int userHard_7 = cursor.getInt(hard_7);
            int userHard_8 = cursor.getInt(hard_8);
            int userHard_9 = cursor.getInt(hard_9);
            int userHard_10 = cursor.getInt(hard_10);

            int userTimeEasy = cursor.getInt(timeEasy);
            int userTimeMed = cursor.getInt(timeMedium);
            int userTimeHard = cursor.getInt(timeHard);
            int userTimeExt = cursor.getInt(timeExtreme);
            int userTimeInsane = cursor.getInt(timeInsane);

            int userFlagEasy = cursor.getInt(flagEasy);
            int userFlagMed = cursor.getInt(flagMedium);
            int userFlagHard = cursor.getInt(flagHard);
            int userFlagExt = cursor.getInt(flagExtreme);
            int userFlagInsane = cursor.getInt(flagInsane);

            int userCountryEasy = cursor.getInt(countryEasy);
            int userCountryMed = cursor.getInt(countryMedium);
            int userCountryHard = cursor.getInt(countryHard);
            int userCountryExt = cursor.getInt(countryExtreme);
            int userCountryInsane = cursor.getInt(countryInsane);

            int userCapEasy = cursor.getInt(capitalEasy);
            int userCapMed = cursor.getInt(capitalMedium);
            int userCapHard = cursor.getInt(capitalHard);
            int userCapExt = cursor.getInt(capitalExtreme);
            int userCapInsane = cursor.getInt(capitalInsane);


            cursor.close();
            return new User(userID,userHard_1,userHard_2,userHard_3,userHard_4,userHard_5,userHard_6,
                    userHard_7,userHard_8,userHard_9,userHard_10, userTimeEasy,userTimeMed,
                    userTimeHard,userTimeExt,userTimeInsane, userFlagEasy,userFlagMed,
                    userFlagHard,userFlagExt,userFlagInsane,userCountryEasy,userCountryMed,
                    userCountryHard,userCountryExt,userCountryInsane,userCapEasy,userCapMed,
                    userCapHard,userCapExt,userCapInsane,userPoint);
        }

        cursor.close();
        return null; // No se encontraron users
    }

    public List<Flag> getAllFlags() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_FLAG, null);
        List<Flag> listFlags = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                int imageIndex = cursor.getColumnIndex(COLUMN_IMAGE);
                int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
                int difficultyIndex = cursor.getColumnIndex(COLUMN_DIFFICULTY);
                int poblationIndex = cursor.getColumnIndex(COLUMN_POBLATION);
                int capitalIndex = cursor.getColumnIndex(COLUMN_CAPITAL);
                int regionIndex = cursor.getColumnIndex(COLUMN_REGION);

                String flagImage = cursor.getString(imageIndex);
                String flagName = cursor.getString(nameIndex);
                int flagDifficulty = cursor.getInt(difficultyIndex);
                int flagPoblation = cursor.getInt(poblationIndex);
                String flagCapital = cursor.getString(capitalIndex);
                String flagRegion = cursor.getString(regionIndex);

                listFlags.add(new Flag(flagImage, flagName, flagDifficulty, flagPoblation,flagCapital, flagRegion));
            } while (cursor.moveToNext()); // Mover al siguiente cursor
        }
        cursor.close();
        return listFlags;
    }

    public String[] getRandomCountryNames(String correctCountryName, int count, String selectedRegion) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Obtén una lista de nombres de países diferentes al correcto
        Cursor cursor = db.rawQuery("SELECT DISTINCT " + COLUMN_NAME + " FROM " + TABLE_NAME_FLAG +
                " WHERE " + COLUMN_NAME + " != ? " +
                " AND " + COLUMN_REGION + " = ? " +
                " ORDER BY RANDOM() LIMIT ?", new String[]{correctCountryName, selectedRegion, String.valueOf(count)});

        String[] countryNames = new String[count];
        int index = 0;

        if (cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
            do {
                countryNames[index] = cursor.getString(nameIndex);
                index++;
            } while (cursor.moveToNext());
        }

        cursor.close();
        return countryNames;
    }

    public String[] getRandomFlagsOptions (String correctCountryName, int count, String selectedRegion) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Obtén una lista de nombres de países diferentes al correcto
        Cursor cursor = db.rawQuery("SELECT DISTINCT " + COLUMN_IMAGE + " FROM " + TABLE_NAME_FLAG +
                " WHERE " + COLUMN_NAME + " != ? " +
                " AND " + COLUMN_REGION + " = ? " +
                " ORDER BY RANDOM() LIMIT ?", new String[]{correctCountryName, selectedRegion, String.valueOf(count)});

        String[] countryNames = new String[count];
        int index = 0;

        if (cursor.moveToFirst()) {
            int imageIndex = cursor.getColumnIndex(COLUMN_IMAGE);
            do {
                countryNames[index] = cursor.getString(imageIndex);
                index++;
            } while (cursor.moveToNext());
        }

        cursor.close();
        return countryNames;
    }

    public void deleteAllFlags() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_FLAG, null, null);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_NAME_FLAG + "'");
    }
    public void deleteAllUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_USER, null, null);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_NAME_USER + "'");
    }

    /**
     * UPDATE RECORDS USER
     */
    public void updateUserPoints(int id, String region, int points) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(getRegionColumn(region), points);
        db.update(TABLE_NAME_USER, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    private String getRegionColumn(String region) {
        switch (region) {
            case "hardcore_1":
                return COLUMN_HARD_1;
            case "hardcore_2":
                return COLUMN_HARD_2;
            case "hardcore_3":
                return COLUMN_HARD_3;
            case "hardcore_4":
                return COLUMN_HARD_4;
            case "hardcore_5":
                return COLUMN_HARD_5;
            case "hardcore_6":
                return COLUMN_HARD_6;
            case "hardcore_7":
                return COLUMN_HARD_7;
            case "hardcore_8":
                return COLUMN_HARD_8;
            case "hardcore_9":
                return COLUMN_HARD_9;
            case "hardcore_10":
                return COLUMN_HARD_10;


            default:
                return region;
        }
    }


    public boolean checkUserDataExists() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_NAME_USER, null);

        if (cursor != null) {
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            cursor.close();
            return count > 0;
        }

        return false;
    }

    public boolean checkFlagDataExists() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_NAME_FLAG, null);

        if (cursor != null) {
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            cursor.close();
            return count > 0;
        }

        return false;
    }
}