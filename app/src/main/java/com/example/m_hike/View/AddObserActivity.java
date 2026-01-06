package com.example.m_hike.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.m_hike.R;
import com.example.m_hike.ViewModel.Obser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddObserActivity extends AppCompatActivity {

    private EditText edtDay, edtTime, edtComment;
    private Button btnSave, btnCancel;
    private Obser observationVM;
    private int hikeId;
    ImageView imgAdd, imgHome, imgLiked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogobser);

        Intent intent = getIntent();
        hikeId = intent.getIntExtra("HIKE_ID", -1);

        edtDay = findViewById(R.id.dialog_edt_day);
        edtTime = findViewById(R.id.dialog_edt_time);
        edtComment = findViewById(R.id.dialog_edt_comment);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        imgAdd = findViewById(R.id.imgAdd);
        imgLiked = findViewById(R.id.imgLiked);
        imgHome = findViewById(R.id.imgHome);

        observationVM = new ViewModelProvider(this).get(Obser.class);

        // mặc định ngày/giờ
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        edtDay.setText(currentDate);
        edtTime.setText(currentTime);
        edtComment.setText("");

        btnSave.setOnClickListener(v -> {
            String day = edtDay.getText().toString().trim();
            String time = edtTime.getText().toString().trim();
            String comment = edtComment.getText().toString().trim();

            observationVM.addObser(hikeId, "Observation", day, time, comment);

            Toast.makeText(this, "Observation added successfully!", Toast.LENGTH_SHORT).show();

            Intent backIntent = new Intent(this, ObserActivity.class);
            backIntent.putExtra("HIKE_ID", hikeId);
            finish();
        });

        btnCancel.setOnClickListener(v -> finish());

        imgAdd.setOnClickListener(v ->{
            Intent i = new Intent(AddObserActivity.this, AddHikeActivity.class);
            startActivity(i);
        });


        imgLiked.setOnClickListener(v -> {
            Intent i = new Intent(AddObserActivity.this, WishListActivity.class);
            startActivity(i);
        });


        imgHome.setOnClickListener(v ->
                {
                    Intent i = new Intent(AddObserActivity.this, MainActivity.class);
                    startActivity(i);
                }
        );
    }
}
