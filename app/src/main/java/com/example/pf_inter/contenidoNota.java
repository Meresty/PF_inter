package com.example.pf_inter;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class contenidoNota extends AppCompatActivity {

    private EditText etNotaTitulo, etNotaContenido;
    private Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenido_nota);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etNotaTitulo = findViewById(R.id.ETnotaTitulo);
        etNotaContenido = findViewById(R.id.EtnotaContenido);
        btnGuardar = findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = etNotaTitulo.getText().toString().trim();
                String contenido = etNotaContenido.getText().toString().trim();

                if (titulo.isEmpty() || contenido.isEmpty()) {
                    Toast.makeText(contenidoNota.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(contenidoNota.this, "Nota guardada", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "Opción Eliminar seleccionada", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.ITAgregar) {
            Toast.makeText(this, "Opción Agregar seleccionada", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
