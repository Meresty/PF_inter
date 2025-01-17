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

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nota unanota = new nota();

                String titu = etNotaTitulo.getText().toString().trim();
                String conte = etNotaContenido.getText().toString().trim();

                unanota.setTitulo(etNotaTitulo.getText().toString());
                unanota.setContenido(etNotaContenido.getText().toString());
                info.lista.add(unanota);

                if (titu.isEmpty() || conte.isEmpty()) {
                    Toast.makeText(contenidoNota.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(contenidoNota.this, "Nota guardada", Toast.LENGTH_SHORT).show();


                    etNotaTitulo.setText("");
                    etNotaContenido.setText("");
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
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
            Toast.makeText(this, "Opción Agregar seleccionada", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
