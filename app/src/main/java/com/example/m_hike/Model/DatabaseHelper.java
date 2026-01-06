package com.example.m_hike.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    //hike
    public static final String DATABASE_NAME = "HikeDB.db";
    public static final String TABLE_HIKE = "hike";
    public static final String COL_HIKE_ID = "HIKE_ID";
    public static final String COL_HIKE_NAME = "NAME";
    public static final String COL_HIKE_LOCATION = "LOCATION";
    public static final String COL_HIKE_DATE = "DATE";
    public static final String COL_HIKE_LENGTH = "LENGTH";
    public static final String COL_HIKE_PARKING = "PARKING";
    public static final String COL_HIKE_WEATHER = "WEATHER";
    public static final String COL_HIKE_TRAIL = "TRAIL";
    public static final String COL_HIKE_DIFFICULT = "DIFFICULT";
    public static final String COL_HIKE_DESCRIPTION = "DESCRIPTION";
    public static final String COL_HIKE_WISHLIST = "WISHLIST";


    // observation
    public static final String TABLE_OBSERVATION = "observations";
    public static final String COL_OBS_ID = "OBS_ID";
    public static final String COL_OBS_HIKE_ID = "HIKE_ID"; // liên kết với bảng hike
    public static final String COL_OBS_DETAIL = "OBSERVATION";
    public static final String COL_OBS_DAY = "DAY";
    public static final String COL_OBS_TIME = "TIME";
    public static final String COL_OBS_COMMENT = "COMMENT";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng Hike
        db.execSQL("CREATE TABLE " + TABLE_HIKE + " ("
                + COL_HIKE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_HIKE_NAME + " TEXT, "
                + COL_HIKE_LOCATION + " TEXT, "
                + COL_HIKE_DATE + " TEXT, "
                + COL_HIKE_LENGTH + " TEXT, "
                + COL_HIKE_PARKING + " INTEGER DEFAULT 0, "
                + COL_HIKE_WEATHER + " TEXT, "
                + COL_HIKE_TRAIL + " TEXT, "
                + COL_HIKE_DIFFICULT + " TEXT, "
                + COL_HIKE_DESCRIPTION + " TEXT, "
                + COL_HIKE_WISHLIST + " INTEGER DEFAULT 0"
                + ")");

        // Tạo bảng Observation
        db.execSQL("CREATE TABLE " + TABLE_OBSERVATION + " ("
                + COL_OBS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_OBS_HIKE_ID + " INTEGER, " + COL_OBS_DETAIL + " TEXT NOT NULL, "+ COL_OBS_DAY + " TEXT NOT NULL, "
                + COL_OBS_TIME + " TEXT NOT NULL, " + COL_OBS_COMMENT + " TEXT, "
                + "FOREIGN KEY(" + COL_OBS_HIKE_ID + ") REFERENCES " + TABLE_HIKE + "(" + COL_HIKE_ID + "))"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OBSERVATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HIKE);
        onCreate(db);
    }


    public boolean insertHike(String name, String location, String date, String length, int parking,
                              String weather, String trail, String difficult, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_HIKE_NAME, name);
        cv.put(COL_HIKE_LOCATION, location);
        cv.put(COL_HIKE_DATE, date);
        cv.put(COL_HIKE_LENGTH, length);
        cv.put(COL_HIKE_PARKING, parking);
        cv.put(COL_HIKE_WEATHER, weather);
        cv.put(COL_HIKE_TRAIL, trail);
        cv.put(COL_HIKE_DIFFICULT, difficult);
        cv.put(COL_HIKE_DESCRIPTION, description);
        cv.put(COL_HIKE_WISHLIST, 0);
        long result = db.insert(TABLE_HIKE, null, cv);
        return result != -1;
    }

    public Cursor getAllHikes() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_HIKE, null);
    }

    public boolean updateHike(int id, String name, String location, String date,
                              String length, int parking,String weather, String trail,  String difficult, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_HIKE_NAME, name);
        cv.put(COL_HIKE_LOCATION, location);
        cv.put(COL_HIKE_DATE, date);
        cv.put(COL_HIKE_LENGTH, length);
        cv.put(COL_HIKE_PARKING, parking);
        cv.put(COL_HIKE_WEATHER, weather);
        cv.put(COL_HIKE_TRAIL, trail);
        cv.put(COL_HIKE_DIFFICULT, difficult);
        cv.put(COL_HIKE_DESCRIPTION, description);

        int result = db.update(TABLE_HIKE, cv, COL_HIKE_ID + "=?", new String[]{String.valueOf(id)});
        return result > 0;
    }


    public boolean deleteHike(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_HIKE, COL_HIKE_ID + "=?", new String[]{String.valueOf(id)});
        return result > 0;
    }


    public boolean insertObservation(int hikeId, String observation, String day, String time, String comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_OBS_HIKE_ID, hikeId);
        cv.put(COL_OBS_DETAIL, observation);
        cv.put(COL_OBS_DAY, day);
        cv.put(COL_OBS_TIME, time);
        cv.put(COL_OBS_COMMENT, comment);
        long result = db.insert(TABLE_OBSERVATION, null, cv);
        return result != -1;
    }

    public Cursor getObservationsByHike(int hikeId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_OBSERVATION + " WHERE " + COL_OBS_HIKE_ID + " = ?",
                new String[]{String.valueOf(hikeId)});
    }

    public boolean updateObservation(int obsId, String observation, String day, String time, String comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_OBS_DETAIL, observation);
        cv.put(COL_OBS_DAY, day);
        cv.put(COL_OBS_TIME, time);
        cv.put(COL_OBS_COMMENT, comment);
        int result = db.update(TABLE_OBSERVATION, cv, COL_OBS_ID + " = ?", new String[]{String.valueOf(obsId)});
        return result > 0;
    }

    public boolean deleteObservation(int obsId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_OBSERVATION, COL_OBS_ID + " = ?", new String[]{String.valueOf(obsId)});
        return result > 0;
    }

    public int getObservationCount(int hikeId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_OBSERVATION + " WHERE " + COL_OBS_HIKE_ID + " = ?", new String[]{String.valueOf(hikeId)});
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }
    public Cursor getWishlist() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_HIKE +
                        " WHERE " + COL_HIKE_WISHLIST + " = 1",
                null);
    }
    public boolean updateWishlist(int hikeId, int wishlist) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_HIKE_WISHLIST, wishlist);

        int result = db.update(TABLE_HIKE, cv, COL_HIKE_ID + "=?", new String[]{String.valueOf(hikeId)});
        return result > 0;
    }


}