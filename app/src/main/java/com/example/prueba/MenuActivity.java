package com.example.prueba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonIOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.xml.transform.ErrorListener;

public class MenuActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton btn_menu_floating;
    String[] nombres, precios, tipos;
    int images[] = {R.drawable.bunuelos, R.drawable.carne_mechada, R.drawable.pechuga_pollo, R.drawable.pinchos_carne,
            R.drawable.rollos_primavera, R.drawable.salteado_pollo, R.drawable.sopa_camarones,
            R.drawable.sushi, R.drawable.tortilla, R.drawable.botella_de_agua,
            R.drawable.botella_de_cola, R.drawable.jugo_naranja, R.drawable.limonada};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        recyclerView = findViewById(R.id.recycler);
        btn_menu_floating = findViewById(R.id.btn_menu_floating);

        nombres = getResources().getStringArray(R.array.comidas);
        precios = getResources().getStringArray(R.array.precios);
        tipos = getResources().getStringArray(R.array.tipos);

        Adaptador adaptador = new Adaptador(this, nombres, precios, tipos, images);
        recyclerView.setAdapter(adaptador);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btn_menu_floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, CarritoActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences sharedpreferences = getSharedPreferences("prefUno", Context.MODE_PRIVATE);
        boolean primeraVez = sharedpreferences.getBoolean("PrimeraVez", true);

        if(primeraVez) {
            CargarProductos();
            Toast.makeText(MenuActivity.this, "Productos se han Cargado con Éxito!", Toast.LENGTH_SHORT).show();
        }
    }

    private void CargarProductos() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        for (int j = 0; j < nombres.length; j++) {
            ContentValues registro = new ContentValues();

            registro.put("nombre", nombres[j]);
            registro.put("tipo", tipos[j]);
            registro.put("precio", precios[j]);
            BaseDeDatos.insert("productos", null, registro);
        }

        BaseDeDatos.close();

        SharedPreferences sharedpreferences = getSharedPreferences("prefUno", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("PrimeraVez", false);
        editor.apply();

    }
}