package com.cablevision.cambaceomovil.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mike on 7/9/15.
 */
public final class DatabaseCambaceo extends SQLiteOpenHelper{


    private static final String DATABASE_NAME = "cambaceo_db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CAT_ENT_FED = "catalogo_col_ent_fed";
    private static final String COLUMN_ID ="id_homologado";
    private static final String COLUMN_COL ="colonia";
    private static final String COLUMN_MPO ="municipio";
    private static final String COLUMN_EDO ="estado";

    private static final String TABLE_CAT_PLAZAS = "catalogo_plazas";
    private static final String COLUMN_PLAZA ="plaza";

    private static final String CREATE_CAT_ENT_FED_TABLE = "CREATE TABLE " + TABLE_CAT_ENT_FED + "("
            + COLUMN_ID + " TEXT PRIMARY KEY,"
            + COLUMN_COL + " TEXT,"
            + COLUMN_MPO + " TEXT,"
            + COLUMN_EDO + " TEXT" + ")";
    private static final String DELETE_TABLE_CAT_ENT_FED =
            "DROP TABLE IF EXISTS " + TABLE_CAT_ENT_FED;

    private static final String CREATE_CAT_PLAZAS = "CREATE TABLE " + TABLE_CAT_PLAZAS + "("
            + COLUMN_ID + " TEXT PRIMARY KEY,"
            + COLUMN_PLAZA + " TEXT" + ")";
    private static final String DELETE_TABLE_CAT_PLAZAS =
            "DROP TABLE IF EXISTS " + TABLE_CAT_PLAZAS;


    public DatabaseCambaceo(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CAT_ENT_FED_TABLE);
        db.execSQL(CREATE_CAT_PLAZAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(DELETE_TABLE_CAT_ENT_FED);
        db.execSQL(DELETE_TABLE_CAT_PLAZAS);
        onCreate(db);
    }

    public void recreateCatPlazas(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(DELETE_TABLE_CAT_PLAZAS);
        db.execSQL(CREATE_CAT_PLAZAS);
    }

    public int getCatEntFedCount(){
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CAT_ENT_FED;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        // return count
        return count;
    }

    public int getCatPlazasCount(){
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CAT_PLAZAS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        // return count
        return count;
    }

    public void insertaRegistrosCatEntFed(String jsonStr ) throws JSONException {
        JSONArray ja = new JSONArray(jsonStr);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        JSONObject child = null;
        for (int i=0; i<ja.length(); i++) {
            child = ja.getJSONObject(i);

            values.put(COLUMN_ID, child.getString("ID_HOMOLOGADO"));
            values.put(COLUMN_COL, child.getString("COLONIA"));
            values.put(COLUMN_MPO, child.getString("MUNICIPIO"));
            values.put(COLUMN_EDO, child.getString("ESTADO"));

            // Inserting Row
            db.insert(TABLE_CAT_ENT_FED, null, values);
        }
        db.close(); // Closing database connection
    }

    public void insertaRegistrosCatPlazas(String jsonStr ) throws JSONException {
        JSONArray ja = new JSONArray(jsonStr);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        JSONObject child = null;
        for (int i=0; i<ja.length(); i++) {
            child = ja.getJSONObject(i);

            values.put(COLUMN_ID, child.getString("ID_HOMOLOGADO"));
            values.put(COLUMN_PLAZA, child.getString("PLAZA"));

            // Inserting Row
            db.insert(TABLE_CAT_PLAZAS, null, values);
        }
        db.close(); // Closing database connection
    }

    public List<String> getPlazas(){
        ArrayList<String> plazasArr = new ArrayList<String>();
        String selectQuery = "SELECT "+COLUMN_PLAZA+" FROM " + TABLE_CAT_PLAZAS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            plazasArr.add(cursor.getString(cursor.getColumnIndex(COLUMN_PLAZA)));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();

        return plazasArr;
    }

    public List<String> getEstados(){
        ArrayList<String> estadosArr = new ArrayList<String>();
        String selectQuery = "SELECT DISTINCT("+COLUMN_EDO+") FROM " + TABLE_CAT_ENT_FED;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            estadosArr.add(cursor.getString(cursor.getColumnIndex(COLUMN_EDO)));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();

        return estadosArr;
    }

    public List<String> getMunicipiosPorEstado(String estado){
        ArrayList<String> mposArr = new ArrayList<String>();
        String selectQuery = "SELECT DISTINCT("+COLUMN_MPO+") FROM " + TABLE_CAT_ENT_FED +
                " WHERE "+COLUMN_EDO+" = "+estado;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            mposArr.add(cursor.getString(cursor.getColumnIndex(COLUMN_MPO)));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();

        return mposArr;
    }

    public List<String> getMunicipios(){
        ArrayList<String> mposArr = new ArrayList<String>();
        String selectQuery = "SELECT DISTINCT("+COLUMN_MPO+") FROM " + TABLE_CAT_ENT_FED;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            mposArr.add(cursor.getString(cursor.getColumnIndex(COLUMN_MPO)));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();

        return mposArr;
    }

    public List<String> getColonias(String municipio){
        ArrayList<String> coloniasArr = new ArrayList<String>();

        String selectQuery = "SELECT DISTINCT("+COLUMN_COL+") FROM " + TABLE_CAT_ENT_FED +
                " WHERE "+COLUMN_MPO+" = '"+ municipio+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            coloniasArr.add(cursor.getString(cursor.getColumnIndex(COLUMN_COL)));
            cursor.moveToNext();
        }

        cursor.close();
        db.close();

        return coloniasArr;
    }
}
