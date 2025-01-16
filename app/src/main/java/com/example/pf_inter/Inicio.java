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
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class Inicio extends AppCompatActivity {
    EditText user, pass;
    Button ingresar;
    Context context;
    SharedPreferences archivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
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
        // Validar que los campos no estén vacíos
        if (user.getText().toString().trim().isEmpty() || pass.getText().toString().trim().isEmpty()) {
            Toast.makeText(Inicio.this, "Por favor, completa ambos campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://192.168.100.220/bd/ingreso.php?usr=";
        url += user.getText().toString().trim();
        url += "&pass=";
        url += pass.getText().toString().trim();

        JsonObjectRequest pet = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getInt("usr") != -1) {
                        Intent i = new Intent(Inicio.this, MainActivity.class);
                        SharedPreferences.Editor editor = archivo.edit();
                        editor.putInt("id_usuario", response.getInt("usr"));
                        editor.commit();
                        startActivity(i);
                        finish();
                    } else {
                        user.setText("");
                        pass.setText("");
                        Toast.makeText(Inicio.this, response.getString("mensaje"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(Inicio.this, volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("yo", volleyError.getMessage());
            }
        });

        RequestQueue lanzarPeticion = Volley.newRequestQueue(this);
        lanzarPeticion.add(pet);
    }


}