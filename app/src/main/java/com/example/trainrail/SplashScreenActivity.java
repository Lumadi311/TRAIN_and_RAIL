package com.example.trainrail;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

import static com.example.trainrail.Train.generateTrainId;


public class SplashScreenActivity extends AppCompatActivity {
    private int SLEEP_TIMER = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        //addTrain();

        LogoLauncher logoLauncher = new LogoLauncher();
        logoLauncher.start();
    }

    private void addTrain() {
        Train train = new Train("maina","SGR3", "21", Calendar.getInstance().getTime().toString(), "Syokimau", "CBD", "Fair");
        train.setTrainId(generateTrainId(train.getFrom(), train.getTo()));
        FirebaseFirestore.getInstance().collection("Trains").document(train.getTrainId()).set(train).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    System.out.println("Train added");
                } else {
                    System.out.println("Failed to add train");
                }
            }
        });
    }

    private class LogoLauncher extends Thread {
        public void run() {
            try {
                sleep(1000 * SLEEP_TIMER);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
            startActivity(intent);
            SplashScreenActivity.this.finish();
        }
    }
}
