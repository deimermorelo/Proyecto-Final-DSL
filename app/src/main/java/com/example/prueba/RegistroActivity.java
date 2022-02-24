package com.example.prueba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {

    private EditText et_registro1;
    private EditText et_registro2;
    private EditText et_registro3;
    private EditText et_registro4;
    private EditText et_registro5;
    private Button btn_registrar;

    private String nombre;
    private String apellido;
    private String cedula;
    private String usuario;
    private String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        et_registro1 = findViewById(R.id.et_registro1);
        et_registro2 = findViewById(R.id.et_registro2);
        et_registro3 = findViewById(R.id.et_registro3);
        et_registro4 = findViewById(R.id.et_registro4);
        et_registro5 = findViewById(R.id.et_registro5);
        btn_registrar = findViewById(R.id.btn_registrar);

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nombre = et_registro1.getText().toString().trim();
                apellido = et_registro2.getText().toString().trim();
                cedula = et_registro3.getText().toString().trim();
                usuario = et_registro4.getText().toString().trim();
                pass = et_registro5.getText().toString().trim();

                if (!nombre.equals("") && !apellido.equals("") && !cedula.equals("") && !usuario.equals("") && !pass.equals("")) {
                    Registrar();
                    finish();
                } else {
                    Toast.makeText(RegistroActivity.this, "Por favor, rellene los campos faltantes!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void Registrar() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();

        nombre = et_registro1.getText().toString().trim();
        apellido = et_registro2.getText().toString().trim();
        cedula = et_registro3.getText().toString().trim();
        usuario = et_registro4.getText().toString().trim();
        pass = et_registro5.getText().toString().trim();

        registro.put("nombre", nombre);
        registro.put("apellido", apellido);
        registro.put("cedula", cedula);
        registro.put("email", usuario);
        registro.put("password", pass);

        BaseDeDatos.insert("clientes", null, registro);

        Toast.makeText(RegistroActivity.this, "Usuario ha sido Registrado con Éxito!", Toast.LENGTH_SHORT).show();
        BaseDeDatos.close();
    }
}