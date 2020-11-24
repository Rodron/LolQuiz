package com.example.lolquiz;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DbManager {

    private DbHelper helper;
    private SQLiteDatabase db;

    public DbManager(Context context) {
        this.helper = new DbHelper(context);
        this.db = this.helper.getWritableDatabase();
    }

    public void insertEntry(String category, String fragment, String title, String answer1,String answer2,String answer3,String answer4){
        this.db.insert(DbContract.DbEntry.TABLE_NAME, null,
                this.generateContentValues(category, fragment, title, answer1,answer2, answer3, answer4));
    }

    public Cursor getEntries () {
        String[] columns = new String[]{
                DbContract.DbEntry.COLUMN_NAME_CATEGORY,
                DbContract.DbEntry.COLUMN_NAME_FRAGMENT,
                DbContract.DbEntry.COLUMN_NAME_TITLE,
                DbContract.DbEntry.COLUMN_NAME_ANSWER1,
                DbContract.DbEntry.COLUMN_NAME_ANSWER2,
                DbContract.DbEntry.COLUMN_NAME_ANSWER3,
                DbContract.DbEntry.COLUMN_NAME_ANSWER4};
        return db.query(DbContract.DbEntry.TABLE_NAME, columns, null, null,
                null, null, null);
    }

    public void deleteAll() {
        this.db.delete(DbContract.DbEntry.TABLE_NAME, null,null);
    }

    private ContentValues generateContentValues(String category, String fragment, String title, String answer1,String answer2,String answer3,String answer4){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContract.DbEntry.COLUMN_NAME_CATEGORY, category);
        contentValues.put(DbContract.DbEntry.COLUMN_NAME_FRAGMENT, fragment);
        contentValues.put(DbContract.DbEntry.COLUMN_NAME_TITLE, title);
        contentValues.put(DbContract.DbEntry.COLUMN_NAME_ANSWER1, answer1);
        contentValues.put(DbContract.DbEntry.COLUMN_NAME_ANSWER2, answer2);
        contentValues.put(DbContract.DbEntry.COLUMN_NAME_ANSWER3, answer3);
        contentValues.put(DbContract.DbEntry.COLUMN_NAME_ANSWER4, answer4);
        return contentValues;
    }

}
