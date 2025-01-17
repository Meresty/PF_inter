package com.example.pf_inter;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import global.info;

public class Modificar extends AppCompatActivity {
    Button btnModificar, siguiente, anterior;
    EditText tituloModif, contenidoModif;
    int indice = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);

        btnModificar = findViewById(R.id.GuardarMod);
        tituloModif = findViewById(R.id.ETTituloModificar);
        contenidoModif = findViewById(R.id.ETnotaModificar);

        indice  = getIntent().getIntExtra("posic", 0);
        mostrarDatos(indice);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        indice  = getIntent().getIntExtra("posic", 0);
        mostrarDatos(indice);


        siguiente.setOnClickListener(v -> {
            if (indice < info.lista.size() - 1) {
                indice++;
                mostrarDatos(indice);
            } else {
                indice = 0;
                mostrarDatos(indice);
            }
        });


        anterior.setOnClickListener(v -> {
            if (indice > 0) {
                indice--;
                mostrarDatos(indice);
            } else {
                indice = info.lista.size()-1;
                mostrarDatos(indice);
            }
        });

        btnModificar.setOnClickListener(v -> guardarDatos(indice));


    }

    private void mostrarDatos(int indice) {
        tituloModif.setText(info.lista.get(indice).getTitulo());
        contenidoModif.setText(info.lista.get(indice).getContenido());
    }

    private void guardarDatos(int indice) {
        info.lista.get(indice).setTitulo(tituloModif.getText().toString());
        info.lista.get(indice).setContenido(contenidoModif.getText().toString());
        Toast.makeText(this, "Datos guardados", Toast.LENGTH_SHORT).show();
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

}