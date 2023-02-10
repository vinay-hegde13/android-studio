package com.shashank.platform.classroomappui;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapterForDailySchedule extends BaseAdapter {
    private Context context;
    private ArrayList<dailySchedule> dailyScheduleArrayList;
    private TextView date,from_to,prof,course,room_no;
    ImageButton delete;
    ImageButton edit;
    int position;
    DBHelper dbHelper;
    ListView lv ;
    int id;
    LayoutInflater inflater;
    dailySchedule ds;
    public CustomAdapterForDailySchedule(Context context, ArrayList<dailySchedule> arrayList) {

        this.context = context;
        this.dailyScheduleArrayList = arrayList;

    }

    @Override
    public int getCount() {
        return dailyScheduleArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int pos, View view, ViewGroup parent) {
       view  = LayoutInflater.from(context).inflate(R.layout.row_for_daily_schedule,parent,false);
       course = view.findViewById(R.id.course_name);
       prof = view.findViewById(R.id.prof_name);
       date = view.findViewById(R.id.date);
       from_to = view.findViewById(R.id.from_to_time);
       room_no = view.findViewById(R.id.room_no);
       delete = view.findViewById(R.id.delete_row_daily_schedule);
       edit  = view.findViewById(R.id.edit_row_daily_schedule);
       lv = (ListView) view.getParent();

        position = pos;
       delete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               dbHelper = new DBHelper(context);

               int id = dbHelper.getIdOfSchedule(dailyScheduleArrayList.get(position),context);
               dbHelper.deleteDailySchedule(id,context);
               CustomAdapterForDailySchedule.this.notifyDataSetChanged();
              notifyDataSetChanged();


           }
       });
       edit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               dbHelper = new DBHelper(context);
               ds = dailyScheduleArrayList.get(position);
               inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
               final View popUp = inflater.inflate(R.layout.daily_schedule_info1,null);
               final PopupWindow popupWindow = new PopupWindow(popUp,1000,1500,true);
               popupWindow.showAtLocation(view.findViewById(R.id.linear_Layout_for_daily_schedules), Gravity.CENTER_HORIZONTAL,0,0);
               id = dbHelper.getIdOfSchedule(ds,context );

               EditText e1 =popUp.findViewById(R.id.prof_name_text_input);
               EditText e2 = popUp.findViewById(R.id.input_course_info);
               EditText e3 = popUp.findViewById(R.id.room_no_input);
               EditText e4 = popUp.findViewById(R.id.date_input_);
               EditText e5 = popUp.findViewById(R.id.from_input);
               EditText e6 = popUp.findViewById(R.id.to_input);

               Button b = popUp.findViewById(R.id.add_a_schedule);
               b.setText(R.string.update_resource);
               e1.setText(ds.getProfessor_name());
               e2.setText(ds.getCourseName());
               e3.setText(ds.getClassN0());
               e4.setText(ds.getDate());
               e5.setText(ds.getFrom_to().split("-")[0]);
               e6.setText(ds.getFrom_to().split("-")[1]);
               b.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       dbHelper.updateDailySchedule(id,ds.getProfessor_name(),ds.getCourseName(),ds.getFrom_to(),ds.getDate(),ds.getClassN0());
popupWindow.dismiss();
                   }
               });

           }


       });
       course.setText(dailyScheduleArrayList.get(pos).getCourseName());
       prof.setText(dailyScheduleArrayList.get(pos).getProfessor_name());
       from_to.setText(dailyScheduleArrayList.get(pos).getFrom_to());
       date.setText(dailyScheduleArrayList.get(pos).getDate());
       room_no.setText(dailyScheduleArrayList.get(pos).getClassN0());

//       return view;

         return view;
    }
}
