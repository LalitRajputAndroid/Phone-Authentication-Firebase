package com.example.otpverification;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTP_verificationActivity2 extends AppCompatActivity {

    EditText verifycode;
    MaterialButton verifybtn;
    TextView numberresend, emailresend;
    String number;
    String otpid;

    ProgressDialog dialog;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification2);
        number = getIntent().getStringExtra("mobile");
        System.out.println(number);
        verifycode = findViewById(R.id.verifynumber_id);
        verifybtn = findViewById(R.id.verifybtn_id);
        numberresend = findViewById(R.id.resend_id);

        auth = FirebaseAuth.getInstance();


        dialog = new ProgressDialog(this);
        dialog.setTitle("Verify Your Code");

        initiateotp();

        verifybtn.setOnClickListener(v -> {
            dialog.show();
            if (verifycode.getText().toString().isEmpty()) {
                Toast.makeText(OTP_verificationActivity2.this, "Blank Field can not be processed", Toast.LENGTH_SHORT).show();
            } else if (verifycode.getText().toString().length() != 6) {
                Toast.makeText(OTP_verificationActivity2.this, "Invalid otp", Toast.LENGTH_SHORT).show();
            } else {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpid, verifycode.getText().toString());
                signInwithphoneAuthcredentail(credential);
            }
        });

        numberresend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiateotp();
            }
        });

    }

    private void initiateotp() {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        otpid = s;
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                        signInwithphoneAuthcredentail(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(OTP_verificationActivity2.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void signInwithphoneAuthcredentail(PhoneAuthCredential credential) {
        dialog.show();
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    dialog.dismiss();
                    if (task.isSuccessful()) {
                        Toast.makeText(OTP_verificationActivity2.this, "Verify Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(OTP_verificationActivity2.this, HomeActivity3.class));
                        finish();
                    } else {
                        Toast.makeText(OTP_verificationActivity2.this, "signin code Error", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(this, e -> Toast.makeText(OTP_verificationActivity2.this, "error" + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}