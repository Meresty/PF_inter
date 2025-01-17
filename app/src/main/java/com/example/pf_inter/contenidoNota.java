package com.example.pf_inter;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class contenidoNota extends AppCompatActivity {

    private EditText etNotaTitulo, etNotaContenido;
    private Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenido_nota);


        etNotaTitulo = findViewById(R.id.ETnotaTitulo);
        etNotaContenido = findViewById(R.id.ETnotaContenido);
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
}
