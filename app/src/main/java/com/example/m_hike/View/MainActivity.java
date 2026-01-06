package com.example.m_hike.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.m_hike.Adapter.HikeAdapter;
import com.example.m_hike.Model.HikeList;
import com.example.m_hike.R;
import com.example.m_hike.ViewModel.Hike;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Hike hikeVM;
    private RecyclerView recyclerView;
    private HikeAdapter hikeAdapter;
    private EditText edtSearch;
    private ImageView imgLiked, imgHome, imgAdd;

    private List<HikeList> allHikes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.hikeList);
        edtSearch = findViewById(R.id.edtSearch);
        imgAdd = findViewById(R.id.imgAdd);
        imgLiked = findViewById(R.id.imgLiked);
        imgHome = findViewById(R.id.imgHome);

        hikeVM = new ViewModelProvider(this).get(Hike.class);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // adapter
        hikeAdapter = new HikeAdapter(this,
                new ArrayList<>(),
                hike -> { // [1] itemClick → View Observation
                    Intent intent = new Intent(MainActivity.this, ObserActivity.class);
                    intent.putExtra("HIKE_ID", hike.getId());
                    intent.putExtra("HIKE_NAME", hike.getName());
                    intent.putExtra("HIKE_LOCATION", hike.getLocation());
                    intent.putExtra("HIKE_DATE", hike.getDate());
                    startActivity(intent);
                },
                hike -> { // [2] editClick
                    Intent intent = new Intent(MainActivity.this, UpdateHikeActivity.class);
                    intent.putExtra("HIKE_ID", hike.getId());
                    intent.putExtra("HIKE_NAME", hike.getName());
                    intent.putExtra("HIKE_LOCATION", hike.getLocation());
                    intent.putExtra("HIKE_DATE", hike.getDate());
                    intent.putExtra("HIKE_LENGTH", hike.getLength());
                    intent.putExtra("HIKE_DIFFICULT", hike.getDifficult());
                    intent.putExtra("HIKE_DESC", hike.getDescription());
                    startActivity(intent);
                },
                hike -> { // [3] deleteClick
                    hikeVM.deleteHike(hike.getId());
                    Toast.makeText(MainActivity.this, "Hike deleted", Toast.LENGTH_SHORT).show();
                },
                hike -> { // [4]
                    Intent intent = new Intent(MainActivity.this, ObserActivity.class);
                    intent.putExtra("HIKE_ID", hike.getId());
                    intent.putExtra("HIKE_NAME", hike.getName());
                    intent.putExtra("HIKE_LOCATION", hike.getLocation());
                    intent.putExtra("HIKE_DATE", hike.getDate());
                    startActivity(intent);
                }, false
        );

        recyclerView.setAdapter(hikeAdapter);

        //  livedata từ viewmodel
        hikeVM.getAllHikes().observe(this, hikes -> {
            allHikes = hikes;
            hikeAdapter.setHikeList(hikes); // cập nhật adapter
        });


        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterList(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });


        imgAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddHikeActivity.class);
            startActivity(intent);
        });


        imgLiked.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WishListActivity.class);
            startActivity(intent);
        });


        imgHome.setOnClickListener(v ->
                Toast.makeText(MainActivity.this, "You're already on Home!", Toast.LENGTH_SHORT).show()
        );

    }


    private void filterList(String query) {
        List<HikeList> filteredList = new ArrayList<>();
        for (HikeList hike : allHikes) {
            if (hike.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(hike);
            }
        }
        hikeAdapter.setHikeList(filteredList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // load danh sách khi back add/update
        hikeVM.loadAllHikes();
    }

}
