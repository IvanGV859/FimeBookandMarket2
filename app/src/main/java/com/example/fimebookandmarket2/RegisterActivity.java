package com.example.fimebookandmarket2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText InputName, InputEmail, InputPassword, InputConfPassword, InputPhone;
    Spinner opciones;
    String TipoUs;
    private ProgressDialog loadingBar;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        opciones = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.TipoUs, android.R.layout.simple_spinner_item);
        opciones.setAdapter(adapter);

        InputName = (EditText) findViewById(R.id.register_username_input);
        InputEmail = (EditText) findViewById(R.id.register_email_input);
        InputPassword = (EditText) findViewById(R.id.register_password_input);
        InputConfPassword = (EditText) findViewById(R.id.register_confpassword_input);
        InputPhone = (EditText) findViewById(R.id.register_phone_input);
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
        TipoUs = opciones.getSelectedItem().toString();

        if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confpassword.isEmpty() && !phone.isEmpty()) {
            if (password.length() >= 6) {
                if(name.length() >=5){
                    if(password.equals(confpassword)){
                        loadingBar.setTitle("Creando cuenta");
                        loadingBar.setMessage("Porfavor espere, mientras validamos sus credenciales.");
                        loadingBar.setCanceledOnTouchOutside(false);
                        loadingBar.show();

                        ValidatephoneNumber(name, email, password, confpassword, phone, TipoUs);
                    } else {
                        Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "El usuario debe tener al menos 5 caracteres", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RegisterActivity.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(RegisterActivity.this, "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void ValidatephoneNumber(final String name, final String email, final String password, final String confpassword, final String phone, final String tipoUs) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("id", mAuth.getUid());
                    userdataMap.put("name", name);
                    userdataMap.put("email", email);
                    userdataMap.put("password", password);
                    userdataMap.put("confpass", confpassword);
                    userdataMap.put("phone", phone);
                    userdataMap.put("tipous", TipoUs);

                    String id = mAuth.getCurrentUser().getUid();

                    mDatabase.child(TipoUs).child(id).setValue(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            //Verificar si registra los datos correctamente en la base de datos
                            if(task2.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "Cuenta creada correctamente", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            else {
                                loadingBar.dismiss();
                                Toast.makeText(RegisterActivity.this, "No se registraron los datos correctamente", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    loadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, "No se pudo registrar el usuario.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}