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
    private static final int DATABASE_VERSION = 1;

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
            int imageIndex = cursor.getColumnIndex(COLUMN_POINTS);
            int nameIndex = cursor.getColumnIndex(COLUMN_ID_USER);
            int difficultyIndex = cursor.getColumnIndex(COLUMN_HARD_GLOBAL);
            int regionIndex = cursor.getColumnIndex(COLUMN_HARD_EUROPE);

            String image = cursor.getString(imageIndex);
            String name = cursor.getString(nameIndex);
            int difficulty = cursor.getInt(difficultyIndex);
            String region = cursor.getString(regionIndex);

            cursor.close();
            return new User(0,0,0,0,0,0,0,0);
        }

        cursor.close();
        return null; // No se encontraron banderas
    }
    public Flag getRandomFlag() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_FLAG + " ORDER BY RANDOM() LIMIT 1", null);

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

    public String[] getRandomCountryNames(String correctCountryName, int count) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Obtén una lista de nombres de países diferentes al correcto
        Cursor cursor = db.rawQuery("SELECT DISTINCT " + COLUMN_NAME + " FROM " + TABLE_NAME_FLAG +
                " WHERE " + COLUMN_NAME + " != ? " +
                " ORDER BY RANDOM() LIMIT ?", new String[]{correctCountryName, String.valueOf(count)});

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

    /**
     * UPDATE USER TABLE
     * @param id
     * @param global
     * @param europe
     * @param america
     * @param asia
     * @param oceania
     * @param africa
     * @param points
     */
    public void insertOrUpdateUser(int id, int global, int europe, int america, int asia, int oceania, int africa, int points) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_HARD_GLOBAL, global);
        values.put(COLUMN_HARD_EUROPE, europe);
        values.put(COLUMN_HARD_AMERICA, america);
        values.put(COLUMN_HARD_ASIA, asia);
        values.put(COLUMN_HARD_OCEANIA, oceania);
        values.put(COLUMN_HARD_AFRICA, africa);
        values.put(COLUMN_POINTS, points);
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
            case "Europe":
                return COLUMN_HARD_EUROPE;
            case "America":
                return COLUMN_HARD_AMERICA;
            case "Asia":
                return COLUMN_HARD_ASIA;
            case "Oceania":
                return COLUMN_HARD_OCEANIA;
            case "Africa":
                return COLUMN_HARD_AFRICA;
            default:
                return COLUMN_HARD_GLOBAL;
        }
    }



}