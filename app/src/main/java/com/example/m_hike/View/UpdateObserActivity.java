package com.example.m_hike.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.m_hike.Model.DatabaseHelper;
import com.example.m_hike.Model.ObserList;
import com.example.m_hike.R;

public class UpdateObserActivity extends AppCompatActivity {

    EditText edtDay, edtTime, edtComment;
    Button btnSave, btnCancel;
    ImageView imgAdd, imgHome, imgLiked;

    int obsId, hikeId;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updateobser);

        edtDay = findViewById(R.id.dialog_edt_day);
        edtTime = findViewById(R.id.dialog_edt_time);
        edtComment = findViewById(R.id.dialog_edt_comment);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        imgAdd = findViewById(R.id. imgAdd);
        imgHome = findViewById(R.id.imgHome);
        imgLiked = findViewById(R.id.imgLiked);

        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        obsId = intent.getIntExtra("OBS_ID", -1);
        hikeId = intent.getIntExtra("HIKE_ID", -1);

        edtDay.setText(intent.getStringExtra("OBS_DAY"));
        edtTime.setText(intent.getStringExtra("OBS_TIME"));
        edtComment.setText(intent.getStringExtra("OBS_COMMENT"));

        btnSave.setOnClickListener(v -> updateObservation());
        btnCancel.setOnClickListener(v -> finish());
        imgAdd.setOnClickListener(v ->{
            Intent i = new Intent(UpdateObserActivity.this, AddHikeActivity.class);
            startActivity(i);
        });


        imgLiked.setOnClickListener(v -> {
            Intent i = new Intent(UpdateObserActivity.this, WishListActivity.class);
            startActivity(i);
        });


        imgHome.setOnClickListener(v ->
                {
                    Intent i = new Intent(UpdateObserActivity.this, MainActivity.class);
                    startActivity(i);
                }
        );
    }


    private void updateObservation() {
        String day = edtDay.getText().toString().trim();
        String time = edtTime.getText().toString().trim();
        String comment = edtComment.getText().toString().trim();

        if (day.isEmpty() || time.isEmpty()) {
            Toast.makeText(this, "Day and Time must not be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean success = db.updateObservation(obsId, "Observation", day, time, comment);

        if (success) {
            Toast.makeText(this, "Observation updated!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to update!", Toast.LENGTH_SHORT).show();
        }
    }
}
