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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import global.info;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Modificar extends AppCompatActivity {
    Button btnModificar, siguiente, anterior;
    EditText tituloModif, contenidoModif;
    int indice = 0;
    private final String URL_API = "http://10.0.2.2/Modificar_BD.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);


        btnModificar = findViewById(R.id.GuardarMod);
        tituloModif = findViewById(R.id.ETTituloModificar);
        contenidoModif = findViewById(R.id.ETnotaModificar);
        siguiente = findViewById(R.id.Siguiente);
        anterior = findViewById(R.id.Anterior);

        indice  = getIntent().getIntExtra("posic", 0);
        mostrarDatos(indice);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



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
        modificarNota();

    }

    private void modificarNota() {
        String nuevoTitulo = tituloModif.getText().toString().trim();
        String nuevoContenido = contenidoModif.getText().toString().trim();

        if (nuevoTitulo.isEmpty() || nuevoContenido.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        OkHttpClient client = new OkHttpClient();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("tituloAnterior", info.lista.get(indice).getTitulo());
            jsonObject.put("nuevoTitulo", nuevoTitulo);
            jsonObject.put("nuevoContenido", nuevoContenido);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(
                jsonObject.toString(),
                MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url("http://10.0.2.2/Modificar_BD.php") // Cambiar a la ruta correspondiente
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(Modificar.this, "Error al modificar: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    runOnUiThread(() -> {
                        // Actualizar localmente la lista después de una respuesta exitosa
                        info.lista.get(indice).setTitulo(nuevoTitulo);
                        info.lista.get(indice).setContenido(nuevoContenido);
                        Toast.makeText(Modificar.this, "Nota modificada exitosamente", Toast.LENGTH_SHORT).show();
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(Modificar.this, "Error en la respuesta: " + response.message(), Toast.LENGTH_SHORT).show());
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
            Toast.makeText(this, "Opción Modificar seleccionada", Toast.LENGTH_SHORT).show(); // Para depuración
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