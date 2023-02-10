package com.shashank.platform.classroomappui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignupScreen extends AppCompatActivity implements View.OnClickListener {

    Button signUp;
    TextView signIn;
    EditText name,password,email;
    DBHelper dbH;
    CheckBox isFaculty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);
        signIn = findViewById(R.id.sign_in);
        signIn.setOnClickListener(this);
        signUp = findViewById(R.id.sign_up);
        signUp.setOnClickListener(this);
        name = findViewById(R.id.fullname_enter);
        password = findViewById(R.id.password_enter);
        email = findViewById(R.id.email_enter);
        isFaculty = findViewById(R.id.is_faculty);
        dbH = new DBHelper(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.sign_in){
            finish();
        }
        else if (view.getId() == R.id.sign_up) {
            String nam = name.getText().toString();
            String pass = password.getText().toString();
            String mail = email.getText().toString();
            boolean isFac = isFaculty.isChecked();
            if(nam.length()==0) {
                Toast.makeText(this, "Name cannot be of zero length", Toast.LENGTH_SHORT).show();
            }else if(pass.length()==0){
                Toast.makeText(this, "Password cannot be of zero length", Toast.LENGTH_SHORT).show();
            }else if(mail.length()==0){
                Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
            }
            dbH.insertUser(nam,pass,mail,isFac);

            Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
            intent.putExtra("isStudent",true);
            startActivity(intent);
        }
    }
}
