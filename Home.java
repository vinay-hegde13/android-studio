package com.shashank.platform.classroomappui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    TextView course;
    TextView announcement;
    TextView view_daily_schedules;

    EditText title, content, time;
    EditText course_name, prof_name, date, room_no, from, to;

    LinearLayout profile;
    FloatingActionButton plus;
    LayoutInflater popup_inflater;
    dailySchedule ds;
    PopupWindow popupWindow, popupWindow1, popupWindow2;

    CustomAdapterForDailySchedules customAdapterForDailySchedules;
    CustomAdapterForAnnouncements customAdapterForAnnouncements;

    DBHelper dbH;
    RecyclerView recyclerView, recyclerView1;
    View v, select, announ, sched;

    public ArrayList<dailySchedule> dailyScheduleArrayList;
    Intent intent;
    boolean isStudent=true;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        intent = getIntent();
        isStudent  = intent.getBooleanExtra("isStudent",true);

        FloatingActionButton fab = findViewById(R.id.fab);
        plus = findViewById(R.id.plus);
        view_daily_schedules = findViewById(R.id.view_all_daily_schedules);
        dailyScheduleArrayList = new ArrayList<>();
        dbH = new DBHelper(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Write your needs to Admin", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        profile = header.findViewById(R.id.profile);
        profile.setOnClickListener(this);
        course = findViewById(R.id.view_course);
        announcement = findViewById(R.id.view_announce);
        course.setOnClickListener(this);
        announcement.setOnClickListener(this);
        popup_inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        customAdapterForDailySchedules = new CustomAdapterForDailySchedules(getApplicationContext(), dbH.getAllDailySchedules(), findViewById(R.id.recycler_view_daily_schedule));
        recyclerView = findViewById(R.id.recycler_view_daily_schedule);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(customAdapterForDailySchedules);

        customAdapterForAnnouncements = new CustomAdapterForAnnouncements(dbH.getAllAnnouncements(), getApplicationContext(),isStudent,dbH);
        recyclerView1=findViewById(R.id.recycler_view_announcements);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView1.setAdapter(customAdapterForAnnouncements);



        if(!isStudent) {
            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final View popup = popup_inflater.inflate(R.layout.plus_popup_window, null);
                    popupWindow = new PopupWindow(popup, 800, 650, true);
                    popupWindow.showAtLocation(findViewById(R.id.home), Gravity.CENTER_HORIZONTAL, -700, 900);
//                    popupWindow1.showAsDropDown(plus);
                    select = popupWindow.getContentView();
                    select.findViewById(R.id.add_an_announcement).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                            View popup = popup_inflater.inflate(R.layout.popupforannouncement, null);
                            popupWindow1 = new PopupWindow(popup, 1000, 1500, true);
//                            popupWindow1.showAtLocation(findViewById(R.id.home), Gravity.BOTTOM, -500, -500);
                            popupWindow1.showAsDropDown(plus);
                            announ = popupWindow1.getContentView();

                            title = announ.findViewById(R.id.title_input_for_announcement);
                            content = announ.findViewById(R.id.content_input_for_announcement);
                            time = announ.findViewById(R.id.time_input_for_announcement);
                            Button b = announ.findViewById(R.id.add_an_announcement_for_db);
                            b.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dbH.insertAnnouncement(title.getText().toString(), content.getText().toString(), time.getText().toString());
                                    recreate();
                                    popupWindow1.dismiss();
                                }
                            });
                        }
                    });

                    select.findViewById(R.id.add_daily_schedule).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                            View popup = popup_inflater.inflate(R.layout.daily_schedule_info1, null);
                            popupWindow2 = new PopupWindow(popup, 1000, 2500, true);
                            popupWindow2.showAtLocation(findViewById(R.id.home), Gravity.CENTER_HORIZONTAL, 0, 0);
                            sched = popupWindow2.getContentView();
                            course_name = sched.findViewById(R.id.input_course_info);
                            prof_name = sched.findViewById(R.id.prof_name_text_input);
                            date = sched.findViewById(R.id.date_input_);
                            room_no = sched.findViewById(R.id.room_no_input);
                            from = sched.findViewById(R.id.from_input);
                            to = sched.findViewById(R.id.to_input);
                            ds = new dailySchedule(course_name.getText().toString(), room_no.getText().toString(), date.getText().toString(),
                                    from.getText().toString() + "-" + to.getText().toString(), prof_name.getText().toString());
                            sched.findViewById(R.id.prof_name_text_input);
//                        dailyScheduleArrayList.add(ds);
                            sched.findViewById(R.id.add_a_schedule).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        dbH.insertDailySchedule(prof_name.getText().toString(), course_name.getText().toString(), from.getText().toString() + "-" + to.getText().toString(),
                                                date.getText().toString(), room_no.getText().toString());
                                        recreate();
                                        Toast.makeText(Home.this, "Added", Toast.LENGTH_SHORT).show();
                                        popupWindow2.dismiss();
                                    } catch (Exception e) {
                                        Toast.makeText(Home.this, "Daily Schedule Not Added", Toast.LENGTH_SHORT).show();
                                        popupWindow2.dismiss();
                                    }
                                }
                            });

                        }
                    });

                }


            });



        }else{
            plus.setVisibility(View.GONE);

        }
        view_daily_schedules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent open_daily_schedules = new Intent(getApplicationContext(), dailySchedulesView.class);
                startActivity(open_daily_schedules);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_courses) {
            Intent intent = new Intent(getApplicationContext(), Courses.class);
            startActivity(intent);
        } else if (id == R.id.nav_events) {
            Intent intent = new Intent(getApplicationContext(), Events.class);
            startActivity(intent);

        } else if (id == R.id.nav_lectures) {
            Intent intent = new Intent(getApplicationContext(), Lectures.class);
            startActivity(intent);
        } else if (id == R.id.nav_announcements) {
            Intent intent = new Intent(getApplicationContext(), Announcements.class);
            intent.putExtra("isStudent",isStudent);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(getApplicationContext(), Settings.class);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {
            finish();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_rate) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.profile) {
            Intent intent = new Intent(getApplicationContext(), MyProfile.class);
            startActivity(intent);
        } else if (view.getId() == R.id.view_course) {
            Intent intent = new Intent(getApplicationContext(), Courses.class);
            startActivity(intent);
        } else if (view.getId() == R.id.view_announce) {
            Intent intent = new Intent(getApplicationContext(), Announcements.class);
            intent.putExtra("isStudent",isStudent);
            Toast.makeText(this, ""+isStudent, Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
    }
}
