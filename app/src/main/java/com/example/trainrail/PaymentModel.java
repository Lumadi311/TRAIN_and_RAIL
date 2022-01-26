package com.example.trainrail;

import java.util.Calendar;

public class PaymentModel {
    private String paymentId;
    private String bookingId;


    public PaymentModel() {

    }

    public PaymentModel(String bookingId) {
        this.bookingId = bookingId;
    }

    public PaymentModel(String paymentId, String bookingId) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }


    public static String generatePaymentId() {
        return Calendar.getInstance().getTime().getTime() + "-" + "PAY";
    }
}
