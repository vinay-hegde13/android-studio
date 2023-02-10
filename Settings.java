package com.shashank.platform.classroomappui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class Settings extends AppCompatActivity {

    ListView lst;
    String name[] = {"Student Alert System", "Done By", "Refer this App To Your Friend", "Email Your Feedback", "Report a bug"};
    String num[] = {"Version 2.0", "Vedha, Prajwal", "Share this app with friends", "Tell me your suggestions", "Tell me if you found any problem"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        lst = findViewById(R.id.listView);
        CustomAdapterSetting adapter = new CustomAdapterSetting(this, name, num);
        lst.setAdapter(adapter);
    }
}
