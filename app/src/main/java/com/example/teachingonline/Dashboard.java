package com.example.teachingonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vishnusivadas.advanced_httpurlconnection.FetchData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Dashboard extends AppCompatActivity {
String JSON_STRING;
TextView instructor;
String namebyemail;
Button createCourseButton;
String email;
Button manageCourseButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        instructor = findViewById(R.id.instructorName);
        manageCourseButton = findViewById(R.id.manageCourseButton);
        Bundle getemail = this.getIntent().getExtras();
        namebyemail = getemail.getString("passemail");
        email = namebyemail;
        createCourseButton = findViewById(R.id.createCourseButton);
        createCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CreateCourse.class);
                intent.putExtra("instemail", email);
                startActivity(intent);
            }
        });

        new getinstrucorbyemail().execute();
        manageCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent managecourseintent = new Intent(getApplicationContext(), manage_course.class);
                managecourseintent.putExtra("instemail", email);
                startActivity(managecourseintent);
            }
        });

    }
    public void editprofile(View v){
        Intent intenteditprofile = new Intent(getApplicationContext(),EditInstProfile.class);
        intenteditprofile.putExtra("passmail", email);
        startActivity(intenteditprofile);
    }
    class getinstrucorbyemail extends AsyncTask<Void,Void,String> {
        String json_url;

        @Override
        protected void onPreExecute() {
            json_url = "http://192.168.1.11/Onlineteaching/Getnamebyemail.php";
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while ((JSON_STRING = bufferedReader.readLine()) !=null){
                    stringBuilder.append(JSON_STRING+ "" +
                            "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String unused) {
            JSONObject js = null;
            try {
                js = new JSONObject(unused);
                JSONArray ja = js.getJSONArray("course_data");

                for(int i=0;i<ja.length();i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    if(jo.getString("email").equals(namebyemail)){
                        instructor.setText(jo.getString("name"));
                        break;
                    }


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}