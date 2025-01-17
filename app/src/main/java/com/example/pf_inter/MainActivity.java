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

        if (item.getItemId() == R.id.ITEliminar) {

            Intent eliminarIntent = new Intent(this, Eliminar.class);
            startActivity(eliminarIntent);
            return true;
        } else if (item.getItemId() == R.id.ITAgregar) {

            Intent agregarIntent = new Intent(this, contenidoNota.class);
            startActivity(agregarIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
