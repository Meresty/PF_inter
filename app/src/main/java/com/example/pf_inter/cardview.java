package com.example.pf_inter;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import global.info;

public class cardview extends AppCompatActivity {
    Toolbar toolbar;
    TextView tituloCard,contenidoCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cardview);

        tituloCard = findViewById(R.id.Titulo_card);
        contenidoCard = findViewById(R.id.Contenido_card);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int posi;

        posi=getIntent().getIntExtra("posic", -1);
        tituloCard.setText(info.lista.get(posi).getTitulo());
        contenidoCard.setText(info.lista.get(posi).getContenido());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}