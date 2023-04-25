package com.example.teachingonline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class course_adapter extends BaseAdapter {
        private  Context context;
        private LayoutInflater inflater;
        private ArrayList<courses> categoryNames;

        public course_adapter( Context ctx,ArrayList<courses> categoryNames){

            context = ctx;
            this.categoryNames = categoryNames;

        }

        @Override
        public int getCount(){

            return categoryNames.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.course_item,null);
            if(inflater == null) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.course_item, null);
            }
            courses course = this.categoryNames.get(position);
            TextView coursename = convertView.findViewById(R.id.courseNameText);
            coursename.setText(course.getName());
            TextView courseprice = convertView.findViewById(R.id.orgPriceText);
            courseprice.setText(course.getOrgprice() + "$");

            return convertView;
        }


    }
   /* List list = new ArrayList();
    public course_adapter( Context context, int resource) {
        super(context, resource);
    }

    public void add( courses object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        View row;
        row = convertView;
        coursesHolder coursesHolder;
        if (row == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.course_item,parent,false);
            coursesHolder = new coursesHolder();
            coursesHolder.tx_name = row.findViewById(R.id.courseNameText);
            coursesHolder.tx_orgprice = row.findViewById(R.id.orgPriceText);
            row.setTag(coursesHolder);

        }
        else {
            coursesHolder = (coursesHolder) row.getTag();

        }
        courses course = (courses) this.getItem(position);
        coursesHolder.tx_name.setText(courses.getName());
        coursesHolder.tx_orgprice.setText(courses.getOrgprice());


        return row;
    }
    static class coursesHolder {
        TextView tx_name, tx_orgprice;
    } */

