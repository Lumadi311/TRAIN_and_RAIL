package com.example.trainrail;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NotificationActivity extends AppCompatActivity {
    private TextView a,b,c;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        getSupportActionBar().setTitle("Notification Alert");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        a=(TextView)findViewById(R.id.textView111);


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

        TextView textView=findViewById(R.id.text_View);
        String message=getIntent().getStringExtra("message");
        textView.setText(message);
    }
}
