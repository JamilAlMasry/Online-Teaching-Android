package com.example.teachingonline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.example.teachingonline.R;

import java.util.ArrayList;
import java.util.List;

public class Courseadapter extends ArrayAdapter<Course> {

    private static final String TAG = "CourseAdapter";
    private Context mcontext;
    int resources;

    public Courseadapter(Context context, int resource, ArrayList<Course> object) {
        super(context, resource, object);
        mcontext = context;
        this.resources = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        //get the person info
        String name = getItem(position).getCourseName();
        int image = getItem(position).getCoursePicture();
        String price = getItem(position).getOrgPrice();
        //create object
        Course course = new Course(name,image,price);

        LayoutInflater inflater = LayoutInflater.from(mcontext);
        convertView = LayoutInflater.from(mcontext).inflate(R.layout.course_item,null);
        convertView = inflater.inflate(resources,parent,false);

        TextView cname = convertView.findViewById(R.id.courseNameText);
        TextView cprice = convertView.findViewById(R.id.orgPriceText);
        ImageView cpic = convertView.findViewById(R.id.coursePic);

        cname.setText(name);
        cprice.setText(price);
        cpic.setImageResource(image);

        return convertView;
    }
}
