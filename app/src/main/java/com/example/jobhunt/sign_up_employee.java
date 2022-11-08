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

public class sign_up_employee extends AppCompatActivity {

    TextView btn;
    private EditText inputUsername,inputPassword,inputEmail,inputConfirmpassword;
    Button btnToLoginIn;
    private FirebaseAuth mAuth;
    private ProgressDialog mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_employee);
        btn=findViewById(R.id.signUpBtn);
        inputUsername=findViewById(R.id.editTextPersonName2);
        inputEmail=findViewById(R.id.editTextEmailAddress2);
        inputPassword=findViewById(R.id.editTextPassword2);
        inputConfirmpassword=findViewById(R.id.editTextConfirmPassword2);
        mAuth= FirebaseAuth.getInstance();
        mLoadingBar=new ProgressDialog(sign_up_employee.this);

        btnToLoginIn=findViewById(R.id.signUpBtn);
        btnToLoginIn.setOnClickListener((v)->{checkCredentials();});
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView btnToLoginIn=findViewById(R.id.textViewLogin);
        btnToLoginIn.setOnClickListener(v->{
            Intent in1 =new Intent(this,employee_login.class);
            startActivity(in1);
        });
    }
    private void checkCredentials(){
        String username=inputUsername.getText().toString();
        String email=inputEmail.getText().toString();
        String password=inputPassword.getText().toString();
        String confirmpassword=inputConfirmpassword.getText().toString();

        if(username.isEmpty() || username.length()<5)
        {
            showError(inputUsername,"Your username is not valid,should be more than 5 letters");
        }
        else if(email.isEmpty()||!email.contains("@"))
        {
            showError(inputEmail,"Email is not valid");
        }
        else if(password.isEmpty()||password.length()<7)
        {
            showError(inputPassword,"Password must be 7 characters");
        }
        else if(confirmpassword.isEmpty()||!confirmpassword.equals(password))
        {
            showError(inputConfirmpassword,"password did not match");
        }
        else
        {
            mLoadingBar.setTitle("Registration");
            mLoadingBar.setMessage("Please wait");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(sign_up_employee.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(sign_up_employee.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(sign_up_employee.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
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