package com.example.m_hike.View;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m_hike.Adapter.HikeAdapter;
import com.example.m_hike.Model.DatabaseHelper;
import com.example.m_hike.Model.HikeList;
import com.example.m_hike.R;

import java.util.ArrayList;
import java.util.List;

public class WishListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView imgAdd, imgHome, imgLiked;
    TextView txtEmpty;
    HikeAdapter adapter;
    DatabaseHelper db;
    List<HikeList> wishList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wishlist);

        db = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.WishList);
        imgAdd = findViewById(R.id. imgAdd);
        imgHome = findViewById(R.id.imgHome);
        imgLiked = findViewById(R.id.imgLiked);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        txtEmpty = findViewById(R.id.txtEmpty);

        loadWishlist();

        if(wishList.isEmpty()){
            txtEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else {
            txtEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        imgAdd.setOnClickListener(v ->{
            Intent i = new Intent(WishListActivity.this, AddHikeActivity.class);
            startActivity(i);
        });


        imgLiked.setOnClickListener(v -> Toast.makeText(WishListActivity.this, "You're already in Wishlist!", Toast.LENGTH_SHORT).show());


        imgHome.setOnClickListener(v ->
                {
                    Intent i = new Intent(WishListActivity.this, MainActivity.class);
                    startActivity(i);
                }
        );
    }

    private void loadWishlist() {
        Cursor cursor = db.getWishlist();
        wishList.clear();

        while (cursor.moveToNext()) {
            HikeList h = new HikeList(
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
            wishList.add(h);
        }
        cursor.close();

        adapter = new HikeAdapter(
                this,
                wishList,
                hike -> {},
                hike -> {},
                hike -> {},
                hike -> {},
                true
        );

        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadWishlist();
    }
}

