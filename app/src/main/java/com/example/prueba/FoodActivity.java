package com.example.prueba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FoodActivity extends AppCompatActivity {

    private TextView tv_nombre, tv_precio;
    private ImageView iv_food;
    private String nombre, precio, tipo;
    private Button btn_ver_carrito, btn_agg_carrito;
    private int image;
    private ArrayList<String> nombresCarrito, preciosCarrito, tiposCarrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        tv_nombre = findViewById(R.id.tv_nombre);
        tv_precio = findViewById(R.id.tv_precio);
        iv_food = findViewById(R.id.image_food);
        btn_agg_carrito = findViewById(R.id.btn_agg_carrito);
        btn_ver_carrito = findViewById(R.id.btn_ver_carrito);

        if(getIntent().hasExtra("img")) {
            nombre = getIntent().getStringExtra("data1");
            precio = getIntent().getStringExtra("data2");
            tipo = getIntent().getStringExtra("data3");
            image = getIntent().getIntExtra("img", 1);
        }

        tv_nombre.setText(nombre);
        tv_precio.setText("$" + precio);
        iv_food.setImageResource(image);

        //Se rescatan los datos del Carrito de compras (En caso de que existan) para posteriormente agregar otro producto
        //con la ayuda del botón btn_agg_carrito.
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(FoodActivity.this);
        String jsonNombres = preferences.getString("listNames", "");
        String jsonPrecios = preferences.getString("listPrices", "");
        String jsonTipos = preferences.getString("listTypes", "");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {}.getType();

        nombresCarrito = gson.fromJson(jsonNombres, type);
        preciosCarrito = gson.fromJson(jsonPrecios, type);
        tiposCarrito = gson.fromJson(jsonTipos, type);

        if(nombresCarrito == null) {
            nombresCarrito = new ArrayList<>();
            preciosCarrito = new ArrayList<>();
            tiposCarrito = new ArrayList<>();
        }

        btn_ver_carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodActivity.this, CarritoActivity.class);
                startActivity(intent);
            }
        });

        btn_agg_carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Con la ayuda de SharedPreferences se insertan los valores que posteriormente serán leídas por la Actividad Carrito
                nombresCarrito.add(nombre);
                preciosCarrito.add(precio);
                tiposCarrito.add(tipo);

                Gson gson = new Gson();
                String jsonNombres = gson.toJson(nombresCarrito);
                String jsonPrecios = gson.toJson(preciosCarrito);
                String jsonTipos = gson.toJson(tiposCarrito);

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(FoodActivity.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("listNames", jsonNombres);
                editor.putString("listPrices", jsonPrecios);
                editor.putString("listTypes", jsonTipos);
                editor.commit();

                Toast.makeText(FoodActivity.this, "Se ha agregado al carrito!", Toast.LENGTH_SHORT).show();

            }
        });

    }
}