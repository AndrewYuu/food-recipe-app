package com.self.varun.foodappsbu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText usertxt = (EditText)findViewById(R.id.usrTxt);
        EditText passtxt = (EditText)findViewById(R.id.pwdTxt);
        Button loginBtn = (Button) findViewById(R.id.btnLogin);
    }
}
