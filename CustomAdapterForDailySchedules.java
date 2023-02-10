package com.shashank.platform.classroomappui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapterForDailySchedules extends RecyclerView.Adapter<CustomAdapterForDailySchedules.ViewHolder> {
    ArrayList<dailySchedule> dailyScheduleArrayList;
    int id;
    Context context;
    DBHelper dbh;
    View parent;
    EditText e1, e2, e3, e4, e5, e6;
    boolean isStudent;

    public CustomAdapterForDailySchedules(Context c, ArrayList<dailySchedule> ds, View parent) {
        this.dailyScheduleArrayList = ds;
        this.context = c;
        this.parent = parent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View single_row = layoutInflater.inflate(R.layout.row_for_daily_schedule, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(single_row);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int pos) {

//
//
//        @Override
//        public void onBindViewHolder(ViewHolder holder, int position) {
//            final MyListData myListData = listdata[position];
//            holder.textView.setText(listdata[position].getDescription());
//            holder.imageView.setImageResource(listdata[position].getImgId());
//            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(view.getContext(),"click on item: "+myListData.getDescription(),Toast.LENGTH_LONG).show();
//                }
//            });

        final dailySchedule dailySchedule_data = dailyScheduleArrayList.get(pos);
        viewHolder.prof_name.setText(dailySchedule_data.getProfessor_name());
        viewHolder.course_name.setText(dailySchedule_data.getCourseName());
        viewHolder.date_.setText(dailySchedule_data.getDate());
        viewHolder.time.setText(dailySchedule_data.getFrom_to());
        viewHolder.room_no.setText(dailySchedule_data.getClassN0());

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbh = new DBHelper(context);
                int id = dbh.getIdOfSchedule(dailySchedule_data, context);
                dbh.deleteDailySchedule(id, context);
                ArrayList<dailySchedule> newer = dbh.getAllDailySchedules();
                setData(newer);

            }
        });
        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbh = new DBHelper(context);
                id = dbh.getIdOfSchedule(dailySchedule_data, context);

                dailySchedule ds = dailySchedule_data;
                LayoutInflater inflater;
                inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                final View popUp = inflater.inflate(R.layout.daily_schedule_info1, null);
                final PopupWindow popupWindow = new PopupWindow(popUp, 1000, 2500, true);
                popupWindow.showAtLocation(parent, Gravity.CENTER_HORIZONTAL, 0, 0);


                e1 = popUp.findViewById(R.id.prof_name_text_input);
                e2 = popUp.findViewById(R.id.input_course_info);
                e3 = popUp.findViewById(R.id.room_no_input);
                e4 = popUp.findViewById(R.id.date_input_);
                e5 = popUp.findViewById(R.id.from_input);
                e6 = popUp.findViewById(R.id.to_input);

                Button b = popUp.findViewById(R.id.add_a_schedule);
                b.setText(R.string.update_resource);
                e1.setText(ds.getProfessor_name());
                e2.setText(ds.getCourseName());
                e3.setText(ds.getClassN0());
                e4.setText(ds.getDate());
                final String[] frto = ds.getFrom_to().split("-");
                e5.setText(frto[0]);
                if (frto.length > 1) e6.setText(frto[1]);

                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dailySchedule ds = dailySchedule_data;

                        dbh.updateDailySchedule(id,
                                e1.getText().toString(),
                                e2.getText().toString(),
                                e5.getText().toString() + "-" + e6.getText().toString(),
                                e4.getText().toString(),
                                e3.getText().toString());
                        setData(dbh.getAllDailySchedules());
                        popupWindow.dismiss();

                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return dailyScheduleArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageButton edit, delete;
        public TextView prof_name, course_name, room_no, date_, time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.edit = (ImageButton) itemView.findViewById(R.id.edit_row_daily_schedule);
            this.delete = (ImageButton) itemView.findViewById(R.id.delete_row_daily_schedule);
            this.prof_name = itemView.findViewById(R.id.prof_name);
            this.course_name = itemView.findViewById(R.id.course_name);
            this.room_no = itemView.findViewById(R.id.room_no);
            this.date_ = itemView.findViewById(R.id.date);
            this.time = itemView.findViewById(R.id.from_to_time);


        }


//        public static class ViewHolder extends RecyclerView.ViewHolder {
//            public ImageView imageView;
//            public TextView textView;
//            public RelativeLayout relativeLayout;
//            public ViewHolder(View itemView) {
//                super(itemView);
//                this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
//                this.textView = (TextView) itemView.findViewById(R.id.textView);
//                relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
//            }
//        }
    }


    public void setData(ArrayList<dailySchedule> ds) {
        this.dailyScheduleArrayList.clear();
        dailyScheduleArrayList.addAll(ds);
        notifyDataSetChanged();
    }


    // parent activity will implement this method to respond to click events


}
