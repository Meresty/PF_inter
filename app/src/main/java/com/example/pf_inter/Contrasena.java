package com.example.pf_inter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Contrasena extends AppCompatActivity {
    EditText usuarioReg, contrasenaReg;
    Button btnActualizarContrasena;
    TextView tvResultadoReg;

    private final String URL_API = "http://10.0.2.2/modContra_BDInt.php";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contrasena);

        usuarioReg = findViewById(R.id.etUsuarioCon);
        contrasenaReg = findViewById(R.id.etContrasenaCon);
        btnActualizarContrasena = findViewById(R.id.btnGuardarCon);
        tvResultadoReg = findViewById(R.id.tvResultadoCon);

        btnActualizarContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = usuarioReg.getText().toString().trim();
                String nuevaContrasena = contrasenaReg.getText().toString().trim();

                if (!usuario.isEmpty() && !nuevaContrasena.isEmpty()) {
                    modificarContrasena(usuario, nuevaContrasena);
                } else {
                    tvResultadoReg.setText("Por favor, completa todos los campos.");
                }
            }
        });

        ImageView contrasenaInicio = findViewById(R.id.contrasena_inicio);
        contrasenaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Contrasena.this, Inicio.class);
                startActivity(intent);
            }
        });
    }

    private void modificarContrasena(String usuario, String nuevaContrasena) {
        OkHttpClient client = new OkHttpClient();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("usuario", usuario);
            jsonObject.put("nuevaContrasena", nuevaContrasena);
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
                runOnUiThread(() -> tvResultadoReg.setText("Error: " + e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseString = response.body().string();

                    runOnUiThread(() -> tvResultadoReg.setText(responseString));
                } else {
                    runOnUiThread(() -> tvResultadoReg.setText("Error en la respuesta: " + response.message()));
                }
            }
        });
    }
}