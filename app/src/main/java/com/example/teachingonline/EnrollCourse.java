package com.example.teachingonline;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class EnrollCourse extends AppCompatActivity {
    TextView studentNamePay,courseNamePay,pricecoursepay;
    EditText emailEnroll, creditCardNumber,cvv;
    Button enroolButton;
    String coursenameintent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enroll_course);
        studentNamePay = findViewById(R.id.studentNamePay);
        courseNamePay = findViewById(R.id.courseNamePay);
        pricecoursepay = findViewById(R.id.pricecoursepay);
        emailEnroll = findViewById(R.id.emailEnroll);
        creditCardNumber = findViewById(R.id.creditCardNumber);
        cvv = findViewById(R.id.cvv);
        enroolButton = findViewById(R.id.enroolButton);
        Bundle getstudentname = this.getIntent().getExtras();
       studentNamePay.setText(getstudentname.getString("usernamecourse"));
        courseNamePay.setText(getstudentname.getString("coursename"));
       pricecoursepay.setText(getstudentname.getString("courseprice"));
           // coursenameintent = getstudentname.getString("coursename");


        enroolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email, coursename, creditcardnum, cvvcode, studentname, price;
                studentname = studentNamePay.getText().toString();
                coursename = courseNamePay.getText().toString();
                email = emailEnroll.getText().toString();
                creditcardnum  = creditCardNumber.getText().toString();
                cvvcode = cvv.getText().toString();
                price = pricecoursepay.getText().toString();


                if ( !emailEnroll.equals("")  && !creditCardNumber.equals("") && !cvv.equals("") ) {

                    HttpsTrustManager.allowAllSSL();
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[6];
                            field[0] = "namecourse";
                            field[1] = "email";
                            field[2] = "credit";
                            field[3] = "cvv";
                            field[4] = "stname";
                            field[5] = "price";
                            //Creating array for data
                            String[] data = new String[6];
                            data[0] = coursename;
                            data[1] = email;
                            data[2] = creditcardnum;
                            data[3] = cvvcode;
                            data[4] = studentname;
                            data[5] = price;

                            PutData putData = new PutData("http://192.168.1.11/Onlineteaching/enrollcourse.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if(result.equals("You enrolled in this course")){
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        finish();
                                    }else {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                                    }
                                   // Log.i("PutData", result);
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
