package com.example.trainrail;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FinishActivity extends AppCompatActivity {
    private Button buttonHome;
    private TextView a,b,c;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        getSupportActionBar().setTitle("Booking  Finished");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        a=(TextView)findViewById(R.id.textView11);
        b=(TextView)findViewById(R.id.textView21);
        c=(TextView)findViewById(R.id.textView31);
        buttonHome=(Button)findViewById(R.id.btnHome);

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("TrainBookingDetails");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String trainDetailName=dataSnapshot.child("travelsName").getValue().toString();
                String trainDetailDate=dataSnapshot.child("date").getValue().toString();
                String trainDetailCondition=dataSnapshot.child("trainCondition").getValue().toString();

                a.setText(trainDetailName);
                b.setText(trainDetailDate);
                c.setText(trainDetailCondition);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message="Your Ticket Booking Success";
                NotificationCompat.Builder builder=new NotificationCompat.Builder(FinishActivity.this)
                        .setSmallIcon(R.drawable.detail)
                        .setContentTitle("New Notification")
                        .setContentText(message)
                        .setAutoCancel(true);
                Intent intent=new Intent(FinishActivity.this,NotificationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("message",message);

                PendingIntent pendingIntent=PendingIntent.getActivity(FinishActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);

                NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.notify(0,builder.build());
                startActivity(new Intent(getApplicationContext(), NavigationActivity.class));
            }
        });
    }
}
