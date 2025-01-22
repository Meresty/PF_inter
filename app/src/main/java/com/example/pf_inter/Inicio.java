package com.example.pf_inter;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;


public class Inicio extends AppCompatActivity {
    private EditText etUser, etPwd;
    private Button btnLogin, btnRegistro;
    private TextView tvResultado;
    private final String URL_API = "http://10.0.2.2/login_BDInt.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inicio);

        etUser = findViewById(R.id.etUsuario);
        etPwd = findViewById(R.id.etContraseña);
        btnLogin = findViewById(R.id.btnLogin);
        tvResultado = findViewById(R.id.tvResultado);
        btnRegistro = findViewById(R.id.btnRegistro);


        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this, Registro.class);
                startActivity(intent);
            }
        });


        // 2. Configurar el botón de login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etUser.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();

                if (!user.isEmpty() && !pwd.isEmpty()) {
                    realizarLogin(user, pwd);
                } else {
                    tvResultado.setText("Por favor, completa todos los campos.");
                }
            }
        });
    }


    private void realizarLogin(String usuario, String pwd) {
        OkHttpClient client = new OkHttpClient();


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("usuario", usuario);
            jsonObject.put("contrasena", pwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 3. Crear RequestBody
        RequestBody body = RequestBody.create(
                jsonObject.toString(),
                MediaType.parse("application/json; charset=utf-8")
        );

        // 4. Crear Request
        Request request = new Request.Builder()
                .url(URL_API)
                .post(body)
                .build();

        // 5. Realizar la solicitud
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> tvResultado.setText("Error: " + e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseString = response.body().string();

                    try {
                        JSONObject responseJson = new JSONObject(responseString);
                        boolean success = responseJson.getBoolean("success");
                        String message = responseJson.getString("message");

                        runOnUiThread(() -> {
                            if (success) {
                                tvResultado.setText("Login exitoso: " + message);
                                Intent intent = new Intent(Inicio.this, MainActivity.class);
                                startActivity(intent);

                            } else {
                                tvResultado.setText("Error: " + message);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    runOnUiThread(() -> tvResultado.setText("Error: " + response.message()));
                }
            }

        });
    }


}