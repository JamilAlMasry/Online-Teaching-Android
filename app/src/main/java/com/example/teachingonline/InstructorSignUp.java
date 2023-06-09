package com.example.teachingonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class InstructorSignUp extends AppCompatActivity {

    TextView logintxt;
    Button instBtn;
    TextInputEditText textInputEditTextFullname, textInputEditTextPassword,textInputEditTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_sign_up);

        textInputEditTextEmail = findViewById(R.id.email);
        textInputEditTextFullname = findViewById(R.id.fullname);
        textInputEditTextPassword = findViewById(R.id.password);
        logintxt = findViewById(R.id.loginText);
        instBtn = findViewById(R.id.instructorRegisterButton);

        logintxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        instBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullname,password,email;

                fullname = String.valueOf(textInputEditTextFullname.getText()) ;
                password = String.valueOf(textInputEditTextPassword.getText());
                email = String.valueOf(textInputEditTextEmail.getText());

                if ( !textInputEditTextFullname.equals("")  && !textInputEditTextPassword.equals("") && !textInputEditTextEmail.equals("") ) {

                    HttpsTrustManager.allowAllSSL();
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[3];
                            field[0] = "fullname";
                            field[1] = "password";
                            field[2] = "email";
                            //Creating array for data
                            String[] data = new String[3];
                            data[0] = fullname;
                            data[1] = password;
                            data[2] = email;
                            PutData putData = new PutData("http://192.168.1.11/Onlineteaching/instructor.php", "POST", field, data);
                            if (putData.startPut()) {
                                HttpsTrustManager.allowAllSSL();
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if(result.equals("Sign Up Success")){
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), Login.class);
                                        startActivity(intent);
                                        finish();
                                    }else {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                                    }
                                  //  Log.i("PutData", result);
                                }
                            }
                        }
                    });
                }else {
                    Toast.makeText(getApplicationContext(), "all fields are required" , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}