package com.example.teachingonline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SectionAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<String> lectureName;

    public SectionAdapter( Context ctx,ArrayList<String> lectureName){

        context = ctx;
        this.lectureName = lectureName;

    }

    @Override
    public int getCount(){

        return lectureName.size();
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
        convertView = LayoutInflater.from(context).inflate(R.layout.section_lecture_item,null);
        if(inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.section_lecture_item, null);
        }

        TextView textview = convertView.findViewById(R.id.lectureNameText);
        textview.setText(lectureName.get(position));
        return convertView;
    }

}

