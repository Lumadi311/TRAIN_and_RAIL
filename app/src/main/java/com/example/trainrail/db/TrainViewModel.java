package com.example.trainrail.db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.trainrail.Train;
import com.example.trainrail.TrainBooking;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TrainViewModel extends AndroidViewModel {

    FirebaseFirestore db ;


    public TrainViewModel(@NonNull Application application) {
        super(application);
        db = FirebaseFirestore.getInstance();
    }


    private MutableLiveData<List<Train>> getTrainsFromDb(String from) {
        MutableLiveData<List<Train>> mutableLiveData = new MutableLiveData<>();
        List<Train> trainList = new ArrayList<>();

        db.collection("Trains").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot document : task.getResult().getDocuments()) {
                    try {
                        Train train = document.toObject(Train.class);
                        trainList.add(train);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Problem mapping data");
                    }
                   //
                }


                mutableLiveData.setValue(trainList.stream().filter(i->i.getFrom().equals(from)).sorted(Comparator.comparing(Train::getFrom)).collect(Collectors.toList()));
                //mutableLiveData.setValue(trainList);


            } else {
                mutableLiveData.setValue(new ArrayList<>());
            }
        });

        return mutableLiveData;
    }

    private MutableLiveData<Boolean> saveBookingDetails(TrainBooking trainBooking) {

        MutableLiveData<Boolean> success = new MutableLiveData<>();

        db.collection("TrainBooking").document(trainBooking.getBookingId()).set(trainBooking).addOnCompleteListener(task -> success.setValue(task.isSuccessful()));

        return success;
    }

    public LiveData<List<Train>> getAllTrains(String from) {
        return getTrainsFromDb(from);
    }

    public LiveData<Boolean> createBookingDetails(TrainBooking trainBooking) {
        return saveBookingDetails(trainBooking);
    }
}
