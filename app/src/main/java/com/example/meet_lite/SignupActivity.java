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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {

    FirebaseAuth auth;
    EditText name,password,email;
    String emailtxt="",passwordtxt="",nametxt="";
    Button create, alreadyhaveacc;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name=findViewById(R.id.name);
        password=findViewById(R.id.password);
        email=findViewById(R.id.email);

        create = findViewById(R.id.create);
        alreadyhaveacc = findViewById(R.id.alreadyhaveacc);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });
        alreadyhaveacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void LoginUser() {
        nametxt = name.getText().toString();
        emailtxt = email.getText().toString();
        passwordtxt = password.getText().toString();
        if (nametxt.isEmpty()) {
            name.setError("Name cannot be empty");
            name.requestFocus();
            return;
        }
        if (emailtxt.isEmpty()) {
            email.setError("Email cannot be empty");
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailtxt).matches()) {
            email.setError("Please enter a valid email id");
            email.requestFocus();
            return;
        }
        if (passwordtxt.isEmpty()) {
            password.setError("Password cannot be empty");
            password.requestFocus();
            return;
        }
        if (passwordtxt.length() < 6) {
            password.setError("Password should atleast 6 char long");
            password.requestFocus();
            return;
        }
        auth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        auth.createUserWithEmailAndPassword(emailtxt,passwordtxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(SignupActivity.this,"Account is created",Toast.LENGTH_LONG).show();
                    User user=new User();
                    user.setName(nametxt);
                    user.setEmail(emailtxt);
                    user.setPass(passwordtxt);
                    firebaseFirestore.collection("Users").document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                        }
                    });
                }
                else
                {
                    Toast.makeText(SignupActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
//        else {
//             RetroWork();
//        }
    }
}