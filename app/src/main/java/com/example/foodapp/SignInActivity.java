package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    Button btnSignIn, btnSignUp, btnVerify, btnCheck;
    TextView eVEmailSI, eVPassSI, tVRandom1, tVRandom2, tVTotal, tVPlus;
    boolean checkVerify = false;

//    String SiteKey = "6Ldf5GwfAAAAAJykjVazt8GY7m9TositBTLZs0oh";
//    String SecretKey = "6Ldf5GwfAAAAAECcZBZOCC5TAj0XcuIXuSK52RFC";

    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btnSignIn = findViewById(R.id.btnSignIn);
        eVEmailSI = findViewById(R.id.eVEmailSI);
        eVPassSI = findViewById(R.id.eVPassSI);
        btnSignUp = findViewById(R.id.btnSignUp);
        tVRandom1 = findViewById(R.id.tVRandom1);
        tVRandom2 = findViewById(R.id.tVRandom2);
        tVTotal = findViewById(R.id.tVTotal);
        btnVerify = findViewById(R.id.btnVerify);
        tVPlus = findViewById(R.id.tVPlus);
        btnCheck = findViewById(R.id.btnCheck);

        tVRandom1.setVisibility(View.INVISIBLE);
        tVRandom2.setVisibility(View.INVISIBLE);
        tVTotal.setVisibility(View.INVISIBLE);
        tVPlus.setVisibility(View.INVISIBLE);
        btnCheck.setVisibility(View.INVISIBLE);

        btnCheck.setOnClickListener(view -> {
            checkVerify();
        });

        btnVerify.setOnClickListener(view -> {
            verify();
        });

        btnSignIn.setOnClickListener(view -> {
            SignIn();
        });

        btnSignUp.setOnClickListener(view -> {
            startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
        });


    }

    private void verify() {
        int numberRandom1 = (int) (Math.random() * 50);
        int numberRandom2 = (int) (Math.random() * 50);

        tVRandom1.setVisibility(View.VISIBLE);
        tVRandom2.setVisibility(View.VISIBLE);
        tVTotal.setVisibility(View.VISIBLE);
        tVPlus.setVisibility(View.VISIBLE);
        btnCheck.setVisibility(View.VISIBLE);

        tVRandom1.setText("" + numberRandom1);
        tVRandom2.setText("" + numberRandom2);

    }

    private void checkVerify() {
        int numberRandom1 = Integer.parseInt(String.valueOf(tVRandom1.getText()));
        int numberRandom2 = Integer.parseInt(String.valueOf(tVRandom2.getText()));
        int total = Integer.parseInt(String.valueOf(tVTotal.getText()));
        int sum = numberRandom1 + numberRandom2;
        if (total == sum) {
            tVRandom1.setVisibility(View.INVISIBLE);
            tVRandom2.setVisibility(View.INVISIBLE);
            tVTotal.setVisibility(View.INVISIBLE);
            tVPlus.setVisibility(View.INVISIBLE);
            btnVerify.setVisibility(View.INVISIBLE);
            btnCheck.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "VERIFY SUCCESSFULLY", Toast.LENGTH_SHORT).show();
        } else {
            verify();
            tVTotal.setText("");
            Toast.makeText(this, "YOU MAYBE ARE ROBOT !!!!", Toast.LENGTH_SHORT).show();
        }
        checkVerify = true;
    }

    private void SignIn() {
        String email = eVEmailSI.getText().toString();
        String pass = eVPassSI.getText().toString();
        if (checkVerify) {
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
                        Toast.makeText(getApplicationContext(), "SIGN IN IS SUCCESSFUL", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignInActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "SIGN IN IS NOT SUCCESSFUL", Toast.LENGTH_SHORT).show();
                        eVEmailSI.setText("");
                        eVPassSI.setText("");
                    }
                }
            });
            checkVerify = false;
        } else {
            Toast.makeText(getApplicationContext(), "YOU NEED VERIFY BEFORE ", Toast.LENGTH_SHORT).show();

        }

    }
}
