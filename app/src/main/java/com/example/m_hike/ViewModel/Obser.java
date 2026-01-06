package com.example.m_hike.ViewModel;

import android.app.Application;
import android.database.Cursor;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.m_hike.Model.DatabaseHelper;
import com.example.m_hike.Model.ObserList;

import java.util.ArrayList;
import java.util.List;

public class Obser extends AndroidViewModel {

    private DatabaseHelper dbHelper;
    private MutableLiveData<List<ObserList>> obserLiveData = new MutableLiveData<>();

    public Obser(Application application) {
        super(application);
        dbHelper = new DatabaseHelper(application);
    }

    public void loadObservations(int hikeId) {
        Cursor cursor = dbHelper.getObservationsByHike(hikeId);
        List<ObserList> list = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                ObserList obs = new ObserList(
                        cursor.getInt(cursor.getColumnIndexOrThrow("OBS_ID")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("HIKE_ID")),
                        cursor.getString(cursor.getColumnIndexOrThrow("OBSERVATION")),
                        cursor.getString(cursor.getColumnIndexOrThrow("DAY")),
                        cursor.getString(cursor.getColumnIndexOrThrow("TIME")),
                        cursor.getString(cursor.getColumnIndexOrThrow("COMMENT"))
                );
                list.add(obs);
            } while (cursor.moveToNext());
            cursor.close();
        }
        obserLiveData.setValue(list);
    }

    public LiveData<List<ObserList>> getObservations() {
        return obserLiveData;
    }

    public boolean deleteObservation(int id, int hikeId) {
        boolean result = dbHelper.deleteObservation(id);
        if (result) loadObservations(hikeId);
        return result;
    }

    public boolean addObser(int hikeId, String observation, String day, String time, String comment) {
        boolean result = dbHelper.insertObservation(hikeId, observation, day, time, comment);
        if (result) {
            loadObservations(hikeId);
        }
        return result;
    }
}
