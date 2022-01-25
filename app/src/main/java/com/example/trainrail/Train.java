package com.example.trainrail;

import java.io.Serializable;
import java.util.Calendar;

//Trains
public class Train implements Serializable {
    public String trainId;
    public String travelsName;
    public String trainNumber;
    public String date;
    public String from;
    public String to;
    public String trainCondition;
    public String trainName;

    public Train() {
    }


    public Train(String travelsName, String trainName, String trainNumber, String date, String from, String to, String trainCondition) {
        this.travelsName = travelsName;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.date = date;
        this.from = from;
        this.to = to;
        this.trainCondition = trainCondition;
    }


    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getTravelsName() {
        return travelsName;
    }

    public void setTravelsName(String travelsName) {
        this.travelsName = travelsName;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTrainCondition() {
        return trainCondition;
    }

    public void setTrainCondition(String trainCondition) {
        this.trainCondition = trainCondition;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public static String generateTrainId(String from, String to) {
        return from.concat("-").concat(Calendar.getInstance().getTime().toString().trim()).concat("-").concat(to);
    }
}
