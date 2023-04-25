package com.example.teachingonline;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class course_data extends AppCompatActivity {
    String JSON_STRING;
String coursename, usernamecourse="", useremailcourse="", nameusercourse="", emailusercourse, namecourse;
TextView detailCourseName, detailDesc, detailReq, detailOrgPrice, detailSalePrice, coursedatainst, coursedatalang;
Button enrollCourseButton, contentCourseButton;
TextView detailcoursenames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coursedata);
        Bundle getcoursename = this.getIntent().getExtras();

        usernamecourse = getcoursename.getString("usernamelist");
        useremailcourse = getcoursename.getString("useremaillist");
        nameusercourse = usernamecourse;
        emailusercourse = useremailcourse;
        coursename = getcoursename.getString("coursename");
        detailcoursenames = findViewById(R.id.detailCourseNames);
        Bundle getcoursenames = this.getIntent().getExtras();
        detailcoursenames.setText(getcoursenames.getString("coursenames"));
        namecourse = coursename;
        detailCourseName = findViewById(R.id.detailCourseName);
        detailCourseName.setText(namecourse);
        detailDesc = findViewById(R.id.detailDesc);
        detailReq = findViewById(R.id.detailReq);
        coursedatainst = findViewById(R.id.coursedatainst);
        coursedatalang = findViewById(R.id.coursedatalang);
        detailOrgPrice = findViewById(R.id.detailOrgPrice);
      //  detailSalePrice = findViewById(R.id.detailSalePrice);
        enrollCourseButton = findViewById(R.id.enrollCourseButton);
        contentCourseButton = findViewById(R.id.contentCourseButton);
        new getcoursedata().execute();
        new checkenroll().execute();
        enrollCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent enrollintent = new Intent(course_data.this, EnrollCourse.class);
                enrollintent.putExtra("coursename", coursename);
                enrollintent.putExtra("courseprice", detailOrgPrice.getText().toString() );
                enrollintent.putExtra("usernamecourse", nameusercourse);
                startActivity(enrollintent);
            }
        });
        contentCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coursecontent = new Intent(course_data.this, course_content.class);
                coursecontent.putExtra("crsname", coursename);
                startActivity(coursecontent);
            }
        });
    }

    class getcoursedata extends AsyncTask<Void, Void, String> {
        String json_url;

        @Override
        protected void onPreExecute() {
            json_url = "http://192.168.1.11/Onlineteaching/coursedata.php";
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
                while ((JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_STRING + "" +
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
            JSONObject js;

            try {
                js = new JSONObject(unused);
                JSONArray ja = js.getJSONArray("course_data");

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    if(jo.getString("name").equals(coursename) || jo.getString("name").equals(detailcoursenames.getText().toString())){
                        detailDesc.setText(jo.getString("description"));
                        detailReq.setText(jo.getString("requirement"));
                        detailOrgPrice.setText(jo.getString("orgprice"));
                       // detailSalePrice.setText(jo.getString("saleprice"));
                        coursedatainst.setText(jo.getString("instructor"));
                        coursedatalang.setText(jo.getString("language"));
                    }

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    class checkenroll extends AsyncTask<Void, Void,String> {
        String json_url;

        @Override
        protected void onPreExecute() {
            json_url = "http://192.168.1.11/Onlineteaching/checkenroll.php";
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
                while ((JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_STRING + "" +
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
            JSONObject js;

            try {
                js = new JSONObject(unused);
                JSONArray ja = js.getJSONArray("enroll_data");

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    if(jo.getString("email").equals(useremailcourse) && (jo.getString("cname").equals(coursename) || jo.getString("cname").equals(detailcoursenames.getText().toString()))){
                        enrollCourseButton.setVisibility(View.INVISIBLE);
                        contentCourseButton.setVisibility(View.VISIBLE);

                    }

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
