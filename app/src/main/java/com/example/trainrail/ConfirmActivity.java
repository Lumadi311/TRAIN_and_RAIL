package com.example.trainrail;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.trainrail.db.TrainViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.trainrail.MainActivity.generateQR;

public class ConfirmActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;
    private EditText emailId;
    private EditText phoneNumber;
    private EditText nameCustomer;
    private EditText ageCustomer;
    private ProgressDialog progressDialog;
    private Button confirmBook;
    private TrainBooking trainBooking;
    private Train train;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        emailId = (EditText) findViewById(R.id.editTextEmail);
        phoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);
        nameCustomer = (EditText) findViewById(R.id.editTextName);
        ageCustomer = (EditText) findViewById(R.id.editTextAge);
        confirmBook = (Button) findViewById(R.id.btnBook);

        progressDialog = new ProgressDialog(this);
        confirmBook.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        if (getIntent().hasExtra("TrainBooking") && getIntent().hasExtra("Train")) {
            trainBooking = (TrainBooking) getIntent().getExtras().get("TrainBooking");
            train = (Train) getIntent().getExtras().get("Train");
        } else {
            Toast.makeText(this, "Booking not found", Toast.LENGTH_SHORT).show();
            finish();
        }


        getSupportActionBar().setTitle("Contact Information");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }


    private void contactBook() {

        String cus_email = emailId.getText().toString().trim();
        String cus_phone = phoneNumber.getText().toString().trim();
        String cus_name = nameCustomer.getText().toString().trim();
        String cus_age = ageCustomer.getText().toString().trim();


        if (TextUtils.isEmpty(cus_email)) {
            //email is empty
            Toast.makeText(this, "Please enter your email address", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(cus_phone)) {
            //password is empty
            Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(cus_name)) {
            //password is empty
            Toast.makeText(this, "Please enter the customer name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(cus_age)) {
            //password is empty
            Toast.makeText(this, "Please enter the customer age", Toast.LENGTH_SHORT).show();
            return;
        }


        CustomerDetail customerDetail = new CustomerDetail(cus_email, cus_phone, cus_name, cus_age);
        trainBooking.setCustomerDetail(customerDetail);

        showConfirmationDialog();


    }

    private void showConfirmationDialog() {
        Dialog d = new Dialog(this);
        d.setContentView(R.layout.confirm_booking);

        StringBuilder details = new StringBuilder();
        details.append("Email : " + trainBooking.getCustomerDetail().getCus_email()).append("Phone Number : " + trainBooking.getCustomerDetail().getCus_phone())
                .append("Age : " + trainBooking.getCustomerDetail().getCus_age()).append("Name : " + trainBooking.getCustomerDetail().getCus_name());


        ImageView qr = d.findViewById(R.id.qrCodeView);
        Button confirm = d.findViewById(R.id.confirmBooking);
        ImageButton dismiss = d.findViewById(R.id.dismiss);

        d.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                qr.setImageBitmap(generateQR(details.toString()));
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        progressDialog.setMessage("Updating Contact Detail Please Wait...");
                        progressDialog.show();

                        new ViewModelProvider(ConfirmActivity.this).get(TrainViewModel.class).createBookingDetails(trainBooking).observe(ConfirmActivity.this, new Observer<Boolean>() {
                            @Override
                            public void onChanged(Boolean success) {
                                if (!success) {
                                    Toast.makeText(ConfirmActivity.this, "Failed to post booking details", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                //todo booking is successful
                            }
                        });

                        //databaseReference..child(user.getUid()).child("CustomerDetails").setValue(customerDetail);
                        //child("bookings").child(userId);

                    }
                });
                dismiss.setOnClickListener(view -> d.dismiss());
            }
        });


        d.show();


    }




    @Override
    public void onClick(View view) {
        if (view == confirmBook) {
            contactBook();
        }
    }

}
