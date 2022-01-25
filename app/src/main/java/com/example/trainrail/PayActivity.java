package com.example.trainrail;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PayActivity extends AppCompatActivity {
    private Button buttonOffer;
    private Button buttonNetBanking;
    private Button buttonWallets;
    private TextView textViewTotal;
    private TextView a,b,c;
    private TrainBooking trainBooking;
    private Train train;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        getSupportActionBar().setTitle("Pay Information");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonOffer = (Button) findViewById(R.id.buttonOffer);
        buttonNetBanking = (Button) findViewById(R.id.buttonNetBanking);


        textViewTotal=(TextView)findViewById(R.id.textViewTotal);
        a=(TextView)findViewById(R.id.textView1);
        b=(TextView)findViewById(R.id.textView2);
        c=(TextView)findViewById(R.id.textView3);

        if (getIntent().hasExtra("TrainBooking") && getIntent().hasExtra("Train")) {
            trainBooking = (TrainBooking) getIntent().getExtras().get("TrainBooking");
            train = (Train) getIntent().getExtras().get("Train");
        } else {
            Toast.makeText(this, "Booking not found", Toast.LENGTH_SHORT).show();
            finish();
        }


        a.setText(train.getTrainName());
        b.setText(train.getDate());
        c.setText(train.getTrainCondition());

        String total=String.valueOf(trainBooking.getAmount().getTotal_cost());
        textViewTotal.setText(total);

        buttonOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(PayActivity.this,CreditActivity.class);
                intent.putExtra("TrainBooking",trainBooking);
                intent.putExtra("Train",train);
                startActivity(intent);
            }
        });



        buttonNetBanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PayActivity.this,ConfirmActivity.class);
                intent.putExtra("TrainBooking",trainBooking);
                intent.putExtra("Train",train);
                startActivity(intent);
            }
        });

        buttonWallets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PayActivity.this,CreditActivity.class);
                intent.putExtra("TrainBooking",trainBooking);
                intent.putExtra("Train",train);
                startActivity(intent);
            }
        });

    }
}
