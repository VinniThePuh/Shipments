package com.example.kursach;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "shipment_database";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "names";
    private static final String TABLE_USER_DATE = "date";
    private static final String TABLE_USER_VALUE = "value";
    private static final String TABLE_USER_PRODUCER = "producer";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DATE = "date";
    private static final String KEY_VALUE = "value";
    private static final String KEY_PRODUCER = "producer";

    private static final String CREATE_TABLE_NAME = "CREATE TABLE "
            + TABLE_NAME + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT );";

    private static final String CREATE_TABLE_USER_VALUE = "CREATE TABLE "
            + TABLE_USER_VALUE + "(" + KEY_ID + " INTEGER,"+ KEY_VALUE + " TEXT );";

    private static final String CREATE_TABLE_USER_DATE = "CREATE TABLE "
            + TABLE_USER_DATE + "(" + KEY_ID + " INTEGER,"+ KEY_DATE + " TEXT );";

    private static final String CREATE_TABLE_USER_PRODUCER = "CREATE TABLE "
            + TABLE_USER_PRODUCER + "(" + KEY_ID + " INTEGER,"+ KEY_PRODUCER + " TEXT );";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        Log.d("table", CREATE_TABLE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_NAME);
        db.execSQL(CREATE_TABLE_USER_DATE);
        db.execSQL(CREATE_TABLE_USER_VALUE);
        db.execSQL(CREATE_TABLE_USER_PRODUCER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_USER_DATE + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_USER_VALUE + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_USER_PRODUCER + "'");
        onCreate(db);
    }

    public void addUser(String name, String date, String value, String producer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        long id = db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);

        ContentValues valuesDate = new ContentValues();
        valuesDate.put(KEY_ID, id);
        valuesDate.put(KEY_DATE, date);
        db.insert(TABLE_USER_DATE, null, valuesDate);

        ContentValues valuesValue = new ContentValues();
        valuesValue.put(KEY_ID, id);
        valuesValue.put(KEY_VALUE, value);
        db.insert(TABLE_USER_VALUE, null, valuesValue);

        ContentValues valuesProducer = new ContentValues();
        valuesProducer.put(KEY_ID, id);
        valuesProducer.put(KEY_PRODUCER, producer);
        db.insert(TABLE_USER_PRODUCER, null, valuesProducer);
    }

    public ArrayList<UserModel> getAllUsers() {
        ArrayList<UserModel> userModelArrayList = new ArrayList<UserModel>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                UserModel userModel = new UserModel();
                userModel.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                userModel.setName(c.getString(c.getColumnIndex(KEY_NAME)));

                String selectDateQuery = "SELECT  * FROM " + TABLE_USER_DATE +" WHERE "+KEY_ID+" = "+ userModel.getId();
                Cursor cDate = db.rawQuery(selectDateQuery, null);

                if (cDate.moveToFirst()) {
                    do {
                        userModel.setDate(cDate.getString(cDate.getColumnIndex(KEY_DATE)));
                    } while (cDate.moveToNext());
                }

                String selectValueQuery = "SELECT  * FROM " + TABLE_USER_VALUE +" WHERE "+KEY_ID+" = "+ userModel.getId();
                Log.d("oppp",selectValueQuery);
                Cursor cValue = db.rawQuery(selectValueQuery, null);

                if (cValue.moveToFirst()) {
                    do {
                        userModel.setValue(cValue.getString(cValue.getColumnIndex(KEY_VALUE)));
                    } while (cValue.moveToNext());
                }

                String selectProducerQuery = "SELECT  * FROM " + TABLE_USER_PRODUCER+" WHERE "+KEY_ID+" = "+ userModel.getId();
                Cursor cProducer = db.rawQuery(selectProducerQuery, null);

                if (cProducer.moveToFirst()) {
                    do {
                        userModel.setProducer(cProducer.getString(cProducer.getColumnIndex(KEY_PRODUCER)));
                    } while (cProducer.moveToNext());
                }

                userModelArrayList.add(userModel);
            } while (c.moveToNext());
        }
        return userModelArrayList;
    }

    public void updateUser(int id, String name, String date, String value, String producer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        db.update(TABLE_NAME, values, KEY_ID + " = ?", new String[]{String.valueOf(id)});

        ContentValues valuesDate = new ContentValues();
        valuesDate.put(KEY_DATE, date);
        db.update(TABLE_USER_DATE, valuesDate, KEY_ID + " = ?", new String[]{String.valueOf(id)});

        ContentValues valuesValue = new ContentValues();
        valuesValue.put(KEY_VALUE, value);
        db.update(TABLE_USER_VALUE, valuesValue, KEY_ID + " = ?", new String[]{String.valueOf(id)});

        ContentValues valuesProducer = new ContentValues();
        valuesProducer.put(KEY_PRODUCER, producer);
        db.update(TABLE_USER_PRODUCER, valuesProducer, KEY_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void deleteUSer(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, KEY_ID + " = ?",new String[]{String.valueOf(id)});

        db.delete(TABLE_USER_DATE, KEY_ID + " = ?",new String[]{String.valueOf(id)});

        db.delete(TABLE_USER_VALUE, KEY_ID + " = ?", new String[]{String.valueOf(id)});

        db.delete(TABLE_USER_PRODUCER, KEY_ID + " = ?",new String[]{String.valueOf(id)});
    }

}