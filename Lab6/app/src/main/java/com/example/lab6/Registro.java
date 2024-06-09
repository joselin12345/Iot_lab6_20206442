package com.example.lab6;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.lab6.Activity.GuardarActivity;
import com.example.lab6.Activity.IngresoActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Registro extends AppCompatActivity {

    EditText editCorreo, editContra;
    ConstraintLayout registrar;
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Toast.makeText(Registro.this, "Sesión Iniciada",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Registro.this, IngresoActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

        editCorreo = findViewById(R.id.correo);
        editContra = findViewById(R.id.contraseña);
        registrar = findViewById(R.id.registrarse);

        mAuth = FirebaseAuth.getInstance();

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo, contra;
                correo = String.valueOf(editCorreo.getText());
                contra = String.valueOf(editContra.getText());

                if(TextUtils.isEmpty(correo)){
                    Toast.makeText(Registro.this, "Por favor, ingrese el correo", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(contra)){
                    Toast.makeText(Registro.this, "Por favor, ingrese la contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(correo, contra)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Registro.this, "Cuenta Creada",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Registro.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(Registro.this, "Error",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });


    }

}
