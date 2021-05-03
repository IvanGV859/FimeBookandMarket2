package com.example.fimebookandmarket2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText InputName, InputEmail, InputPassword, InputConfPassword, InputPhone;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        InputName = findViewById(R.id.register_username_input);
        InputEmail = findViewById(R.id.register_email_input);
        InputPassword = findViewById(R.id.register_password_input);
        InputConfPassword = findViewById(R.id.register_confpassword_input);
        InputPhone = findViewById(R.id.register_phone_input);
        loadingBar = new ProgressDialog(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.register_btn:
                CreateAccount();
                break;
        }
    }

    private void CreateAccount() {
        String name = InputName.getText().toString();
        String email = InputEmail.getText().toString();
        String password = InputPassword.getText().toString();
        String confpassword = InputConfPassword.getText().toString();
        String phone = InputPhone.getText().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Escribe un nombre...", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Escribe un correo electronico...", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Escriba una contraseña...", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(confpassword)){
            Toast.makeText(this, "Porfavor confirme su contraseña...", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Agrege un numero de telefono.", Toast.LENGTH_SHORT).show();
        } else if(password.length() < 6) {
            Toast.makeText(this, "La contraseña debe tener más de 6 caracteres.", Toast.LENGTH_SHORT).show();
        } else if(name.length() < 5){
            Toast.makeText(this, "El nombre de usuario debe tener mas de 5 caracteres.", Toast.LENGTH_SHORT).show();
        } else if(!password.equals(confpassword)){
            Toast.makeText(this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Creando cuenta");
            loadingBar.setMessage("Porfavor espere, mientras validamos sus credenciales.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidatephoneNumber(name, email, password, phone);
        }
    }

    private void ValidatephoneNumber(final String name, final String email, final String password, final String phone) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users").child(phone).exists())){

                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("name", name);
                    userdataMap.put("email", email);
                    userdataMap.put("password", password);
                    userdataMap.put("phone", phone);

                    RootRef.child("users").child(phone).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "Cuenta creada correctamente", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    } else {
                                        loadingBar.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Error de conexión: Porfavor intente en un rato...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(RegisterActivity.this, "El telefono " + phone + " ya existe.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Intente con otro numero de telefono.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}