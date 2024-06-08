package com.example.lab6.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.lab6.Entity.Egreso;
import com.example.lab6.Entity.Ingreso;
import com.example.lab6.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class GuardarActivity extends AppCompatActivity {

    private ConstraintLayout Guardar;
    //CONEXIÓN BD
    FirebaseFirestore db;
    // Guaradar:
    private TextView nuevo;
    private EditText titulo;
    private EditText descripcion;
    private EditText fecha;
    private EditText monto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.guardar);

        Intent intent = getIntent();
        String tipo = intent.getStringExtra("tipo");

        nuevo = findViewById(R.id.nuevo);
        nuevo.setText("Nuevo " + tipo);

        //BD

        db = FirebaseFirestore.getInstance();
        Guardar =  findViewById(R.id.Guardar);
        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmacionPopup(tipo);
            }
        });

    }

    private void ConfirmacionPopup(String tipo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Estas seguro de guardar los cambios?");

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                guardar(tipo);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void guardar(String tipo) {
        titulo = findViewById(R.id.titulo);
        String tituloString = titulo.getText().toString().trim();

        descripcion = findViewById(R.id.descripcion);
        String descripcionString = descripcion.getText().toString().trim();

        fecha = findViewById(R.id.fecha);
        String fechaString = fecha.getText().toString().trim();

        monto = findViewById(R.id.monto);
        String montoString = monto.getText().toString().trim();

        if (tituloString.isEmpty() || fechaString.isEmpty() || montoString.isEmpty()) {
            Toast.makeText(GuardarActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if ("ingreso".equals(tipo)){
            Ingreso ingreso = new Ingreso();
            ingreso.setTitulo(tituloString);
            ingreso.setDescripcion(descripcionString);
            ingreso.setFecha(fechaString);
            ingreso.setMonto(montoString);

            db.collection(tipo)
                    .add(ingreso)
                    .addOnSuccessListener(unused -> {
                        // Correcto
                        Toast.makeText(GuardarActivity.this, "Ingreso creado correctamente", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(GuardarActivity.this, IngresoActivity.class);
                        startActivity(intent);
                    })
                    .addOnFailureListener(e -> {
                        // Error
                        Toast.makeText(GuardarActivity.this, "No se creó el ingreso", Toast.LENGTH_SHORT).show();
                    });
        }else{
            Egreso ingreso = new Egreso();
            ingreso.setTitulo(tituloString);
            ingreso.setDescripcion(descripcionString);
            ingreso.setFecha(fechaString);
            ingreso.setMonto(montoString);
            db.collection(tipo)
                    .add(ingreso)
                    .addOnSuccessListener(unused -> {
                        // Correcto
                        Toast.makeText(GuardarActivity.this, "Egreso creado correctamente", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(GuardarActivity.this, EgresosActivity.class);
                        startActivity(intent);
                    })
                    .addOnFailureListener(e -> {
                        // Error
                        Toast.makeText(GuardarActivity.this, "No se creó el egreso", Toast.LENGTH_SHORT).show();
                    });
        }

    }


}
