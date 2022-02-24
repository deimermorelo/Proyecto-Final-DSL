package com.example.prueba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorCarrito extends RecyclerView.Adapter<AdaptadorCarrito.MyViewHolderCarrito> {
    //Clase nesesaria para la creación del RecyclerView de la Actividad Carrito

    Context context;
    ArrayList<String> nombresCarrito, preciosCarrito;

    public AdaptadorCarrito(Context cont, ArrayList arr1, ArrayList arr2) {
        context = cont;
        nombresCarrito = arr1;
        preciosCarrito = arr2;
    }

    @NonNull
    @Override
    public AdaptadorCarrito.MyViewHolderCarrito onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_carrito_view, parent, false);

        return new MyViewHolderCarrito(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorCarrito.MyViewHolderCarrito holder, int position) {
        holder.text1.setText(nombresCarrito.get(position));
        holder.text2.setText("$" + preciosCarrito.get(position));
    }

    @Override
    public int getItemCount() {
        if(nombresCarrito == null) return 0;
        return nombresCarrito.size();
    }

    public class MyViewHolderCarrito extends RecyclerView.ViewHolder {

        TextView text1, text2;
        CardView cardView;

        public MyViewHolderCarrito(@NonNull View itemView) {
            super(itemView);

            text1 = itemView.findViewById(R.id.titulo_card_carrito);
            text2 = itemView.findViewById(R.id.precio_card_carrito);
            cardView = itemView.findViewById(R.id.card_carrito);
        }
    }
}
