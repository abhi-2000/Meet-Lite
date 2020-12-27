package com.example.meet_lite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText emaillogin, passwordlogin;
    String emailstr, passwordstr;
    private Button login,createacc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emaillogin = findViewById(R.id.emaillogin);
        passwordlogin = findViewById(R.id.passwordlogin);
        login = findViewById(R.id.login);
        createacc=findViewById(R.id.createnew);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });
        createacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void LoginUser() {
        emailstr = emaillogin.getText().toString();
        passwordstr = passwordlogin.getText().toString();
        if (emailstr.isEmpty()) {
            emaillogin.setError("Email cannot be empty");
            emaillogin.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailstr).matches()) {
            emaillogin.setError("Please enter a valid email id");
            emaillogin.requestFocus();
            return;
        }
        if (passwordstr.isEmpty()) {
            passwordlogin.setError("Password cannot be empty");
            passwordlogin.requestFocus();
            return;
        }
//        if (passwordtxt.length() < 6) {
//            password.setError("Password should atleast 6 char long");
//            password.requestFocus();
//            return;
//        } else {
//             RetroWork();
//        }
    }
}