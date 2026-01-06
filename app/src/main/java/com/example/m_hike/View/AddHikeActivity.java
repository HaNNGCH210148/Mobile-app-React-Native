package com.example.m_hike.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.m_hike.Model.HikeList;
import com.example.m_hike.R;
import com.example.m_hike.ViewModel.Hike;

import java.util.Calendar;

public class AddHikeActivity extends AppCompatActivity {

    private EditText edtName, edtLocation, edtDate, edtLength, edtDsc;
    private Spinner spnDifficulty;
    private Button btnSave, btnCancel;
    RadioGroup radioParking;
    Spinner spnWeather, spnTrail;

    private Hike hikeViewModel;
    ImageView imgAdd, imgHome, imgLiked;

    private final String[] difficultyLevels = {"Easy", "Medium", "Hard"};
    private final String[] weatherLevels = {"Sunny", "Cloudy", "Rainy", "Foggy", "Windy"};
    private final String[] trailLevels = {"Clear", "Rocky", "Muddy", "Slippery"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addhike);

        edtName = findViewById(R.id.edtName);
        edtLocation = findViewById(R.id.edtLocation);
        edtDate = findViewById(R.id.edtDate);
        edtLength = findViewById(R.id.edtLength);
        edtDsc = findViewById(R.id.edtDsc);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        imgAdd = findViewById(R.id.imgAdd);
        imgLiked = findViewById(R.id.imgLiked);
        imgHome = findViewById(R.id.imgHome);
        radioParking = findViewById(R.id.radioParking);
        spnWeather = findViewById(R.id.spinnerWeather);
        spnTrail = findViewById(R.id.spinnerTrail);
        spnWeather.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, weatherLevels));
        spnTrail.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, trailLevels));


        spnDifficulty = findViewById(R.id.spinnerDifficulty);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, difficultyLevels);
        spnDifficulty.setAdapter(adapter);

        hikeViewModel = new ViewModelProvider(this).get(Hike.class);

        edtDate.setOnClickListener(v -> showDatePicker());

        btnSave.setOnClickListener(v -> saveHike());

        btnCancel.setOnClickListener(v -> finish());

        imgAdd.setOnClickListener(v ->Toast.makeText(AddHikeActivity.this, "You're already in Add Hike Page!", Toast.LENGTH_SHORT).show() );


        imgLiked.setOnClickListener(v -> {
            Intent i = new Intent(AddHikeActivity.this, WishListActivity.class);
            startActivity(i);
        });


        imgHome.setOnClickListener(v ->
                {
                    Intent i = new Intent(AddHikeActivity.this, MainActivity.class);
                    startActivity(i);
                }
        );
    }



    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (DatePicker view, int year1, int month1, int dayOfMonth) -> {
                    String date = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                    edtDate.setText(date);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void saveHike() {
        String name = edtName.getText().toString().trim();
        String location = edtLocation.getText().toString().trim();
        String date = edtDate.getText().toString().trim();
        String length = edtLength.getText().toString().trim();
        String description = edtDsc.getText().toString().trim();
        String difficulty = spnDifficulty.getSelectedItem().toString();
        String weather = spnWeather.getSelectedItem().toString();
        String trail = spnTrail.getSelectedItem().toString();


        int parking = radioParking.getCheckedRadioButtonId() == R.id.btnYes ? 1 : 0;

        if (name.isEmpty() || location.isEmpty() || date.isEmpty() || length.isEmpty()) {
            Toast.makeText(this, "Please fill all required fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        HikeList newHike = new HikeList(
                0,
                name,
                location,
                date,
                length,
                parking,
                weather,
                trail,
                difficulty,
                description,
                0
        );

        hikeViewModel.insertHike(newHike);

        Toast.makeText(this, "Hike added successfully!", Toast.LENGTH_SHORT).show();
        finish();
    }

}
