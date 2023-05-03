package com.example.project_todo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    private static final String DB_NAME = "finalproject";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "todo";
    private static final String ID = "id";
    private static final String DATA_TITLE = "title";
    private static final String DATA_DESC = "description";
    private Context context;

    public Database(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "( "
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DATA_TITLE + " TEXT,"
                + DATA_DESC + " TEXT)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void createData(String title, String desc) {
        // Add data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DATA_TITLE, title);
        values.put(DATA_DESC, desc);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Cursor readData() {
        // Tampilkan Data
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void updateData(String id, String title, String desc) {
        // update/edit data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DATA_TITLE, title);
        cv.put(DATA_DESC, desc);
        db.update(TABLE_NAME, cv, "id=?", new String[]{id});

    }


    public void deleteData(String id) {
        // hapus data
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id=?", new String[]{id});
    }
}