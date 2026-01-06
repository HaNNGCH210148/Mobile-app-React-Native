package com.example.m_hike.ViewModel;

import android.app.Application;
import android.database.Cursor;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.m_hike.Model.DatabaseHelper;
import com.example.m_hike.Model.HikeList;

import java.util.ArrayList;
import java.util.List;

public class Hike extends AndroidViewModel {

    private DatabaseHelper dbHelper;
    private MutableLiveData<List<HikeList>> hikesLiveData = new MutableLiveData<>();

    public Hike(Application application) {
        super(application);
        dbHelper = new DatabaseHelper(application);
        loadAllHikes();
    }

    public void loadAllHikes() {
        Cursor cursor = dbHelper.getAllHikes();
        List<HikeList> hikeList = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                HikeList hike = new HikeList(
                        cursor.getInt(cursor.getColumnIndexOrThrow("HIKE_ID")),
                        cursor.getString(cursor.getColumnIndexOrThrow("NAME")),
                        cursor.getString(cursor.getColumnIndexOrThrow("LOCATION")),
                        cursor.getString(cursor.getColumnIndexOrThrow("DATE")),
                        cursor.getString(cursor.getColumnIndexOrThrow("LENGTH")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("PARKING")),
                        cursor.getString(cursor.getColumnIndexOrThrow("WEATHER")),
                        cursor.getString(cursor.getColumnIndexOrThrow("TRAIL")),
                        cursor.getString(cursor.getColumnIndexOrThrow("DIFFICULT")),
                        cursor.getString(cursor.getColumnIndexOrThrow("DESCRIPTION")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("WISHLIST"))
                );
                hikeList.add(hike);
            } while (cursor.moveToNext());
            cursor.close();
        }
        hikesLiveData.setValue(hikeList);
    }

    public LiveData<List<HikeList>> getAllHikes() {
        return hikesLiveData;
    }

    public boolean insertHike(HikeList hike) {
        boolean result = dbHelper.insertHike(
                hike.getName(),
                hike.getLocation(),
                hike.getDate(),
                hike.getLength(),
                hike.getParking(),
                hike.getWeather(),
                hike.getTrail(),
                hike.getDifficult(),
                hike.getDescription()
        );
        if (result) loadAllHikes();
        return result;
    }


    public boolean deleteHike(int id) {
        boolean result = dbHelper.deleteHike(id);
        if (result) loadAllHikes();
        return result;
    }
}
