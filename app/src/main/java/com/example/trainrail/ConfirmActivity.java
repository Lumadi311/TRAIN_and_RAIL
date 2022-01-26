package com.example.trainrail;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.trainrail.db.TrainViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.trainrail.MainActivity.generateQR;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@RequiresApi(api = Build.VERSION_CODES.O)
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
    private final MutableLiveData<Optional<String>> checkoutId = new MutableLiveData<>();


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

        if (!phoneNumber.getText().toString().startsWith("254")) {
            phoneNumber.setError("Must start with 254");
            return;
        }


        if (phoneNumber.getText().toString().length() != 12) {
            phoneNumber.setError("Must have 13 digits");
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

        checkoutId.observe(this, new Observer<Optional<String>>() {
            @Override
            public void onChanged(Optional<String> paymentId) {
                paymentId.ifPresent(p -> new ViewModelProvider(ConfirmActivity.this).get(TrainViewModel.class).createPaymentDetails(new PaymentModel(p, trainBooking.getBookingId())).observe(ConfirmActivity.this, success -> {
                    if (success) {
                        Toast.makeText(ConfirmActivity.this, "Saved payment", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(ConfirmActivity.this, "Failed to save payment", Toast.LENGTH_SHORT).show();
                    }
                }));
            }
        });

    }

    private void showConfirmationDialog() {
        Dialog d = new Dialog(this);
        d.setContentView(R.layout.confirm_booking);

        StringBuilder details = new StringBuilder();
        details.append("Email : " + trainBooking.getCustomerDetail().getCus_email())
                .append("Phone Number : " + trainBooking.getCustomerDetail().getCus_phone())
                .append("Age : " + trainBooking.getCustomerDetail().getCus_age())
                .append("Name : " + trainBooking.getCustomerDetail().getCus_name());


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

                        d.dismiss();

                        new ViewModelProvider(ConfirmActivity.this).get(TrainViewModel.class).createBookingDetails(trainBooking).observe(ConfirmActivity.this, new Observer<Boolean>() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onChanged(Boolean success) {
                                if (!success) {
                                    Toast.makeText(ConfirmActivity.this, "Failed to post booking details", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                //todo booking is successful
                                Thread thread = new Thread(() -> {
                                    Looper.prepare();
                                    try {
                                        sendPaymentDetails(trainBooking.getCustomerDetail().getCus_phone(), trainBooking.getBookingId());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                });
                                thread.start();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendPaymentDetails(String phone, String bookingId) {

        try {
            Optional<String> paymentId = sendStkPush(phone, bookingId);
            if (!paymentId.isPresent()) {
                Toast.makeText(ConfirmActivity.this, "Failed to initialize payment", Toast.LENGTH_SHORT).show();
                return;
            }

            paymentId.ifPresent(p -> checkoutId.postValue(paymentId));

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Toast.makeText(ConfirmActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }

    }




    @RequiresApi(api = Build.VERSION_CODES.O)
    public String authenticate() throws IOException, JSONException {

        String encodedBasicAuthString = getBasicAuth("eiaKJaH41Twej5U0KV4ZwHNtc5ABYYDW", "6sAGXzDCbByAAdrF");
        System.out.println("Authorization " + encodedBasicAuthString);


        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url("https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials")
                .method("GET", null)
                .addHeader("Authorization", "Basic " + encodedBasicAuthString)
                .build();
        Response response = client.newCall(request).execute();


        assert response.body() != null;
        JSONObject jsonObject = new JSONObject(Objects.requireNonNull(response.body()).string());

        return jsonObject.getString("access_token");
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public Optional<String> sendStkPush(String phone, String bookingId) throws IOException, JSONException {

        JSONArray jsonArray = new JSONArray();


        JSONObject requestBodyJson = new JSONObject();
        requestBodyJson.put("BusinessShortCode", 174379);
        requestBodyJson.put("Password", getEncodedPassword("174379", "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919"));
        requestBodyJson.put("Timestamp", getTimestamp());
        requestBodyJson.put("TransactionType", "CustomerPayBillOnline");
        requestBodyJson.put("Amount", String.valueOf(trainBooking.getAmount().getTotal_cost()));
        requestBodyJson.put("PartyA", phone);
        requestBodyJson.put("PartyB", "174379");
        requestBodyJson.put("PhoneNumber", phone);
        requestBodyJson.put("CallBackURL", "https://train-pay.herokuapp.com/api/v1/pay");
        requestBodyJson.put("AccountReference", phone);
        requestBodyJson.put("TransactionDesc", phone);


        jsonArray.put(requestBodyJson);

        String requestJson = jsonArray.toString().replaceAll("[\\[\\]]", "");

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestJson);
        Request requesting = new Request.Builder()
                .url("https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + authenticate())
                .build();


        Response response = client.newCall(requesting).execute();

        assert response.body() != null;
        JSONObject jsonObject = new JSONObject(Objects.requireNonNull(response.body()).string());

        System.out.println("Json is " + jsonObject);

        return Optional.of(jsonObject.getString("CheckoutRequestID"));
    }

    public static String getTimestamp() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return simpleDateFormat.format(timestamp);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getEncodedPassword(String businessShortCode, String passKey) {
        String password = businessShortCode + passKey + getTimestamp();
        byte[] bytesPassword = password.getBytes(StandardCharsets.ISO_8859_1);
        return Base64.getEncoder().encodeToString(bytesPassword);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getBasicAuth(String consumerKey, String consumerSecret) throws UnsupportedEncodingException {
        String auth = consumerKey + ":" + consumerSecret;
        byte[] bytesAuth = auth.getBytes("ISO-8859-1");
        return Base64.getEncoder().encodeToString(bytesAuth);
    }

    @Override
    public void onClick(View view) {
        if (view == confirmBook) {
            contactBook();
        }
    }

}
