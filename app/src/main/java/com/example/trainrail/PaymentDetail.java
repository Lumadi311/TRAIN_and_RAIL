package com.example.trainrail;

public class PaymentDetail {
    public int total_cost;
    public String total_seats;

    public PaymentDetail(int total_cost, String total_seats) {
        this.total_cost = total_cost;
        this.total_seats = total_seats;
    }

    public PaymentDetail() {
    }

    public int getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(int total_cost) {
        this.total_cost = total_cost;
    }

    public String getTotal_seats() {
        return total_seats;
    }

    public void setTotal_seats(String total_seats) {
        this.total_seats = total_seats;
    }
}


