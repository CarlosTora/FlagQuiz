package com.flagquiz.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.flagquiz.model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDatabase {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public UserDatabase(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insertUser(User user) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ID_USER, user.getId());
        values.put(DatabaseHelper.COLUMN_HARD_GLOBAL, user.getHardcoreGlobal());
        values.put(DatabaseHelper.COLUMN_HARD_EUROPE, user.getHardcoreEurope());
        values.put(DatabaseHelper.COLUMN_HARD_AMERICA, user.getHardcoreAmerica());
        values.put(DatabaseHelper.COLUMN_HARD_ASIA, user.getHardcoreAsia());
        values.put(DatabaseHelper.COLUMN_HARD_OCEANIA, user.getHardcoreOceania());
        values.put(DatabaseHelper.COLUMN_HARD_AFRICA, user.getHardcoreAfrica());
        values.put(DatabaseHelper.COLUMN_POINTS, user.getPoints());

        return database.insert(DatabaseHelper.TABLE_NAME_USER, null, values);
    }

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

    public void updateUser(User user) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ID_USER, user.getId());
        values.put(DatabaseHelper.COLUMN_HARD_GLOBAL, user.getHardcoreGlobal());
        values.put(DatabaseHelper.COLUMN_HARD_EUROPE, user.getHardcoreEurope());
        values.put(DatabaseHelper.COLUMN_HARD_AMERICA, user.getHardcoreAmerica());
        values.put(DatabaseHelper.COLUMN_HARD_ASIA, user.getHardcoreAsia());
        values.put(DatabaseHelper.COLUMN_HARD_OCEANIA, user.getHardcoreOceania());
        values.put(DatabaseHelper.COLUMN_HARD_AFRICA, user.getHardcoreAfrica());
        values.put(DatabaseHelper.COLUMN_POINTS, user.getPoints());

        database.update(DatabaseHelper.TABLE_NAME_USER, values, DatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
    }



    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME_USER, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ID);
            int globalIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_HARD_GLOBAL);
            int europeIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_HARD_EUROPE);
            int americaIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_HARD_AMERICA);
            int asiaIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_HARD_ASIA);
            int oceaniaIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_HARD_OCEANIA);
            int africaIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_HARD_AFRICA);
            int pointsIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_POINTS);

            do {
                int id = cursor.getInt(idIndex);
                int global = cursor.getInt(globalIndex);
                int europe = cursor.getInt(europeIndex);
                int america = cursor.getInt(americaIndex);
                int asia = cursor.getInt(asiaIndex);
                int oceania = cursor.getInt(oceaniaIndex);
                int africa = cursor.getInt(africaIndex);
                int points = cursor.getInt(pointsIndex);

                User user = new User(id, global, europe, america, asia, oceania, africa, points);
                userList.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return userList;
    }
}