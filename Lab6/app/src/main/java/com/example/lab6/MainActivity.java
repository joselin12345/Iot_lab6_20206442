package com.example.lab6;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lab6.Activity.IngresoActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.checkerframework.checker.nullness.qual.NonNull;

public class MainActivity extends AppCompatActivity {

    EditText editCorreo, editContra;
    ConstraintLayout registrar, ingresar;
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Toast.makeText(MainActivity.this, "Sesión Iniciada",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, IngresoActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editCorreo = findViewById(R.id.correo);
        editContra = findViewById(R.id.contraseña);
        registrar = findViewById(R.id.registrarse);
        ingresar = findViewById(R.id.ingresar);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Registro.class);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String correo, contra;
                correo = String.valueOf(editCorreo.getText());
                contra = String.valueOf(editContra.getText());

                if(TextUtils.isEmpty(correo)){
                    Toast.makeText(MainActivity.this, "Por favor, ingrese el correo", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(contra)){
                    Toast.makeText(MainActivity.this, "Por favor, ingrese la contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(correo, contra)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Sesión Iniciada",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, IngresoActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(MainActivity.this, "No existe la cuenta",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

    }
}