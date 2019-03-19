package com.comov.myapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.comov.myapp.Adapters.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TableActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter adapter;

    private List<String> datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        datos = new ArrayList<>();

        // Instanciamos el recycler
        recyclerView = findViewById(R.id.reciclerView);

        // Se encarga de la representacion del layout
        // En este caso linear, como una lista normal
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Ahora tenemos que representar los items. Para ello hace falta un adapter.
        // Cada elemento que se representa, es un layout independiente.
        // ---> text_view_layout.xml
        adapter = new RecyclerAdapter(datos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    public void addItem(View v) {
        datos.add("Item: "+datos.size());
        adapter.notifyDataSetChanged();
    }

    public void removeItem(View v) {
        if(datos.size() > 0) {
            datos.remove(datos.get(datos.size()-1));
            adapter.notifyDataSetChanged();
        }
    }
}
