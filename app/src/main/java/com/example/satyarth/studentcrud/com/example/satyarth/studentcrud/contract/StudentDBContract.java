package com.example.satyarth.studentcrud.com.example.satyarth.studentcrud.contract;

import android.provider.BaseColumns;

/**
 * Created by satyarth on 05/11/17.
 */

public class StudentDBContract {
    private StudentDBContract(){}

    //base column is an interface that adds _ID and _count

    public static class StudentEntry implements BaseColumns{
        public static final String TABLE_NAME = "student";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_AGE = "age";
        public static final String COLUMN_NAME_ADDRESS = "address";
    }
}
