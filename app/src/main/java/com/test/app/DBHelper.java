package com.test.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boris on 03.02.16.
 *
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TestDB.db";
    public static final String TABLE_NAME = "specialists";

    public static final String ID_COLUMN= "id";
    public static final String NAME_COLUMN= "name";
    public static final String SURNAME_COLUMN = "surname";
    public static final String YOB_COLUMN = "year_of_birth";
    public static final String CITY_COLUMN = "city";
    public static final String POSITION_COLUMN = "position";

    private static final String SQL_CREATE_TABLE = "CREATE TABLE specialists " + "(id integer primary key," +
            " name text, surname text, year_of_birth integer, position text, city text)";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS specialists");
        onCreate(db);
    }

    public long insertRow(String name, String surname, Integer yob, String city, String position) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME_COLUMN, name);
        contentValues.put(SURNAME_COLUMN, surname);
        contentValues.put(YOB_COLUMN, yob);
        contentValues.put(CITY_COLUMN, city);
        contentValues.put(POSITION_COLUMN, position);
        return db.insert(TABLE_NAME, null, contentValues);
    }

    public ArrayList<Specialist> geAllData() {
        ArrayList<Specialist> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.query(TABLE_NAME, new String[]{ID_COLUMN, NAME_COLUMN, SURNAME_COLUMN, YOB_COLUMN, CITY_COLUMN, POSITION_COLUMN}, null, null, null, null, null);
        if (res.moveToFirst()) {
            do {
                Specialist specialist = new Specialist();
                specialist.setId(res.getInt(res.getColumnIndex(ID_COLUMN)));
                specialist.setName(res.getString(res.getColumnIndex(NAME_COLUMN)));
                specialist.setSurname(res.getString(res.getColumnIndex(SURNAME_COLUMN)));
                specialist.setCity(res.getString(res.getColumnIndex(CITY_COLUMN)));
                specialist.setYob(res.getInt(res.getColumnIndex(YOB_COLUMN)));
                specialist.setPosition(res.getString(res.getColumnIndex(POSITION_COLUMN)));
                list.add(specialist);
            } while (res.moveToNext());
        }
        return list;
    }

    public Cursor getRow(Integer id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, new String[] {ID_COLUMN, NAME_COLUMN, SURNAME_COLUMN, YOB_COLUMN, CITY_COLUMN, POSITION_COLUMN},"id = ?", new String[] {id.toString()}, null, null, null, null);
    }

    public Integer updateRow(Integer id, String name, String surname, Integer yob, String city, String position) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME_COLUMN, name);
        contentValues.put(SURNAME_COLUMN, surname);
        contentValues.put(YOB_COLUMN, yob);
        contentValues.put(CITY_COLUMN, city);
        contentValues.put(POSITION_COLUMN, position);
        return db.update(TABLE_NAME, contentValues, "id = ?", new String[]{id.toString()});
    }
}
