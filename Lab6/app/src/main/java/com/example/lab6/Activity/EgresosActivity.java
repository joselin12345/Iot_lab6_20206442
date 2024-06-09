package com.example.lab6.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab6.Adapter.EgresoAdapter;
import com.example.lab6.Adapter.IngresoAdapter;
import com.example.lab6.Entity.Egreso;
import com.example.lab6.Entity.Ingreso;
import com.example.lab6.MainActivity;
import com.example.lab6.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class EgresosActivity extends AppCompatActivity {


    private RecyclerView recyclerView  ;
    private EgresoAdapter adapter;
    FirebaseFirestore db;
    List<Egreso> datalist = new ArrayList<>();
    private ConstraintLayout fab;
    FirebaseUser user;
    String correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_egreso);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //BD

        db = FirebaseFirestore.getInstance();

        //Obteneindo info del usuario
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Intent intent1 = new Intent(EgresosActivity.this, MainActivity.class);
            startActivity(intent1);
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
                Intent intent = new Intent(EgresosActivity.this, GuardarActivity.class);
                intent.putExtra("tipo", "egreso");
                startActivity(intent);
            }
        });

        db.collection("egreso")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Egreso ingreso = documentSnapshot.toObject(Egreso.class);
                            datalist.add(ingreso);
                        }

                        adapter = new EgresoAdapter(datalist);
                        recyclerView.setAdapter(adapter);
                    }
                });


    }
}
