package com.example.lab6.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lab6.MainActivity;
import com.example.lab6.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Navegador extends Fragment {

    private ImageButton buttonSitio, buttonTipo, buttonEquipo, buttonHistorial;

    FirebaseAuth mAuth;
    FirebaseUser user;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.navegador, container, false);

        buttonSitio = view.findViewById(R.id.imageButtonSitio);
        buttonTipo = view.findViewById(R.id.imageButtonTipo);
        buttonEquipo = view.findViewById(R.id.imageButtonEquipo);
        buttonHistorial = view.findViewById(R.id.imageButtonHistorial);

        configurarListeners();

        return view;
    }

    private void configurarListeners() {
        buttonSitio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOptionClick(true, false, false, false);
            }
        });

        buttonTipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOptionClick(false, true, false, false);
            }
        });

        buttonEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOptionClick(false, false, true, false);
            }
        });

        buttonHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOptionClick(false, false, false, true);
            }
        });

    }

    private void handleOptionClick(boolean isSitio, boolean isTipo, boolean isEquipo, boolean isHistorial) {

        if (isSitio) {
            buttonSitio.setScaleX(0.8f);
            buttonSitio.setScaleY(0.8f);
            Intent intent = new Intent(getActivity(), IngresoActivity.class);
            startActivity(intent);
        }

        if (isTipo) {
            buttonTipo.setScaleX(0.8f);
            buttonTipo.setScaleY(0.8f);
            Intent intent = new Intent(getActivity(), EgresosActivity.class);
            startActivity(intent);
        }

        if (isEquipo) {
            buttonEquipo.setScaleX(0.8f);
            buttonEquipo.setScaleY(0.8f);
            Intent intent = new Intent(getActivity(), ResumenActicity.class);
            startActivity(intent);
        }

        if (isHistorial) {
            buttonHistorial.setScaleX(0.8f);
            buttonHistorial.setScaleY(0.8f);

            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }


    }

}
