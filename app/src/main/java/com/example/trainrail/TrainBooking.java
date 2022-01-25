package com.example.trainrail;

import java.io.Serializable;
import java.util.Calendar;

public class TrainBooking implements Serializable {
    private String bookingId;
    private String userId;
    private String trainId;
    private PaymentDetail amount;
    private CustomerDetail customerDetail;
    private String bookingDate;
    private boolean paid;

    public TrainBooking(String bookingId, String userId, String trainId, PaymentDetail amount, String bookingDate, boolean paid) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.trainId = trainId;
        this.amount = amount;
        this.bookingDate = bookingDate;
        this.paid = paid;
    }


    public static String generateBookingId(String uid) {
        return uid.concat("-").concat(Calendar.getInstance().getTime().toString().trim()).concat("-".concat("BK"));
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public PaymentDetail getAmount() {
        return amount;
    }

    public void setAmount(PaymentDetail amount) {
        this.amount = amount;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public CustomerDetail getCustomerDetail() {
        return customerDetail;
    }

    public void setCustomerDetail(CustomerDetail customerDetail) {
        this.customerDetail = customerDetail;
    }
}
