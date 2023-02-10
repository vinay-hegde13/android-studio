package com.shashank.platform.classroomappui;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.HashMap;


public class LoginScreen extends AppCompatActivity implements View.OnClickListener {
    HashMap<String,String> nameToPassword = new HashMap<>();
    Button signIn;
    EditText pass;
    TextView signUp;
    EditText mail;
    DBHelper dbH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        signIn = findViewById(R.id.sign_in);
        signUp = findViewById(R.id.sign_up);
        signIn.setOnClickListener(this);
        signUp.setOnClickListener(this);

        mail = findViewById(R.id.mail);
        pass = findViewById(R.id.password);
        pass.setOnClickListener(this);

        dbH = new DBHelper(this);
    }

    @Override
    public void onClick(View view) {

        String password = pass.getText().toString();
        String email = mail.getText().toString();
        if (view.getId() == R.id.sign_in) {
            mail = findViewById(R.id.mail);




                if(dbH.containsUser(password,email)){
                    boolean isFaculty = dbH.isFaculty(password,email);
                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    intent.putExtra("isStudent", !isFaculty);
                    startActivity(intent);
                }else
                Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show();

        }
        else if (view.getId() == R.id.sign_up) {
            Intent intent = new Intent(getApplicationContext(), SignupScreen.class);
            startActivity(intent);
        }
    }
}
