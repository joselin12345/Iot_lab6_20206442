package com.example.lab6.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab6.Adapter.IngresoAdapter;
import com.example.lab6.Entity.Ingreso;
import com.example.lab6.MainActivity;
import com.example.lab6.R;
import com.example.lab6.Registro;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IngresoActivity extends AppCompatActivity {
    
    private RecyclerView recyclerView  ;
    private IngresoAdapter adapter;
    FirebaseFirestore db;
    FirebaseUser user;
    List<Ingreso> datalist = new ArrayList<>();
    private ConstraintLayout fab;
    String correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //BD

        db = FirebaseFirestore.getInstance();
        //Obteneindo info del usuario
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(IngresoActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            correo = user.getEmail();
            Log.d("mensajeLogin", correo);

        }


        datalist = new ArrayList<>();

        fab = findViewById(R.id.guardar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // enviar al activity de guardar
                Intent intent = new Intent(IngresoActivity.this, GuardarActivity.class);
                intent.putExtra("tipo", "ingreso");
                startActivity(intent);
            }
        });

        db.collection("ingreso")
                .whereEqualTo("correoUsuario", correo)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Ingreso ingreso = documentSnapshot.toObject(Ingreso.class);
                            datalist.add(ingreso);
                        }

                        adapter = new IngresoAdapter(datalist);
                        recyclerView.setAdapter(adapter);
                    }
                });




    }


}
