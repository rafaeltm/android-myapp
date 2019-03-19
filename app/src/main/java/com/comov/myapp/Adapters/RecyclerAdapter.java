package com.comov.myapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.comov.myapp.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    // Esta propiedad contiene los datos que se van a mostrar en la lista
    private List<String> datos;

    public RecyclerAdapter(List<String> lista) {
        this.datos = lista;
    }

    // Este metodo se llama por cada elemento de la lista, por cada holder, cuando se crea el recyclerview
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.text_view_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(textView);

        return myViewHolder;
    }

    // Este metodo asigna los datos a cada uno de los elementos
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(datos.get(position));
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    // Clase responsable de cada elemento de nuestra lista
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // En nuestra lista, las clases son un textview
        TextView textView;

        // Inicializamos nuestro textview
        public MyViewHolder(@NonNull TextView itemView) {
            super(itemView);
            textView = itemView;
        }
    }

}
