package com.example.trainrail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PayableActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private Button buttonPay;
    TextView totalCost;
    TextView totalSeat;
    private TextView a,b,c;
    private TrainBooking trainBooking;
    private Train train;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payable);

        firebaseAuth= FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        getSupportActionBar().setTitle("You Can Pay");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        a=(TextView)findViewById(R.id.textView11);
        b=(TextView)findViewById(R.id.textView21);
        c=(TextView)findViewById(R.id.textView31);

        totalCost=(TextView)findViewById(R.id.totalCostFinal);
        totalSeat=(TextView)findViewById(R.id.totalSeatsFinal);

        if (getIntent().hasExtra("TrainBooking") && getIntent().hasExtra("Train")) {
            trainBooking = (TrainBooking) getIntent().getExtras().get("TrainBooking");
            train = (Train) getIntent().getExtras().get("Train");
        } else {
            Toast.makeText(this, "Booking not found", Toast.LENGTH_SHORT).show();
            finish();
        }

        final String total=String.valueOf(trainBooking.getAmount().getTotal_cost());
        final String seats=trainBooking.getAmount().getTotal_seats();

        final String nameTrain=train.getTrainName();
        final String dateTrain=train.getDate();
        final String conditionTrain=train.getTrainCondition();

        a.setText(nameTrain);
        b.setText(dateTrain);
        c.setText(conditionTrain);

        totalCost.setText("Payable : Ksh."+total);
        totalSeat.setText("Number Of Seats : "+seats);

        buttonPay=(Button)findViewById(R.id.btnPay);
        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          String totalPriceI=totalCost.getText().toString();

                String total_cost=totalCost.getText().toString().trim();
                String total_seats=totalSeat.getText().toString().trim();


                Intent intent=new Intent(PayableActivity.this,PayActivity.class);
                intent.putExtra("TrainBooking",trainBooking);
                intent.putExtra("Train",train);
                startActivity(intent);
              startActivity(new Intent(getApplicationContext(), PayActivity.class));
            }
        });
    }
}