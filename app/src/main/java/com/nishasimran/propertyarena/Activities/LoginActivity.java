package com.nishasimran.propertyarena.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.nishasimran.propertyarena.R;
import com.nishasimran.propertyarena.Utils.Utils;
import com.nishasimran.propertyarena.Values.Values;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = "LoginAct";

    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private String verificationId;
    private boolean codeSent = false;

    private EditText phoneEditText, otpEditText;
    private ProgressBar progress;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        setDefaults();
    }

    private void setDefaults() {
        otpEditText.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);

        submitButton.setOnClickListener(v -> {
            if (codeSent) {
                submitCode();
            } else {
                String phone = validateEditText(phoneEditText, Values.PHONE_LENGTH);
                if (phone != null) {
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("Confirm")
                            .setMessage("Is this your phone no: " + Values.COUNTRY_CODE + " " + phone)
                            .setPositiveButton("Yes", (dialog, which) -> startPhoneAuth(phone))
                            .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                            .show();
                } else {
                    phoneEditText.setError(Values.ERROR_PHONE);
                    Toast.makeText(LoginActivity.this, Values.ERROR_PHONE, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void submitCode() {
        String otp = validateEditText(otpEditText, Values.OTP_LENGTH);
        if (otp != null) {
            progress.setVisibility(View.VISIBLE);
            disableView(otpEditText);
            disableView(phoneEditText);
            disableView(submitButton);
            signInWithCode(otp, verificationId);
        } else {
            otpEditText.setError(Values.ERROR_OTP);
            Toast.makeText(LoginActivity.this, Values.ERROR_OTP, Toast.LENGTH_SHORT).show();
        }
    }

    private void signInWithCode( String otp, String verificationId) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);
        signInWithPhoneAuthCredential(credential);
    }

    private void startPhoneAuth(String phone) {
        if (phone != null) {
            phone = Values.COUNTRY_CODE + phone;
            disableView(phoneEditText);
            progress.setVisibility(View.VISIBLE);
            disableView(submitButton);
            signIn(phone, generateCallbacks());
        }
    }

    private void signIn(String phone, PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(phone)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(LoginActivity.this)
                        .setCallbacks(callbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    @NotNull
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks generateCallbacks() {
        return new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull @NotNull PhoneAuthCredential phoneAuthCredential) {
                Log.d(TAG, "Verification success");
                disableView(phoneEditText);
                progress.setVisibility(View.GONE);
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {
                Log.d(TAG, "verificationFailed: " + e.getMessage());
                if (e instanceof FirebaseNetworkException)
                    Toast.makeText(LoginActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                Toast.makeText(LoginActivity.this, "Failure: " + e.getMessage(), Toast.LENGTH_LONG).show();
                progress.setVisibility(View.GONE);
                enableView(phoneEditText);
                enableView(submitButton);
            }

            @Override
            public void onCodeSent(@NonNull @NotNull String verificationId, @NonNull @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(verificationId, forceResendingToken);

                codeSent = true;
                disableView(phoneEditText);
                otpEditText.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                enableView(submitButton);
                LoginActivity.this.verificationId = verificationId;
            }
        };
    }

    private void enableView(View view) {
        if (view instanceof EditText) {
            view.setFocusableInTouchMode(true);
        } else if (view instanceof Button) {
            view.setEnabled(true);
            view.setClickable(true);
        } else {
            view.setEnabled(true);
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnSuccessListener(authResult -> {
                    Log.d(TAG, "Verification success");
                    progress.setVisibility(View.GONE);
                    disableView(otpEditText);
                    disableView(phoneEditText);
                    disableView(submitButton);
                    FirebaseUser user = authResult.getUser();
                    if (user != null) {
                        String uid = user.getUid();
                        String phone = user.getPhoneNumber();
                        Utils.setPhoneNo(getApplication(), phone);
                        Utils.setUid(getApplication(), uid);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Log.w(TAG, "signInWithPhoneAuthCredential", new NullPointerException("User is null"));
                    }

                })
                .addOnFailureListener(e -> {
                    Log.d(TAG, "VerificationFailed: " + e.getMessage());
                    enableView(submitButton);
                    disableView(phoneEditText);
                    enableView(otpEditText);
                    progress.setVisibility(View.GONE);
                });
    }

    private void disableView(View view) {
        if (view instanceof EditText) {
            view.setFocusable(false);
        } else if (view instanceof Button) {
            view.setEnabled(false);
            view.setClickable(false);
        } else {
            view.setEnabled(false);
        }
    }

    @Nullable
    private String validateEditText(EditText editText, int length) {
        if (editText.getText().toString().trim().isEmpty() || (editText.getText().length() == 0)) {
            return null;
        } else if (editText.getText().toString().trim().length() == length) {
            return editText.getText().toString().trim();
        } else {
            return null;
        }
    }

    private void initViews() {
        phoneEditText = findViewById(R.id.login_phone_edit_text);
        otpEditText = findViewById(R.id.login_otp_edit_text);
        progress = findViewById(R.id.login_progress);
        submitButton = findViewById(R.id.login_submit_btn);
    }
}