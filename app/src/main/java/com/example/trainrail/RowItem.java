package com.example.trainrail;

public class RowItem {
    private String trainName;
    private int picId;
    private String route;
    private String time;

    public RowItem(String trainName, int picId, String route, String time) {
        this.trainName = trainName;
        this.picId = picId;
        this.route = route;
        this.time = time;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
