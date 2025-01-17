package com.example.pf_inter;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ITEliminar:

                Intent eliminarIntent = new Intent(this, EliminarActivity.class);
                startActivity(eliminarIntent);
                return true;

            case R.id.ITAgregar:

                Intent agregarIntent = new Intent(this, contenidoNota.class);
                startActivity(agregarIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
