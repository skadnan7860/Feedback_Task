package com.example.myandroidapplication.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class FeedbackDataBase extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "feedback.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "feedback_table";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FEEDBACK = "feedback";
    private static final String COLUMN_DATE = "date";

    public FeedbackDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Fix the CREATE_TABLE query by adding the missing space
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_FEEDBACK + " TEXT, "
                + COLUMN_DATE + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Insert feedback data into the database
    public void insertFeedbackData(String feedback, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FEEDBACK, feedback);
        cv.put(COLUMN_DATE, date);
        db.insert(TABLE_NAME, null, cv);

    }


    public Cursor readAllData() {


        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        }
        return cursor;
    }

    public void updateData(String id,String feedback,String date){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FEEDBACK,feedback);
        cv.put(COLUMN_DATE,date);

        long result = db.update(TABLE_NAME, cv, "id=?", new String[]{id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    public void deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "id=?", new String[]{id});
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Deleted Successfully!", Toast.LENGTH_SHORT).show();
        }
    }


}
