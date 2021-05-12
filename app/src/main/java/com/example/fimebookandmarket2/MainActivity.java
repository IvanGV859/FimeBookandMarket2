package com.example.fimebookandmarket2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fimebookandmarket2.Model.Users;
import com.example.fimebookandmarket2.Prevalent.Prevalent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    private DatabaseReference mDatabase;
    private String parentDbName = "Estudiante";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //SystemClock.sleep(2000);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Paper.init(this);
        loadingBar = new ProgressDialog(this);

        String UserEmailKey = Paper.book().read(Prevalent.UserEmailKey);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);

        if(UserEmailKey != "" && UserPasswordKey != ""){
            if(!TextUtils.isEmpty(UserEmailKey) && !TextUtils.isEmpty(UserPasswordKey)){
                /*mAuth.signInWithEmailAndPassword(UserEmailKey, UserPasswordKey).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Entrando...", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(MainActivity.this, "Verificar bien los datos", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }
                });*/
                AllowAccess(UserEmailKey, UserPasswordKey);
                loadingBar.setTitle("Iniciado sesion");
                loadingBar.setMessage("Porfavor espere...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
            }
        }

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.main_join_now_btn:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.main_login_btn:
                Intent intent1 = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent1);
                break;
        }
    }


    private void AllowAccess(final String email, final String password){
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
                            String correo = usuario.getEmail();
                            String password1=usuario.getPassword();
                            //String tipousuario = usuario.getTipoUs();

                            //parentDbName = Prevalent.currentOnlineUser.getTipoUs();

                            if(email.equals(correo) && password.equals(password1))
                            {
                                if(parentDbName.equals("Administrador")){
                                    Toast.makeText(MainActivity.this, "Entrando a cuenta Admin...", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();

                                    Intent intent = new Intent(MainActivity.this, AdminCategoryActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else if(parentDbName.equals("Estudiante")){
                                    Toast.makeText(MainActivity.this, "Entrando a cuenta estudiante...", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();

                                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                    Prevalent.currentOnlineUser = usuario;
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Verificar bien los datos o el tipo de usuario", Toast.LENGTH_SHORT).show();
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
    }
}
