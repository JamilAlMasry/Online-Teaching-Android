package com.example.teachingonline;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.teachingonline.R;

import java.util.ArrayList;

public class LectureAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<String> courses;

    public LectureAdapter( Context ctx,ArrayList<String> courses){

        context = ctx;
        this.courses = courses;

    }

    @Override
    public int getCount(){

        return courses.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.manage_course_item,null);
        if(inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.manage_course_item, null);
        }

        TextView textview = convertView.findViewById(R.id.courseNameText);
        textview.setText(courses.get(position));
        return convertView;
    }


}
