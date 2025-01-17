package com.example.pf_inter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Inicio extends AppCompatActivity {
    EditText user, pass;
    Button ingresar;
    SharedPreferences archivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        user = findViewById(R.id.ET_user);
        pass = findViewById(R.id.ET_pwd);
        ingresar = findViewById(R.id.btn_inicio);
        archivo = this.getSharedPreferences("sesion", Context.MODE_PRIVATE);

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingresar();
            }
        });
    }

    private void ingresar() {
        String baseUrl = "http://192.168.100.220/ingreso.php?";
        String usr;
        String pwd;

        // Obtener los valores de usuario y contraseña
        usr = user.getText().toString().trim();
        pwd = pass.getText().toString().trim();

        // Validar campos vacíos
        if (usr.isEmpty() || pwd.isEmpty()) {
            Toast.makeText(Inicio.this, "Por favor, ingrese usuario y contraseña.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Codificar los valores de usuario y contraseña
        try {
            usr = URLEncoder.encode(usr, StandardCharsets.UTF_8.toString());
            pwd = URLEncoder.encode(pwd, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            Toast.makeText(Inicio.this, "Error de codificación: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        // Construir la URL
        String url = baseUrl + "usr=" + usr + "&pass=" + pwd;
        Log.d("RequestURL", "URL: " + url);

        JsonObjectRequest pet = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Validar la respuesta del servidor
                            if (response.has("usr")) {
                                int userId = response.getInt("usr");
                                if (userId != -1) {
                                    // Usuario válido, guardar en SharedPreferences
                                    Intent i = new Intent(Inicio.this, MainActivity.class);
                                    SharedPreferences.Editor editor = archivo.edit();
                                    editor.putInt("id_usuario", userId);
                                    editor.apply();
                                    startActivity(i);
                                    finish();
                                } else {
                                    user.setText("");
                                    pass.setText("");
                                    Toast.makeText(Inicio.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(Inicio.this, "Respuesta no válida del servidor", Toast.LENGTH_SHORT).show();
                                Log.e("ServerResponse", "Respuesta inesperada: " + response.toString());
                            }
                        } catch (JSONException e) {
                            Toast.makeText(Inicio.this, "Error procesando respuesta JSON: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("JSONError", e.getMessage(), e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar errores de conexión o respuesta
                        String message = (error.getMessage() != null) ? error.getMessage() : "Error desconocido";
                        Toast.makeText(Inicio.this, "Error en la conexión: " + message, Toast.LENGTH_SHORT).show();
                        Log.e("VolleyError", message, error);
                    }
                });

        // Enviar la solicitud
        RequestQueue lanzarPeticion = Volley.newRequestQueue(this);
        lanzarPeticion.add(pet);
    }
}
