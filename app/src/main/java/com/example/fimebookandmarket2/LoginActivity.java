package com.example.fimebookandmarket2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText InputNumber, InputPassword;
    private Button LoginButton;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginButton = (Button) findViewById(R.id.login_btn);
        InputEmail = (EditText) findViewById(R.id.register_email_input);
        InputPassword = (EditText) findViewById(R.id.login_password_input);
        InputNumber = (EditText) findViewById(R.id.login_phone_number_input);
        InputPhone = (EditText) findViewById(R.id.register_phone_input);
        loadingBar = new ProgressDialog(this);

    }
}