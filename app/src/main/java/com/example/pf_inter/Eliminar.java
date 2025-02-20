package com.example.pf_inter;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import POJO.nota;
import adaptadores.adaptadorEliminar;
import global.info;

public class Eliminar extends AppCompatActivity {
    RecyclerView rvE;
    Toolbar toolbar;
    Button Eliminar_Boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_eliminar);

        rvE = findViewById(R.id.recyclerViewEliminar);
        Eliminar_Boton = findViewById(R.id.Boton_Elim);

        adaptadorEliminar adaptadorElim = new adaptadorEliminar(this);
        adaptadorElim.context = this;

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvE.setLayoutManager(linearLayoutManager);
        rvE.setAdapter(adaptadorElim);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.ITInicio) {

            Intent inicioIntent = new Intent(this, MainActivity.class);
            startActivity(inicioIntent);
            return true;
        } else if (item.getItemId() == R.id.ITEliminar) {

            Intent eliminarIntent = new Intent(this, Eliminar.class);
            startActivity(eliminarIntent);
            return true;
        } else if (item.getItemId() == R.id.ITAgregar) {

            Intent agregarIntent = new Intent(this, contenidoNota.class);
            startActivity(agregarIntent);
            return true;
        } else if (item.getItemId() == R.id.ITModificar) {

            Intent modificarIntent = new Intent(this, Modificar.class);
            startActivity(modificarIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Eliminar_Boton(View view) {
        for (int i = 0; i < info.listaEliminar.size(); i++)
        {
            nota unanota = info.listaEliminar.get(i);
            info.lista.remove(unanota);
        }
        info.listaEliminar.clear();
        rvE.getAdapter().notifyDataSetChanged();
    }
}