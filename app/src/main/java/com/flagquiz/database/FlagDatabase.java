package com.flagquiz.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

public class FlagDatabase {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public FlagDatabase(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insertFlag(String image, String name, int difficulty, String region) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_IMAGE, image);
        values.put(DatabaseHelper.COLUMN_NAME, name);
        values.put(DatabaseHelper.COLUMN_DIFFICULTY, difficulty);
        values.put(DatabaseHelper.COLUMN_REGION, region);

        return database.insert(DatabaseHelper.TABLE_NAME_FLAG, null, values);
    }


}