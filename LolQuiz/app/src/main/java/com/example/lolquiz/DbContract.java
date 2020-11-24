package com.example.lolquiz;

import android.provider.BaseColumns;

public class DbContract {


    static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DbContract.DbEntry.TABLE_NAME + " (" +
                    DbContract.DbEntry._ID + " INTEGER PRIMARY KEY," +
                    DbEntry.COLUMN_NAME_CATEGORY + " TEXT," +
                    DbEntry.COLUMN_NAME_FRAGMENT + " TEXT," +
                    DbEntry.COLUMN_NAME_TITLE + " TEXT," +
                    DbEntry.COLUMN_NAME_ANSWER1 + " TEXT,"+
                    DbEntry.COLUMN_NAME_ANSWER2 +" TEXT," +
                    DbEntry.COLUMN_NAME_ANSWER3 +" TEXT,"  +
                    DbEntry.COLUMN_NAME_ANSWER4 +" TEXT)";

    static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DbContract.DbEntry.TABLE_NAME;

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.


    /* Inner class that defines the table contents */
    static class DbEntry implements BaseColumns {
        static final String TABLE_NAME = "answers";
        static final String COLUMN_NAME_TITLE = "title";
        static final String COLUMN_NAME_CATEGORY = "category";
        static final String COLUMN_NAME_FRAGMENT = "fragment";
        static final String COLUMN_NAME_ANSWER1 = "answer1";
        static final String COLUMN_NAME_ANSWER2 = "answer2";
        static final String COLUMN_NAME_ANSWER3 = "answer3";
        static final String COLUMN_NAME_ANSWER4 = "answer4";

    }
}


