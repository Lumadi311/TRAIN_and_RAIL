package com.example.trainrail;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainrail.db.TrainViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class TrainActivity extends AppCompatActivity implements ItemClickListener {

    private RecyclerView recyclerView;
    private TrainAdapter adapter;
    private List<Train> trainList;
    private DatabaseReference dbTrains;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);

        getSupportActionBar().setTitle("Select Your Desired Train");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        trainList = new ArrayList<>();
        adapter = new TrainAdapter(this, trainList);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(new TrainAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Train train = trainList.get(position);
                Intent intent = new Intent(TrainActivity.this, SeatActivity.class).putExtra("Train", train);
                startActivity(intent);
            }

        });

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        String fromTrain = getIntent().getStringExtra("FROM_TRAIN");
        final String toTrain = getIntent().getStringExtra("TO_TRAIN");
        final String dateTrain = getIntent().getStringExtra("DATE_TRAIN");


        getTrains(fromTrain);
    }


    private void getTrains(String fromTrain) {
        new ViewModelProvider(this).get(TrainViewModel.class).getAllTrains(fromTrain).observe(this, new Observer<List<Train>>() {
            @Override
            public void onChanged(List<Train> trains) {
                trainList.clear();
                trainList.addAll(trains);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View view, int position) {

    }
}
