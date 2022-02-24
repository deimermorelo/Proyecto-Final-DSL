package com.example.prueba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText et1;
    private EditText et2;
    private Button btn_login1;
    private Button btn_login2;

    private String usuario;
    private String pass;

    public static ArrayList<String> datosUsuario = new ArrayList<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = findViewById(R.id.et_login1);
        et2 = findViewById(R.id.et_login2);
        btn_login1 = findViewById(R.id.btn_login1);
        btn_login2 = findViewById(R.id.btn_login2);

        btn_login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usuario = et1.getText().toString().trim();
                pass = et2.getText().toString().trim();

                if (!usuario.equals("") && !pass.equals("")) {
                    IniciarSesion();
                } else {
                    Toast.makeText(MainActivity.this, "Por favor, rellene los campos faltantes!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });
    }

    public void IniciarSesion() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        usuario = et1.getText().toString().trim();
        pass = et2.getText().toString().trim();

        Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM clientes WHERE email = '" + usuario + "' AND password = '" + pass + "'", null);

        if(fila.moveToFirst()) {
            Toast.makeText(MainActivity.this, "Bienvenido!", Toast.LENGTH_LONG).show();
            datosUsuario.add(fila.getString(0));
            datosUsuario.add(fila.getString(1));
            datosUsuario.add(fila.getString(2));
            datosUsuario.add(fila.getString(3));
            datosUsuario.add(fila.getString(4));

            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(MainActivity.this, "Usuario o Contraseña Incorrecta!", Toast.LENGTH_LONG).show();
        }

        BaseDeDatos.close();
    }
}