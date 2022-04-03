package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.Executor;

public class SignInActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks {
    Button btnSignIn, btnSignUp;
    TextView eVEmailSI, eVPassSI;
    CheckBox cbRobot;
    GoogleApiClient googleApiClient;
    String SiteKey = "6LdjfT4fAAAAACULNiQCdZdlfMhymD_By8gjrLYz";// cần đăng kí tên pj tại đường link sau
    // https://www.google.com/recaptcha/admin/create
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btnSignIn = findViewById(R.id.btnSignIn);
        eVEmailSI = findViewById(R.id.eVEmailSI);
        eVPassSI = findViewById(R.id.eVPassSI);
        btnSignUp = findViewById(R.id.btnSignUp);
        cbRobot = findViewById(R.id.cbRobot);

//        btnSignIn.setVisibility(View.INVISIBLE);
        btnSignIn.setOnClickListener(view -> {
            SignIn();
        });
        btnSignUp.setOnClickListener(view -> {
            startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
        });
        Log.d("abc", "55");
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(SafetyNet.API)
                .addConnectionCallbacks(SignInActivity.this)
                .build();

        googleApiClient.connect();

        cbRobot.setOnClickListener(view -> {
            if (cbRobot.isChecked()) {
                Log.d("abc", "" + 67);
                SafetyNet.getClient(this).verifyWithRecaptcha(SiteKey)
                        .addOnSuccessListener((Executor) this,
                                new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                                    @Override
                                    public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
                                        // Indicates communication with reCAPTCHA service was
                                        // successful.
                                        String userResponseToken = response.getTokenResult();
                                        if (!userResponseToken.isEmpty()) {
                                            // Validate the user response token using the
                                            // reCAPTCHA siteverify API.
                                        }
                                    }
                                })
                        .addOnFailureListener((Executor) this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (e instanceof ApiException) {
                                    // An error occurred when communicating with the
                                    // reCAPTCHA service. Refer to the status code to
                                    // handle the error appropriately.
                                    ApiException apiException = (ApiException) e;
                                    int statusCode = apiException.getStatusCode();
                                    Log.d("abc", "Error: " + CommonStatusCodes
                                            .getStatusCodeString(statusCode));
                                } else {
                                    // A different, unknown type of error occurred.
                                    Log.d("abc", "Error: " + e.getMessage());
                                }
                            }
                        });
            } else {
                cbRobot.setTextColor(Color.RED);
            }
        });
    }
    private void SignIn() {
        String email = eVEmailSI.getText().toString();
        String pass = eVPassSI.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Email Is Empty", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(getApplicationContext(), "Password Is Empty", Toast.LENGTH_SHORT).show();
        }

        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Sign In Is Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignInActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Sign In Is Not Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignInActivity.this, SignInActivity.class));

                }
            }
        });

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}