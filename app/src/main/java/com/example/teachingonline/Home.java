package com.example.teachingonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

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

public class Home extends AppCompatActivity {
    String JSON_STRING;
    TextView homename;
    String namebyemail;
    TextView seeall;
    GridView gridView;
    ArrayList<String> category;
    ArrayList<String> course;
    ArrayAdapter<String> adapt;
    AutoCompleteTextView autocomplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        category = new ArrayList();
        category.add("Web Development");
        category.add("Marketing");
        category.add("Business");
        category.add("Mobile Development");
        gridView = findViewById(R.id.grid);
        Bundle getemail = this.getIntent().getExtras();
        namebyemail = getemail.getString("passemail");
        seeall = findViewById(R.id.seeall);
        homename = findViewById(R.id.homename);
        autocomplete = findViewById(R.id.autocomplete);
        course = new ArrayList<>();
        new getcourses().execute();

        //  adapt = new ArrayAdapter(this, android.R.layout.simple_list_item_1, course);
        //  autocomplete.setAdapter(adapt);
        seeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AllCategories.class);
                intent.putExtra("useremail", namebyemail);
                intent.putExtra("username", homename.getText().toString());
                startActivity(intent);


            }
        });
        //   homename = findViewById(R.id.homename);
        new getNameByemail().execute();
        adaptcategory adapter = new adaptcategory(this, category);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Category Chosen: " + category.get(position), Toast.LENGTH_SHORT).show();
                Intent ntentcategory = new Intent(Home.this, category_course.class);
                ntentcategory.putExtra("category", category.get(position));
                startActivity(ntentcategory);
            }
        });
        autocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent autocompletecourse = new Intent(Home.this, course_data.class);
                autocompletecourse.putExtra("coursenames", course.get(position));
                startActivity(autocompletecourse);
            }
        });

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.homemenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
       switch(item.getItemId()){
           case R.id.editProfileMenu:
               Intent editstudentprofile = new Intent(Home.this, edit_student_profile.class);
               editstudentprofile.putExtra("studentemail", namebyemail);
               startActivity(editstudentprofile);
               return true;
           case R.id.viewCoursesMenu:
               Intent viewenrolledstudents = new Intent(Home.this, view_enrolled_courses.class);
               viewenrolledstudents.putExtra("studentemail", namebyemail);
               startActivity(viewenrolledstudents);
               return true;
           case R.id.paymentDetailMenu:
               Intent viewpaymentdetails = new Intent(Home.this, view_payment_history.class);
               viewpaymentdetails.putExtra("studentemail", namebyemail);
               startActivity(viewpaymentdetails);
               return true;
       }
       return(super.onOptionsItemSelected(item));
    }











    public void search(View v) {
        autocomplete.setVisibility(View.VISIBLE);
    }


    class getNameByemail extends AsyncTask<Void, Void, String> {
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
            JSONObject js = null;
            try {
                js = new JSONObject(unused);
                JSONArray ja = js.getJSONArray("course_data");

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    if (jo.getString("email").equals(namebyemail)) {
                        homename.setText(jo.getString("name"));
                        break;
                    }


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public class getcourses extends AsyncTask<Void, Void, String> {
        String json_url;


        @Override
        protected void onPreExecute() {
            json_url = "http://192.168.1.11/Onlineteaching/getcourses.php";
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
            JSONObject js = null;
            try {
                js = new JSONObject(unused);
                JSONArray ja = js.getJSONArray("course_name");

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    String name = jo.getString("name");
                    course.add(name);
                    adapt = new ArrayAdapter<String>(Home.this, android.R.layout.simple_dropdown_item_1line, course);
                    autocomplete.setAdapter(adapt);


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }
}