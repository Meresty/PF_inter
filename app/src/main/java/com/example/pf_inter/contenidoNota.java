package com.example.pf_inter;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import POJO.nota;
import global.info;

public class contenidoNota extends AppCompatActivity {

    private EditText etNotaTitulo, etNotaContenido;
    private Button btnGuardar;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenido_nota);

        etNotaTitulo = findViewById(R.id.ETnotaTitulo);
        etNotaContenido = findViewById(R.id.ETnotaContenido);
        btnGuardar = findViewById(R.id.btnGuardar);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnGuardar.setOnClickListener(v -> {
            String titu = etNotaTitulo.getText().toString().trim();
            String conte = etNotaContenido.getText().toString().trim();

            if (titu.isEmpty() || conte.isEmpty()) {
                Toast.makeText(contenidoNota.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                nota unanota = new nota();
                unanota.setTitulo(titu);
                unanota.setContenido(conte);
                info.lista.add(unanota);

                Toast.makeText(contenidoNota.this, "Nota guardada", Toast.LENGTH_SHORT).show();
                etNotaTitulo.setText("");
                etNotaContenido.setText("");
            }
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
        } else if (item.getItemId() == R.id.ITCerrarSesion) {

            Intent modificarIntent = new Intent(this, Inicio.class);
            startActivity(modificarIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}