package com.example.prueba;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CarritoActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView tv_carrito_num;
    Button btn_carrito_continuar;

    ArrayList<String> listaNombres, listaPrecios, listaTipos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        recyclerView = findViewById(R.id.carrito_recycler);
        tv_carrito_num = findViewById(R.id.tv_carrito_num);
        btn_carrito_continuar = findViewById(R.id.btn_carrito_continuar);

        //Aquí se leen los datos obtenidos con la ayuda de SharedPreferences que luego serán enviadas al AdapterCarrito
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(CarritoActivity.this);
        String jsonNombres = preferences.getString("listNames", "");
        String jsonPrecios = preferences.getString("listPrices", "");
        String jsonTipos = preferences.getString("listTypes", "");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {}.getType();

        listaNombres = gson.fromJson(jsonNombres, type);
        listaPrecios = gson.fromJson(jsonPrecios, type);
        listaTipos = gson.fromJson(jsonTipos, type);

        float precioTotal = 0;

        if(listaNombres == null) {
            listaNombres = new ArrayList<>();
            listaPrecios = new ArrayList<>();
            listaTipos = new ArrayList<>();
        }

        for(int i = 0; i < listaPrecios.size(); i++) {
            precioTotal += Float.parseFloat(listaPrecios.get(i));
        }

        tv_carrito_num.setText("$" + precioTotal);

        AdaptadorCarrito adaptadorCarrito = new AdaptadorCarrito(this, listaNombres, listaPrecios);
        recyclerView.setAdapter(adaptadorCarrito);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Ventana de Alerta que nos dirigirá a una Actividad dependiendo de la Opción que se escoja.
        btn_carrito_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(CarritoActivity.this);
                alert.setTitle("Restaurante");
                alert.setMessage("Desea que su pedido sea de entrega: ");

                alert.setPositiveButton("Domicilio", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(CarritoActivity.this, DomicilioActivity.class);
                        intent.putExtra("intentNombres", listaNombres);
                        intent.putExtra("intentPrecios", listaPrecios);
                        intent.putExtra("intentTipos", listaTipos);
                        startActivity(intent);
                    }
                });
                alert.setNegativeButton("Inmediata", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(CarritoActivity.this, FacturarActivity.class);
                        intent.putExtra("intentNombres", listaNombres);
                        intent.putExtra("intentPrecios", listaPrecios);
                        intent.putExtra("intentTipos", listaTipos);
                        Toast.makeText(CarritoActivity.this, "Su Pedido fue Realizado con Éxito!", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                });
                alert.create().show();
            }
        });


    }
}