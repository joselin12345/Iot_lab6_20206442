package com.example.lab6.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab6.Activity.DetallesActivity;
import com.example.lab6.Activity.ResumenActicity;
import com.example.lab6.Entity.Ingreso;
import com.example.lab6.R;

import java.util.List;

public class IngresoAdapter extends RecyclerView.Adapter<IngresoAdapter.ViewHolder> {

    private List<Ingreso> datalist;
    private AdapterView.OnItemClickListener mListener;

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mListener = listener;
    }

    public IngresoAdapter(List<Ingreso> datalist) {
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.iitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingreso ingreso = datalist.get(position);
        holder.titulo.setText(ingreso.getTitulo());
        holder.monto.setText(ingreso.getMonto());
        holder.fecha.setText(ingreso.getFecha());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetallesActivity.class);
                intent.putExtra("tipo", "ingreso");
                intent.putExtra("titulo", ingreso.getTitulo());
                intent.putExtra("descripcion", ingreso.getDescripcion());
                intent.putExtra("fecha", ingreso.getFecha());
                intent.putExtra("monto", ingreso.getMonto());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titulo;
        TextView monto;
        TextView fecha;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.titulo);
            monto = itemView.findViewById(R.id.monto);
            fecha = itemView.findViewById(R.id.fecha);
        }
    }

}
