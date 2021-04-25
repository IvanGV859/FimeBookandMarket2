package com.example.fimebookandmarket2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.example.fimebookandmarket2.Model.Users;
import com.rey.material.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fimebookandmarket2.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import io.paperdb.Paper;


public class LoginActivity extends AppCompatActivity {

    private EditText InputEmail, InputPassword;
    private Button LoginButton;
    private ProgressDialog loadingBar;

    FirebaseAuth mAuth;
    private String parentDbName = "Estudiante";
    private CheckBox chkBoxRememberMe;

    private TextView AdminLink, NotAdminLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        LoginButton = (Button) findViewById(R.id.login_btn);
        InputPassword = (EditText) findViewById(R.id.login_password_input);
        InputEmail = (EditText) findViewById(R.id.login_email_input);
        loadingBar = new ProgressDialog(this);

        AdminLink = (TextView) findViewById(R.id.admin_panel_link);
        NotAdminLink = (TextView) findViewById(R.id.not_admin_panel_link);

        chkBoxRememberMe = (CheckBox) findViewById(R.id.remember_me_chkb);
        Paper.init(this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });

        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginButton.setText("Inicio sesion Admin");
                AdminLink.setVisibility(View.INVISIBLE);
                NotAdminLink.setVisibility(View.VISIBLE);
                parentDbName = "Administrador";
            }
        });

        NotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginButton.setText("Inicio sesion Estudiante");
                AdminLink.setVisibility(View.VISIBLE);
                NotAdminLink.setVisibility(View.INVISIBLE);
                parentDbName = "Estudiante";
            }
        });
    }

    private void LoginUser() {
        //Obtener los datos que escribio el usuario
        String email = InputEmail.getText().toString();
        String password = InputPassword.getText().toString();

        //Condiciones para ver si no dejo campos vacios
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this,"Escribe tu correo...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this,"Escribe tu contraseña...", Toast.LENGTH_SHORT).show();
        } else {
                loadingBar.setTitle("Cargando cuenta");
                loadingBar.setMessage("Porfavor espere, mientras validamos sus credenciales.");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                if(chkBoxRememberMe.isChecked()){
                    Paper.book().write(Prevalent.UserEmailKey, email);
                    Paper.book().write(Prevalent.UserPasswordKey, password);
                }

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        if(parentDbName.equals("Administrador")){
                            Toast.makeText(LoginActivity.this, "Entrando a cuenta Admin...", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Intent intent = new Intent(LoginActivity.this, AdminCategoryActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }  else if(parentDbName.equals("Estudiante")){
                            Toast.makeText(LoginActivity.this, "Entrando...", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    }else {
                        Toast.makeText(LoginActivity.this, "Verificar bien los datos o el tipo de usuario", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            });
        }
    }
}

