package com.example.lab6.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lab6.MainActivity;
import com.example.lab6.R;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditarActivity extends AppCompatActivity {

    private ConstraintLayout Guardar;
    FirebaseFirestore db;
    FirebaseUser user;
    String correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar);
        db = FirebaseFirestore.getInstance();

        //Obteneindo info del usuario
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Intent intent1 = new Intent(EditarActivity.this, MainActivity.class);
            startActivity(intent1);
            finish();
        }else{
            correo = user.getEmail();
            Log.d("mensajeLogin", correo);
        }

        Intent intent = getIntent();
        String tipo = intent.getStringExtra("tipo");
        String titulo = intent.getStringExtra("titulo");
        String descripcion = intent.getStringExtra("descripcion");
        String fecha = intent.getStringExtra("fecha");
        String monto =  intent.getStringExtra("monto");

        // Referencias a los EditTexts
        TextView tituloText= findViewById(R.id.titulo);
        EditText montoEditText = findViewById(R.id.monto);
        EditText descripcionEditText = findViewById(R.id.descripcion);

        tituloText.setText(titulo);
        montoEditText.setText(monto);
        descripcionEditText.setText(descripcion);

        Guardar =  findViewById(R.id.Guardar);
        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nuevoMonto = montoEditText.getText().toString();
                String nuevaDescripcion = descripcionEditText.getText().toString();

                ConfirmacionPopup(tipo, titulo,nuevoMonto, nuevaDescripcion);
            }
        });

    }

    private void ConfirmacionPopup(String tipo, String titulo, String nuevoMonto, String nuevaDescripcion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Estas seguro de guardar los cambios?");


        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                editarEquipo(tipo, titulo, nuevoMonto, nuevaDescripcion);
                if ("ingreso".equals(tipo)){
                    Intent intent = new Intent(EditarActivity.this, IngresoActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                }else{
                    Intent intent = new Intent(EditarActivity.this, EgresosActivity.class);
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

    private void editarEquipo(String tipo, String titulo, String nuevoMonto, String nuevaDescripcion) {
        db.collection(tipo)
                .whereEqualTo("titulo", titulo)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (!task.getResult().isEmpty()) {
                            DocumentReference equipoRef = task.getResult().getDocuments().get(0).getReference();

                            equipoRef.update("monto", nuevoMonto,
                                            "descripcion", nuevaDescripcion)
                                    .addOnSuccessListener(aVoid -> {
                                        finish();
                                    })
                                    .addOnFailureListener(e -> {
                                    });
                        } else {
                            // No se encontró ningún documento con el `sku` especificado
                            Log.e("EquipoEditarActivity", "El documento no existe.");
                        }
                    } else {
                        Log.e("EquipoEditarActivity", "Error al obtener el documento", task.getException());
                    }
                });
    }

}
