package com.marcosledesma.ejercicio02edu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.marcosledesma.ejercicio02edu.modelos.Nota;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final int CREAR_ACTIVITY = 1;
    private final int EDIT_NOTA = 2;

    // Variables para la Vista
    private LinearLayout contenedor;
    private Button btnAgregar;

    // Variables para los modelos
    private ArrayList<Nota> listaNotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contenedor = findViewById(R.id.contenedorMain);
        btnAgregar = findViewById(R.id.btnAgregarMain);
        listaNotas = new ArrayList<>();

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CrearActivity.class);
                startActivityForResult(intent, CREAR_ACTIVITY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CREAR_ACTIVITY && resultCode == RESULT_OK){
            if (data != null){
                final Nota nota = data.getExtras().getParcelable("NOTA");
                listaNotas.add(nota);
                final int posicion = listaNotas.size()-1;
                // TextView, Button, LinearLayout horizontal
                TextView txtTitulo = new TextView(this);
                txtTitulo.setText(nota.getTitulo());
                txtTitulo.setTextColor(Color.BLUE);
                txtTitulo.setTextSize(24);
                txtTitulo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Para ir a la actividad VER_NOTA
                        Bundle bundle = new Bundle();
                        bundle.putInt("POS", posicion);
                        bundle.putParcelable("NOTA", nota);
                        Intent intent = new Intent(MainActivity.this, EditNotaActivity.class);
                        intent.putExtras(bundle);
                        startActivityForResult(intent, EDIT_NOTA);

                    }
                });
                // Params Layout
                LinearLayout.LayoutParams paramsTXT = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 3);
                paramsTXT.setMargins(10, 10, 10, 10);
                txtTitulo.setLayoutParams(paramsTXT);

                // Button
                Button btnEliminar = new Button(this);
                btnEliminar.setText("ELIMINAR");
                btnEliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Para eliminar
                        listaNotas.remove(posicion);
                        repintarNotas();
                    }
                });
                // Params btn (peso 1)
                LinearLayout.LayoutParams paramsBTN = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                paramsBTN.setMargins(10, 10, 10, 10);
                btnEliminar.setLayoutParams(paramsBTN);

                // Layout horizontal
                LinearLayout contenedorNota = new LinearLayout(this);
                contenedorNota.setOrientation(LinearLayout.HORIZONTAL);

                // Agregar los elementos a la vista
                contenedorNota.addView(txtTitulo);
                contenedorNota.addView(btnEliminar);
                contenedor.addView(contenedorNota);
            }
        }

        if (requestCode == EDIT_NOTA && resultCode == RESULT_OK){
            if (data != null){
                int posicion = data.getExtras().getInt("POS");
                Nota nota = data.getExtras().getParcelable("NOTA");
                listaNotas.get(posicion).setTitulo(nota.getTitulo());
                listaNotas.get(posicion).setContenido(nota.getContenido());
                repintarNotas();
            }
        }
    }

    private void repintarNotas() {
        contenedor.removeAllViews();
        for (int i = 0; i < listaNotas.size(); i++) {
            final Nota nota = listaNotas.get(i);
            final int posicion = i;
            // TextView, Button, LinearLayout horizontal
            TextView txtTitulo = new TextView(this);
            txtTitulo.setText(nota.getTitulo());
            txtTitulo.setTextColor(Color.BLUE);
            txtTitulo.setTextSize(24);
            txtTitulo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Para ir a la actividad VER_NOTA
                    Bundle bundle = new Bundle();
                    bundle.putInt("POS", posicion);
                    bundle.putParcelable("NOTA", nota);
                    Intent intent = new Intent(MainActivity.this, EditNotaActivity.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, EDIT_NOTA);
                }
            });
            // Params Layout
            LinearLayout.LayoutParams paramsTXT = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 3);
            paramsTXT.setMargins(10, 10, 10, 10);
            txtTitulo.setLayoutParams(paramsTXT);

            // Button
            Button btnEliminar = new Button(this);
            btnEliminar.setText("ELIMINAR");
            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Para eliminar
                    listaNotas.remove(posicion);
                    repintarNotas();
                }
            });
            // Params btn (peso 1)
            LinearLayout.LayoutParams paramsBTN = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            paramsBTN.setMargins(10, 10, 10, 10);
            btnEliminar.setLayoutParams(paramsBTN);

            // Layout horizontal
            LinearLayout contenedorNota = new LinearLayout(this);
            contenedorNota.setOrientation(LinearLayout.HORIZONTAL);

            // Agregar los elementos a la vista
            contenedorNota.addView(txtTitulo);
            contenedorNota.addView(btnEliminar);
            contenedor.addView(contenedorNota);
        }
    }
}