package com.example.m_hike.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.m_hike.Adapter.ObserAdapter;
import com.example.m_hike.Model.ObserList;
import com.example.m_hike.R;
import com.example.m_hike.ViewModel.Obser;

import java.util.ArrayList;

public class ObserActivity extends AppCompatActivity {

    private Obser observationVM;
    private RecyclerView recyclerView;
    private ObserAdapter adapter;
    private Button btnAddObservation, btnCancel;
    private TextView txtHikeInfo;

    private int hikeId;
    private String hikeName;
    private String hikeLocation;
    private String hikeDate;
    TextView txtEmpty;
    ImageView imgAdd, imgHome, imgLiked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.observations);

        recyclerView = findViewById(R.id.hikeObser);
        btnAddObservation = findViewById(R.id.btnAddObservation);
        btnCancel = findViewById(R.id.btnCancel);
        txtHikeInfo = findViewById(R.id.txtHikeInfo);
        imgAdd = findViewById(R.id. imgAdd);
        imgHome = findViewById(R.id.imgHome);
        imgLiked = findViewById(R.id.imgLiked);
        TextView tvDetailHikeLocation = findViewById(R.id.tvDetailHikeLocation);
        TextView tvDetailHikeDate = findViewById(R.id.tvDetailHikeDate);
        txtEmpty = findViewById(R.id.txtEmpty);

        Intent intent = getIntent();
        hikeId = intent.getIntExtra("HIKE_ID", -1);
        hikeName = intent.getStringExtra("HIKE_NAME");
        hikeLocation = intent.getStringExtra("HIKE_LOCATION");
        hikeDate = intent.getStringExtra("HIKE_DATE");
        txtHikeInfo.setText(hikeName);
        tvDetailHikeLocation.setText("Location: " + hikeLocation);
        tvDetailHikeDate.setText("Date: " + hikeDate);

        imgAdd.setOnClickListener(v ->{
            Intent i = new Intent(ObserActivity.this, AddHikeActivity.class);
            startActivity(i);
        });


        imgLiked.setOnClickListener(v -> {
            Intent i = new Intent(ObserActivity.this, WishListActivity.class);
            startActivity(i);
        });


        imgHome.setOnClickListener(v ->
                {
                    Intent i = new Intent(ObserActivity.this, MainActivity.class);
                    startActivity(i);
                }
        );


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        observationVM = new ViewModelProvider(this).get(Obser.class);

        adapter = new ObserAdapter(new ArrayList<>(), new ObserAdapter.OnItemClickListener() {


            @Override
            public void onEditClick(ObserList obs) {
                Intent editIntent = new Intent(ObserActivity.this, UpdateObserActivity.class);
                editIntent.putExtra("OBS_ID", obs.getObserId());
                editIntent.putExtra("HIKE_ID", hikeId);
                editIntent.putExtra("OBS_DAY", obs.getDay());
                editIntent.putExtra("OBS_TIME", obs.getTime());
                editIntent.putExtra("OBS_COMMENT", obs.getComment());
                startActivity(editIntent);
            }

            @Override
            public void onDeleteClick(ObserList obs) {
                observationVM.deleteObservation(obs.getObserId(), hikeId);
                Toast.makeText(ObserActivity.this, "Deleted!", Toast.LENGTH_SHORT).show();
            }

        });

        recyclerView.setAdapter(adapter);

        // data ViewModel
        observationVM.getObservations().observe(this, list -> {
            adapter.setObservations(list);
            if (list == null || list.isEmpty()) {
                txtEmpty.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                txtEmpty.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });

        // load danh sách
        observationVM.loadObservations(hikeId);


        btnAddObservation.setOnClickListener(v -> {
            Intent addIntent = new Intent(ObserActivity.this, AddObserActivity.class);
            addIntent.putExtra("HIKE_ID", hikeId);
            addIntent.putExtra("HIKE_NAME", hikeName);
            addIntent.putExtra("HIKE_DATE", hikeDate);
            startActivity(addIntent);

        });

        btnCancel.setOnClickListener(v -> finish());



    }

    @Override
    protected void onResume() {
        super.onResume();
        observationVM.loadObservations(hikeId);
    }
}
