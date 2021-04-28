package com.example.fimebookandmarket2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.example.fimebookandmarket2.Model.Users;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    private DatabaseReference mDatabase;
    private String parentDbName = "Estudiante";
    private CheckBox chkBoxRememberMe;

    private TextView AdminLink, NotAdminLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

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
        final String email = InputEmail.getText().toString();
        final String password = InputPassword.getText().toString();

        //Condiciones para ver si no dejo campos vacios
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this,"Escribe tu correo...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this,"Escribe tu contraseña...", Toast.LENGTH_SHORT).show();
        } else {
                loadingBar.setTitle("Cargando cuenta");
                loadingBar.setMessage("Porfavor espere, mientras validamos sus datos.");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                if(chkBoxRememberMe.isChecked()){
                    Paper.book().write(Prevalent.UserEmailKey, email);
                    Paper.book().write(Prevalent.UserPasswordKey, password);
                }

            mDatabase.child(parentDbName).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {
                    for(final DataSnapshot snapshot1:snapshot.getChildren())
                    {
                        mDatabase.child(parentDbName).child(snapshot1.getKey()).addValueEventListener(new ValueEventListener()
                        {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot)
                            {
                                Users usuario = snapshot1.getValue(Users.class);
                                String correo=usuario.getEmail();
                                String password1=usuario.getPassword();
                                String tipousuario = usuario.getTipoUs();

                                if(email.equals(correo) && password.equals(password1))
                                {
                                    if(parentDbName.equals("Administrador")){
                                        Toast.makeText(LoginActivity.this, "Entrando a cuenta Admin...", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(LoginActivity.this, AdminCategoryActivity.class);
                                        startActivity(intent);
                                    } else if(parentDbName.equals("Estudiante")){
                                        Toast.makeText(LoginActivity.this, "Entrando a cuenta estudiante...", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                    }
                                } else {
                                    Toast.makeText(LoginActivity.this, "Verificar bien los datos o el tipo de usuario", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) { }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) { }
            });

                /*
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
                        if(mFirebaseUser != null) {
                            String currentUserID = mFirebaseUser.getUid(); //Do what you need to do with the id

                            if (dataSnapshot.child(parentDbName).child(currentUserID).exists()) {
                                Users usersData = dataSnapshot.child(parentDbName).child(currentUserID).getValue(Users.class);

                                if (usersData.getEmail().equals(email)) {
                                    if(usersData.getPassword().equals(password)){
                                        if(parentDbName.equals("Administrador")){
                                            Toast.makeText(LoginActivity.this, "Entrando a cuenta Admin...", Toast.LENGTH_SHORT).show();
                                            loadingBar.dismiss();

                                            Intent intent = new Intent(LoginActivity.this, AdminCategoryActivity.class);
                                            startActivity(intent);
                                        } else if(parentDbName.equals("Estudiante")){
                                            Toast.makeText(LoginActivity.this, "Entrando a cuenta Admin...", Toast.LENGTH_SHORT).show();
                                            loadingBar.dismiss();

                                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                            startActivity(intent);
                                        }
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Verificar bien los datos o el tipo de usuario", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    };*/

            /*mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull final Task<AuthResult> task) {

                    mDatabase.child(parentDbName).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(task.isSuccessful()){
                                String id = mAuth.getCurrentUser().getUid();

                                if(dataSnapshot.child(parentDbName).child(id).exists()){

                                    Users usersData = dataSnapshot.child(parentDbName).child(id).getValue(Users.class);

                                    if(usersData.getEmail().equals(email)){
                                        if(usersData.getPassword().equals(password)){
                                            if(parentDbName.equals("Administrador")){
                                                Toast.makeText(LoginActivity.this, "Entrando a cuenta Admin...", Toast.LENGTH_SHORT).show();
                                                loadingBar.dismiss();
                                                Intent intent = new Intent(LoginActivity.this, AdminCategoryActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent);
                                                finish();
                                            }
                                            if (parentDbName.equals("Estudiante")){
                                                Toast.makeText(LoginActivity.this, "Entrando...", Toast.LENGTH_SHORT).show();
                                                loadingBar.dismiss();
                                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Verificar bien los datos o el tipo de usuario", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });*/

                    /*if(task.isSuccessful()){
                        if(parentDbName.equals("Administrador")){
                            Toast.makeText(LoginActivity.this, "Entrando a cuenta Admin...", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Intent intent = new Intent(LoginActivity.this, AdminCategoryActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                        if (parentDbName.equals("Estudiante")){
                            Toast.makeText(LoginActivity.this, "Entrando...", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }*/
        }
    }
}

