package com.example.lab6.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.lab6.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class DetallesActivity extends AppCompatActivity {

    TextView texttitulo;
    TextView textdescripcion;
    TextView textfecha;
    TextView textmonto;

    ConstraintLayout buttonBorrarEq;
    ConstraintLayout buttonEditarEq;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.detalles);

        Intent intent = getIntent();
        String tipo = intent.getStringExtra("tipo");
        Log.d("tipoDETALLES", tipo);
        String titulo = intent.getStringExtra("titulo");
        String descripcion = intent.getStringExtra("descripcion");
        String fecha = intent.getStringExtra("fecha");
        String monto =  intent.getStringExtra("monto");

        db = FirebaseFirestore.getInstance();
        texttitulo = findViewById(R.id.titulo);
        textdescripcion = findViewById(R.id.descripcion);
        textfecha = findViewById(R.id.fecha);
        textmonto = findViewById(R.id.monto);

        texttitulo.setText(titulo);
        textdescripcion.setText(descripcion);
        textfecha.setText(fecha);
        textmonto.setText(monto);

        buttonBorrarEq =  findViewById(R.id.borrar);
        buttonBorrarEq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmacionPopup(tipo, titulo);
            }
        });

        buttonEditarEq =  findViewById(R.id.editar);
        buttonEditarEq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetallesActivity.this, EditarActivity.class);
                intent.putExtra("tipo", tipo);
                intent.putExtra("titulo", titulo);
                intent.putExtra("descripcion", descripcion);
                intent.putExtra("fecha", fecha);
                intent.putExtra("monto", monto);
                startActivity(intent);

            }
        });

    }

    private void ConfirmacionPopup(String tipo, String titulo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Estas seguro de eliminarlo?");


        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                borrarEquipoPorSKU( tipo, titulo);
                if ("ingreso".equals(tipo)){
                    Intent intent = new Intent(DetallesActivity.this, IngresoActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                }else{
                    Intent intent = new Intent(DetallesActivity.this, EgresosActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                }
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

    private void borrarEquipoPorSKU(String tipo, String titulo) {
        db.collection(tipo)
                .whereEqualTo("titulo", titulo)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            db.collection(tipo).document(document.getId())
                                    .delete()
                                    .addOnSuccessListener(unused -> {
                                        Toast.makeText(this, "Eliminado correctamente", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(this, "No se pudo eliminar", Toast.LENGTH_SHORT).show();
                                    });
                        }
                    } else {
                        Toast.makeText(this, "No se encontró el documento con el título", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al buscar", Toast.LENGTH_SHORT).show();
                });
    }


}
