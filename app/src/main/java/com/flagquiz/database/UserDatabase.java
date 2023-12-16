package com.flagquiz.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.flagquiz.model.User;

import java.sql.SQLException;

public class UserDatabase {

    private SQLiteDatabase database;
    private final DatabaseHelper dbHelper;

    public UserDatabase(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void insertUser(User user) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ID_USER, user.getId());
        values.put(DatabaseHelper.COLUMN_HARD_1, user.getHardcore_1());
        values.put(DatabaseHelper.COLUMN_HARD_2, user.getHardcore_2());
        values.put(DatabaseHelper.COLUMN_HARD_3, user.getHardcore_3());
        values.put(DatabaseHelper.COLUMN_HARD_4, user.getHardcore_4());
        values.put(DatabaseHelper.COLUMN_HARD_5, user.getHardcore_5());
        values.put(DatabaseHelper.COLUMN_HARD_6, user.getHardcore_6());
        values.put(DatabaseHelper.COLUMN_HARD_7, user.getHardcore_7());
        values.put(DatabaseHelper.COLUMN_HARD_8, user.getHardcore_8());
        values.put(DatabaseHelper.COLUMN_HARD_9, user.getHardcore_9());
        values.put(DatabaseHelper.COLUMN_HARD_10, user.getHardcore_10());
        values.put(DatabaseHelper.COLUMN_POINTS, user.getPoints());

        database.insert(DatabaseHelper.TABLE_NAME_USER, null, values);
    }
/*
    public User getUserById(int userId) {
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME_USER, null, DatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(userId)}, null, null, null);

        User user = null;

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ID);
            int globalIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_HARD_GLOBAL);
            int europeIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_HARD_EUROPE);
            int americaIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_HARD_AMERICA);
            int asiaIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_HARD_ASIA);
            int oceaniaIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_HARD_OCEANIA);
            int africaIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_HARD_AFRICA);
            int pointsIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_POINTS);

            int id = cursor.getInt(idIndex);
            int global = cursor.getInt(globalIndex);
            int europe = cursor.getInt(europeIndex);
            int america = cursor.getInt(americaIndex);
            int asia = cursor.getInt(asiaIndex);
            int oceania = cursor.getInt(oceaniaIndex);
            int africa = cursor.getInt(africaIndex);
            int points = cursor.getInt(pointsIndex);

            user = new User(id, global, europe, america, asia, oceania, africa, points);
        }

        cursor.close();
        return user;
    }

 */

    public void updateUser(User user) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ID_USER, user.getId());
        values.put(DatabaseHelper.COLUMN_POINTS, user.getPoints());

        database.update(DatabaseHelper.TABLE_NAME_USER, values, DatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
    }


}