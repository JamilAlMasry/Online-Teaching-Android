package com.example.teachingonline;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


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
import java.util.ArrayList;
import java.util.HashMap;

public class manage_course extends AppCompatActivity {
    String JSON_STRING;
    ArrayList<String> coursesNames;
    LectureAdapter adapter;
    private Context context;
    ListView listView;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_course);
        coursesNames = new ArrayList<String>();

        Bundle getemail = this.getIntent().getExtras();
        email = getemail.getString("instemail");

        listView = findViewById(R.id.manageCourseList);

        new getCourses().execute();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(manage_course.this, Lecture.class);
                i.putExtra("coursename", coursesNames.get(position));
                i.putExtra("email", email);
                startActivity(i);
            }
        });


    }

    class getCourses extends AsyncTask<Void, Void, String> {
        String json_url;

        @Override
        protected void onPreExecute() {
            json_url = "http://192.168.1.11/Onlineteaching/getinstcourses.php";
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
            coursesNames.clear();
            try {
                js = new JSONObject(unused);
                JSONArray ja = js.getJSONArray("course_data");
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    if(jo.getString("email").equals(email)){
                        String name = jo.getString("name");
                        coursesNames.add(name);
                        adapter = new LectureAdapter(manage_course.this, coursesNames);
                        listView.setAdapter(adapter);
                    }

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
