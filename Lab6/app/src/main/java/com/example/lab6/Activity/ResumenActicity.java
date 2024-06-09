package com.example.lab6.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab6.MainActivity;
import com.example.lab6.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class ResumenActicity extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseUser user;
    String correo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resumen);

        db = FirebaseFirestore.getInstance();

        //Obteneindo info del usuario
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Intent intent1 = new Intent(ResumenActicity.this, MainActivity.class);
            startActivity(intent1);
            finish();
        }else{
            correo = user.getEmail();
            Log.d("mensajeLogin", correo);
        }


    }

}
