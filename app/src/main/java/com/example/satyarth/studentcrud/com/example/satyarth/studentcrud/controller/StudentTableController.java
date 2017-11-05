package com.example.satyarth.studentcrud.com.example.satyarth.studentcrud.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.example.satyarth.studentcrud.com.example.satyarth.studentcrud.contract.StudentDBContract;
import com.example.satyarth.studentcrud.com.example.satyarth.studentcrud.handler.DatabaseHandler;
import com.example.satyarth.studentcrud.com.example.satyarth.studentcrud.model.StudentModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by satyarth on 05/11/17.
 */

public class StudentTableController extends DatabaseHandler {
    public StudentTableController(Context context){
        super(context);
    }

    public boolean create(StudentModel studentObject){


        //helper function that content resolver can resolve
        ContentValues values = new ContentValues();

        values.put(StudentDBContract.StudentEntry.COLUMN_NAME_NAME, studentObject.name);
        values.put(StudentDBContract.StudentEntry.COLUMN_NAME_EMAIL, studentObject.email);
        values.put(StudentDBContract.StudentEntry.COLUMN_NAME_AGE, Integer.toString(studentObject.age));
        values.put(StudentDBContract.StudentEntry.COLUMN_NAME_ADDRESS, studentObject.address);

        // get writable database is inherited from controller(which has extended SQLiteopenerHelper)
        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccess = db.insert(StudentDBContract.StudentEntry.TABLE_NAME, null, values) > 0;
        db.close();

        return createSuccess;
    }


    public int getCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, StudentDBContract.StudentEntry.TABLE_NAME);
        db.close();
        return (int)count;
    }


    public List<StudentModel> read(){
        SQLiteDatabase db = this.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                StudentDBContract.StudentEntry._ID,
                StudentDBContract.StudentEntry.COLUMN_NAME_NAME,
                StudentDBContract.StudentEntry.COLUMN_NAME_EMAIL
        };


// How you want the results sorted in the resulting Cursor
        String sortOrder =
                StudentDBContract.StudentEntry.COLUMN_NAME_NAME + " ASC";

        Cursor cursor = db.query(
                StudentDBContract.StudentEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        List<StudentModel> recordList = getRecordsFromCursor(cursor);


        cursor.close();
        db.close();

        return recordList;
    }

    public StudentModel readSingleRecord(int studentId){
        SQLiteDatabase db = this.getReadableDatabase();
        StudentModel objectStudent = null;



// Filter results WHERE "title" = 'My Title'
        String selection = StudentDBContract.StudentEntry._ID + " = ?";
        String[] selectionArgs = {Integer.toString(studentId)};

        Cursor cursor = db.query(
                StudentDBContract.StudentEntry.TABLE_NAME,                     // The table to query
                null,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        if (cursor.moveToFirst()) {

            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(StudentDBContract.StudentEntry._ID)));
            String name = cursor.getString(cursor.getColumnIndex(StudentDBContract.StudentEntry.COLUMN_NAME_NAME));
            String email = cursor.getString(cursor.getColumnIndex(StudentDBContract.StudentEntry.COLUMN_NAME_EMAIL));
            int age = Integer.parseInt(cursor.getString(cursor.getColumnIndex(StudentDBContract.StudentEntry.COLUMN_NAME_AGE)));
            String address = cursor.getString(cursor.getColumnIndex(StudentDBContract.StudentEntry.COLUMN_NAME_ADDRESS));

            objectStudent = new StudentModel();
            objectStudent.id = id;
            objectStudent.name = name;
            objectStudent.email = email;
            objectStudent.age = age;
            objectStudent.address = address;

        }

        cursor.close();
        db.close();

        return objectStudent;

    }
    public boolean update(StudentModel studentObject){
        ContentValues values = new ContentValues();

        values.put(StudentDBContract.StudentEntry.COLUMN_NAME_NAME, studentObject.name);
        values.put(StudentDBContract.StudentEntry.COLUMN_NAME_EMAIL, studentObject.email);
        values.put(StudentDBContract.StudentEntry.COLUMN_NAME_AGE, Integer.toString(studentObject.age));
        values.put(StudentDBContract.StudentEntry.COLUMN_NAME_ADDRESS, studentObject.address);

        String selection = StudentDBContract.StudentEntry._ID + " = ?";
        String[] selectionArgs = {Integer.toString(studentObject.id)};


        SQLiteDatabase db = this.getWritableDatabase();

        boolean updateSuccessful = db.update(StudentDBContract.StudentEntry.TABLE_NAME, values, selection, selectionArgs) > 0;
        db.close();

        return updateSuccessful;
    }
    public boolean delete(int studentId) {
        boolean deleteSuccessful = false;

        String selection = StudentDBContract.StudentEntry._ID + " = ?";
        String[] selectionArgs = {Integer.toString(studentId)};

        SQLiteDatabase db = this.getWritableDatabase();
        deleteSuccessful = db.delete(StudentDBContract.StudentEntry.TABLE_NAME, selection, selectionArgs) > 0;
        db.close();

        return deleteSuccessful;

    }

    public List<StudentModel> searchByName(String name){
        SQLiteDatabase db = this.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                StudentDBContract.StudentEntry._ID,
                StudentDBContract.StudentEntry.COLUMN_NAME_NAME,
                StudentDBContract.StudentEntry.COLUMN_NAME_EMAIL
        };

        String selection = StudentDBContract.StudentEntry.COLUMN_NAME_NAME + " LIKE ?";
        String[] selectionArgs = {name +"%"};


// How you want the results sorted in the resulting Cursor
        String sortOrder =
                StudentDBContract.StudentEntry.COLUMN_NAME_NAME + " ASC";

        Cursor cursor = db.query(
                StudentDBContract.StudentEntry.TABLE_NAME,                     // The table to query
                null,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        List<StudentModel> recordList = getRecordsFromCursor(cursor);


        cursor.close();
        db.close();

        return recordList;
    }


    public List<StudentModel> getRecordsFromCursor(Cursor cursor){
        List<StudentModel> recordList = new ArrayList<StudentModel>();
        if (cursor.moveToFirst()) {
            do {

                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(StudentDBContract.StudentEntry._ID)));
                String studentName = cursor.getString(cursor.getColumnIndex(StudentDBContract.StudentEntry.COLUMN_NAME_NAME));
                String studentEmail = cursor.getString(cursor.getColumnIndex(StudentDBContract.StudentEntry.COLUMN_NAME_EMAIL));

                StudentModel objectStudent = new StudentModel();
                objectStudent.id = id;
                objectStudent.name = studentName;
                objectStudent.email = studentEmail;

                recordList.add(objectStudent);

            } while (cursor.moveToNext());
        }
        return recordList;
    }





}
