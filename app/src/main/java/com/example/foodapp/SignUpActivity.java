package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    Button btnSU;
    TextView eVRPassSU, eVPassSU,eVEmailSU;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private static final int nid = 1;
    public static final int PRIMARY_FOREGROUND_NOTIF_SERVICE_ID = 1001;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        eVRPassSU = findViewById(R.id.eVRPassSU);
        btnSU = findViewById(R.id.btnSU);
        eVPassSU = findViewById(R.id.eVPassSU);
        eVEmailSU = findViewById(R.id.eVEmailSU);

        btnSU.setOnClickListener(view -> {
            signUp();
            notification();
        });
    }
    private void signUp() {
        String email = eVEmailSU.getText().toString();
        String pass = eVPassSU.getText().toString();
        String repass = eVRPassSU.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Email Is Empty", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(pass)) {
            Toast.makeText(getApplicationContext(), "Password Is Empty", Toast.LENGTH_SHORT).show();
        }else if(pass.equals(repass)){
            auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Sign Up Is Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                        Toast.makeText(getApplicationContext(), "Please Sign In Before Discover My App!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Sign Up Is Not Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpActivity.this, SignUpActivity.class));

                    }
                }
            });
        }
    }
    private void notification() {
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
//
//        Notification notification = new Notification.Builder(this)
//                .setContentTitle("FOOOAPP")
//                .setContentText("Email:  Was Signed Up Successfully")
//                .setSmallIcon(R.drawable.background)
//                .setLargeIcon(bitmap)
//                .build();
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService((Context.NOTIFICATION_SERVICE));
//
//        if(notificationManager !=null){
//            notificationManager.notify(nid,notification);
//        }

        //   }
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String id = "_channel_01";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel mChannel = new NotificationChannel(id, "notification", importance);
            mChannel.enableLights(true);

            Notification notification = new Notification.Builder(getApplicationContext(), id)
                    .setContentTitle("FOODAPP MESSAGE")
                    .setContentText("Sign Up Successfully !!")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setLargeIcon(bitmap)
                    .build();

            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(mChannel);
                mNotificationManager.notify(PRIMARY_FOREGROUND_NOTIF_SERVICE_ID, notification);
            }

        }
    }
    }