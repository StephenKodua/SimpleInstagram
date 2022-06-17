package com.example.loginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "LoginActivity";
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //getCurrentUser() retrieves
        // the currently logged in ParseUser with a valid session
        if (ParseUser.getCurrentUser() != null){
            goMainActivity();
        }

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when clicked
                Log.i(TAG, "onClick login button");
                //Store the username and password into a string variable
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                loginUser(username, password);
            }
        });
    }

    private void loginUser (String username, String password){
        Log.i(TAG, "onClick login button" + username);
        //navigate to main activity is login is successful

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                //if there is an exception (means login is unsuccessful)
                if (e != null){
                    Log.e(TAG, "Issue with login", e);
                    return;
                }
                //go to main activity if successful
                goMainActivity();
            }
        });
    }

    private void goMainActivity(){
        //create an intention (intent) to start main activity
        Intent i = new Intent (this, MainActivity.class);
        startActivity(i);
        finish(); //finishing login activity to clear username and password
    }
}