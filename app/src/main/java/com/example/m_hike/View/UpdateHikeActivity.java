package com.example.m_hike.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.m_hike.Model.DatabaseHelper;
import com.example.m_hike.R;

public class UpdateHikeActivity extends AppCompatActivity {

    EditText edtName, edtLocation, edtDate, edtLength, edtDsc;
    Spinner spinnerDifficulty, spnWeather, spnTrail;
    ImageView imgAdd, imgHome, imgLiked;;
    Button btnUpdate, btnCancel;

    RadioGroup radioParking;
    RadioButton btnYes, btnNo;

    int hikeId;
    private final String[] difficultyLevels = {"Easy", "Medium", "Hard"};
    private final String[] weatherLevels = {"Sunny", "Cloudy", "Rainy", "Foggy", "Windy"};
    private final String[] trailLevels = {"Clear", "Rocky", "Muddy", "Slippery"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatehike);

        edtName = findViewById(R.id.edtName);
        edtLocation = findViewById(R.id.edtLocation);
        edtDate = findViewById(R.id.edtDate);
        edtLength = findViewById(R.id.edtLength);
        spinnerDifficulty = findViewById(R.id.spinnerDifficulty);
        spnWeather = findViewById(R.id.spinnerWeather);
        spnTrail = findViewById(R.id.spinnerTrail);
        edtDsc = findViewById(R.id.edtDsc);
        imgAdd = findViewById(R.id. imgAdd);
        imgHome = findViewById(R.id.imgHome);
        imgLiked = findViewById(R.id.imgLiked);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnCancel = findViewById(R.id.btnCancel);

        radioParking = findViewById(R.id.radioParking);
        btnYes = findViewById(R.id.btnYes);
        btnNo = findViewById(R.id.btnNo);

//        difficult
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, difficultyLevels);
        spinnerDifficulty.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDifficulty.setAdapter(adapter);

        // weather
        ArrayAdapter<String> weatherAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, weatherLevels);
        spnWeather.setAdapter(weatherAdapter);

        // trail
        ArrayAdapter<String> trailAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, trailLevels);
        spnTrail.setAdapter(trailAdapter);

        Intent intent = getIntent();
        hikeId = intent.getIntExtra("HIKE_ID", -1);
        int parking = intent.getIntExtra("HIKE_PARKING", 0);
        if (parking == 1) {
            btnYes.setChecked(true);
        } else {
            btnNo.setChecked(true);

            edtName.setText(intent.getStringExtra("HIKE_NAME"));
        edtLocation.setText(intent.getStringExtra("HIKE_LOCATION"));
        edtDate.setText(intent.getStringExtra("HIKE_DATE"));
        edtLength.setText(intent.getStringExtra("HIKE_LENGTH"));


            String weather = intent.getStringExtra("HIKE_WEATHER");
            if (weather != null) {
                int wIndex = weatherAdapter.getPosition(weather);
                if (wIndex >= 0) spnWeather.setSelection(wIndex);
            }


            String trail = intent.getStringExtra("HIKE_TRAIL");
            if (trail != null) {
                int tIndex = trailAdapter.getPosition(trail);
                if (tIndex >= 0) spnTrail.setSelection(tIndex);
            }

        edtDsc.setText(intent.getStringExtra("HIKE_DESC"));

        String difficulty = intent.getStringExtra("HIKE_DIFFICULT");
        if (difficulty != null) {
            int index = adapter.getPosition(difficulty);
            spinnerDifficulty.setSelection(index);
        }

        btnUpdate.setOnClickListener(v -> {
            DatabaseHelper db = new DatabaseHelper(this);
            int parkingUpdate = radioParking.getCheckedRadioButtonId() == R.id.btnYes ? 1 : 0;
            boolean success = db.updateHike(
                    hikeId,
                    edtName.getText().toString(),
                    edtLocation.getText().toString(),
                    edtDate.getText().toString(),
                    edtLength.getText().toString(),
                    parkingUpdate,
                    spnWeather.getSelectedItem().toString(),
                    spnTrail.getSelectedItem().toString(),
                    spinnerDifficulty.getSelectedItem().toString(),
                    edtDsc.getText().toString()
            );

            if (success) {
                Toast.makeText(this, "Hike updated successfully!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to update hike!", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(v -> finish());

        imgAdd.setOnClickListener(v ->{
            Intent i = new Intent(UpdateHikeActivity.this, AddHikeActivity.class);
            startActivity(i);
        });


        imgLiked.setOnClickListener(v -> {
            Intent i = new Intent(UpdateHikeActivity.this, WishListActivity.class);
            startActivity(i);
        });


        imgHome.setOnClickListener(v ->
                {
                        Intent i = new Intent(UpdateHikeActivity.this, MainActivity.class);
                    startActivity(i);
                }
        );
    }
}}
