package com.example.prueba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DomicilioActivity extends AppCompatActivity {

    EditText et_domicilio1, et_domicilio2;
    Button btn_domicilio;

    ArrayList<String> listaNombres, listaPrecios, listaTipos;
    ArrayList<String> datosDomicilio = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domicilio);

        et_domicilio1 = findViewById(R.id.et_domicilio1);
        et_domicilio2 = findViewById(R.id.et_domicilio2);
        btn_domicilio = findViewById(R.id.btn_domicilio);

        listaNombres = getIntent().getStringArrayListExtra("intentNombres");
        listaPrecios = getIntent().getStringArrayListExtra("intentPrecios");
        listaTipos = getIntent().getStringArrayListExtra("intentTipos");

        btn_domicilio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datosDomicilio.add(et_domicilio1.getText().toString());
                datosDomicilio.add(et_domicilio2.getText().toString());

                Intent intent = new Intent(DomicilioActivity.this, FacturarActivity.class);
                intent.putExtra("intentDatosDomicilio", datosDomicilio);
                intent.putExtra("intentNombres", listaNombres);
                intent.putExtra("intentPrecios", listaPrecios);
                intent.putExtra("intentTipos", listaTipos);
                intent.putExtra("intentDomicilio", true);
                startActivity(intent);
                finish();
            }
        });

    }
}