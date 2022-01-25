package com.example.trainrail;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private EditText editTextEmail;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextPassword;
    private TextView textViewSignin;


    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth=FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            //profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(),NavigationActivity.class));
        }

        button = (Button) findViewById(R.id.button);
        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);


        progressDialog=new ProgressDialog(this);

        button.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }

    private void registerUser() {
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


        if (TextUtils.isEmpty(firstName)) {
            //first name is empty
            Toast.makeText(this, "Please enter your First Name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(lastName)) {
            //last name is empty
            Toast.makeText(this, "Please enter your Last Name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(this, "Please enter your Email Address", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!email.matches(emailPattern)) {
            Toast.makeText(this, "Please enter the valid Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            //password is empty
            Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();


        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(getApplicationContext(), NavigationActivity.class));
                            //Toast.makeText(RegisterActivity.this,"Registered ",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Registered Error", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }



    @Override
    public void onClick(View view) {
        if (view == button) {
            registerUser();
        }
        if(view == textViewSignin){
            startActivity(new Intent(this,LoginActivity.class));
        }
    }
}
