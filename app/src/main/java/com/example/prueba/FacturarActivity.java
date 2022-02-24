package com.example.prueba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FacturarActivity extends AppCompatActivity {

    ArrayList<String> listaNombres, listaPrecios, listaTipos, datosDomicilio;
    TextView tv_factura2, tv_factura3;
    boolean esDomicilio;
    float precioTotal = 0;
    ArrayList<String> datosUsuario = MainActivity.datosUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facturar);
        getSupportActionBar().hide();

        tv_factura2 = findViewById(R.id.tv_factura2);
        tv_factura3 = findViewById(R.id.tv_factura3);

        datosDomicilio = getIntent().getStringArrayListExtra("intentDatosDomicilio");
        listaNombres = getIntent().getStringArrayListExtra("intentNombres");
        listaPrecios = getIntent().getStringArrayListExtra("intentPrecios");
        listaTipos = getIntent().getStringArrayListExtra("intentTipos");
        esDomicilio = getIntent().getBooleanExtra("intentDomicilio", false);

        ImprimirFactura();
        RegistrarPedido();
        RegistrarPedProd();
        RegistrarFactura();
        VaciarCarrito();
    }

    public void ImprimirFactura() {
        String texto = "";

        String textClienteDefault = "Cliente: Consumidor Final " + "\n" +
                "Dirección: " + "San Bernardo del Viento" + "\n" +
                "Teléfono: " + "3012345682" + "\n" +
                "Email: " + "consumidor@gmail.com" + "\n" +
                "Fecha: " + getDate() + "\n";

        if (esDomicilio) {
            String textClienteDomicilio = "Cliente: " + datosUsuario.get(1) + " " + datosUsuario.get(2) + "\n" +
                    "Cédula: " + datosUsuario.get(3) + "\n" +
                    "Email: " + datosUsuario.get(4) + "\n" +
                    "Dirección: " + datosDomicilio.get(0) + "\n" +
                    "Teléfono: " + datosDomicilio.get(1) + "\n" +
                    "Fecha: " + getDate() + "\n";

            tv_factura2.setText(textClienteDomicilio);
        } else {
            tv_factura2.setText(textClienteDefault);
        }

        for (int i = 0; i < listaNombres.size(); i++) {
            texto += listaNombres.get(i) + ": $" + listaPrecios.get(i) + "\n";
            precioTotal += Float.parseFloat(listaPrecios.get(i));
        }

        tv_factura3.setText(texto + "\n" + "Total: $" + precioTotal);
    }

    public String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());

        return currentDateandTime;
    }

    public void VaciarCarrito() {
        if (!listaNombres.isEmpty()) {

            listaNombres.clear();
            listaPrecios.clear();
            listaTipos.clear();

            Gson gson = new Gson();
            String jsonNombres = gson.toJson(listaNombres);
            String jsonPrecios = gson.toJson(listaPrecios);
            String jsonTipos = gson.toJson(listaTipos);

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(FacturarActivity.this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("listNames", jsonNombres);
            editor.putString("listPrices", jsonPrecios);
            editor.putString("listTypes", jsonTipos);
            editor.commit();
        }
    }

    public void RegistrarPedido() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();

        String tipo_entrega;

        if (esDomicilio) {
            tipo_entrega = "Domicilio";
        } else {
            tipo_entrega = "Inmediata";
        }

        registro.put("id_cliente", datosUsuario.get(0));
        registro.put("tipo_entrega", tipo_entrega);

        BaseDeDatos.insert("pedidos", null, registro);

        Toast.makeText(FacturarActivity.this, "Su Pedido fue Procesado con Éxito!", Toast.LENGTH_SHORT).show();

        BaseDeDatos.close();
    }

    public void RegistrarPedProd() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String id_pedido = "";
        Cursor filaPedido = BaseDeDatos.rawQuery("SELECT * FROM pedidos", null);
        //Busca el último pedido ingresado a la Base de Datos.
        if (filaPedido.moveToLast()) {
            id_pedido = filaPedido.getString(0);
        }

        String id_producto = "";

        for (int i = 0; i < listaNombres.size(); i++) {

            Cursor filaProducto = BaseDeDatos.rawQuery("SELECT * FROM productos WHERE nombre = '" + listaNombres.get(i) + "'", null);
            //Busca el id del producto para posteriormente ingresarlo a la Base de Datos.
            if (filaProducto.moveToFirst()) {
                id_producto = filaProducto.getString(0);
            }

            ContentValues registro = new ContentValues();

            registro.put("id_pedido", id_pedido);
            registro.put("id_producto", id_producto);

            BaseDeDatos.insert("pedido_producto", null, registro);
        }

        BaseDeDatos.close();
    }

    public void RegistrarFactura() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String id_pedido = "";
        Cursor filaPedido = BaseDeDatos.rawQuery("SELECT * FROM pedidos", null);
        //Busca el último pedido ingresado a la Base de Datos.
        if (filaPedido.moveToLast()) {
            id_pedido = filaPedido.getString(0);
        }


        ContentValues registro = new ContentValues();

        registro.put("id_pedido", id_pedido);
        registro.put("fecha", getDate());
        registro.put("total", precioTotal);

        BaseDeDatos.insert("facturas", null, registro);

        BaseDeDatos.close();
    }
}