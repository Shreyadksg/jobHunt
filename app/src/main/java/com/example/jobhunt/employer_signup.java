package com.example.jobhunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class employer_signup extends AppCompatActivity {
    TextView LoginTxtViewEmployer;
    private EditText inputUsername,inputPassword,inputEmail,inputConfirmpassword;
    Button btnToSignUp;
    private FirebaseAuth mAuth;
    private ProgressDialog mLoadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_signup);
        LoginTxtViewEmployer=findViewById(R.id.EmployerTextViewLogin);
        inputUsername=findViewById(R.id.EmployerNameEdtTxtSignUp);
        inputEmail=findViewById(R.id.EmployerEmailEdtTxtSignUp);
        inputPassword=findViewById(R.id.EmployerPasswordSignUp);
        inputConfirmpassword=findViewById(R.id.EmployerConfirmPasswordEdtTxtSignUp);
        mAuth= FirebaseAuth.getInstance();
        mLoadingBar=new ProgressDialog(employer_signup.this);

        btnToSignUp=findViewById(R.id.EmployersignUpBtn);
        btnToSignUp.setOnClickListener((v)->{checkCredentials();});
        LoginTxtViewEmployer.setOnClickListener(v->{
            Intent in1 =new Intent(this,employee_login.class);
            startActivity(in1);
        });
    }

    private void checkCredentials() {
        String username = inputUsername.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String confirmpassword = inputConfirmpassword.getText().toString();

        if (username.isEmpty() || username.length() < 5) {
            showError(inputUsername, "Your username is not valid,should be more than 5 letters");
        } else if (email.isEmpty() || !email.contains("@")) {
            showError(inputEmail, "Email is not valid");
        } else if (password.isEmpty() || password.length() < 7) {
            showError(inputPassword, "Password must be 7 characters");
        } else if (confirmpassword.isEmpty() || !confirmpassword.equals(password)) {
            showError(inputConfirmpassword , "password did not match");
        } else {
            mLoadingBar.setTitle("Registration");
            mLoadingBar.setMessage("Please wait");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(employer_signup.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(employer_signup.this, postJobActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(employer_signup.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

        private void showError(EditText input , String s) {
            input.setError(s);
            input.requestFocus();
        }
            }

