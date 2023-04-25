package com.example.teachingonline;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import java.util.List;

public class category_course extends AppCompatActivity {
    String JSON_STRING;
    private Context context;
    ListView listView;
    TextView categorychosen, categorychosenfromhome;
    course_adapter adapter;
    ArrayList courseslist;
    String usernamelist, useremaillist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_course);
        categorychosen = findViewById(R.id.categorychosen);
        listView = findViewById(R.id.courseListView);
        categorychosenfromhome =findViewById(R.id.categorychosenfromhome);
        courseslist = new ArrayList();

        Bundle getcategory = this.getIntent().getExtras();
        categorychosen.setText(getcategory.getString("categoryname"));
        usernamelist = getcategory.getString("usernamecat");
        useremaillist = getcategory.getString("useremailcat");
        new getcategorycourse().execute();
        Bundle categoire = this.getIntent().getExtras();
       categorychosenfromhome.setText(categoire.getString("category"));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               courses course = (courses) courseslist.get(position);
                Toast.makeText(getApplicationContext(), "Course " + course.getName(), Toast.LENGTH_SHORT).show();
                Intent courseintent = new Intent(category_course.this, course_data.class);
                courseintent.putExtra("coursename", course.getName());
                courseintent.putExtra("usernamelist", usernamelist);
                courseintent.putExtra("useremaillist", useremaillist);
                startActivity(courseintent);
            }
        });





    }

    class getcategorycourse extends AsyncTask<Void, Void, String> {
        String json_url;

        @Override
        protected void onPreExecute() {
            json_url = "http://192.168.1.11/Onlineteaching/getcategorycourse.php";
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
                    String name = jo.getString("name");
                    String orgprice = jo.getString("orgprice");
                    String category = jo.getString("categoryname");
                    if(category.equals(categorychosen.getText().toString() ) || category.equals(categorychosenfromhome.getText().toString())){
                        courses courses = new courses(name,orgprice);
                        courseslist.add(courses);
                        adapter = new course_adapter(category_course.this,courseslist);
                        listView.setAdapter(adapter);



                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
