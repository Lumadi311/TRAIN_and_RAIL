package com.example.trainrail;


public class TrainDetail {
    public String train_name;
    public String train_number;
    public String train_date;
    public String train_from;
    public String train_to;
    public String train_condition;


    public TrainDetail(String train_name, String train_number, String train_date, String train_from, String train_to, String train_condition) {
        this.train_name = train_name;
        this.train_number = train_number;
        this.train_date = train_date;
        this.train_from = train_from;
        this.train_to = train_to;
        this.train_condition = train_condition;
    }

    public TrainDetail() {
    }
}
