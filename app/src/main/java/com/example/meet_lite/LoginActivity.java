package com.example.meet_lite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText emaillogin, passwordlogin;
    String emailstr, passwordstr;
    private Button login,createacc;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emaillogin = findViewById(R.id.emaillogin);
        passwordlogin = findViewById(R.id.passwordlogin);
        login = findViewById(R.id.login);
        firebaseAuth=FirebaseAuth.getInstance();
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
        firebaseAuth.signInWithEmailAndPassword(emailstr,passwordstr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                    Toast.makeText(LoginActivity.this,"Logged In",Toast.LENGTH_LONG).show();
           else
               Toast.makeText(LoginActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
//        if (passwordtxt.length() < 6) {
//            password.setError("Password should atleast 6 char long");
//            password.requestFocus();
//            return;
//        } else {
//             RetroWork();
//        }
    }
}