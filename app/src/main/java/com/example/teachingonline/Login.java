package com.example.teachingonline;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Login extends AppCompatActivity {

    TextView stRegisterText;
    TextInputEditText textInputEditTextPassword,textInputEditTextEmail;
    Button loginBtn;
    TextView instRegText;
    Spinner spin;
    String[] role = {"Instructor", "Student"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        stRegisterText = findViewById(R.id.studentRegisterText);
        spin = findViewById(R.id.spinnerLog);
        textInputEditTextEmail = findViewById(R.id.email);
        textInputEditTextPassword = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginButton);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, role);
        spin.setAdapter(adapter);

        stRegisterText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StudentSignUp.class);
                startActivity(intent);
                finish();
                    
                }
        });

        instRegText = findViewById(R.id.instructorRegisterText);
        instRegText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InstructorSignUp.class);
                startActivity(intent);
                finish();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = spin.getSelectedItemPosition();
                HttpsTrustManager.allowAllSSL();
                String password,email;
                password = String.valueOf(textInputEditTextPassword.getText());
                email = String.valueOf(textInputEditTextEmail.getText());

                if ( !textInputEditTextPassword.equals("") && !textInputEditTextEmail.equals("") ) {
                    if(role[index] == "Instructor") {
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //Starting Write and Read data with URL
                                //Creating array for parameters
                                String[] field = new String[2];
                                field[0] = "email";
                                field[1] = "password";
                                //Creating array for data
                                String[] data = new String[2];
                                data[0] = email;
                                data[1] = password;
                                PutData putData = new PutData("http://192.168.1.11/Onlineteaching/login2.php", "POST", field, data);
                                if (putData.startPut()) {
                                    if (putData.onComplete()) {
                                        String result = putData.getResult();
                                        if (result.equals("Login Success")) {
                                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                                            intent.putExtra("passemail", email);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                                        }
                                        Log.i("PutData", result);
                                    }
                                }
                            }
                        });
                    }else if (role[index] == "Student"){
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //Starting Write and Read data with URL
                                //Creating array for parameters
                                String[] field = new String[2];
                                field[0] = "email";
                                field[1] = "password";
                                //Creating array for data
                                String[] data = new String[2];
                                data[0] = email;
                                data[1] = password;
                                PutData putData = new PutData("http://192.168.1.11/Onlineteaching/login1.php", "POST", field, data);
                                if (putData.startPut()) {
                                    if (putData.onComplete()) {
                                        String result = putData.getResult();
                                        if (result.equals("Login Success")) {
                                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), Home.class);
                                            intent.putExtra("passemail", email);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                                        }
                                        Log.i("PutData", result);
                                    }
                                }
                            }
                        });
                    }

                }else {
                    Toast.makeText(getApplicationContext(), "all fields are required" , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}