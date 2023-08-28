package com.flagquiz.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.flagquiz.model.Flag;
import com.flagquiz.model.User;

import java.util.ArrayList;
import java.util.Collections;
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
    public static final String COLUMN_REGION = "region";

    // USER
    public static final String COLUMN_ID_USER = "id";
    public static final String TABLE_NAME_USER = "users";
    public static final String COLUMN_HARD_GLOBAL = "hardcore_global";
    public static final String COLUMN_HARD_EUROPE = "hardcore_europe";
    public static final String COLUMN_HARD_AMERICA = "hardcore_america";
    public static final String COLUMN_HARD_ASIA = "hardcore_asia";
    public static final String COLUMN_HARD_OCEANIA = "hardcore_oceania";
    public static final String COLUMN_HARD_AFRICA = "hardcore_africa";
    public static final String COLUMN_TIME_GLOBAL = "hardcore_global";
    public static final String COLUMN_TIME_EUROPE = "hardcore_europe";
    public static final String COLUMN_TIME_AMERICA = "hardcore_america";
    public static final String COLUMN_TIME_ASIA = "hardcore_asia";
    public static final String COLUMN_TIME_OCEANIA = "hardcore_oceania";
    public static final String COLUMN_TIME_AFRICA = "hardcore_africa";
    public static final String COLUMN_POINTS = "points";


    // Crear la tabla
    private static final String CREATE_FLAG_TABLE =
            "CREATE TABLE " + TABLE_NAME_FLAG + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_IMAGE + " TEXT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_DIFFICULTY + " INTEGER, " +
                    COLUMN_REGION + " TEXT);";

    private static final String CREATE_USER_TABLE =
            "CREATE TABLE " + TABLE_NAME_USER + " (" +
                    COLUMN_ID_USER + " INTEGER, " +
                    COLUMN_HARD_GLOBAL + " INTEGER, " +
                    COLUMN_HARD_EUROPE + " INTEGER, " +
                    COLUMN_HARD_AMERICA + " INTEGER, " +
                    COLUMN_HARD_ASIA + " INTEGER, " +
                    COLUMN_HARD_OCEANIA + " INTEGER, " +
                    COLUMN_HARD_AFRICA + " INTEGER, " +
                    COLUMN_TIME_GLOBAL + " INTEGER, " +
                    COLUMN_TIME_EUROPE + " INTEGER, " +
                    COLUMN_TIME_AMERICA + " INTEGER, " +
                    COLUMN_TIME_ASIA + " INTEGER, " +
                    COLUMN_TIME_OCEANIA + " INTEGER, " +
                    COLUMN_TIME_AFRICA + " INTEGER, " +
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_FLAG);
        onCreate(db);
    }
    public User getUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_USER + " ORDER BY RANDOM() LIMIT 1", null);
        if (cursor.moveToFirst()) {
            int points = cursor.getColumnIndex(COLUMN_POINTS);
            int id = cursor.getColumnIndex(COLUMN_ID_USER);
            int hardGlobal = cursor.getColumnIndex(COLUMN_HARD_GLOBAL);
            int hardEur = cursor.getColumnIndex(COLUMN_HARD_EUROPE);
            int hardAme = cursor.getColumnIndex(COLUMN_HARD_AMERICA);
            int hardAsia = cursor.getColumnIndex(COLUMN_HARD_ASIA);
            int hardOce = cursor.getColumnIndex(COLUMN_HARD_OCEANIA);
            int hardAfri = cursor.getColumnIndex(COLUMN_HARD_AFRICA);
            int timeGlobal = cursor.getColumnIndex(COLUMN_TIME_GLOBAL);
            int timeEur = cursor.getColumnIndex(COLUMN_TIME_EUROPE);
            int timeAme = cursor.getColumnIndex(COLUMN_TIME_AMERICA);
            int timeAsia = cursor.getColumnIndex(COLUMN_TIME_ASIA);
            int timeOce = cursor.getColumnIndex(COLUMN_TIME_OCEANIA);
            int timeAfri = cursor.getColumnIndex(COLUMN_TIME_AFRICA);

            int userPoint = cursor.getInt(points);
            int userID = cursor.getInt(id);
            int userHardGlobal = cursor.getInt(hardGlobal);
            int userHardEur = cursor.getInt(hardEur);
            int userHardAme = cursor.getInt(hardAme);
            int userHardAsia = cursor.getInt(hardAsia);
            int userHardOce = cursor.getInt(hardOce);
            int userHardAfri = cursor.getInt(hardAfri);
            int userTimeGlobal = cursor.getInt(hardGlobal);
            int userTimeEur = cursor.getInt(hardEur);
            int userTimeAme = cursor.getInt(hardAme);
            int userTimeAsia = cursor.getInt(hardAsia);
            int userTimeOce = cursor.getInt(hardOce);
            int userTimeAfri = cursor.getInt(hardAfri);

            cursor.close();
            return new User(userID,userHardGlobal,userHardEur,userHardAme,userHardAsia,userHardOce,userHardAfri,
                    userTimeGlobal,userTimeEur,userTimeAme,userTimeAsia,userTimeOce,userTimeAfri,userPoint);
        }

        cursor.close();
        return null; // No se encontraron banderas
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
                int regionIndex = cursor.getColumnIndex(COLUMN_REGION);

                String flagImage = cursor.getString(imageIndex);
                String flagName = cursor.getString(nameIndex);
                int flagDifficulty = cursor.getInt(difficultyIndex);
                String flagRegion = cursor.getString(regionIndex);

                listFlags.add(new Flag(flagImage, flagName, flagDifficulty, flagRegion));
            } while (cursor.moveToNext()); // Mover al siguiente cursor
        }
        cursor.close();
        return listFlags;
    }

    public Flag getRandomFlag(String selectedRegion) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_FLAG + " WHERE " + COLUMN_REGION + " = ? " +
                " ORDER BY RANDOM() LIMIT 1", new String[]{selectedRegion});

        if (cursor.moveToFirst()) {
            int imageIndex = cursor.getColumnIndex(COLUMN_IMAGE);
            int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
            int difficultyIndex = cursor.getColumnIndex(COLUMN_DIFFICULTY);
            int regionIndex = cursor.getColumnIndex(COLUMN_REGION);

            String image = cursor.getString(imageIndex);
            String name = cursor.getString(nameIndex);
            int difficulty = cursor.getInt(difficultyIndex);
            String region = cursor.getString(regionIndex);

            cursor.close();
            return new Flag(image, name, difficulty, region);
        }

        cursor.close();
        return null; // No se encontraron banderas
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

    public void insertOrUpdateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, user.getId());
        values.put(COLUMN_HARD_GLOBAL, user.getHardcoreGlobal());
        values.put(COLUMN_HARD_EUROPE, user.getHardcoreEurope());
        values.put(COLUMN_HARD_AMERICA, user.getHardcoreAmerica());
        values.put(COLUMN_HARD_ASIA, user.getHardcoreAsia());
        values.put(COLUMN_HARD_OCEANIA, user.getHardcoreOceania());
        values.put(COLUMN_HARD_AFRICA, user.getHardcoreAfrica());
        values.put(COLUMN_POINTS, user.getPoints());
        db.insertWithOnConflict(TABLE_NAME_USER, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    /**
     * UPDATE RECORDS USER
     * @param id
     * @param region
     * @param points
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
            case "minuteModeEurope":
                return COLUMN_TIME_EUROPE;
            case "hardcoreModeEurope":
                return COLUMN_HARD_EUROPE;

            case "minuteModeAmerica":
                return COLUMN_TIME_AMERICA;
            case "hardcoreModeAmerica":
                return COLUMN_HARD_AMERICA;

            case "minuteModeAsia":
                return COLUMN_TIME_ASIA;
            case "hardcoreModeAsia":
                return COLUMN_HARD_ASIA;

            case "minuteModeOceania":
                return COLUMN_TIME_OCEANIA;
            case "hardcoreModeOceania":
                return COLUMN_HARD_OCEANIA;

            case "minuteModeAfrica":
                return COLUMN_TIME_AFRICA;
            case "hardcoreModeAfrica":
                return COLUMN_HARD_AFRICA;

            case "minuteModeGlobal":
                return COLUMN_TIME_GLOBAL;
            case "hardcoreModeGlobal":
                return COLUMN_HARD_GLOBAL;

            default:
                return region;
        }
    }
}