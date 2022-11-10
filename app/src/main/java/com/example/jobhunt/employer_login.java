package com.example.jobhunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

public class employer_login extends AppCompatActivity {
    TextView btn2;
    EditText inputPassword2, inputEmail2;
    Button btnToLogIn2;
    private FirebaseAuth mAuth;
    private ProgressDialog mLoadingBar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_login);
        btn2 = findViewById(R.id.textViewSignUpEmployer);
        inputEmail2 = findViewById(R.id.emailEmployerLoginEdtTxt);
        inputPassword2 = findViewById(R.id.editTextPasswordEmployerLogin);
        btnToLogIn2 = findViewById(R.id.EmployerLoginBtn);
        mAuth = FirebaseAuth.getInstance();
        mLoadingBar = new ProgressDialog(employer_login.this);
        btnToLogIn2.setOnClickListener((v) -> {
            checkCredentials();
        });
        btn2.setOnClickListener(v -> {
            Intent in = new Intent(this, sign_up_employee.class);
            startActivity(in);
        });
    }

    private void checkCredentials() {
        String email = inputEmail2.getText().toString();
        String password = inputPassword2.getText().toString();

        if (email.isEmpty() || !email.contains("@")) {
            showError(inputEmail2, "Email is not valid");
        } else if (password.isEmpty() || password.length() < 7) {
            showError(inputPassword2, "Password must be 7 characters");
        } else {
            mLoadingBar.setTitle("Login");
            mLoadingBar.setMessage("Please wait");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(employer_login.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                        mLoadingBar.dismiss();
                        Intent intent = new Intent(employer_login.this, showAllJobPostsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    } else {
                        Toast.makeText(employer_login.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

}