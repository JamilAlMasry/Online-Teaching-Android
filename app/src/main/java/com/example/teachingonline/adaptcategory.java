package com.example.teachingonline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class adaptcategory extends BaseAdapter {

        private Context context;
        private LayoutInflater inflater;
        private ArrayList<String> categoryNames;

        public adaptcategory( Context ctx,ArrayList<String> categoryNames){

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
            convertView = LayoutInflater.from(context).inflate(R.layout.gridviewhome,null);
            if(inflater == null) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.gridviewhome, null);
            }

            TextView textview = convertView.findViewById(R.id.view);
            textview.setText(categoryNames.get(position));
            return convertView;
        }


    }


