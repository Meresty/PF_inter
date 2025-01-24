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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import POJO.nota;
import global.info;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class contenidoNota extends AppCompatActivity {

    private EditText etNotaTitulo, etNotaContenido;
    private Button btnGuardar;
    Toolbar toolbar;
    private final String URL_API = "http://10.0.2.2/Ingresar_BD.php";

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
                guardarNota();
            }
        });
    }

    private void guardarNota() {
        String titulo = etNotaTitulo.getText().toString().trim();
        String contenido = etNotaContenido.getText().toString().trim();

        if (titulo.isEmpty() || contenido.isEmpty()) {
            Toast.makeText(contenidoNota.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            registrarNota(titulo, contenido);
            nota unanota = new nota();
            unanota.setTitulo(titulo);
            unanota.setContenido(contenido);
            info.lista.add(unanota);

            Toast.makeText(contenidoNota.this, "Nota guardada", Toast.LENGTH_SHORT).show();
            etNotaTitulo.setText("");
            etNotaContenido.setText("");

        }
    }

    private void registrarNota(String titulo, String contenido) {
        OkHttpClient client = new OkHttpClient();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("titulo", titulo);
            jsonObject.put("contenido", contenido);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(
                jsonObject.toString(),
                MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(URL_API)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> etNotaContenido.setText("Error: " + e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseString = response.body().string();
                   // runOnUiThread(() -> etNotaContenido.setText(responseString));

                } else {
                    runOnUiThread(() -> etNotaContenido.setText("Error en la respuesta: " + response.message()));
                }
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