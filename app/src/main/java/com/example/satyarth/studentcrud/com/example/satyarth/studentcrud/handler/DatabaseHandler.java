package com.example.satyarth.studentcrud.com.example.satyarth.studentcrud.handler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.satyarth.studentcrud.com.example.satyarth.studentcrud.contract.StudentDBContract;

/**
 * Created by satyarth on 05/11/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    //if you want to change schema incremnet DB version
    private static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "StudentDatabase";


    private static final String SQL_CREATE_STUDENT = "CREATE TABLE " + StudentDBContract.StudentEntry.TABLE_NAME  +
            " (" + StudentDBContract.StudentEntry._ID + " INTEGER PRIMARY KEY, "+ StudentDBContract.StudentEntry.COLUMN_NAME_NAME +
            " TEXT, " + StudentDBContract.StudentEntry.COLUMN_NAME_EMAIL +
            " TEXT, " + StudentDBContract.StudentEntry.COLUMN_NAME_AGE  +
            " TEXT, "+ StudentDBContract.StudentEntry.COLUMN_NAME_ADDRESS +
            " TEXT)";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + StudentDBContract.StudentEntry.TABLE_NAME;
    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_STUDENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //discards data on upgrade of database
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);

        onCreate(sqLiteDatabase);

    }
}
