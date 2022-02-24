package com.example.prueba;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    //Clase nesesaria para la creación y administración de la Base de Datos.

    private Context context;
    private static final String DB_NOMBRE = "restaurante";
    private static final int DB_VERSION = 1;

    String query1 = "CREATE TABLE clientes(id_cliente INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, apellido TEXT, cedula TEXT, email TEXT, password TEXT ); ";
    String query2 = "CREATE TABLE productos(id_producto INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, tipo TEXT, precio REAL); ";
    String query3 = "CREATE TABLE pedidos(id_pedido INTEGER PRIMARY KEY AUTOINCREMENT, id_cliente INTEGER, tipo_entrega TEXT, FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente)); ";
    String query4 = "CREATE TABLE pedido_producto(id_pedido_producto INTEGER PRIMARY KEY AUTOINCREMENT, id_pedido INTEGER, id_producto INTEGER, FOREIGN KEY (id_pedido) REFERENCES pedidos(id_pedido), FOREIGN KEY (id_producto) REFERENCES productos(id_producto)); ";
    String query5 = "CREATE TABLE facturas(id_factura INTEGER PRIMARY KEY AUTOINCREMENT, id_pedido INTEGER, fecha DATE, total REAL, FOREIGN KEY (id_pedido) REFERENCES pedidos(id_pedido)); ";

    public AdminSQLiteOpenHelper(@Nullable Context context) {
        super(context, DB_NOMBRE, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase Database) {
        Database.execSQL(query1);
        Database.execSQL(query2);
        Database.execSQL(query3);
        Database.execSQL(query4);
        Database.execSQL(query5);
    }

    @Override
    public void onUpgrade(SQLiteDatabase Database, int i, int i1) {

    }
}
