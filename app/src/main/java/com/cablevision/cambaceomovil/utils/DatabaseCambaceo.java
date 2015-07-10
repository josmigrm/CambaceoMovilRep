package com.cablevision.cambaceomovil.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Mike on 7/9/15.
 */
public final class DatabaseCambaceo extends SQLiteOpenHelper{


    private static final String DATABASE_NAME = "cambaceo_db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CONTACTS = "catalogo_col_ent_fed";
    private static final String COLUMN_ID_HOMOLOGADO ="id_homologado";
    private static final String COLUMN_COLONIA ="colonia";
    private static final String COLUMN_MUNICIPIO ="municipio";
    private static final String COLUMN_ESTADO ="estado";

    public DatabaseCambaceo(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



}
