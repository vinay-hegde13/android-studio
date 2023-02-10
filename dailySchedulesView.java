package com.shashank.platform.classroomappui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class dailySchedulesView extends AppCompatActivity {
   CustomAdapterForDailySchedule customAdapterForDailySchedule;
   CustomAdapterForDailySchedules customAdapterForDailySchedules;
   DBHelper mydb;
   RecyclerView recyclerView;
   ListView ds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_schedules_view);
        mydb = new DBHelper(this);

        ArrayList<dailySchedule> courses;
               courses =  mydb.getAllDailySchedules();

//        ArrayList<dailySchedule> complete = new ArrayList<>();
//        for(int i = 0;i<courses.size();i++){
////            complete.add(new dailySchedule(courses.get(i),Integer.toString(220+i),"3/2/2002","10:30-2:30","Rashmi R"));
//
//        }
        for(int i = 0;i<courses.size();i++) Toast.makeText(this, "courses"+courses.get(i).getCourseName(), Toast.LENGTH_SHORT).show();
        customAdapterForDailySchedule = new CustomAdapterForDailySchedule(getApplicationContext(),courses);
        customAdapterForDailySchedules = new CustomAdapterForDailySchedules(getApplicationContext(),courses,findViewById(R.id.linear_Layout_for_daily_schedules));
        recyclerView = findViewById(R.id.linear_Layout_for_daily_schedules);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        ds = findViewById(R.id.linear_Layout_for_daily_schedules);
//        ds.setAdapter(customAdapterForDailySchedule);
        recyclerView.setAdapter(customAdapterForDailySchedules);

    }



}
